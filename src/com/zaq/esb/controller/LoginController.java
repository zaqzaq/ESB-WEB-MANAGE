/**
 * 
 */
package com.zaq.esb.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.zaq.esb.common.BaseController;
import com.zaq.esb.common.BaseModel;
import com.zaq.esb.common.Constans;
import com.zaq.esb.service.impl.AdminUserService;
import com.zaq.esb.service.impl.AppInfoService;
import com.zaq.esb.util.EncryptUtil;

@Controller
@Scope("prototype")
public class LoginController extends BaseController {
	@Autowired
	private AdminUserService adminUserService;
	@Autowired
	private AppInfoService appInfoService;

	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request) {
		if (SecurityUtils.getSubject().isAuthenticated()) {
			return main();
		}
		return new ModelAndView("login");
	}

	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			logger.info("用户" + ((BaseModel)subject.getPrincipal()).getStr("username") + "退出登录");
			subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存

		}
		return new ModelAndView("login");
	}

	@RequestMapping("/index")
	public ModelAndView index(@RequestParam(value = "userName", required = false) String username, @RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "rememberMe", required = false) String rememberMe, HttpServletResponse response, HttpServletRequest request) {
		
		if (SecurityUtils.getSubject().isAuthenticated()) {
			return main();
		}
		
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			request.setAttribute("message_login", "用户名或密码不能为空");
			return new ModelAndView("login");
		}

		
		UsernamePasswordToken token = new UsernamePasswordToken(username, EncryptUtil.encryptMd5(password));
		token.setRememberMe(true);
		logger.debug("为了验证登录用户而封装的token为" + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));
		// 获取当前的Subject
		Subject currentUser = SecurityUtils.getSubject();
		try {
			// 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
			// 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
			// 所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
			logger.debug("对用户[" + username + "]进行登录验证..验证开始");
			currentUser.login(token);
			logger.debug("对用户[" + username + "]进行登录验证..验证通过");
		} catch (UnknownAccountException uae) {
			logger.debug("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
			request.setAttribute("message_login", "未知账户");
		} catch (IncorrectCredentialsException ice) {
			logger.debug("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
			request.setAttribute("message_login", "密码不正确");
		} catch (LockedAccountException lae) {
			logger.debug("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
			request.setAttribute("message_login", "账户已锁定");
		} catch (ExcessiveAttemptsException eae) {
			logger.debug("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
			request.setAttribute("message_login", "用户名或密码错误次数过多");
		} catch (AuthenticationException ae) {
			// 通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
			logger.debug("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
			ae.printStackTrace();
			request.setAttribute("message_login", "用户名或密码不正确");
		}
		// 验证是否登录成功
		if (currentUser.isAuthenticated()) {
			logger.info("用户[" + username + "]登录认证通过");
			Subject subject = SecurityUtils.getSubject();
			request.getSession().setAttribute("fullname",((BaseModel)subject.getPrincipal()).getStr("fullname"));
			request.getSession().setAttribute("username", username);
			saveCookie(username, password, rememberMe, response);
			return main();
		} else {
			token.clear();
			return new ModelAndView("login");
		}
	}

	/**
	 * 后台首页面
	 * 
	 * @return
	 */
	private ModelAndView main() {
		ModelAndView modelAndView = new ModelAndView("index");

		StringBuilder listGroup = new StringBuilder("");
		int total = 0;
		int del = 0;
		// 拼装菜单
		for (BaseModel m : appInfoService.listApp()) {
			listGroup.append("<li><a href=");
			listGroup.append("'#' ");
			listGroup.append("onclick='showPage(\"admin/app/" + m.getLong("id") + "\",\"" + m.getStr("filePath") + "\")' ");
			if (m.getInt("isDel").intValue() == Constans.DEL_Y) {
				listGroup.append("style=\"text-decoration:line-through;color:red\"");
				del++;
			}

			listGroup.append(" ><span class='fa fa-caret-right'></span>");
			listGroup.append(m.getStr("name"));
			listGroup.append("</a></li>");
			total++;
		}

		modelAndView.addObject("listGroupModel", appInfoService.listApp());
		modelAndView.addObject("listGroup", listGroup.toString());
		modelAndView.addObject("total", total);
		modelAndView.addObject("del", del);
		modelAndView.addObject("runing", total - del);
		return modelAndView;
	}

	
/*	@RequestMapping("/index")
	public ModelAndView index(@RequestParam(value = "userName", required = false) String userName, @RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "rememberMe", required = false) String rememberMe, HttpServletResponse response, HttpServletRequest request) throws Exception {

		if (null != request.getSession().getAttribute("username") && StringUtils.isNotEmpty(request.getSession().getAttribute("username").toString())) {
			return main();
		}

		if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
			return new ModelAndView("login");
		}

		model = adminUserService.getByUserName(userName);
		boolean retMsg = false;

		if (null != model) {
			System.out.println(EncryptUtil.encryptMd5(password));
			if (EncryptUtil.encryptMd5(password).equals(model.getStr("password"))) {
				retMsg = true;
				saveCookie(userName, password, rememberMe, response);
				request.getSession().setAttribute("fullname", model.getStr("fullname"));
				request.getSession().setAttribute("username", model.getStr("username"));
				model = null;
			}
		}

		if (retMsg) {

			return main();
		} else {
			logger.info("userName:" + userName + "password:" + password);
			return new ModelAndView("login");
		}
	}*/
	private ModelAndView main1() {
		ModelAndView modelAndView = new ModelAndView("index1");

		StringBuilder listGroup = new StringBuilder("");
		for (BaseModel m : appInfoService.listApp()) {
			// listGroul.append(",status:").append(m.getInt("status"));
			// listGroul.append(",isDel:").append(m.getInt("isDel"));

			listGroup.append("<li ");
			if (m.getInt("isDel").intValue() == Constans.DEL_Y) {
				listGroup.append("style=\"text-decoration:line-through;color:red\"");
			}
			listGroup.append(" class='list-group-item' name='app_" + m.getLong("id") + "'>");
			listGroup.append(m.getStr("name"));
			listGroup.append("</li>");

		}
		modelAndView.addObject("listGroup", listGroup.toString());

		return modelAndView;
	}

	private void saveCookie(String userName, String password, String rememberMe, HttpServletResponse response) {
		if ("on".equals(rememberMe)) {
			Cookie cookie = new Cookie("cookie_user", userName + "-" + password + "-" + rememberMe);
			cookie.setMaxAge(2592000); // cookie 保存30天
			response.addCookie(cookie);
		} else {
			Cookie cookie = new Cookie("cookie_user", userName + "-" + null + "-" + null);
			cookie.setMaxAge(2592000); // cookie 保存30天
			response.addCookie(cookie);
		}

	}
}
