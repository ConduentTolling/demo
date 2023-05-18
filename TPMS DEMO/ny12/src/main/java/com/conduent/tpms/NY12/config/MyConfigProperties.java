package com.conduent.tpms.NY12.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="spring.datasource")
public class MyConfigProperties {
	@Value("{spring.datasource.url}")
	private String url;
	@Value("{spring.datasource.username}")
	private String username;
    @Value("{spring.datasource.password}")
	private String password;
    @Value("{spring.datasource.driverClassName}")
	private String driverClassName;
    
    @Value("{username}")
	private String userName2;
	
    public String getUserName2() {
		return userName2;
	}
	public void setUserName2(String userName2) {
		this.userName2 = userName2;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDriverClassName() {
		return driverClassName;
	}
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	public MyConfigProperties() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public MyConfigProperties(String url, String username, String password, String driverClassName, String userName2) {
		super();
		this.url = url;
		this.username = username;
		this.password = password;
		this.driverClassName = driverClassName;
		this.userName2 = userName2;
	}
	@Override
	public String toString() {
		return "MyConfigProperties [url=" + url + ", username=" + username + ", password=" + password
				+ ", driverClassName=" + driverClassName + ", userName2=" + userName2 + "]";
	}
	

}