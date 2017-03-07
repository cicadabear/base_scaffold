package cc.cicadabear.api.web.context;

import cc.cicadabear.profile.domain.shared.security.WdcyUserDetails;
import cc.cicadabear.profile.domain.user.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;

/**
 * Created by Jack on 2/26/17.
 */
public class SecurityHolder {

    public static WdcyUserDetails currentUserDetail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof WdcyUserDetails) {
            return (WdcyUserDetails) principal;
        }
        return null;
    }

    public static User currentUser() {
        WdcyUserDetails userDetails = currentUserDetail();
        return (userDetails != null ? userDetails.user() : null);
    }

//    public static void updatePassword(String encPassword) {
//        WdcyUserDetails currentUserDetail = currentUserDetail();
//        Authentication authentication = new UsernamePasswordAuthenticationToken(currentUserDetail, encPassword, currentUserDetail.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//    }

    public static void revokeAllAccessTokenOfCurrentUser(ServletContext context) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String clientId = ((OAuth2Authentication) auth).getOAuth2Request().getClientId();

        String username = currentUserDetail().getUsername();

        TokenStore tokenStore = getTokenStore(context);

        for (OAuth2AccessToken accessToken : tokenStore.findTokensByClientIdAndUserName(clientId, username)) {
            tokenStore.removeAccessToken(accessToken);
        }
        SecurityContextHolder.getContext().setAuthentication(null);//清除当前登录用户
    }

    public static void revokeOneAccessToken(ServletContext context) {
        TokenStore tokenStore = getTokenStore(context);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String tokenValue = authHeader.replace("Bearer", "").trim();
//        OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
        OAuth2AccessToken accessToken = tokenStore.getAccessToken((OAuth2Authentication) auth);
        tokenStore.removeAccessToken(accessToken);
    }

    private static TokenStore getTokenStore(ServletContext context) {
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
        return ctx.getBean(TokenStore.class);
    }

}
