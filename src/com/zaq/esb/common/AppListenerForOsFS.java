package com.zaq.esb.common;

import com.zaq.esb.util.AppUtil;

/**
 * ESB部署的应用的文件监听器
 * @author zaqzaq
 * 2015年12月8日
 *
 */
public class AppListenerForOsFS extends Thread{

	@Override
	public void run() {
		String listenerPath= AppUtil.getPropertity("listenerPath");
		
		
	}
}
