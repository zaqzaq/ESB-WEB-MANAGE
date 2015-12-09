package com.zaq.esb.util;

import java.util.HashMap;
import java.util.Map;
/**
 * 文件监听器工具类
 * @author zaqzaq
 * 2015年12月9日
 *
 */
public class FileMonitorUtil {
	private static Map<String, AppListenerForOsFS> map=new HashMap<String, AppListenerForOsFS>();
	
	/**
	 * 添加一个监听器
	 * @param listenerForOsFS
	 */
	public static void addListener(AppListenerForOsFS listenerForOsFS){
		map.put(listenerForOsFS.getListenerPath(), listenerForOsFS);
		listenerForOsFS.start();
		
		AppUtil.newApp(listenerForOsFS.getListenerPath());
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
		AppUtil.delApp(path);
	}
}
