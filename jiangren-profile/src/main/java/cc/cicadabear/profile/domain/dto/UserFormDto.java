package cc.cicadabear.profile.domain.dto;

import cc.cicadabear.profile.domain.user.Privilege;
import cc.cicadabear.profile.domain.user.User;
import cc.cicadabear.profile.infrastructure.PasswordHelper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 2016/3/25
 *
 * @author Shengzhao Li
 */
public class UserFormDto extends UserDto {
    private static final long serialVersionUID = 7959857016962260738L;

    @Autowired
    private PasswordHelper passwordHelper;

    private String password;

    public UserFormDto() {
    }


    public Privilege[] getAllPrivileges() {
        return new Privilege[]{Privilege.MOBILE, Privilege.UNITY};
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User newUser() {
        final User user = new User()
                .username(getUsername())
                .phone(getPhone())
                .email(getEmail())
                .password(getPassword());
        passwordHelper.encryptPassword(user);//设置密码
        user.privileges().addAll(getPrivileges());
        return user;
    }
}
