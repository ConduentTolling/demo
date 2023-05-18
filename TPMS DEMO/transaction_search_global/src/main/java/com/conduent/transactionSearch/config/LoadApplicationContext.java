package com.conduent.transactionSearch.config;




import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LoadApplicationContext {

	private static ApplicationContext applicationContext = null;
	private static final String DATABASE_QUERY = "database-queries.xml";

	public LoadApplicationContext() {
		
	}
	synchronized static public ApplicationContext getApplicationContext() {
		if (applicationContext == null) {		
			applicationContext = new ClassPathXmlApplicationContext(new String[] {DATABASE_QUERY});			
		}
		return applicationContext;
	}
}
