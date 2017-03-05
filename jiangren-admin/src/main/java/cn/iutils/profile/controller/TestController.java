package cn.iutils.profile.controller;

import cn.iutils.common.ResultVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by Jack on 2/28/17.
 */
@Controller("cn.iutils.profile.controller.TestController")
@RequestMapping("${adminPath}/test")
public class TestController {
    Logger logger = LoggerFactory.getLogger(TestController.class.getName());

    @RequestMapping("test")
    @ResponseBody
    public Object test(ServletRequest request, ServletResponse response) {
        ResultVo resultVo = new ResultVo();
        logger.debug("==========test");
        logger.info("==========test");
        return resultVo;
    }
}
