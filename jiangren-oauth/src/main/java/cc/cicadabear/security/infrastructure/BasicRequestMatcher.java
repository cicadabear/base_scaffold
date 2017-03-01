package cc.cicadabear.security.infrastructure;


import org.springframework.security.web.util.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Jack on 2/20/17.
 */
public class BasicRequestMatcher implements RequestMatcher {
    @Override
    public boolean matches(HttpServletRequest httpServletRequest) {
        String auth = httpServletRequest.getHeader("Authorization");
        return (auth != null && auth.startsWith("Basic"));
    }
}
