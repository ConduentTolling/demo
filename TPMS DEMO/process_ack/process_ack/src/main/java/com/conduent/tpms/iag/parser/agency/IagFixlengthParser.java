package com.conduent.tpms.iag.parser.agency;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.tpms.iag.constants.IAGConstants;
import com.conduent.tpms.iag.dto.AckFileInfoDto;
import com.conduent.tpms.iag.dto.AckFileWrapper;
import com.conduent.tpms.iag.dto.IaFileStats;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.dto.IagDetailInfoVO;
import com.conduent.tpms.iag.dto.IagFileNameInfo;
import com.conduent.tpms.iag.model.XferControl;
import com.conduent.tpms.iag.utility.Convertor;
import com.conduent.tpms.iag.utility.DateUtils;
import com.conduent.tpms.iag.utility.MasterDataCache;
import com.conduent.tpms.iag.validation.FileParserImpl;

import org.apache.commons.lang3.StringUtils;

@Component
public class IagFixlengthParser extends FileParserImpl {
	
	private IagDetailInfoVO detailVO;
	private List<IagDetailInfoVO> detailsRecord;
	private static final Logger log = LoggerFactory.getLogger(IagFixlengthParser.class);
	
	@Autowired
	MasterDataCache masterDataCache;

	@Override
	public void initialize() {
		setFileFormat(IAGConstants.FIXED);
		setFileType(IAGConstants.ACK);
		setAgencyId(IAGConstants.HOME_AGENCY_ID);
		setIsDetailsPresentInFile(true);
		getFileParserParameter().setAgencyId(IAGConstants.IAG_AGENCY_ID);
		getFileParserParameter().setFileType(IAGConstants.ACK);
		fileNameVO = new IagFileNameInfo();
		detailVO = new IagDetailInfoVO();
		detailsRecord = new ArrayList<IagDetailInfoVO>();

	}

	@Override
	public void processEndOfLine(String fileSection) {
		if (fileSection.equals("2AGDETAIL") || fileSection.equals("3AGDETAIL")) {
			detailsRecord.add(detailVO);
		}
	}

	@Override
	public void processStartOfLine(String fileSection) {
		log.info("Process start of line..");
		if (fileSection.equals("2AGDETAIL") || fileSection.equals("3AGDETAIL")) {
			detailVO = new IagDetailInfoVO();
			detailsRecord = new ArrayList<IagDetailInfoVO>();
		}

	}

	@Override
	public void doFieldMapping(String value, MappingInfoDto fMapping) {

		switch (fMapping.getFieldName()) {

		case "F_FROM_AGENCY_ID":
			fileNameVO.setFromAgencyId(value);
			break;
			
		case "F_TO_AGENCY_ID":
			fileNameVO.setToAgencyId(value);
			break;
			
		case "F_FROM_TO_AGENCY_ID":
			fileNameVO.setFromToAgencyId(value);
			break;

		case "F_FILE_DATE_TIME":
			fileNameVO.setFileDateTime(value);
			break;

		case "F_EXTENSION":
			fileNameVO.setExtension(value);
			break;
			
		case "F_FILE_EXTENSION":
			fileNameVO.setFileExtension(value);
			break;

		case "D_FILE_EXTENSION":
			detailVO.setFileExtension(value);
			break;
			
		case "D_VERSION":
			detailVO.setVersion(value);
			break;
			
		case "D_FILE_DATE_TIME":
			detailVO.setFileDateTime(value);
			break;

		case "D_FROM_AGENCY_ID":
			detailVO.setFromAgencyId(value);
			break;

		case "D_TO_AGENCY_ID":
			detailVO.setToAgencyId(value);
			break;

		case "D_FILE_FROM_AGENCY_ID":
			detailVO.setFileFromAgencyId(value);
			break;
			
		case "D_FILE_TO_AGENCY_ID":
			detailVO.setFileToAgencyId(value);
			break;	

		case "D_UNDERSCORE":
			detailVO.setUnderscroe(value);
			break;

		case "D_FILE_NAME":
			detailVO.setFileName(value);
			break;

		case "D_DOT":
			detailVO.setDot(value);
			break;

		case "D_FILE_TYPE":
			detailVO.setFileType(value);
			break;

		case "D_FILE_DATE":
			detailVO.setFileDate(value);
			break;

		case "D_FILE_TIME":
			detailVO.setFileTime(value);
			break;

		case "D_RETURN_CODE":
			detailVO.setReturnCode(value);
			break;

		}
	}

	@Override
	public AckFileWrapper prePareAckFileMetaData(String StatusCode, File file) {
		AckFileWrapper ackObj = new AckFileWrapper();
		AckFileInfoDto ackFileInfoDto = new AckFileInfoDto();
		StringBuilder sbAckFileName = new StringBuilder();
		StringBuilder sbFileName = new StringBuilder();
		Boolean isValidFileName = validateFileName(file);
		if(isValidFileName==true) {
			StatusCode = IAGConstants.INVALID_DETAIL;
		}
		if (detailsRecord != null && !detailsRecord.isEmpty() && detailsRecord.size() > 0) 
		{
		
			for (IagDetailInfoVO obj : detailsRecord) 
			{
			sbAckFileName.append(file.getName().split("\\.")[0]).append(IAGConstants.ACK_EXT);
			ackFileInfoDto.setAckFileName(sbAckFileName.toString());
			//ackFileInfoDto.setAckFileDate(DateUtils.parseLocalDate(obj.getFileDate().substring(0, 10).replaceAll("-",""), "yyyyMMdd"));
			//ackFileInfoDto.setAckFileTime(obj.getFileDate().substring(11, 19).replaceAll(":",""));
			ackFileInfoDto.setAckFileStatus(obj.getReturnCode());
			
			if(obj.getFileType()!=null && obj.getFileType().equals(IAGConstants.ITAG) || obj.getFileType().equals(IAGConstants.ICLP))
			{
				sbFileName.append(obj.getToAgencyId()).append("_"+obj.getFileName()+".").append(obj.getFileType());
				ackFileInfoDto.setAckFileDate(DateUtils.parseLocalDate(obj.getFileDateTime().substring(0, 10).replaceAll("-",""), "yyyyMMdd"));
				ackFileInfoDto.setAckFileTime(obj.getFileDateTime().substring(11, 19).replaceAll(":",""));
			}
			else 
			{
				sbFileName.append(obj.getFileFromAgencyId()).append("_"+obj.getFileToAgencyId()).append("_"+obj.getFileName()+".").append(obj.getFileType());
				ackFileInfoDto.setAckFileDate(DateUtils.parseLocalDate(obj.getFileDate().substring(0, 10).replaceAll("-",""), "yyyyMMdd"));
				ackFileInfoDto.setAckFileTime(obj.getFileDate().substring(11, 19).replaceAll(":",""));
			}
			ackFileInfoDto.setTrxFileName(sbFileName.toString());
			ackFileInfoDto.setReconFileName(null);
			ackFileInfoDto.setFileType(obj.getFileType());
			
			if(obj.getFileType()!=null && obj.getFileType().equals(IAGConstants.ICRX) )
			{
				ackFileInfoDto.setFromAgency(obj.getToAgencyId());
				ackFileInfoDto.setToAgency(obj.getFromAgencyId());
			}
			else
			{
			ackFileInfoDto.setFromAgency(obj.getFromAgencyId());
			ackFileInfoDto.setToAgency(obj.getToAgencyId());
			}
			XferControl XferFilePresent = tIagDao.getXferControlInfo(ackFileInfoDto.getTrxFileName());
			IaFileStats IagFilePresent = tIagDao.getIaFileStatsInfo(ackFileInfoDto.getTrxFileName());
			if(XferFilePresent!=null) {
				ackFileInfoDto.setExternFileId(XferFilePresent.getXferControlId());
				ackFileInfoDto.setAtpFileId((long) 0);
			}else {
			ackFileInfoDto.setExternFileId(Convertor.toLong(IagFilePresent.getXferControlId()));
			ackFileInfoDto.setAtpFileId(tIagDao.checkAtpFileInStats(ackFileInfoDto));
			
			
			}
			ackObj.setAckFileInfoDto(ackFileInfoDto);
		}
		}
		if (StatusCode.equals(IAGConstants.SUCCESS)) {
			ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getProcDir());
		} else if (StatusCode.equals(IAGConstants.INVALID_DETAIL)|| StatusCode.equals(IAGConstants.INVALID_FILE)) {
			ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
		}
		ackObj.setFile(file);	
		if(detailsRecord!=null) {
		detailsRecord.clear();
		}
		return ackObj;
	}

	private Boolean validateFileName(File file) {
		for (IagDetailInfoVO obj : detailsRecord) {
			String fileName = file.getName();
			StringBuilder sbFileName = new StringBuilder();
			if(obj.getFileToAgencyId()!=null) {
			
				sbFileName.append(obj.getFileFromAgencyId()+"_"+obj.getFileToAgencyId()+"_"+obj.getFileName()+"."+obj.getFileType());
				
				if(!(sbFileName.toString().equals(fileName.substring(5, 34).replace("_I", ".I"))) ||
						!(obj.getFromAgencyId().equals(obj.getFileToAgencyId())) || !(obj.getToAgencyId().equals(obj.getFileFromAgencyId()))){
					
					detailsRecord = null;
				}
			}else {
				
				sbFileName.append(obj.getFileFromAgencyId()+"_"+obj.getFileName()+"."+obj.getFileType());
				
				if(!(sbFileName.toString().equals(fileName.substring(5, 29).replace("_I", ".I"))) ||
						!(obj.getToAgencyId().equals(obj.getFileFromAgencyId())) || !(obj.getFromAgencyId().equals(fileName.substring(0, 4)))) {
					
					detailsRecord = null;
				}
			}
			
			
		}
		if(detailsRecord==null) {
			return true;
		}else {
		return false;
		}
	}
}
