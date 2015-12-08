package com.zaq.esb.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zaq.esb.common.BaseController;

@Controller
@Scope("prototype")
public class ESBController extends BaseController{

	/**
	 * 获取所有APP的详情
	 * @param request
	 * @return
	 */
	@RequestMapping("/admin/app/list")
	public String listApps(HttpServletRequest request) {
		return "";
	}
	
	/**
	 * 获取appId应用下部署的所有xml
	 * @param appId
	 * @param request
	 * @return
	 */
	@RequestMapping("/admin/app/{appId}/listXml")
	public String listXml(@PathVariable("appId") String appId,HttpServletRequest request) {
		
		return "";
	}
}
