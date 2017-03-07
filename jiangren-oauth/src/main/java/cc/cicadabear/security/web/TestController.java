package cc.cicadabear.security.web;

import cc.cicadabear.profile.domain.user.User;
import cc.cicadabear.profile.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Jack on 2/22/17.
 */
@Controller("oauth-test")
@RequestMapping("/oauth")
@ResponseBody
public class TestController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ApplicationContext applicationContext;

    @RequestMapping("/test")
    public Object test() {
        Map<String, Object> re = new HashMap();

        Object bean = applicationContext.getBean("securityEhcache");


        User user = userRepository.findByMobile("18682323715");

        re.put("code", 1);
        re.put("message", "success");
        re.put("data", user);
        return re;
    }


}
