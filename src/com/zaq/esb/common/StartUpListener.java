/**
 * 
 */
package com.zaq.esb.common;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

public class StartUpListener extends ContextLoaderListener {
  @Override
  public void contextInitialized(ServletContextEvent event) {
    super.contextInitialized(event);
    event.getServletContext().setAttribute("ctx","/"+event.getServletContext().getServletContextName());
  }

	@Override
	protected Class<?> determineContextClass(ServletContext servletContext) {
		Thread thread= new AppListenerForOsFS();
	    thread.setDaemon(true);
	    thread.start();
		return super.determineContextClass(servletContext);
	}

}
