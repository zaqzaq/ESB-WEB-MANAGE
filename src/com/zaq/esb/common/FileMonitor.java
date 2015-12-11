package com.zaq.esb.common;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import com.zaq.esb.service.impl.AppInfoService;
import com.zaq.esb.util.AppUtil;
/**
 * 文件监听器工具类
 * @author zaqzaq
 * 2015年12月9日
 *
 */
public class FileMonitor {
	private static Logger logger=Logger.getLogger(FileMonitor.class);

	private static Map<String, AppListenerForOsFS> map=new HashMap<String, AppListenerForOsFS>();
	
	/**
	 * 添加一个监听器
	 * @param listenerForOsFS
	 */
	public static void addListener(AppListenerForOsFS listenerForOsFS){
		map.put(listenerForOsFS.getListenerPath(), listenerForOsFS);
		listenerForOsFS.start();
		
		newApp(listenerForOsFS.getListenerPath());
	}
	/**
	 * 删除监听器
	 * @param path
	 */
	public static void delListener(String path){
		AppListenerForOsFS appListener=map.get(path);
		if(null!=appListener){
			appListener.donotListener();
			map.remove(path);
		}
		delApp(path);
	}
	
	
	/**
	 * 以下为对 app 或 xml的操作
	 */
	
	protected static void updateAppXml(String path, String listenerPath) {
		AppInfoService appInfoService=(AppInfoService) AppUtil.getBean("appInfoService");
		BaseModel appInfo=appInfoService.getByFilePath(path);
		
		if(null==appInfo){
			newAppXml(path, listenerPath);
		}else{
			appInfo.set("timeStart", new Date());
			appInfo.set("status", Constans.STATUS_Y);
			appInfo.set("isDel", Constans.DEL_N);
			appInfoService.update(appInfo);
		}
		
	}
	protected static void delAppOrXml(String path) {
		AppInfoService appInfoService=(AppInfoService) AppUtil.getBean("appInfoService");
		BaseModel appInfo=new BaseModel();
		appInfo.set("filePath", path);
		appInfoService.del(appInfo);;
	}

	/**
	 * 创建一个APPxml
	 * @param path
	 * @param listenerPath
	 * @param userLastUpdate
	 */
	protected static void newAppXml(String path, String listenerPath) {
		AppInfoService appInfoService=(AppInfoService) AppUtil.getBean("appInfoService");
		BaseModel appInfo=appInfoService.getByFilePath(path);
		if(null!=appInfo){
			 updateAppXml(path, listenerPath);
		}else{
			appInfo=new BaseModel();
			appInfo.set("parentId", appInfoService.getByFilePath(listenerPath).getLong("id"));
			appInfo.set("filePath", path);
			appInfo.set("status", Constans.STATUS_Y);
			appInfo.set("timeStart", new Date());
			appInfo.set("flowFuns", "未描述");
			appInfo.set("name", FilenameUtils.getBaseName(path));
			
			appInfoService.add(appInfo);
		}
	}

	protected synchronized static void newApp(String path) {
		AppInfoService appInfoService=(AppInfoService) AppUtil.getBean("appInfoService");
		BaseModel appInfo=appInfoService.getByFilePath(path);
		if(null!=appInfo){
			appInfo.set("timeStart", new Date());
			appInfo.set("status", Constans.STATUS_Y);
			appInfo.set("isDel", Constans.DEL_N);
			appInfoService.update(appInfo);
		}else{
			appInfo=new BaseModel();
			appInfo.set("filePath", path);
			appInfo.set("status", Constans.STATUS_Y);
			appInfo.set("timeStart", new Date());
			appInfo.set("name", FilenameUtils.getBaseName(path));
			
			appInfoService.add(appInfo);
			//同时初始化一下新加的app的xml  解决在copy文件夹时 监听器还未启动而xml已copy完成的BUG
			for(File ff:new File(path).listFiles()){
    			if(ff.isFile()&&ff.getName().endsWith(".xml")){
    				logger.info("初始化xml："+ff.getAbsolutePath());
    				newAppXml(ff.getAbsolutePath(),path);
    			}
    		}
		}
	}

	protected static void delApp(String path) {
		delAppOrXml(path);		
	}
}
