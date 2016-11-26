package com.ssic.catering.jobs.boot;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

public class Bootstrap {
	
	protected static final Log logger = LogFactory.getLog(Bootstrap.class);

	public static void main(String[] args) {
	    logger.info(" catering jobs task start ");
	    ApplicationContext context = ApplicationContextFactory.getApplicationContext();
	    
	    
	}

}
