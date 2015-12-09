package com.zaq.esb.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.zaq.esb.common.BaseModel;
import com.zaq.esb.common.Constans;
import com.zaq.esb.service.impl.AppInfoService;

public class AppUtil implements ApplicationContextAware{
	private static ApplicationContext appContext;
	private static Logger logger=Logger.getLogger(AppUtil.class);
	private static Properties props ;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		appContext = applicationContext;
		//for change the config info to type of 'first load database info then load config file infos to override' on 2011-07-08 end
		props = new Properties();
		try {
			InputStream is = new BufferedInputStream(new FileInputStream(PathUtil.instance().getPath("app.properties")));
			props.load(is);
		} catch (Exception ex) {
			logger.error("app.properties 文件加载失败",ex);
			
			System.exit(0);
		}
	}
	
	public static String getPropertity(String string) {
		return props.getProperty(string, "");
	}
	
	public static String getPropertity(String string,String dfStr) {
		return props.getProperty(string, dfStr);
	}
	
	public static int getPropertity(String string,int dfInt) {
		return Integer.valueOf(props.getProperty(string, dfInt+""));
	}
	
	public static Object getBean(String beanId) {
		return appContext.getBean(beanId);
	}

	
	
	protected static void updateAppXml(String path, String listenerPath) {
		updateAppXml(path, listenerPath, null);
	}
	protected static void updateAppXml(String path, String listenerPath,String userLastUpdate) {
		AppInfoService appInfoService=(AppInfoService) getBean("appInfoService");
		BaseModel appInfo=appInfoService.getByFilePath(path);
		
		if(null==appInfo){
			newAppXml(path, listenerPath, userLastUpdate);
		}else{
			if(!StringUtils.isEmpty(userLastUpdate)){
				appInfo.set("userLastUpdate", userLastUpdate);
			}
			appInfo.set("timeStart", new Date());
			appInfo.set("status", Constans.STATUS_Y);
			appInfo.set("isDel", Constans.DEL_N);
			appInfo.set("flowFuns", "准备解析xml文件获取flow节点的name值");
			appInfoService.update(appInfo);
		}
		
	}
	protected static void delAppXml(String path) {
		AppInfoService appInfoService=(AppInfoService) getBean("appInfoService");
		BaseModel appInfo=new BaseModel();
		appInfo.set("filePath", path);
		appInfoService.del(appInfo);;
	}

	public static void newAppXml(String path, String listenerPath) {
		newAppXml(path, listenerPath, null);
	}
	/**
	 * 创建一个APPxml
	 * @param path
	 * @param listenerPath
	 * @param userLastUpdate
	 */
	protected static void newAppXml(String path, String listenerPath,String userLastUpdate) {
		AppInfoService appInfoService=(AppInfoService) getBean("appInfoService");
		BaseModel appInfo=appInfoService.getByFilePath(path);
		if(null!=appInfo){
			 updateAppXml(path, listenerPath, userLastUpdate);
		}else{
			appInfo=new BaseModel();
			appInfo.set("parentId", appInfoService.getByFilePath(listenerPath).getLong("id"));
			appInfo.set("filePath", path);
			appInfo.set("status", Constans.STATUS_Y);
			appInfo.set("timeStart", new Date());
			appInfo.set("flowFuns", "准备解析xml文件获取flow节点的name值");
			appInfo.set("userLastUpdate", userLastUpdate);
			
			appInfoService.add(appInfo);
		}
	}

	protected static void updateApp(String path,String userLastUpdate) {
		AppInfoService appInfoService=(AppInfoService) getBean("appInfoService");
		BaseModel appInfo=appInfoService.getByFilePath(path);
		if(!StringUtils.isEmpty(userLastUpdate)){
			appInfo.set("userLastUpdate", userLastUpdate);
		}
		appInfo.set("timeStart", new Date());
		appInfo.set("status", Constans.STATUS_Y);
		appInfo.set("isDel", Constans.DEL_N);
		appInfoService.update(appInfo);
	}
	
	protected static void newApp(String path){
		newApp(path, null);
	}
	
	protected synchronized static void newApp(String path,String userLastUpdate) {
		AppInfoService appInfoService=(AppInfoService) getBean("appInfoService");
		BaseModel appInfo=appInfoService.getByFilePath(path);
		if(null!=appInfo){
			 updateAppXml(path, userLastUpdate);
		}else{
			appInfo=new BaseModel();
			appInfo.set("filePath", path);
			appInfo.set("status", Constans.STATUS_Y);
			appInfo.set("timeStart", new Date());
			appInfo.set("userLastUpdate", userLastUpdate);
			
			appInfoService.add(appInfo);
			//同时初始化一下新加的app的xml  解决在copy文件夹时 监听器还未启动而xml已copy完成的BUG
			for(File ff:new File(path).listFiles()){
    			if(ff.isFile()&&ff.getName().endsWith(".xml")){
    				logger.info("初始化xml："+ff.getAbsolutePath());
    				AppUtil.newAppXml(ff.getAbsolutePath(),path);
    			}
    		}
		}
	}

	protected static void delApp(String path) {
		delAppXml(path);		
	}
}
