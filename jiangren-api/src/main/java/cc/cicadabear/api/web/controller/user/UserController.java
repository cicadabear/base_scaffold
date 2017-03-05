package cc.cicadabear.api.web.controller.user;

import cc.cicadabear.api.web.context.SecurityHolder;
import cc.cicadabear.api.web.validator.user.ChangePasswordFormDtoValidator;
import cc.cicadabear.api.web.validator.user.ForgetPasswordFormDtoValidator;
import cc.cicadabear.api.web.validator.user.RegisterFormDtoValidator;
import cc.cicadabear.business.common.ResultVo;
import cc.cicadabear.business.common.utils.MessageUtils;
import cc.cicadabear.business.domain.dto.user.ChangePasswordFormDto;
import cc.cicadabear.business.domain.dto.user.ForgetPasswordFormDto;
import cc.cicadabear.business.domain.dto.user.RegisterFormDto;
import cc.cicadabear.business.domain.entity.enums.ValiCodeTypeEnum;
import cc.cicadabear.business.service.ValiCodeService;
import cc.cicadabear.profile.domain.user.User;
import cc.cicadabear.profile.infrastructure.PasswordHelper;
import cc.cicadabear.profile.service.UserService;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;

/**
 * Created by Jack on 2/20/17.
 */
@RestController("cc.cicadabear.api.web.controller.user.UserController")
@RequestMapping("/user")
@ResponseBody
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private ServletContext context;

    @Autowired
    private RegisterFormDtoValidator registerFormDtoValidator;

    @Autowired
    private ChangePasswordFormDtoValidator changePasswordFormDtoValidator;

    @Autowired
    private ForgetPasswordFormDtoValidator forgetPasswordFormDtoValidator;

    @Autowired
    private ValiCodeService valiCodeService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordHelper passwordHelper;

    @Secured("ROLE_CLIENT")
    @RequestMapping("/register")
    public ResultVo register(RegisterFormDto registerFormDto) {
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(HttpStatus.SC_EXPECTATION_FAILED);
        //验证参数
        registerFormDtoValidator.validate(registerFormDto, resultVo);
        if (resultVo.fail()) return resultVo;
        //检查验证码
        valiCodeService.checkValiCode(registerFormDto.getUsername(), ValiCodeTypeEnum.register.getType(), registerFormDto.getCode(), resultVo);
        if (resultVo.fail()) return resultVo;

        //保存数据库
        try {
            registerFormDto.setServletContext(context);
            userService.saveUser(registerFormDto);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getStackTrace().toString());
            resultVo.setCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            resultVo.setRet(ResultVo.FAILURE);
            resultVo.setMsg(e.getMessage());
            return resultVo;
        }
        //删除验证码
        valiCodeService.removeByMobile(registerFormDto.getUsername());
        //返回信息
        resultVo.setCode(HttpStatus.SC_OK);
        resultVo.setRet(ResultVo.SUCCESS);
        resultVo.setMsg(MessageUtils.getMessage("user.form.register_success"));
        return resultVo;
    }

    @Secured("ROLE_USER")
    @RequestMapping("/change_password")
    public ResultVo changePassword(ChangePasswordFormDto formDto) {
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(HttpStatus.SC_EXPECTATION_FAILED);
        //验证参数
        changePasswordFormDtoValidator.validate(formDto, resultVo);
        if (resultVo.fail()) return resultVo;

        User user = SecurityHolder.currentUser();
        if (!passwordHelper.isPasswordValid(user.password(), formDto.getOldPassword(), user.salt())) {
            resultVo.setCode(HttpStatus.SC_FORBIDDEN);
            resultVo.fail();
            resultVo.setMsg(MessageUtils.getMessage("user.form.change_password.old_password_error"));
            return resultVo;
        }
        //保存数据库
        formDto.setServletContext(context);
        formDto.setUser(user);
        userService.saveUser(formDto);
        //返回信息
        resultVo.setCode(HttpStatus.SC_OK);
        resultVo.fail(false);
        resultVo.setMsg(MessageUtils.getMessage("user.form.change_password.success"));
        return resultVo;
    }

    @Secured("ROLE_CLIENT")
    @RequestMapping("/forget_password")
    public ResultVo forgetPassword(ForgetPasswordFormDto formDto) {
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(HttpStatus.SC_EXPECTATION_FAILED);
        //验证参数
        forgetPasswordFormDtoValidator.validate(formDto, resultVo);
        if (resultVo.fail()) return resultVo;
        //检查验证码
        valiCodeService.checkValiCode(formDto.getMobile(), ValiCodeTypeEnum.forgetPassword.getType(), formDto.getCode(), resultVo);
        if (resultVo.fail()) return resultVo;
        //保存数据库
        formDto.setServletContext(context);
        userService.saveUser(formDto);
        //删除验证码
        valiCodeService.removeByMobile(formDto.getMobile());


        //返回信息
        resultVo.setCode(HttpStatus.SC_OK);
        resultVo.fail(false);
        resultVo.setMsg(MessageUtils.getMessage("user.form.change_password.success"));


        return resultVo;

    }

}
