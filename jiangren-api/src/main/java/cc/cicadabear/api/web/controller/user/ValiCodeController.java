package cc.cicadabear.api.web.controller.user;

import cc.cicadabear.api.common.utils.MatchUtils;
import cc.cicadabear.business.common.utils.MessageUtils;
import cc.cicadabear.business.common.ResultVo;
import cc.cicadabear.business.domain.entity.enums.ValiCodeTypeEnum;
import cc.cicadabear.business.service.ValiCodeService;
import cc.cicadabear.profile.service.UserService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Jack on 2/20/17.
 */
@RestController
@RequestMapping(value = "${restPath}/sms", method = RequestMethod.POST)
@ResponseBody
public class ValiCodeController {

    @Autowired
    private ValiCodeService valiCodeService;

    @Autowired
    private UserService userService;

    /**
     * 发送一般验证码 注册 找回密码
     *
     * @param mobile
     * @param type
     * @return
     */
    @Secured("ROLE_CLIENT")
    @RequestMapping("/send_code")
    public ResultVo sendValidCode(String mobile, Integer type) {
        ResultVo ret = new ResultVo();

        ret = validateParams(mobile, type, ret, false);
        if (ret.getRet() == ResultVo.FAILURE) {
            return ret;
        }

        return valiCodeService.sendValiCode(mobile, type, ret);
    }

    @Secured("ROLE_USER")
    @RequestMapping("/send_security_code")
    public ResultVo sendSecurityValidCode(String mobile, Integer type) {
        ResultVo ret = new ResultVo();

        ret = validateParams(mobile, type, ret, true);
        if (ret.getRet() == ResultVo.FAILURE) {
            return ret;
        }
        return valiCodeService.sendValiCode(mobile, type, ret);
    }

    /**
     * @param mobile
     * @param type
     * @param ret
     * @param isSecured 是否需要被保护 比如需要登录的验证码 修改密码验证码 提现验证码
     * @return
     */
    private ResultVo validateParams(String mobile, Integer type, ResultVo ret, boolean isSecured) {
        ret.setRet(ResultVo.FAILURE);
        ret.setCode(HttpStatus.SC_EXPECTATION_FAILED);
        //验证手机号码类型
        if (!MatchUtils.isMobileNo(mobile)) {
            ret.setMsg(MessageUtils.getMessage("user.form.validator.mobile_no_error"));
            return ret;
        }
        //验证请求的验证码类型是否允许
        boolean isTypeValidated = false;
        for (ValiCodeTypeEnum valiCodeTypeEnum : ValiCodeTypeEnum.values()) {
            if (valiCodeTypeEnum.isSecurity() == isSecured && type.equals(valiCodeTypeEnum.getType())) {
                isTypeValidated = true;
                break;
            }
        }
        if (!isTypeValidated) {
            ret.setMsg(MessageUtils.getMessage("dev.user.form.validator.sms.validcode_type_error"));
        } else {
            //注册验证手机号码是否已存在
            if (ValiCodeTypeEnum.register.getType().equals(type) && userService.isExistedMobile(mobile)) {
                ret.setMsg(MessageUtils.getMessage("user.form.validator.mobile_already_exists"));
            } else {
                ret.setRet(ResultVo.SUCCESS);
            }
        }
        return ret;
    }

}
