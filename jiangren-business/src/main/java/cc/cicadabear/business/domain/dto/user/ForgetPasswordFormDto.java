package cc.cicadabear.business.domain.dto.user;

import cc.cicadabear.profile.domain.dto.UserFormDto;
import cc.cicadabear.profile.domain.user.User;
import cc.cicadabear.profile.infrastructure.PasswordHelper;
import cc.cicadabear.profile.service.UserService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;

/**
 * Created by Jack on 2/26/17.
 */
public class ForgetPasswordFormDto extends UserFormDto {

    private ServletContext servletContext;

    private PasswordHelper passwordHelper;

    private UserService userService;

    private String mobile;
    private String code;
    private String password;
    private String rePassword;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
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

        userService = (UserService) ctx.getBean("userService");
        user = userService.loadUserByMobile(mobile);

        passwordHelper = (PasswordHelper) ctx.getBean("passwordHelper");
        String encPassword = passwordHelper.encodePassword(password, user.salt());
        user.password(encPassword);
        return user;
    }
}
