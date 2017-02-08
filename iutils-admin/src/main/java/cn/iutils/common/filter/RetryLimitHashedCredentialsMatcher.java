package cn.iutils.common.filter;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 重试次数限定匹配验证器
 * @author cc
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    private Cache<String, AtomicInteger> passwordRetryCache;

    /**
     * 初始化密码缓存
     * @param cacheManager
     */
    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String)token.getPrincipal();
        //错误次数累加 10分钟后清除
        AtomicInteger retryCount = passwordRetryCache.get(username);
        if(retryCount == null) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(username, retryCount);
        }
        if(retryCount.incrementAndGet() > 5) {
            //规定时间内超出错误次数
            throw new ExcessiveAttemptsException();
        }

        boolean matches = super.doCredentialsMatch(token, info);
        if(matches) {
            //匹配正确后，清除错误计数器
            passwordRetryCache.remove(username);
        }
        return matches;
    }
}
