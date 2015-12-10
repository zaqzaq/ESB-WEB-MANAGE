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
		return new ModelAndView("login");
	}

	@RequestMapping("/index")
	public ModelAndView index(@RequestParam(value = "userName", required = false) String userName, @RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "rememberMe", required = false) String rememberMe, HttpServletResponse response, HttpServletRequest request) throws Exception {
		
		if(StringUtils.isEmpty(userName)||StringUtils.isEmpty(password)){
			return new ModelAndView("login");
		}
		
		if(null!=request.getSession().getAttribute("username")&&StringUtils.isEmpty(request.getSession().getAttribute("username").toString())){
			return main();
		}
		
		model = adminUserService.getByUserName(userName);
		boolean retMsg = false;
		
		if(null!=model){
			System.out.println(EncryptUtil.encryptMd5(password));
			if(EncryptUtil.encryptMd5(password).equals(model.getStr("password"))){
				retMsg=true;
				saveCookie(userName, password, rememberMe, response);
				request.getSession().setAttribute("fullname", model.getStr("fullname"));
				request.getSession().setAttribute("username", model.getStr("username"));
				model=null;
			}
		}
		
		if (retMsg) {
			
			return main();
		} else {
			logger.info("userName:" + userName + "password:" + password);
			return new ModelAndView("login");
		}
	}

	/**
	 * 后台首页面
	 * @return
	 */
	private ModelAndView main(){
		ModelAndView modelAndView=new ModelAndView("index");
		
		StringBuilder listGroup=new StringBuilder("");
		int total=0;
		int del=0;
		//拼装菜单
		for(BaseModel m: appInfoService.listApp()) {
			listGroup.append("<li><a href=");
			listGroup.append("'#' ");
			listGroup.append("onclick='showPage(\"admin/app/"+m.getLong("id")+"\")' ");
			if(m.getInt("isDel").intValue()==Constans.DEL_Y){
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
		modelAndView.addObject("runing", total-del);
		return modelAndView;
	}
	
	private ModelAndView main1(){
		ModelAndView modelAndView=new ModelAndView("index1");
		
		StringBuilder listGroup=new StringBuilder("");
		for(BaseModel m: appInfoService.listApp()) {
//			listGroul.append(",status:").append(m.getInt("status"));
//			listGroul.append(",isDel:").append(m.getInt("isDel"));
			
			listGroup.append("<li ");
			if(m.getInt("isDel").intValue()==Constans.DEL_Y){
				listGroup.append("style=\"text-decoration:line-through;color:red\"");
			}
			listGroup.append(" class='list-group-item' name='app_"+m.getLong("id")+"'>");
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
