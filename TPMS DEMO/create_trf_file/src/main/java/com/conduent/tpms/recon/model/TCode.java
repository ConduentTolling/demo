package com.conduent.tpms.recon.model;

/**
 * 
 * Model class for T_Code table
 * 
 * @author taniyan
 *
 */
public class TCode {

		private String codeType;
		private Integer codeId;
		private String codeValue;
		

		public String getCodeType() {
			return codeType;
		}

		public void setCodeType(String codeType) {
			this.codeType = codeType;
		}

		public Integer getCodeId() {
			return codeId;
		}

		public void setCodeId(Integer codeId) {
			this.codeId = codeId;
		}

		public String getCodeValue() {
			return codeValue;
		}

		public void setCodeValue(String codeValue) {
			this.codeValue = codeValue;
		}

		@Override
		public String toString() {
			return "TCode [codeType=" + codeType + ", codeId=" + codeId + ", codeValue=" + codeValue + "]";
		}

}
