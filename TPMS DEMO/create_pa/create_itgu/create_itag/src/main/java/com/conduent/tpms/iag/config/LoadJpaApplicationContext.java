package com.conduent.tpms.iag.config;




import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author urvashic
 *
 */

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
