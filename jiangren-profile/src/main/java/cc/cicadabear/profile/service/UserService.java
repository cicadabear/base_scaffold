package cc.cicadabear.profile.service;

import cc.cicadabear.profile.domain.dto.UserFormDto;
import cc.cicadabear.profile.domain.dto.UserJsonDto;
import cc.cicadabear.profile.domain.dto.UserOverviewDto;

/**
 * @author Shengzhao Li
 */
public interface UserService {

    UserJsonDto loadCurrentUserJsonDto();

    UserOverviewDto loadUserOverviewDto(UserOverviewDto overviewDto);

    boolean isExistedUsername(String username);

    String saveUser(UserFormDto formDto);
}