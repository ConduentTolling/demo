package com.conduent.tpms.qatp.model;

import com.google.gson.annotations.Expose;

public class AtpFileIdObject {
		
	@Expose(serialize = true, deserialize = true)
	private Long atpFileId;
	@Expose(serialize = true, deserialize = true)
	private String fileType;
		
		public Long getAtpFileId() {
			return atpFileId;
		}
		public void setAtpFileId(Long atpFileId) {
			this.atpFileId = atpFileId;
		}
		public String getFileType() {
			return fileType;
		}
		public void setFileType(String fileType) {
			this.fileType = fileType;
		}
		
		@Override
		public String toString() {
			return "AtpFileIdObject [atpFileId=" + atpFileId + ", fileType=" + fileType + "]";
		}
	
		
}
