package com.ssic.game.app.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class InitApplicationContext implements ServletContextListener {

	protected static final Log logger = LogFactory.getLog(InitApplicationContext.class);
	
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("###### game-app application start ######");

	}

	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
