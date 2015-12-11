package com.zaq.esb.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

/**
 * ESB部署的应用的文件监听器
 * 
 * @author zaqzaq 2015年12月8日
 * 
 */
public class AppListenerForOsFS extends Thread {
	private static Logger logger=Logger.getLogger(AppListenerForOsFS.class);
	private String listenerPath;//监听的绝对路径
	private WatchService watcher =null;
	private boolean isRoot=false;//是否为根目录
	private boolean flag=true;//监听开关
	
	public AppListenerForOsFS(String path){
		this(path,false);
	}
	public AppListenerForOsFS(String path,boolean isRoot){
		this.setDaemon(true);
		this.listenerPath = path;
		this.isRoot=isRoot;
		Path myDir = Paths.get(listenerPath);
		try {
			watcher = myDir.getFileSystem().newWatchService();
			if(isRoot){
				myDir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);
			}else{
				myDir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
			}
		} catch (IOException e) {
			logger.error(listenerPath+"文件监听启动异常", e);
			donotListener();
		}
		
	}
	/**
	 * 停止监听
	 */
	public void donotListener(){
		flag=false;
	}
	
	@Override
	public void run() {
		try {
			
			while(flag){
				WatchKey watchKey = watcher.take();
				List<WatchEvent<?>> events = watchKey.pollEvents();
				for (WatchEvent<?> event : events) {
					String path=FilenameUtils.concat(listenerPath, event.context().toString());

					if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
						if(isRoot){
							if(new File(path).isDirectory()){
								//新建了一个APP应用
								 FileMonitor.addListener(new AppListenerForOsFS(path));
							}
						}else{
							if(event.context().toString().endsWith(".xml")){
								// 新建了一个App的xml配置
								FileMonitor.newAppXml(path,listenerPath);
							}
						}
					}
					if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
						if(isRoot){
//							if(new File(path).isDirectory()){
								//删除了一个APP应用
								FileMonitor.delListener(path);
//							}
							
						}else{
							if(event.context().toString().endsWith(".xml")){
								//删除了一个App的xml配置
								FileMonitor.delAppOrXml(path);
							}
						}
					}
					if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
						//子应用的文件修改
						if(event.context().toString().endsWith(".xml")){
							// 应用的xml修改了
							FileMonitor.updateAppXml(path,listenerPath);
						}
						
					}
					logger.info(("["+listenerPath+"/"+event.context()+"]文件发生了["+event.kind()+"]事件"));  
				}
				watchKey.reset(); 
			}

		} catch (Exception e) {
			logger.error(listenerPath+"文件监听异常", e);
		}finally{
			if(null!=watcher){
				try {
					watcher.close();
				} catch (IOException e) {
					logger.error(listenerPath+"文件监听异常后关闭又日了TMD", e);
				}
			}
			//重新开启一个线程监听
			if(flag){
				FileMonitor.addListener(new AppListenerForOsFS(listenerPath));
			}
		}

	}
	public String getListenerPath() {
		return listenerPath;
	}
}
