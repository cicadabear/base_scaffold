package cc.cicadabear.profile.service;

import cc.cicadabear.profile.domain.dto.UserFormDto;
import cc.cicadabear.profile.domain.dto.UserJsonDto;
import cc.cicadabear.profile.domain.dto.UserOverviewDto;
import cc.cicadabear.profile.domain.user.User;
import cn.iutils.common.Page;

import java.util.List;

/**
 * @author Shengzhao Li
 */
public interface UserService {

    UserJsonDto loadCurrentUserJsonDto();

    UserOverviewDto loadUserOverviewDto(UserOverviewDto overviewDto);

    boolean isExistedUsername(String username);

    boolean isExistedMobile(String mobile);

    String saveUser(UserFormDto formDto);

    User loadUserByMobile(String mobile);

    List<User> findPage(Page<User> page);

}