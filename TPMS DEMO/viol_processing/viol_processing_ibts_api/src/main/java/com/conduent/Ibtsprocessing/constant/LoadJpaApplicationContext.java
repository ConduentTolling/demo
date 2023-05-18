package com.conduent.Ibtsprocessing.constant;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LoadJpaApplicationContext {
	
	private static ApplicationContext applicationContext = null;
	private static final String DATABASE_QUERY = "database-queries.xml";

	private LoadJpaApplicationContext() {
		
	}
	synchronized static public ApplicationContext getApplicationContext() {
		if (applicationContext == null) {		
			applicationContext = new ClassPathXmlApplicationContext(new String[] {DATABASE_QUERY});			
		}
		return applicationContext;
	}

}
