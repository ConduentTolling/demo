package com.conduent.transactionSearch.dto;

import java.io.Serializable;
import java.util.List;

public class MappingInfoDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8737737456682867089L;
	private String fieldName;
	private String fieldType;
	private Long startPosition;
	private Long endPosition;
	private String validationType;
	private String validationValue;
	private Long fieldIndexPosition;
	private Long minRangeValue;
	private Long maxRangeValue;
	private List<String> listOfValidValues;
	private String fixeddValidValue;
	private String isPojoValue;
	private String defaultValue;
	private String characterEncoding;
	private String fileLevelRejection;


	@Override
	public String toString() {
		return "MappingInfoDto [fieldName=" + fieldName + ", fieldType=" + fieldType + ", startPosition="
				+ startPosition + ", endPosition=" + endPosition + ", validationType=" + validationType
				+ ", validationValue=" + validationValue + ", fieldIndexPosition=" + fieldIndexPosition
				+ ", minRangeValue=" + minRangeValue + ", maxRangeValue=" + maxRangeValue + ", listOfValidValues="
				+ listOfValidValues + ", fixeddValidValue=" + fixeddValidValue + ", isPojoValue=" + isPojoValue
				+ ", defaultValue=" + defaultValue + ", characterEncoding=" + characterEncoding
				+ ", fileLevelRejection=" + fileLevelRejection + "]";
	}

	public String getIsPojoValue() {
		return isPojoValue;
	}

	public void setIsPojoValue(String isPojoValue) {
		this.isPojoValue = isPojoValue;
	}

	public String getFixeddValidValue() {
		return fixeddValidValue;
	}

	public void setFixeddValidValue(String fixeddValidValue) {
		this.fixeddValidValue = fixeddValidValue;
	}

	public Long getMinRangeValue() {
		return minRangeValue;
	}

	public void setMinRangeValue(Long minRangeValue) {
		this.minRangeValue = minRangeValue;
	}

	public Long getMaxRangeValue() {
		return maxRangeValue;
	}

	public void setMaxRangeValue(Long maxRangeValue) {
		this.maxRangeValue = maxRangeValue;
	}

	public List<String> getListOfValidValues() {
		return listOfValidValues;
	}

	public void setListOfValidValues(List<String> listOfValidValues) {
		this.listOfValidValues = listOfValidValues;
	}

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

	public Long getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(Long startPosition) {
		this.startPosition = startPosition;
	}

	public Long getEndPosition() {
		return endPosition;
	}

	public void setEndPosition(Long endPosition) {
		this.endPosition = endPosition;
	}

	public String getValidationType() {
		return validationType;
	}

	public void setValidationType(String validationType) {
		this.validationType = validationType;
	}

	public String getValidationValue() {
		return validationValue;
	}

	public void setValidationValue(String validationValue) {
		this.validationValue = validationValue;
	}

	public Long getFieldIndexPosition() {
		return fieldIndexPosition;
	}

	public void setFieldIndexPosition(Long fieldIndexPosition) {
		this.fieldIndexPosition = fieldIndexPosition;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getCharacterEncoding() {
		return characterEncoding;
	}

	public void setCharacterEncoding(String characterEncoding) {
		this.characterEncoding = characterEncoding;
	}
	public String getFileLevelRejection() {
		return fileLevelRejection;
	}

	public void setFileLevelRejection(String fileLevelRejection) {
		this.fileLevelRejection = fileLevelRejection;
	}

}
