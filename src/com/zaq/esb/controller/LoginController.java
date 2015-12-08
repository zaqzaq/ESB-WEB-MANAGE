/**
 * 
 */
package com.zaq.esb.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.zaq.esb.common.BaseController;
import com.zaq.esb.service.impl.AdminUserService;
import com.zaq.esb.util.EncryptUtil;

@Controller
@Scope("prototype")
public class LoginController extends BaseController {
	@Autowired
	private AdminUserService adminUserService;

	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request) {
		return new ModelAndView("login");
	}

	@RequestMapping("/index")
	public ModelAndView index(@RequestParam(value = "userName", required = false) String userName, @RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "rememberMe", required = false) String rememberMe, HttpServletResponse response, HttpServletRequest request) throws Exception {
		// boolean retMsg = cloudUserService.isPASS(userName, password);
		if(null!=request.getSession().getAttribute("username")&&StringUtils.isEmpty(request.getSession().getAttribute("username").toString())){
			return new ModelAndView("index");
		}
		model = adminUserService.getByUserName(userName);
		boolean retMsg = false;
		
		if(null!=model){
			System.out.println(EncryptUtil.encryptMd5(password));
			if(EncryptUtil.encryptMd5(password).equals(model.get("password"))){
				retMsg=true;
				saveCookie(userName, password, rememberMe, response);
				request.getSession().setAttribute("fullname", model.get("fullname"));
				request.getSession().setAttribute("username", model.get("username"));
				model=null;
			}
		}
		
		if (retMsg) {
			
			return new ModelAndView("index");
		} else {
			logger.info("userName:" + userName + "password:" + password);
			return new ModelAndView("login");
		}
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
