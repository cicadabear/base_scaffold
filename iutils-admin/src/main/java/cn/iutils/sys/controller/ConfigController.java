package cn.iutils.sys.controller;

import cn.iutils.common.Page;
import cn.iutils.common.controller.BaseController;
import cn.iutils.common.utils.JStringUtils;
import cn.iutils.sys.entity.Config;
import cn.iutils.sys.service.ConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
* 公共配置表 控制器
* @author iutils.cn
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/sys/config")
public class ConfigController extends BaseController {

    @Autowired
    private ConfigService configService;

    @ModelAttribute
    public Config get(@RequestParam(required = false) String id) {
        Config entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = configService.get(id);
        }
        if (entity == null) {
            entity = new Config();
        }
        return entity;
    }

    @RequiresPermissions("sys:config:view")
    @RequestMapping()
    public String list(Config config,Model model, Page<Config> page) {
        page.setEntity(config);
        model.addAttribute("page", page.setList(configService.findPage(page)));
        return "sys/config/list";
    }

    @RequiresPermissions("sys:config:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Config config,Model model) {
        model.addAttribute("config", config);
        return "sys/config/form";
    }

    @RequiresPermissions("sys:config:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(Config config, RedirectAttributes redirectAttributes) {
        configService.save(config);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/sys/config/update?id="+config.getId();
    }

    @RequiresPermissions("sys:config:update")
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(Config config, Model model) {
        model.addAttribute("config", config);
        return "sys/config/form";
    }

    @RequiresPermissions("sys:config:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(Config config, RedirectAttributes redirectAttributes) {
        configService.save(config);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/sys/config/update?id="+config.getId();
    }

    @RequiresPermissions("sys:config:delete")
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        configService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/sys/config?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
