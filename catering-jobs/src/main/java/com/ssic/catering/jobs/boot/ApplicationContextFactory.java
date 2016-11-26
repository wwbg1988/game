package com.ssic.catering.jobs.boot;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextFactory {
	
	public  ApplicationContextFactory(){
	}

	private static ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:/spring-config/applicationContext.xml");
	
	public static ApplicationContext getApplicationContext(){
		return context;
	}	
	
}
