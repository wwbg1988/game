package com.ssic.game.ims.boot;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Bootstrap {
	
	protected static final Log logger = LogFactory.getLog(Bootstrap.class);

	public static void main(String[] args) {
	    com.ssic.sync.Bootstrap.start();
	    
	}

}
