package cc.cicadabear.api.web.context;

import cc.cicadabear.profile.domain.shared.security.WdcyUserDetails;
import cc.cicadabear.profile.domain.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
}
