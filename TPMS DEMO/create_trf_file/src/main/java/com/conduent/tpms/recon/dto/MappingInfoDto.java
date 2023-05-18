package com.conduent.tpms.recon.dto;

import java.io.Serializable;

public class MappingInfoDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8737737456682867089L;
	private String fieldName;
	private String fieldType;
	private int fieldLength;
	private Long  fieldIndexPosition;
	private String padCharacter;
	private String justification;
	private String format;
	private String defaultValue;
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public int getFieldLength() {
		return fieldLength;
	}
	public void setFieldLength(int fieldLength) {
		this.fieldLength = fieldLength;
	}
	public Long getFieldIndexPosition() {
		return fieldIndexPosition;
	}
	public void setFieldIndexPosition(Long fieldIndexPosition) {
		this.fieldIndexPosition = fieldIndexPosition;
	}
	public String getPadCharacter() {
		return padCharacter;
	}
	public void setPadCharacter(String padCharacter) {
		this.padCharacter = padCharacter;
	}
	public String getJustification() {
		return justification;
	}
	public void setJustification(String justification) {
		this.justification = justification;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	@Override
	public String toString() {
		return "MappingInfoDto [fieldName=" + fieldName + ", fieldType=" + fieldType + ", fieldLength=" + fieldLength
				+ ", fieldIndexPosition=" + fieldIndexPosition + ", padCharacter=" + padCharacter + ", justification="
				+ justification + ", format=" + format + ", defaultValue=" + defaultValue + "]";
	}
	
}
