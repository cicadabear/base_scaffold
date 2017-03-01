package cn.iutils.sys.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.iutils.common.controller.BaseController;
import cn.iutils.common.utils.UserUtils;
import cn.iutils.sys.entity.Resource;
import cn.iutils.sys.service.ResourceService;
import cn.iutils.sys.service.UserService;

/**
 * 首页控制器
 * 
 * @author cc
 */
@Controller
@RequestMapping(value = "${adminPath}")
public class IndexController extends BaseController {

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		Set<String> permissions = userService.findPermissions();
		List<Resource> menus = resourceService.findMenus(permissions);
		model.addAttribute("menus", menus);
		return "index";
	}

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String welcome() {
		return "welcome";
	}

}
