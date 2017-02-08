package cn.iutils.sys.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authz.permission.WildcardPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.service.CrudService;
import cn.iutils.sys.dao.IResourceDao;
import cn.iutils.sys.entity.Resource;
import cn.iutils.sys.entity.enums.ResourceEnum;

/**
 * 资源服务
 * 
 * @author cc
 */
@Service
@Transactional(readOnly = true)
public class ResourceService extends CrudService<IResourceDao, Resource> {

	/**
	 * 获取权限标识
	 * @param resourceIds
	 * @return
	 */
	public Set<String> findPermissions(Set<String> resourceIds) {
		Set<String> permissions = new HashSet<String>();
		List<Resource> resources = dao.getResources(resourceIds);
		for (int i=0;i<resources.size();i++) {
			Resource resource = resources.get(i);
			if (resource != null
					&& !StringUtils.isEmpty(resource.getPermission())) {
				permissions.add(resource.getPermission());
			}
		}
		return permissions;
	}

	/**
	 * 获取菜单
	 * @param permissions
	 * @return
	 */
	public List<Resource> findMenus(Set<String> permissions) {
		List<Resource> allResources = findList(null);
		List<Resource> menus = new ArrayList<Resource>();
		for (Resource resource : allResources) {
			if(!resource.getAvailable()){
				continue;
			}
			if (resource.isRootNode()) {
				continue;
			}
			if (resource.getType() != ResourceEnum.menu) {
				continue;
			}
			if (!hasPermission(permissions, resource)) {
				continue;
			}
			menus.add(resource);
		}
		return menus;
	}

	/**
	 * 获取角色菜单
	 * @param permissions
	 * @return
	 */
	public List<Resource> findRoleMenus(Set<String> permissions){
		List<Resource> allResources = findList(null);
		List<Resource> menus = new ArrayList<Resource>();
		for (Resource resource : allResources) {
			if (resource.isRootNode()) {
				continue;
			}
			if (!hasPermission(permissions, resource)) {
				continue;
			}
			menus.add(resource);
		}
		return menus;
	}

	private boolean hasPermission(Set<String> permissions, Resource resource) {
		if (StringUtils.isEmpty(resource.getPermission())) {
			return true;
		}
		for (String permission : permissions) {
			WildcardPermission p1 = new WildcardPermission(permission);
			WildcardPermission p2 = new WildcardPermission(
					resource.getPermission());
			if (p1.implies(p2) || p2.implies(p1)) {
				return true;
			}
		}
		return false;
	}

}
