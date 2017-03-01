package cn.iutils.profile.controller;

import cc.cicadabear.profile.domain.user.User;
import cc.cicadabear.profile.service.UserService;
import cn.iutils.common.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Jack on 2/28/17.
 */
@Controller
@RequestMapping("${adminPath}/member")
public class MemberController {
    @Autowired
    private UserService memberService;

    @RequiresPermissions("profile:member:view")
    @RequestMapping()
    public String index(User user, Model model, Page<User> page) {
        return list(user, model, page);
    }

    /**
     * 会员列表
     *
     * @param model
     * @param page
     * @return
     */
    @RequiresPermissions("profile:member:view")
    @RequestMapping("/list")
    public String list(User user, Model model, Page<User> page) {
        page.setEntity(user);
        model.addAttribute("page", page.setList(memberService.findPage(page)));
        return "profile/member/list";
    }

//    @RequiresPermissions("profile:member:view")
//    @RequestMapping(method = RequestMethod.GET)
//    public String index(Model model) {
//        return "sys/user/index";
//    }
}
