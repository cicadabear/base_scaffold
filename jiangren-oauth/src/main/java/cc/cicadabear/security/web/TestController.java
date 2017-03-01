package cc.cicadabear.security.web;

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

    @RequestMapping("/test")
    public Object test() {
        Map<String, Object> re = new HashMap();
        re.put("code", 1);
        re.put("message", "success");
        return re;
    }
}
