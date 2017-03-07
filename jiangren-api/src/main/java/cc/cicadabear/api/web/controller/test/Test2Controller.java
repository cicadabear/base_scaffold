package cc.cicadabear.api.web.controller.test;

import cc.cicadabear.profile.domain.user.User;
import cc.cicadabear.profile.service.UserService;
import net.sf.ehcache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * Created by Jack on 2/23/17.
 */
@RestController("cc.cicadabear.api.web.controller.test.Test2Controller")
@RequestMapping("/test2")
@ResponseBody
public class Test2Controller {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    UserService userService;

    @Autowired
    @Qualifier("securityCacheManager")
    EhCacheCacheManager ehCacheCacheManager;

    @PostConstruct
    public void init() {
        logger.info("TestController", "PostConstruct");
        System.out.println("========================" + str);
    }

    @Value("${restPath}")
    String str;

    @Secured("ROLE_CLIENT")
    @RequestMapping(value = "/test")
    public Object test() {
        System.out.println("=========test");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        return "test";
    }

    @RequestMapping("/test_cache")
    public Object testfindByMobile() {
        String mobile = "18682323715";

        userService.isExistedUsername(mobile);

        userService.isExistedMobile(mobile);

//        User user = userService.loadUserByMobile(mobile);
//        userService.saveUser(user);

        return "test_cache";
    }

    @RequestMapping("/test_cache_update")
    public Object testUpdateUser() {
        String mobile = "18682323715";
        User user = userService.loadUserByMobile(mobile);
        user.email("cicadabear@163.com");
        userService.saveUser(user);
        return "test_cache_update";
    }

    @RequestMapping("/test_cache_list")
    public Object testCacheManager() {

        EhCacheCache ehCacheCache = (EhCacheCache) ehCacheCacheManager.getCache("userCache");
        Cache cache = (Cache) ehCacheCache.getNativeCache();
//        for (Object key : cache.getKeys()) {
//            Element element = cache.get(key);
//
//        }
        return cache.getKeys();
//        return "test_cache_list";
    }
}
