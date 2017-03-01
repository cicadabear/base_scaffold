package cc.cicadabear.profile.service.impl;

import cc.cicadabear.profile.domain.dto.UserDto;
import cc.cicadabear.profile.domain.dto.UserFormDto;
import cc.cicadabear.profile.domain.dto.UserJsonDto;
import cc.cicadabear.profile.domain.dto.UserOverviewDto;
import cc.cicadabear.profile.domain.shared.security.WdcyUserDetails;
import cc.cicadabear.profile.domain.user.User;
import cc.cicadabear.profile.domain.user.UserRepository;
import cc.cicadabear.profile.service.UserService;
import cn.iutils.common.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 处理用户, 账号, 安全相关业务
 *
 * @author Shengzhao Li
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserJsonDto loadCurrentUserJsonDto() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Object principal = authentication.getPrincipal();

        if (authentication instanceof OAuth2Authentication &&
                (principal instanceof String || principal instanceof org.springframework.security.core.userdetails.User)) {
            return loadOauthUserJsonDto((OAuth2Authentication) authentication);
        } else {
            final WdcyUserDetails userDetails = (WdcyUserDetails) principal;
            return new UserJsonDto(userRepository.findByGuid(userDetails.user().guid()));
        }
    }

    @Override
    public UserOverviewDto loadUserOverviewDto(UserOverviewDto overviewDto) {
        List<User> users = userRepository.findUsersByUsername(overviewDto.getUsername());
        overviewDto.setUserDtos(UserDto.toDtos(users));
        return overviewDto;
    }

    @Override
    public boolean isExistedUsername(String username) {
        final User user = userRepository.findByUsername(username);
        return user != null;
    }

    @Override
    public boolean isExistedMobile(String mobile) {
        final User user = userRepository.findByMobile(mobile);
        return user != null;
    }

    @Override
    public String saveUser(UserFormDto formDto) {
        User user = formDto.user();
        if (StringUtils.isNotEmpty(user.guid())) {
            userRepository.updateUser(user);
        } else {
            userRepository.saveUser(user);
        }
        return user.guid();
    }

    @Override
    public User loadUserByMobile(String mobile) {
        return userRepository.findByMobile(mobile);
    }

    private UserJsonDto loadOauthUserJsonDto(OAuth2Authentication oAuth2Authentication) {
        UserJsonDto userJsonDto = new UserJsonDto();
        userJsonDto.setUsername(oAuth2Authentication.getName());

        final Collection<GrantedAuthority> authorities = oAuth2Authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            userJsonDto.getPrivileges().add(authority.getAuthority());
        }

        return userJsonDto;
    }

    /**
     * 查询分页数据
     *
     * @param page
     * @return
     */
    public List<User> findPage(Page<User> page) {
        page.setTotal(userRepository.count(page));
        return userRepository.findPage(page);
    }
}