package cn.iutils.sys.controller;

import cn.iutils.common.ResultVo;
import cn.iutils.common.controller.BaseController;
import cn.iutils.sys.entity.User;
import cn.iutils.sys.service.PasswordHelper;
import cn.iutils.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户接口
 * @author iutils.cn
 * @version 1.0
 */
@RestController
@RequestMapping("${restPath}/user")
public class UserRest extends BaseController {

    @Autowired
    UserService userService;

    @Autowired
    private PasswordHelper passwordHelper;

    /**
     * 用户测试
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @RequestMapping(value = "/register_dep", method = RequestMethod.POST)
    public @ResponseBody
    ResultVo register(String username,String password){
        ResultVo resultVo = null;
        try{
            User user = userService.getUserByUserName(username);
            if(user==null){
                user = new User();
                user.setUsername(username);
                user.setPassword(password);
                user.setOrganizationId("1");
                user.setRoleIdsStr("3,");
                passwordHelper.encryptPassword(user);
                userService.save(user);
                resultVo = new ResultVo(ResultVo.SUCCESS,"1","注册成功",null);
            }else{
                resultVo = new ResultVo(ResultVo.SUCCESS,"2","账号已存在",null);
            }
        }catch (Exception e){
            logger.error("注册接口调用失败",e.getMessage());
            resultVo = new ResultVo(ResultVo.FAILURE,"-1","注册失败",null);
        }
        return resultVo;
    }

}
