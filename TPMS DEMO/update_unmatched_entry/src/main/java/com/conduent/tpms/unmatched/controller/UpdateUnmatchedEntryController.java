package com.conduent.tpms.unmatched.controller;

import java.text.ParseException;
import java.time.format.DateTimeFormatter;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.unmatched.model.TUnmatchedEntryTxRequestModel;
import com.conduent.tpms.unmatched.service.UpdateUnmatchedEntryService;
import com.conduent.tpms.unmatched.updateexception.TPMSGateway;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/ibts")
public class UpdateUnmatchedEntryController {

	final Logger logger = LoggerFactory.getLogger(UpdateUnmatchedEntryController.class);

	@Autowired
	UpdateUnmatchedEntryService updateUnmatchedEntryService;
	
	@Autowired
	TimeZoneConv timeZoneConv;

	String methodName = null;
	String className = this.getClass().getName();

	@ApiResponses(value = { @ApiResponse(code=200, message= "Record with 20000145135 updated successfully", response= String.class) })
    @ApiOperation(value= "Update T_UNMATCHED_ENTRY_TX table")
	@PutMapping(value = "/updateUnmatchedEntry", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_ATOM_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_ATOM_XML_VALUE })
	public TPMSGateway<Object> updateUnmatchedEntryDetails(
			@RequestBody @Valid TUnmatchedEntryTxRequestModel tUnmatchedEntryTxRequestModel) throws ParseException {
		logger.info("Starting with update plan suspension api..");
		MDC.put("logFileName", "UPDATE_T_UNMATCHED_ENTRY_TX_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
		logger.info("logFileName: {}",MDC.get("logFileName"));

		if (tUnmatchedEntryTxRequestModel.getLaneTxId() != null
				&& tUnmatchedEntryTxRequestModel.getTxExternRefNo() == null
				&& tUnmatchedEntryTxRequestModel.getTxSeqNumber() == null
				&& tUnmatchedEntryTxRequestModel.getExternFileId() == null
				&& tUnmatchedEntryTxRequestModel.getLaneId() == null
				&& tUnmatchedEntryTxRequestModel.getTxTimeStamp() == null
				&& tUnmatchedEntryTxRequestModel.getTxSeqNumber() == null
				&& tUnmatchedEntryTxRequestModel.getTxTypeInd() == null
				&& tUnmatchedEntryTxRequestModel.getTxSubtypeInd() == null
				&& tUnmatchedEntryTxRequestModel.getTollSystemType() == null
				&& tUnmatchedEntryTxRequestModel.getLaneMode() == null
				&& tUnmatchedEntryTxRequestModel.getLaneType() == null
				&& tUnmatchedEntryTxRequestModel.getLaneState() == null
				&& tUnmatchedEntryTxRequestModel.getLaneHealth() == null
				&& tUnmatchedEntryTxRequestModel.getPlazaAgencyId() == null
				&& tUnmatchedEntryTxRequestModel.getPlazaId() == null
				&& tUnmatchedEntryTxRequestModel.getCollectorId() == null
				&& tUnmatchedEntryTxRequestModel.getTourSegmentId() == null
				&& tUnmatchedEntryTxRequestModel.getEntryDataSource() == null
				&& tUnmatchedEntryTxRequestModel.getEntryLaneId() == null
				&& tUnmatchedEntryTxRequestModel.getEntryPlazaId() == null
				&& tUnmatchedEntryTxRequestModel.getEntryTimestamp() == null
				&& tUnmatchedEntryTxRequestModel.getEntryTxSeqNumber() == null
				&& tUnmatchedEntryTxRequestModel.getEntryVehicleSpeed() == null
				&& tUnmatchedEntryTxRequestModel.getLaneTxStatus() == null
				&& tUnmatchedEntryTxRequestModel.getLaneTxType() == null
				&& tUnmatchedEntryTxRequestModel.getTollRevenueType() == null
				&& tUnmatchedEntryTxRequestModel.getActualClass() == null
				&& tUnmatchedEntryTxRequestModel.getActualAxles() == null
				&& tUnmatchedEntryTxRequestModel.getActualExtraAxles() == null
				&& tUnmatchedEntryTxRequestModel.getCollectorClass() == null
				&& tUnmatchedEntryTxRequestModel.getCollectorAxles() == null
				&& tUnmatchedEntryTxRequestModel.getPreclassClass() == null
				&& tUnmatchedEntryTxRequestModel.getPreclassAxles() == null
				&& tUnmatchedEntryTxRequestModel.getPostclassClass() == null
				&& tUnmatchedEntryTxRequestModel.getPostclassAxles() == null
				&& tUnmatchedEntryTxRequestModel.getForwardAxles() == null
				&& tUnmatchedEntryTxRequestModel.getReverseAxles() == null
				&& tUnmatchedEntryTxRequestModel.getCollectedAmount() == null
				&& tUnmatchedEntryTxRequestModel.getUnrealizedAmount() == null
				&& tUnmatchedEntryTxRequestModel.getIsDiscountable() == null
				&& tUnmatchedEntryTxRequestModel.getIsMedianFare() == null
				&& tUnmatchedEntryTxRequestModel.getIsPeak() == null
				&& tUnmatchedEntryTxRequestModel.getPriceScheduleId() == null
				&& tUnmatchedEntryTxRequestModel.getVehicleSpeed() == null
				&& tUnmatchedEntryTxRequestModel.getReceiptIssued() == null
				&& tUnmatchedEntryTxRequestModel.getDeviceNo() == null
				&& tUnmatchedEntryTxRequestModel.getAccountType() == null
				&& tUnmatchedEntryTxRequestModel.getDeviceCodedClass() == null
				&& tUnmatchedEntryTxRequestModel.getDeviceAgencyClass() == null
				&& tUnmatchedEntryTxRequestModel.getDeviceIagClass() == null
				&& tUnmatchedEntryTxRequestModel.getDeviceAxles() == null
				&& tUnmatchedEntryTxRequestModel.getEtcAccountId() == null
				&& tUnmatchedEntryTxRequestModel.getAccountAgencyId() == null
				&& tUnmatchedEntryTxRequestModel.getReadAviClass() == null
				&& tUnmatchedEntryTxRequestModel.getReadAviAxles() == null
				&& tUnmatchedEntryTxRequestModel.getDeviceProgramStatus() == null
				&& tUnmatchedEntryTxRequestModel.getBufferedReadFlag() == null
				&& tUnmatchedEntryTxRequestModel.getLaneDeviceStatus() == null
				&& tUnmatchedEntryTxRequestModel.getPostDeviceStatus() == null
				&& tUnmatchedEntryTxRequestModel.getPreTxnBalance() == null
				&& tUnmatchedEntryTxRequestModel.getPlanTypeId() == null
				&& tUnmatchedEntryTxRequestModel.getTxStatus() == null
				&& tUnmatchedEntryTxRequestModel.getSpeedViolFlag() == null
				&& tUnmatchedEntryTxRequestModel.getImageTaken() == null
				&& tUnmatchedEntryTxRequestModel.getPlateCountry() == null
				&& tUnmatchedEntryTxRequestModel.getPlateNumber() == null
				&& tUnmatchedEntryTxRequestModel.getPlateState() == null
				&& tUnmatchedEntryTxRequestModel.getRevenueDate() == null
				&& tUnmatchedEntryTxRequestModel.getPostedDate() == null
				&& tUnmatchedEntryTxRequestModel.getAtpFileId() == null
				&& tUnmatchedEntryTxRequestModel.getIsReversed() == null
				&& tUnmatchedEntryTxRequestModel.getCorrReasonId() == null
				&& tUnmatchedEntryTxRequestModel.getReconDate() == null
				&& tUnmatchedEntryTxRequestModel.getReconStatusInd() == null
				&& tUnmatchedEntryTxRequestModel.getReconSubCodeInd() == null
				&& tUnmatchedEntryTxRequestModel.getExternFileDate() == null
				&& tUnmatchedEntryTxRequestModel.getMileage() == null
				&& tUnmatchedEntryTxRequestModel.getTxDate() == null
				&& tUnmatchedEntryTxRequestModel.getDeviceReadCount() == null
				&& tUnmatchedEntryTxRequestModel.getDeviceWriteCount() == null
				&& tUnmatchedEntryTxRequestModel.getEntryDeviceReadCount() == null
				&& tUnmatchedEntryTxRequestModel.getEntryDeviceWriteCount() == null
				&& tUnmatchedEntryTxRequestModel.getDepositId() == null
				&& tUnmatchedEntryTxRequestModel.getEtcFareAmount() == null
				&& tUnmatchedEntryTxRequestModel.getPostedFareAmount() == null
				&& tUnmatchedEntryTxRequestModel.getExpectedRevenueAmount() == null
				&& tUnmatchedEntryTxRequestModel.getVideoFareAmount() == null
				&& tUnmatchedEntryTxRequestModel.getCashFareAmount() == null
				&& tUnmatchedEntryTxRequestModel.getMatchedTxExternRefNo() == null
				&& tUnmatchedEntryTxRequestModel.getEventType() == null
				&& tUnmatchedEntryTxRequestModel.getOutputFileType() == null
				&& tUnmatchedEntryTxRequestModel.getEventTimeStamp() == null
				&& tUnmatchedEntryTxRequestModel.getImageBatchID() == null
				&& tUnmatchedEntryTxRequestModel.getImageBatchSeqNumber() == null
				&& tUnmatchedEntryTxRequestModel.getOutputFileID() == null
				&& tUnmatchedEntryTxRequestModel.getImageIndex() == null
				&& tUnmatchedEntryTxRequestModel.getImgFileIndex() == null
				&& tUnmatchedEntryTxRequestModel.getImageRvwClerkID() == null
				&& tUnmatchedEntryTxRequestModel.getReviewedVehicleType() == null
				&& tUnmatchedEntryTxRequestModel.getReviewedDate() == null
				&& tUnmatchedEntryTxRequestModel.getDmvPlateType() == null
				&& tUnmatchedEntryTxRequestModel.getTaxi() == null
				&& tUnmatchedEntryTxRequestModel.getLimousine() == null
				&& tUnmatchedEntryTxRequestModel.getTrailerPlate() == null
				&& tUnmatchedEntryTxRequestModel.getImageReviewTimeStamp() == null
				&& tUnmatchedEntryTxRequestModel.getFrontImgRejReason() == null
				&& tUnmatchedEntryTxRequestModel.getReviewedClass() == null) {

			return new TPMSGateway<Object>(true, HttpStatus.OK, "Provide the inputs that needs to be updated");

		} else {
			Integer responseId = updateUnmatchedEntryService.updateUnmatchedEntryDetails(tUnmatchedEntryTxRequestModel);
			if (responseId != null && responseId.intValue() > 0) {
				return new TPMSGateway<Object>(true, HttpStatus.OK,
						"Record with " + tUnmatchedEntryTxRequestModel.getLaneTxId() + " updated successfully");
			} else if (responseId == null || responseId.intValue() == 0) {
				return new TPMSGateway<Object>(false, HttpStatus.OK, "Record NOT FOUND");
			} else {
				return new TPMSGateway<Object>(false, HttpStatus.OK, "Record Updation got failed");
			}
		}

	}
}