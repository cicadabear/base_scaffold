package cn.iutils.sys.controller;

import cn.iutils.common.Page;
import cn.iutils.common.controller.BaseController;
import cn.iutils.common.utils.JStringUtils;
import cn.iutils.common.utils.UserUtils;
import cn.iutils.sys.entity.Organization;
import cn.iutils.sys.entity.Role;
import cn.iutils.sys.entity.User;
import cn.iutils.sys.service.OrganizationService;
import cn.iutils.sys.service.PasswordHelper;
import cn.iutils.sys.service.RoleService;
import cn.iutils.sys.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("${adminPath}/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordHelper passwordHelper;

    @ModelAttribute
    public User get(@RequestParam(required = false) String id) {
        User entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = userService.get(id);
        }
        if (entity == null) {
            entity = new User();
        }
        return entity;
    }

    /**
     * 用户主页
     *
     * @param model
     * @return
     */
    @RequiresPermissions("sys:user:view")
    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        return "sys/user/index";
    }

    /**
     * 组织机构树型结构
     *
     * @param model
     * @return
     */
    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public String tree(Model model) {
        Organization organization = new Organization();
        organization.setUser(UserUtils.getLoginUser());
        model.addAttribute("organizationList", organizationService.findList(organization));
        return "sys/user/tree";
    }

    /**
     * 用户列表
     *
     * @param model
     * @param page
     * @return
     */
    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = "/list")
    public String list(User user, Model model, Page<User> page) {
        page.setEntity(user);
        model.addAttribute("page", page.setList(userService.findPage(page)));
        return "sys/user/list";
    }

    /**
     * 用户新增
     *
     * @param model
     * @return
     */
    @RequiresPermissions("sys:user:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(User user, Model model) {
        Organization organization = new Organization();
        organization.setUser(UserUtils.getLoginUser());
        model.addAttribute("organizationList", organizationService.findList(organization));
        Role role = new Role();
        role.setUser(UserUtils.getLoginUser());
        model.addAttribute("roleList", roleService.findList(role));
        user.setOrganization(organizationService.get(user.getOrganizationId()));
        model.addAttribute("user", user);
        return "sys/user/edit";
    }

    /**
     * 用户新增保存
     *
     * @param user
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:user:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(User user, RedirectAttributes redirectAttributes) {
        passwordHelper.encryptPassword(user);
        userService.save(user);
        addMessage(redirectAttributes, "保存成功");
        return "redirect:" + adminPath + "/user/update?id=" + user.getId();
    }

    /**
     * 用户修改
     *
     * @param user
     * @param model
     * @return
     */
    @RequiresPermissions("sys:user:update")
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(User user, Model model) {
        Organization organization = new Organization();
        organization.setUser(UserUtils.getLoginUser());
        model.addAttribute("organizationList", organizationService.findList(organization));
        Role role = new Role();
        role.setUser(UserUtils.getLoginUser());
        model.addAttribute("roleList", roleService.findList(role));
        model.addAttribute("user", user);
        return "sys/user/edit";
    }

    /**
     * 用户修改保存
     *
     * @param user
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:user:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(User user, RedirectAttributes redirectAttributes) {
        //不是部门经理，不能修改，超级管理员可以修改，自己的信息可以修改
        User loginUser = UserUtils.getLoginUser();
        if (loginUser.isAdmin() || user.getId().equals(loginUser.getId()) || loginUser.getIsDept()) {
            userService.save(user);
            addMessage(redirectAttributes, "保存成功");
        } else {
            addMessage(redirectAttributes, "不能修改");
        }
        return "redirect:" + adminPath + "/user/update?id=" + user.getId();
    }

    /**
     * 用户删除
     *
     * @param user
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:user:delete")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(User user, int pageNo, int pageSize,
                         RedirectAttributes redirectAttributes) {
        if (user.isAdmin() && user.getId().equals(UserUtils.getLoginUser().getId())) {
            //不能删除超级用户，和当前登录用户
            addMessage(redirectAttributes, "不能删除");
        } else {
            userService.delete(user);
            addMessage(redirectAttributes, "删除成功");
        }
        return "redirect:" + adminPath + "/user/list?organizationId=" + user.getOrganizationId() + "&pageNo=" + pageNo + "&pageSize=" + pageSize;
    }

    /**
     * 修改密码
     *
     * @param id
     * @param model
     * @return
     */
    @RequiresPermissions("sys:user:update")
    @RequestMapping(value = "/{id}/changePassword", method = RequestMethod.GET)
    public String showChangePasswordForm(@PathVariable("id") String id,
                                         Model model) {
        model.addAttribute("id", id);
        return "sys/user/changePassword";
    }

    /**
     * 修改密码保存
     *
     * @param id
     * @param password
     * @param rePassword
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:user:update")
    @RequestMapping(value = "/{id}/changePassword", method = RequestMethod.POST)
    public String changePassword(@PathVariable("id") String id,
                                 String password, String rePassword,
                                 RedirectAttributes redirectAttributes) {
        if (!password.equals(rePassword)) {
            logger.error("两次密码不一致", "changePassword");
            addMessage(redirectAttributes, "两次密码不一致");
            return "redirect:" + adminPath + "/user/" + id + "/changePassword";
        }
        try {
            userService.superChangePassword(id, password);
            addMessage(redirectAttributes, "修改密码成功");
            return "redirect:" + adminPath + "/user/" + id + "/changePassword";
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            addMessage(redirectAttributes, "修改密码失败");
            return "redirect:" + adminPath + "/user/" + id + "/changePassword";
        }
    }

    /**
     * 用户资料
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo(Model model) {
        User user = UserUtils.getLoginUser();
        model.addAttribute("user", user);
        return "sys/user/config-userInfo";
    }

    /**
     * 保存用户资料
     *
     * @param user
     * @param model
     * @return
     */
    @RequestMapping(value = "/saveUserInfo", method = RequestMethod.POST)
    public String saveUserInfo(User user, Model model) {
        userService.save(user);
        model.addAttribute("user", user);
        addMessage(model, "保存资料成功");
        return "sys/user/config-userInfo";
    }

    /**
     * 用户密码
     *
     * @return
     */
    @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
    public String myPassword() {
//		user user = UserUtils.getLoginUser();
//		model.addAttribute("user", user);
        return "sys/user/config-changePassword";
    }

    /**
     * 修改密码保存
     * @param oldPassword
     * @param newPassword
     * @param rePassword
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public String changeMyPassword(String oldPassword,
                                   String newPassword, String rePassword,
                                   RedirectAttributes redirectAttributes) {
        String id = UserUtils.getLoginUser().getId();
        if (!newPassword.equals(rePassword)) {
            logger.error("两次密码不一致", "changePassword");
            addMessage(redirectAttributes, "两次密码不一致");
            return "redirect:" + adminPath + "/user/changePassword";
        }
        try {
            userService.changePassword(id, oldPassword, newPassword);
            addMessage(redirectAttributes, "修改密码成功");
            return "redirect:" + adminPath + "/user/changePassword";
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            addMessage(redirectAttributes, "原密码错误");
            return "redirect:" + adminPath + "/user/changePassword";
        }
    }

    /**
     * 用户配置
     *
     * @return
     */
    @RequestMapping(value = "/configure", method = RequestMethod.GET)
    public String configure() {
        return "sys/user/config";
    }

}
