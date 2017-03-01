package cc.cicadabear.profile.domain.user;

import cc.cicadabear.profile.domain.shared.Repository;
import cn.iutils.common.Page;

import java.util.List;

/**
 * @author Shengzhao Li
 */

public interface UserRepository extends Repository {

    User findByGuid(String guid);

    void saveUser(User user);

    void updateUser(User user);

    User findByUsername(String username);

    User findByMobile(String mobile);

    List<User> findUsersByUsername(String username);

    List<User> findPage(Page<User> page);

    int count(Page<User> page);

}