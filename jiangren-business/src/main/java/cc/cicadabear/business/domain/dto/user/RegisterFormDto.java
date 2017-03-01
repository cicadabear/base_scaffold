package cc.cicadabear.business.domain.dto.user;

import cc.cicadabear.profile.domain.dto.UserFormDto;
import cc.cicadabear.profile.domain.user.User;
import cc.cicadabear.profile.infrastructure.PasswordHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;

/**
 * Created by Jack on 2/25/17.
 */
public class RegisterFormDto extends UserFormDto {

    private ServletContext servletContext;
    private String username;
    private String password;
    private String rePassword;
    private String code;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

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


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getEmail() {
        return "";
    }

    @Override
    public String getPhone() {
        return getUsername();
    }

    private PasswordHelper passwordHelper;

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public User user() {
        final User user = new User()
                .username(getUsername())
                .phone(getPhone())
                .email(getEmail())
                .password(getPassword());
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        passwordHelper = (PasswordHelper) ctx.getBean("passwordHelper");
        passwordHelper.encryptPassword(user);//设置密码
        user.privileges().addAll(getPrivileges());
        return user;
    }
}
