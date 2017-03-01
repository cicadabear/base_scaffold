package cn.iutils.sys.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.iutils.common.Page;
import cn.iutils.common.controller.BaseController;
import cn.iutils.common.utils.JStringUtils;
import cn.iutils.sys.entity.SLog;
import cn.iutils.sys.service.SLogService;

/**
 * 日志记录 控制器
 *
 * @author iutils
 * @version 1.0
 */
@Controller
@RequestMapping("${adminPath}/slog")
public class LogController extends BaseController {

    @Autowired
    private SLogService logService;

    @ModelAttribute
    public SLog get(@RequestParam(required = false) String id) {
        SLog entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = logService.get(id);
        }
        if (entity == null) {
            entity = new SLog();
        }
        return entity;
    }

    /**
     * 查看日志列表
     *
     * @param model
     * @param page
     * @return
     */
    @RequiresPermissions("sys:slog:view")
    @RequestMapping()
    public String list(SLog sLog, Model model, Page<SLog> page) {
        page.setEntity(sLog);
        page.setOrderBy("a.create_date desc");
        model.addAttribute("page", page.setList(logService.findPage(page)));
        return "sys/log/list";
    }

    /**
     * 查看日志详细
     *
     * @param log
     * @param model
     * @return
     */
    @RequiresPermissions("sys:slog:view")
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(SLog log, Model model) {
        model.addAttribute("log", log);
        return "sys/log/edit";
    }

    /**
     * 批量删除日志
     *
     * @param ids
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:slog:edit")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(String[] ids, int pageNo, int pageSize,
                         RedirectAttributes redirectAttributes) {
        logService.deleteAll(ids);
        addMessage(redirectAttributes, "删除成功");
        return "redirect:" + adminPath + "/slog?pageNo=" + pageNo + "&pageSize=" + pageSize;
    }

}
