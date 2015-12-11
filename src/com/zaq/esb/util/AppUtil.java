package com.zaq.esb.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;
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
	
}
