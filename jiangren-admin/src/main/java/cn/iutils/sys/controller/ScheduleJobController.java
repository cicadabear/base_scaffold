package cn.iutils.sys.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.SchedulerException;
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
import cn.iutils.sys.entity.ScheduleJob;
import cn.iutils.sys.service.ScheduleJobService;

/**
 * 任务调度 控制器
 * 
 * @author MyCode
 * @version 1.0
 */
@Controller
@RequestMapping("${adminPath}/scheduleJob")
public class ScheduleJobController extends BaseController {

	@Autowired
	private ScheduleJobService scheduleJobService;

	@ModelAttribute
	public ScheduleJob get(@RequestParam(required = false) String id) {
		ScheduleJob entity = null;
		if (JStringUtils.isNotBlank(id)) {
			entity = scheduleJobService.get(id);
		}
		if (entity == null) {
			entity = new ScheduleJob();
		}
		return entity;
	}

	@RequiresPermissions("sys:scheduleJob:view")
	@RequestMapping()
	public String list(ScheduleJob scheduleJob,Model model, Page<ScheduleJob> page) {
		page.setEntity(scheduleJob);
		model.addAttribute("page",
				page.setList(scheduleJobService.findPage(page)));
		return "sys/scheduleJob/list";
	}

	@RequiresPermissions("sys:scheduleJob:create")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(ScheduleJob scheduleJob,Model model) {
		model.addAttribute("scheduleJob", scheduleJob);
		return "sys/scheduleJob/edit";
	}

	@RequiresPermissions("sys:scheduleJob:create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(ScheduleJob scheduleJob,RedirectAttributes redirectAttributes) {
		scheduleJobService.save(scheduleJob);
		addMessage(redirectAttributes, "新增成功");
		return "redirect:" + adminPath +"/scheduleJob/update?id="+scheduleJob.getId();
	}

	@RequiresPermissions("sys:scheduleJob:update")
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(ScheduleJob scheduleJob, Model model) {
		model.addAttribute("scheduleJob", scheduleJob);
		return "sys/scheduleJob/edit";
	}

	@RequiresPermissions("sys:scheduleJob:update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(ScheduleJob scheduleJob,RedirectAttributes redirectAttributes) {
		scheduleJobService.save(scheduleJob);
		addMessage(redirectAttributes, "修改成功");
		return "redirect:" + adminPath +"/scheduleJob/update?id="+scheduleJob.getId();
	}

	@RequiresPermissions("sys:scheduleJob:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(ScheduleJob scheduleJob,int pageNo,int pageSize,
			RedirectAttributes redirectAttributes) {
		scheduleJobService.delete(scheduleJob);
		try {
			scheduleJobService.deleteJob(scheduleJob);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		addMessage(redirectAttributes, "删除成功");
		return "redirect:" + adminPath + "/scheduleJob?pageNo="+pageNo+"&pageSize="+pageSize;
	}

	/**
	 * 更改任务状态
	 * 
	 * @param scheduleJob
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:scheduleJob:changeJobStatus")
	@RequestMapping(value = "/changeJobStatus", method = RequestMethod.GET)
	public String changeJobStatus(ScheduleJob scheduleJob,int pageNo,int pageSize,
			RedirectAttributes redirectAttributes) {
		scheduleJobService.changeJobStatus(scheduleJob);
		addMessage(redirectAttributes, "任务更改成功");
		return "redirect:" + adminPath + "/scheduleJob?pageNo="+pageNo+"&pageSize="+pageSize;
	}

	/**
	 * 暂停任务
	 * 
	 * @param scheduleJob
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:scheduleJob:changeJobStatus")
	@RequestMapping(value = "/pauseJob", method = RequestMethod.GET)
	public String pauseJob(ScheduleJob scheduleJob,int pageNo,int pageSize,
			RedirectAttributes redirectAttributes) {
		try {
			scheduleJobService.pauseJob(scheduleJob);
			addMessage(redirectAttributes, "任务已暂停");
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return "redirect:" + adminPath + "/scheduleJob?pageNo="+pageNo+"&pageSize="+pageSize;
	}

	/**
	 * 恢复任务
	 * 
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:scheduleJob:changeJobStatus")
	@RequestMapping(value = "/resumeJob", method = RequestMethod.GET)
	public String resumeJob(ScheduleJob scheduleJob,int pageNo,int pageSize,
			RedirectAttributes redirectAttributes) {
		try {
			scheduleJobService.resumeJob(scheduleJob);
			addMessage(redirectAttributes, "任务已恢复");
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return "redirect:" + adminPath + "/scheduleJob?pageNo="+pageNo+"&pageSize="+pageSize;
	}

	/**
	 * 立即执行任务
	 * 
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:scheduleJob:changeJobStatus")
	@RequestMapping(value = "/runAJobNow", method = RequestMethod.GET)
	public String runAJobNow(ScheduleJob scheduleJob,int pageNo,int pageSize,
			RedirectAttributes redirectAttributes) {
		try {
			scheduleJobService.runAJobNow(scheduleJob);
			addMessage(redirectAttributes, "任务已执行");
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return "redirect:" + adminPath + "/scheduleJob?pageNo="+pageNo+"&pageSize="+pageSize;
	}

}
