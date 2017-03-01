package cc.cicadabear.business.domain.dto.user;

import cc.cicadabear.profile.domain.dto.UserFormDto;
import cc.cicadabear.profile.domain.user.User;
import cc.cicadabear.profile.infrastructure.PasswordHelper;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;

/**
 * Created by Jack on 2/26/17.
 */
public class ChangePasswordFormDto extends UserFormDto {

    private ServletContext servletContext;

    private PasswordHelper passwordHelper;

    private String oldPassword;
    private String newPassword;
    private String rePassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public void setOld_password(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setNew_password(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public void setRe_password(String rePassword) {
        this.rePassword = rePassword;
    }

    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public void setServletContext(ServletContext context) {
        this.servletContext = context;
    }

    @Override
    public User user() {
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        passwordHelper = (PasswordHelper) ctx.getBean("passwordHelper");
        String encPassword = passwordHelper.encodePassword(newPassword, user.salt());
        user.password(encPassword);
        return user;
    }
}
