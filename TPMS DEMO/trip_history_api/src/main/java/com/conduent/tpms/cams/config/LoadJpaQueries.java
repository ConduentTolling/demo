package com.conduent.tpms.cams.config;

import java.util.Map;


public class LoadJpaQueries {

	private static Map<String, String> queryMap = null;
	
	private static Map<String, String> queryMapReport = null;
	
	private LoadJpaQueries() {
		
	}
	
	@SuppressWarnings("unchecked")
	synchronized static public Map<String, String> getQueriesMap() throws Exception {
		if (queryMap == null) {
			Object obj = LoadJpaApplicationContext.getApplicationContext().getBean("queries");
			if(obj != null) {
				queryMap = (Map<String, String>) obj;
			} else {
				throw new Exception("Core Framework Database Queries file do not contain queries tag");
			}
		}
		return queryMap;
	}
	
	static public String getQueryById(String queryId) {
		String query = null;
		try {
			query = getQueriesMap().get(queryId);
		} catch (Exception e) {
			
		}
		return query;
	}
	
	/**
	 * @Function : to load xml based on name of bean (bean name mapped with xml bean id param)
	 * @param queryBean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	synchronized static public Map<String, String> getQueriesMap(String queryBean) throws Exception {
		if (queryMapReport == null) {
			Object obj = LoadJpaApplicationContext.getApplicationContext().getBean(queryBean);
			if(obj != null) {
				queryMapReport = (Map<String, String>) obj;
			} else {
				throw new Exception("Core Framework Database Queries file do not contain queries tag");
			}
		}
		return queryMapReport;
	}
	
	
	/**
	 * @Function : to fetch the query from passed querybean (bean name mapped with xml bean id param)
	 * @param queryBean
	 * @param queryId
	 * @return
	 */
	static public String getQueryById(String queryBean, String queryId) {
		String query = null;
		try {
			query = getQueriesMap(queryBean).get(queryId);
		} catch (Exception e) {
			
		}
		return query;
	}
}
