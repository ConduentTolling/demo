package com.conduent.tpms.iag.parser.agency;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.tpms.iag.constants.Constants;
import com.conduent.tpms.iag.constants.IAGConstants;
import com.conduent.tpms.iag.dto.AckFileInfoDto;
import com.conduent.tpms.iag.dto.AckFileWrapper;
import com.conduent.tpms.iag.dto.IagDetailInfoVO;
import com.conduent.tpms.iag.dto.IagFileNameInfo;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.dto.TFOFileStats;
import com.conduent.tpms.iag.exception.InvlidFileDetailException;
import com.conduent.tpms.iag.model.XferControl;
import com.conduent.tpms.iag.utility.DateUtils;
import com.conduent.tpms.iag.utility.MasterDataCache;
import com.conduent.tpms.iag.validation.FileParserImpl;

@Component
public class IagFixlengthParser extends FileParserImpl {

	private IagDetailInfoVO detailVO;
	List<IagDetailInfoVO> detailsRecord;
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
		if (IAGConstants.DETAILAG2.equalsIgnoreCase(fileSection)
				|| IAGConstants.DETAILAG3.equalsIgnoreCase(fileSection)) {
			detailVO = new IagDetailInfoVO();
			detailsRecord = new ArrayList<IagDetailInfoVO>();
		}

	}

	@Override
	public void doFieldMapping(String value, MappingInfoDto fMapping) throws InvlidFileDetailException {

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
			if(value.equals(Constants.ACK)){
			detailVO.setFileExtension(value);
			}else {
				throw new InvlidFileDetailException("Invalid File Type");
			}
			break;
			

		case "D_FROM_AGENCY_ID":
			detailVO.setFromAgencyId(value);
			break;

		case "D_TO_AGENCY_ID":
			detailVO.setToAgencyId(value);
			break;

		case "D_FILE_FROM_AGENCY_ID":
			if(value.equals(detailVO.getFromAgencyId())) {
			detailVO.setFileFromAgencyId(value);
			}else {
				throw new InvlidFileDetailException("From Agency Id is not matching with File from Agency Id");
				}
			break;

		case "D_FILE_TO_AGENCY_ID":
			if(value.equals(detailVO.getToAgencyId())) {
			detailVO.setFileToAgencyId(value);
			}else {
				throw new InvlidFileDetailException("To Agency Id is not matching with File To Agency Id");
				}
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
		if (isValidFileName == true) {
			StatusCode = IAGConstants.INVALID_DETAIL;
		}
		if (detailsRecord != null && !detailsRecord.isEmpty() && detailsRecord.size() > 0) {

			for (IagDetailInfoVO obj : detailsRecord) {
				sbAckFileName.append(file.getName().split("\\.")[0]).append(IAGConstants.ACK_EXT);// check
				ackFileInfoDto.setAckFileName(sbAckFileName.toString());
				ackFileInfoDto.setAckFileDate(DateUtils.parseLocalDate(obj.getFileDate(), "yyyyMMdd"));
				ackFileInfoDto.setAckFileTime(obj.getFileTime());
				ackFileInfoDto.setAckFileStatus(obj.getReturnCode());
				if (obj.getFileType() != null && obj.getFileType().equals(Constants.FNDX_FILE_NAME)
						|| obj.getFileType().equals(Constants.FDXN_FILE_NAME)
						|| obj.getFileType().equals(Constants.FTXN_FILE_NAME)
						|| obj.getFileType().equals(Constants.FNTX_FILE_NAME)
						|| obj.getFileType().equals(Constants.FRXN_FILE_NAME)
						|| obj.getFileType().equals(Constants.FNRX_FILE_NAME)
						|| obj.getFileType().equals(Constants.ITXN_FILE_NAME)
						|| obj.getFileType().equals(Constants.INTX_FILE_NAME)
						|| obj.getFileType().equals(Constants.IRXN_FILE_NAME)
						|| obj.getFileType().equals(Constants.INRX_FILE_NAME)) {
					
					sbFileName.append(obj.getFileFromAgencyId()).append("_" + obj.getFileToAgencyId())
					.append("_" + obj.getFileName() + ".").append(obj.getFileType());
				} else {
					
					sbFileName.append(obj.getToAgencyId()).append("_" + obj.getFromAgencyId())
					.append("_" + obj.getFileName() + ".").append(obj.getFileType());
				}
				ackFileInfoDto.setTrxFileName(sbFileName.toString());
				ackFileInfoDto.setReconFileName(null);
				ackFileInfoDto.setFileType(obj.getFileType());
				if (obj.getFileType() != null && (obj.getFileType().equals(Constants.FNTX_FILE_NAME)
						|| obj.getFileType().equals(Constants.FTXN_FILE_NAME)
						|| obj.getFileType().equals(Constants.FNDX_FILE_NAME)
						|| obj.getFileType().equals(Constants.FDXN_FILE_NAME)
						|| obj.getFileType().equals(Constants.FNRX_FILE_NAME)
						|| obj.getFileType().equals(Constants.FRXN_FILE_NAME)
						|| obj.getFileType().equals(Constants.INRX_FILE_NAME)
						|| obj.getFileType().equals(Constants.IRXN_FILE_NAME)
						|| obj.getFileType().equals(Constants.INTX_FILE_NAME)
						|| obj.getFileType().equals(Constants.ITXN_FILE_NAME))) {
					
					ackFileInfoDto.setFromAgency(obj.getFromAgencyId());
					ackFileInfoDto.setToAgency(obj.getToAgencyId());
					
				} else {
					
					ackFileInfoDto.setFromAgency(obj.getToAgencyId());
					ackFileInfoDto.setToAgency(obj.getFromAgencyId());
				}
				XferControl XferFilePresent = tIagDao.getXferControlInfo(ackFileInfoDto.getTrxFileName());
				// IaFileStats IagFilePresent =
				// tIagDao.getIaFileStatsInfo(ackFileInfoDto.getTrxFileName());
				TFOFileStats tFOFilePresent = tIagDao.getFOFileStatsInfo(ackFileInfoDto.getTrxFileName());
				if (XferFilePresent != null) {
					ackFileInfoDto.setExternFileId(XferFilePresent.getXferControlId());
					ackFileInfoDto.setAtpFileId((long) 0);
				} else {
					// ackFileInfoDto.setExternFileId(Convertor.toLong(IagFilePresent.getXferControlId()));
					ackFileInfoDto.setExternFileId(tFOFilePresent.getFileControlId());
					ackFileInfoDto.setAtpFileId(tIagDao.checkAtpFileInStats(ackFileInfoDto));

				}
				ackObj.setAckFileInfoDto(ackFileInfoDto);
			}
		}
		if (StatusCode.equals(IAGConstants.SUCCESS)) {
			ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getProcDir());
		} else if (StatusCode.equals(IAGConstants.INVALID_DETAIL)) {
			ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
			//ackFileInfoDto.setAckFileStatus(IAGConstants.INVALID_DETAIL);
		} else if (StatusCode.equals(IAGConstants.INVALID_FILE)) {
			ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
			//ackFileInfoDto.setAckFileStatus(IAGConstants.INVALID_FILE);
		}
		ackObj.setFile(file);
		if (detailsRecord != null) {
			detailsRecord.clear();
		}
		return ackObj;
	}

	// Done chages related to substring
	private Boolean validateFileName(File file) {
		// detailsRecord comes from validation
		for (IagDetailInfoVO obj : detailsRecord) {
			String fileName = file.getName();
			StringBuilder sbFileName = new StringBuilder();
			if (obj.getFileToAgencyId() != null) {

				sbFileName.append(obj.getFileFromAgencyId() + "_" + obj.getFileToAgencyId() + "_" + obj.getFileName()
						+ "." + obj.getFileType());

				// check with if conditions
				if ((fileName.substring(23, 27).equals(Constants.FNDX_FILE_NAME)
						|| fileName.substring(23, 27).equals(Constants.FDXN_FILE_NAME)
						|| fileName.substring(23, 27).equals(Constants.FNTX_FILE_NAME)
						|| fileName.substring(23, 27).equals(Constants.FTXN_FILE_NAME)
						|| fileName.substring(23, 27).equals(Constants.FRXN_FILE_NAME)
						|| fileName.substring(23, 27).equals(Constants.FNRX_FILE_NAME))
						&& (!(sbFileName.toString().equals(fileName.substring(0, 27).replace("_F", ".F")))
								|| (obj.getFromAgencyId().equals(obj.getFileToAgencyId()))
								|| (obj.getToAgencyId().equals(obj.getFileFromAgencyId())))) {

					detailsRecord = null;
				} else if ((fileName.substring(23, 27).equals(Constants.ITXN_FILE_NAME)
						|| fileName.substring(23, 27).equals(Constants.INTX_FILE_NAME)
						|| fileName.substring(23, 27).equals(Constants.INRX_FILE_NAME)
						|| fileName.substring(23, 27).equals(Constants.IRXN_FILE_NAME))
						&& (!(sbFileName.toString().equals(fileName.substring(0, 27).replace("_I", ".I")))
								|| (obj.getFromAgencyId().equals(obj.getFileToAgencyId()))
								|| (obj.getToAgencyId().equals(obj.getFileFromAgencyId())))) {

					detailsRecord = null;
				}
			} else {

				sbFileName.append(obj.getFileFromAgencyId() + "_" + obj.getFileName() + "." + obj.getFileType());

				if ((fileName.substring(23, 27).equals(Constants.FNDX_FILE_NAME)
						|| fileName.substring(23, 27).equals(Constants.FDXN_FILE_NAME)
						|| fileName.substring(23, 27).equals(Constants.FNTX_FILE_NAME)
						|| fileName.substring(23, 27).equals(Constants.FTXN_FILE_NAME)
						|| fileName.substring(23, 27).equals(Constants.FRXN_FILE_NAME)
						|| fileName.substring(23, 27).equals(Constants.FNRX_FILE_NAME))
						&& (!(sbFileName.toString().equals(fileName.substring(0, 27).replace("_F", ".F")))
								|| !(obj.getToAgencyId().equals(obj.getFileFromAgencyId()))
								|| !(obj.getFromAgencyId().equals(fileName.substring(0, 3))))) {

					detailsRecord = null;
				} else if ((fileName.substring(23, 27).equals(Constants.ITXN_FILE_NAME)
						|| fileName.substring(23, 27).equals(Constants.INTX_FILE_NAME)
						|| fileName.substring(23, 27).equals(Constants.INRX_FILE_NAME)
						|| fileName.substring(23, 27).equals(Constants.IRXN_FILE_NAME))
						&& (!(sbFileName.toString().equals(fileName.substring(0, 27).replace("_I", ".I")))
								|| !(obj.getToAgencyId().equals(obj.getFileFromAgencyId()))
								|| !(obj.getFromAgencyId().equals(fileName.substring(0, 3))))) {
					
					detailsRecord = null;
				}
			}

		}
		if (detailsRecord == null) {
			return true;
		} else {
			return false;
		}
	}
}
