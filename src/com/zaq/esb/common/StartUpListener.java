/**
 * 
 */
package com.zaq.esb.common;

import java.io.File;

import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoaderListener;

import com.zaq.esb.util.AppUtil;

public class StartUpListener extends ContextLoaderListener {
	private Logger logger=Logger.getLogger(getClass());
  @Override
  public void contextInitialized(ServletContextEvent event) {
    super.contextInitialized(event);
    event.getServletContext().setAttribute("ctx","/"+event.getServletContext().getServletContextName());
    String rootPath=AppUtil.getPropertity("listenerPath");
    
    new AppListenerForOsFS(rootPath,true).start();//监听root路径
    
    
    for(File f:new File(rootPath).listFiles()){
    	if(f.isDirectory()){
    		logger.info("初始化app："+f.getAbsolutePath());
    		FileMonitor.addListener(new AppListenerForOsFS(f.getAbsolutePath()));
    		
    		for(File ff:f.listFiles()){
    			if(ff.isFile()&&ff.getName().endsWith(".xml")){
    				logger.info("初始化xml："+ff.getAbsolutePath());
    				FileMonitor.newAppXml(ff.getAbsolutePath(), f.getAbsolutePath());
    			}
    		}
    		
    	}
    }
    
  }
}
