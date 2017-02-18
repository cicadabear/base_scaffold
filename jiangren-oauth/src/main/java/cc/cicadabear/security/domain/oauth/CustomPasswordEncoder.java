package cc.cicadabear.security.domain.oauth;

import cc.cicadabear.profile.infrastructure.PasswordHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;

/**
 * Created by Jack on 2/17/17.
 */
public class CustomPasswordEncoder implements PasswordEncoder {

    @Autowired
    private PasswordHelper passwordHelper;

    public String encodePassword(String rawPass, Object salt) {
        return passwordHelper.encodePassword(rawPass, salt);
    }

    public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
        return passwordHelper.isPasswordValid(encPass, rawPass, salt);
    }
}
