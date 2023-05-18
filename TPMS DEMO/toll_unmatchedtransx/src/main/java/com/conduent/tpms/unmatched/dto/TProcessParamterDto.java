package com.conduent.tpms.unmatched.dto;

import java.time.LocalDateTime;

public class TProcessParamterDto 
{
	private int process_parameter_id;
	private int agency_id;
	private int plaza_id;
	private int store_id;
	private String param_name;
	private String param_group;
	private String param_code;
	private String param_value;
	private String param_desc;
	private Integer param_config;
	private String csc_lookup_key;
	private LocalDateTime update_ts;
	public int getProcess_parameter_id() {
		return process_parameter_id;
	}
	public void setProcess_parameter_id(int process_parameter_id) {
		this.process_parameter_id = process_parameter_id;
	}
	public int getAgency_id() {
		return agency_id;
	}
	public void setAgency_id(int agency_id) {
		this.agency_id = agency_id;
	}
	public int getPlaza_id() {
		return plaza_id;
	}
	public void setPlaza_id(int plaza_id) {
		this.plaza_id = plaza_id;
	}
	public int getStore_id() {
		return store_id;
	}
	public void setStore_id(int store_id) {
		this.store_id = store_id;
	}
	public String getParam_name() {
		return param_name;
	}
	public void setParam_name(String param_name) {
		this.param_name = param_name;
	}
	public String getParam_group() {
		return param_group;
	}
	public void setParam_group(String param_group) {
		this.param_group = param_group;
	}
	public String getParam_code() {
		return param_code;
	}
	public void setParam_code(String param_code) {
		this.param_code = param_code;
	}
	public String getParam_value() {
		return param_value;
	}
	public void setParam_value(String param_value) {
		this.param_value = param_value;
	}
	public String getParam_desc() {
		return param_desc;
	}
	public void setParam_desc(String param_desc) {
		this.param_desc = param_desc;
	}
	public Integer getParam_config() {
		return param_config;
	}
	public void setParam_config(Integer param_config) {
		this.param_config = param_config;
	}
	public String getCsc_lookup_key() {
		return csc_lookup_key;
	}
	public void setCsc_lookup_key(String csc_lookup_key) {
		this.csc_lookup_key = csc_lookup_key;
	}
	public LocalDateTime getUpdate_ts() {
		return update_ts;
	}
	public void setUpdate_ts(LocalDateTime update_ts) {
		this.update_ts = update_ts;
	}
	
	

}
