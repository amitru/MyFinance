package com.amitru.mystockalert.main;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class StockAlertContextProvider implements ApplicationContextAware {

	private static ApplicationContext context;

	public static ApplicationContext getApplicationContext() {
	      return context;
	}

	  public void setApplicationContext(ApplicationContext ac) throws BeansException {
		  context = ac;
		
	}
	
}
