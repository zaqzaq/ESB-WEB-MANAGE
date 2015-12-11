package com.zaq.esb.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zaq.esb.common.BaseController;
import com.zaq.esb.common.BaseModel;
import com.zaq.esb.common.Constans;
import com.zaq.esb.service.impl.AppInfoService;

@Controller
@Scope("prototype")
@RequestMapping("/admin")
public class ESBController extends BaseController{
	@Autowired
	private AppInfoService appInfoService;
	/**
	 * 获取所有APP的详情
	 * @param request
	 * @return
	 */
//	@RequestMapping(value="/app/list",produces="text/json;charset=UTF-8")
	@RequestMapping("/app/list")
	@ResponseBody
	public String listApps(HttpServletRequest request) {
		StringBuilder retVal=new StringBuilder("[");
		for(BaseModel m: appInfoService.listApp()) {
			retVal.append("{");
			retVal.append("id:").append(m.getLong("id"));
			retVal.append(",name:'").append(m.getStr("name")).append("'");
			retVal.append(",status:").append(m.getInt("status"));
			retVal.append(",isDel:").append(m.getInt("isDel"));
			retVal.append("},");
		}
		if(retVal.length()>1){
			retVal.deleteCharAt(retVal.length()-1);
		}
		retVal.append("]");
		return retVal.toString();
	}
	
	
	@RequestMapping("app/xml/updateDeploy")
	@ResponseBody
	public Boolean updateDeploy(@RequestParam(value = "id", required = true) Long id,
			   				@RequestParam(value = "xml", required = true) String xml,
							HttpServletRequest request) {
		
		BaseModel appInfo=appInfoService.getById(id);
		
		String xmlFilePath=appInfo.getStr("filePath");
		
		try {
			FileUtils.writeStringToFile(new File(xmlFilePath), xml);
		} catch (IOException e) {
			logger.error("重新部署失败："+xmlFilePath, e);
			return false;
		}
		
		/*文件监听器会处理
 			appInfo.set("timeStart", new Date());
			appInfo.set("isDel", Constans.DEL_N);
		 */
		appInfo.set("userLastUpdate", request.getSession().getAttribute("fullname"));
		appInfoService.update(appInfo);
		
		return true;
	}
	
	@RequestMapping("app/xml/updateInfo")
	@ResponseBody
	public Boolean updateInfo(@RequestParam(value = "id", required = true) Long id,
							   @RequestParam(value = "name", required = true) String name,
							   @RequestParam(value = "flowFuns", required = true) String flowFuns,
							   @RequestParam(value = "remark", required = false) String remark,
								HttpServletRequest request) {
		BaseModel appInfo=appInfoService.getById(id);
		appInfo.set("name", name);
		appInfo.set("flowFuns", flowFuns);
		appInfo.set("remark", remark);
		appInfo.set("userLastUpdate", request.getSession().getAttribute("fullname"));
		appInfoService.update(appInfo);
		
		return true;
	}
	
	/**
	 * 进入APP 的xml列表页面
	 * @param appId
	 * @param request
	 * @return
	 */
	@RequestMapping("/app/{appId}")
	private ModelAndView app(@PathVariable("appId") Long appId,HttpServletRequest request){
		ModelAndView modelAndView=new ModelAndView("app");
		List<BaseModel> list= appInfoService.getByApp(appId);
		modelAndView.addObject("list", list);
		return modelAndView;
	}
	/**
	 * 进入xml的详情页面
	 * @param xmlId
	 * @param request
	 * @return
	 */
	@RequestMapping("/app/xml/{xmlId}")
	private ModelAndView xml(@PathVariable("xmlId") Long xmlId,HttpServletRequest request){
		ModelAndView modelAndView=new ModelAndView("xml");
		BaseModel model=appInfoService.getById(xmlId);
		if(null!=model){
			modelAndView.addObject("name", model.getStr("name"));
			modelAndView.addObject("flowFuns", model.getStr("flowFuns"));
			modelAndView.addObject("remark", model.getStr("remark"));
			modelAndView.addObject("id", model.getLong("id"));
			try {
				modelAndView.addObject("xml", FileUtils.readFileToString(
									new File(model.getStr("filePath")), "utf-8"));
			} catch (IOException e) {
				modelAndView.addObject("xml","error.....xml文件不存在！");
			}
		}
		
		return modelAndView;
	}
	
	/**
	 * 获取appId应用下部署的所有xml
	 * @param appId
	 * @param request
	 * @return
	 */
	@RequestMapping("/app/{appId}/listXml")
	public String listXml(@PathVariable("appId") String appId,HttpServletRequest request) {
		
		return "";
	}
}
