package com.conduent.tpms.qatp.classification.model;

public class AtpFileIdObject {
	
	  private Long atpFileId;
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
          return "[atpFileId:" + atpFileId + ", fileType:" + fileType + "]";
      }

}
