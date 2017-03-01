package cn.iutils.sys.controller;

import cn.iutils.common.config.JConfig;
import cn.iutils.common.controller.BaseController;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;

/**
 * Session控制器
 * 
 * @author cc
 */
@Controller
@RequestMapping("${adminPath}/sessions")
public class SessionController extends BaseController {

	@Autowired
	private SessionDAO sessionDAO;

	/**
	 * 获取在线用户列表
	 * @param model
	 * @return
	 */
	@RequestMapping()
	public String list(Model model) {
		Collection<Session> sessions = sessionDAO.getActiveSessions();
		model.addAttribute("sessions", sessions);
		model.addAttribute("sessionCount", sessions.size());
		return "sys/sessions/list";
	}

	/**
	 * 强制退出
	 * @param sessionId
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/{sessionId}/forceLogout")
	public String forceLogout(@PathVariable("sessionId") String sessionId,
			RedirectAttributes redirectAttributes) {
		try {
			Session session = sessionDAO.readSession(sessionId);
			if (session != null) {
				session.setAttribute(JConfig.SESSION_FORCE_LOGOUT_KEY,
						Boolean.TRUE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		redirectAttributes.addFlashAttribute("msg", "强制退出成功！");
		return "redirect:" + adminPath + "/sessions";
	}

}
