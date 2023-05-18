package com.conduent.Ibtsprocessing.serviceimpl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.conduent.Ibtsprocessing.constant.ConfigVariable;
import com.conduent.Ibtsprocessing.constant.QatpClassificationConstant;
import com.conduent.Ibtsprocessing.dao.IOaAddressDao;
import com.conduent.Ibtsprocessing.daoimpl.OaAddressDao;
import com.conduent.Ibtsprocessing.dto.Exclusion;
import com.conduent.Ibtsprocessing.dto.IbtsViolProcessDTO;
import com.conduent.Ibtsprocessing.dto.QueueMessage;
import com.conduent.Ibtsprocessing.model.Codes;
import com.conduent.Ibtsprocessing.model.EtcAccount;
import com.conduent.Ibtsprocessing.model.Lane;
import com.conduent.Ibtsprocessing.model.OADevices;
import com.conduent.Ibtsprocessing.model.OaAddress;
import com.conduent.Ibtsprocessing.model.Plaza;
import com.conduent.Ibtsprocessing.model.ProcessParameters;
import com.conduent.Ibtsprocessing.oss.OssStreamClient;
import com.conduent.Ibtsprocessing.service.IPushMessageService;
import com.conduent.Ibtsprocessing.service.MessageReaderService;
import com.conduent.Ibtsprocessing.utility.DateUtils;
import com.conduent.Ibtsprocessing.utility.MasterCache;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Service
public class IbtsProcessingJob implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(IbtsProcessingJob.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ConfigVariable configVariable;

	@Autowired
	private IOaAddressDao oaAddressDao;

	@Autowired
	MasterCache masterCache;

	@Autowired
	IPushMessageService pushMessageService;

	@Autowired
	MessageReaderService messageReaderService;

	private String paramValueTagRetry;

	@Override
	public void run() {
		OssStreamClient atpQueueClient = null;
		try {
			atpQueueClient = new OssStreamClient(configVariable, configVariable.getAtpQueue(),
					configVariable.getGroupName());

			while (!Thread.currentThread().isInterrupted()) {
				try {

					// Thread.sleep(5000);
					/** Step 1: Read message from queue **/
					logger.info("Entered IbtsProcessingJob");
					// List<String> queueMessageList = getMessages(atpQueueClient, 1);
					
					List<String> queueMessageList = messageReaderService.readMessages(configVariable.getStreamId(), 1);
					
					//List<String> queueMessageList = new ArrayList<>();
					//queueMessageList.add("{\"laneTxId\":20000416410,\"txExternRefNo\":\"10000000000000000111\",\"externFileId\":1004,\"plazaAgencyId\":2,\"plazaId\":221,\"laneId\":18257,\"txTimestamp\":\"2023-02-18T07:12:02.02-05:00\",\"txModSeq\":0,\"txTypeInd\":\"V\",\"txSubtypeInd\":\"Q\",\"laneHealth\":0,\"entryVehicleSpeed\":60,\"laneTxStatus\":0,\"lanetxType\":0,\"tollRevenueType\":60,\"actualClass\":1,\"actualAxles\":10,\"actualExtraAxles\":8,\"collectorClass\":0,\"collectorAxles\":0,\"preClassClass\":0,\"preClassAxles\":0,\"postClassClass\":1,\"postClassAxles\":10,\"forwardAxles\":0,\"reverseAxles\":0,\"collectedAmount\":0.0,\"unrealizedAmount\":0.0,\"isDiscountable\":\"N\",\"isMedianFare\":\"N\",\"isPeak\":\"N\",\"priceScheduleId\":0,\"vehicleSpeed\":60,\"receiptIssued\":0,\"deviceNo\":\"00160000000050\",\"accountType\":1,\"deviceAgencyClass\":0,\"deviceIagClass\":2,\"deviceAxles\":0,\"accountAgencyId\":16,\"readAviAxles\":0,\"bufferReadFlag\":\"F\",\"postDeviceStatus\":0,\"preTxnBalance\":0.0,\"planTypeId\":0,\"txStatus\":501,\"speedViolFlag\":\"0\",\"imageTaken\":\"N\",\"plateCountry\":\"***\",\"revenueDate\":\"2023-02-01\",\"atpFileId\":26357,\"isReversed\":\"N\",\"corrReasonId\":0,\"reconStatusInd\":0,\"reconSubCodeInd\":0,\"mileage\":0,\"txDate\":\"2023-02-18\",\"updateTs\":\"2023-03-31T13:53:36.595899300\",\"tollSystemType\":\"B\",\"aetFlag\":\"Y\",\"eventTimestamp\":\"2023-03-31 04:23:38\",\"eventType\":1,\"prevViolTxStatus\":0,\"violTxStatus\":3,\"violType\":3,\"reciprocityTrx\":\"F\",\"sourceSystem\":0,\"etcFareAmount\":0.0,\"postedFareAmount\":10.17,\"expectedRevenueAmount\":10.17,\"videoFareAmount\":10.17,\"cashFareAmount\":0.0,\"discountedAmount\":0.0,\"discountedAmount2\":0.0,\"itolFareAmount\":0.0,\"direction\":\"0\",\"event\":\"Y\",\"hovFlag\":\"N\",\"hovPresenceTime\":\"2023-02-02T02:02:02.02-05:00\",\"hovLostPresenceTime\":\"2023-04-04T04:04:04.04-04:00\",\"avcDiscrepancyFlag\":\"Y\",\"degraded\":\"A\",\"uoCode\":\"YYV\",\"avcProfile\":\"H\",\"ti1TagReadStatus\":\"Y\",\"ti2AdditionalTags\":\"Y\",\"ti3ClassMismatchFlag\":\"N\",\"ti4TagStatus\":\"0\",\"ti5PaymentFlag\":\"V\",\"ti6RevenueFlag\":\"Y\"}");
					QueueMessage queueObj = new QueueMessage();
					if (queueMessageList != null && !(queueMessageList.isEmpty())) {
						for (String queueMessage : queueMessageList) {
							queueObj = QueueMessage.dtoFromJson(queueMessage);
							logger.info(queueMessage);
							// temporary change

							getAetFlag(queueObj);
							// if (queueObj.getTxTypeInd().equals("V")) {
							if (queueObj.getTxTypeInd() != null && queueObj.getTxTypeInd().equals("V")) {

								Integer InvalidetcId = getViolationTypeValue(QatpClassificationConstant.VIOLATION_TYPE,
										"INVALIDETC");

								if (InvalidetcId != null && queueObj.getViolType() == InvalidetcId) {

									// if (queueObj.getTxSubtype().equals("T")) {
									if (queueObj.getTxSubtypeInd() != null && queueObj.getTxSubtypeInd().equals("T") || queueObj.getTxSubtypeInd().equals("Q")) {

										// check for home
										if (!(queueObj.getReciprocityTrx().equals("T"))) {

											/*
											 * Integer agencyId = masterCache.getAgencyId("NYSTA").getAgencyId(); if
											 * (agencyId != null && agencyId == queueObj.getPlazaAgencyId() && agencyId
											 * == queueObj.getAccountAgencyId()) {
											 */
											// RESERVED = 'VFEE'
											if (queueObj.getPlazaAgencyId() != null) {
												queueObj.setTxStatus(masterCache
														.getCodeID(QatpClassificationConstant.TX_STATUS, "POSTTOACCT")
														.getCodeId());

											} else {
												if (queueObj.getEtcAccountId() != null) {
													EtcAccount etcAccountList = masterCache
															.getEtcAccountInfo((int) (long) queueObj.getEtcAccountId());
													if (etcAccountList != null) {
														String isAccountSuspended = etcAccountList
																.getAccountSuspended();
														if (isAccountSuspended != null
																&& isAccountSuspended.equals("Y")) {

															queueObj.setTxStatus(masterCache
																	.getCodeID(QatpClassificationConstant.TX_STATUS,
																			"POSTTOACCT")
																	.getCodeId());

														}
													}
												}
											}
										} else {
											// check for away
											/*
											 * Integer agencyId = masterCache.getAgencyId("NYSTA").getAgencyId(); if
											 * (agencyId != null && agencyId == queueObj.getPlazaAgencyId() && agencyId
											 * == queueObj.getAccountAgencyId()) {
											 */
											
											if(queueObj.getPlazaAgencyId()!=null && queueObj.getPlazaAgencyId().equals(2) && queueObj.getTxSubtypeInd().equals("Q")) {
												queueObj.setTxStatus(masterCache
														.getCodeID(QatpClassificationConstant.TX_STATUS, "POSTTOACCT")
														.getCodeId());
											}

											// RESERVED = 'VFEE'
											else if (queueObj.getPlazaAgencyId() != null) {
												queueObj.setTxStatus(masterCache
														.getCodeID(QatpClassificationConstant.TX_STATUS, "QECTX")
														.getCodeId());

											} else {
												if (queueObj.getEtcAccountId() != null) {
													EtcAccount etcAccountList = masterCache
															.getEtcAccountInfo((int) (long) queueObj.getEtcAccountId());
													if (etcAccountList != null) {
														String isAccountSuspended = etcAccountList
																.getAccountSuspended();
														if (isAccountSuspended != null
																&& isAccountSuspended.equals("Y")) {

															queueObj.setTxStatus(masterCache
																	.getCodeID(QatpClassificationConstant.TX_STATUS,
																			"QECTX")
																	.getCodeId());

														}
													}
												}
											}

										}
										// } else if (queueObj.getTxSubtype().equals("Y")) {
									} else if (queueObj.getTxSubtypeInd() != null
											&& queueObj.getTxSubtypeInd().equals("Y")) {

										/*
										 * Integer agencyId = masterCache.getAgencyId("NYSTA").getAgencyId();
										 * 
										 * if (agencyId != null && agencyId == queueObj.getPlazaAgencyId() && agencyId
										 * == queueObj.getAccountAgencyId()) {
										 */
										if (queueObj.getPlazaAgencyId() != null) {
											queueObj.setTxStatus(masterCache
													.getCodeID(QatpClassificationConstant.TX_STATUS, "TAGRETRY")
													.getCodeId());
											// RESERVED = 'VFEE'
											queueObj.setViolTxStatus(masterCache
													.getCodeID(QatpClassificationConstant.VIOL_TX_STATUS, "TAGRETRY")
													.getCodeId());

										}
										// } else {
										//
										// queueObj.setViolTxStatus(masterCache
										// .getCodeID(QatpClassificationConstant.VIOL_TX_STATUS, "ACCTAVAIL")
										// .getCodeId());
										// }

										if (queueObj.getPlazaAgencyId() != null) {
											ProcessParameters processParameterListImage = masterCache.getParamValue(
													queueObj.getPlazaAgencyId(), "IMAGE_ON_FIRST_NOTICE");
											ProcessParameters processParameterListTagRetry = masterCache.getParamValue(
													queueObj.getPlazaAgencyId(), "IMAGE_ON_FIRST_NOTICE");
											if (processParameterListTagRetry != null) {
												String paramValueTagRetry = processParameterListTagRetry
														.getParamValue();
												if (paramValueTagRetry.equals("Y")) {

													queueObj.setTxStatus(masterCache
															.getCodeID(QatpClassificationConstant.TX_STATUS, "TAGRETRY")
															.getCodeId());

													queueObj.setViolTxStatus(masterCache
															.getCodeID(QatpClassificationConstant.VIOL_TX_STATUS,
																	"TAGRETRY")
															.getCodeId());

												}
											}
										}
									} else if (queueObj.getTxSubtypeInd() != null
											&& queueObj.getTxSubtypeInd().equals("F") || queueObj.getTxSubtypeInd().equals("G") 
											|| queueObj.getTxSubtypeInd().equals("H") || queueObj.getTxSubtypeInd().equals("C")) {
										queueObj.setTxStatus(
												masterCache.getCodeID(QatpClassificationConstant.TX_STATUS, "REVIEWIMG")
														.getCodeId());
										queueObj.setViolTxStatus(masterCache
												.getCodeID(QatpClassificationConstant.VIOL_TX_STATUS, "REVIEWIMG")
												.getCodeId());
									}
								}
							}

							logger.info("Before push Message Service in IBTS JOB");

							boolean sucessFlag = pushMessageService.pushMessageToViol(queueObj);
							
							logger.info("Publish message push to IBTS == > "+ sucessFlag);

							// <---------------------------Not Required for
							// Now--------------------------------------------------------->
							// TODO: Uncomment this code while deployment
							Boolean isAuthorizeReject = getViol(queueObj);
							if (isAuthorizeReject) {
								queueObj.setViolTxStatus(masterCache
										.getCodeID(QatpClassificationConstant.VIOL_TX_STATUS, "AUTHREJ").getCodeId());
							}

							if (getDaysGap(queueObj) != null
									&& getDaysGap(queueObj) > QatpClassificationConstant.TRANSACTION_DAYS_GAP) {
								queueObj.setViolTxStatus(masterCache
										.getCodeID(QatpClassificationConstant.VIOL_TX_STATUS, "NOPOSTAGE").getCodeId());
							}

							// if (queueObj.getTxTypeInd().equals("V")) {
							if (queueObj.getTxTypeInd() != null && queueObj.getTxTypeInd().equals("V")) {

								if (queueObj.getPlazaAgencyId() != null) {
									ProcessParameters processParameterList = masterCache
											.getParamValue(queueObj.getPlazaAgencyId(), "TAGRETRY_ENABLED");
									if (processParameterList != null) {
										paramValueTagRetry = processParameterList.getParamValue();
										if (paramValueTagRetry != null && paramValueTagRetry.equals("Y")) {

											queueObj.setViolTxStatus(masterCache
													.getCodeID(QatpClassificationConstant.VIOL_TX_STATUS, "TAGRETRY")
													.getCodeId());
										}
									}
								}

								Integer InvalidetcId = getViolationTypeValue(QatpClassificationConstant.VIOLATION_TYPE,
										"INVALIDETC");
								Integer InsfId = getViolationTypeValue(QatpClassificationConstant.VIOLATION_TYPE,
										"INSF");
								Integer NoPaymentId = getViolationTypeValue(QatpClassificationConstant.VIOLATION_TYPE,
										"NOPAYMENT");
								Integer NvtgId = getViolationTypeValue(QatpClassificationConstant.VIOLATION_TYPE,
										"NVTG");
								Integer NotvId = getViolationTypeValue(QatpClassificationConstant.VIOLATION_TYPE,
										"NOTV");
								Integer NoClassId = getViolationTypeValue(QatpClassificationConstant.VIOLATION_TYPE,
										"NOCLASS");
								Integer ColmisclasId = getViolationTypeValue(QatpClassificationConstant.VIOLATION_TYPE,
										"COLMISCLAS");
								Integer mgraId = getViolationTypeValue(QatpClassificationConstant.VIOLATION_TYPE,
										"MGRA");

								if (InvalidetcId != null && queueObj.getViolType() == InvalidetcId) {

									// if (queueObj.getTxSubtype().equals("T")) {
									if (queueObj.getTxSubtypeInd() != null && queueObj.getTxSubtypeInd().equals("T")) {

										if (queueObj.getReciprocityTrx().equals("T")) {

											if (queueObj.getDeviceNo() != null
													&& !(queueObj.getDeviceNo().trim().isEmpty())
													&& queueObj.getTxTimestamp() != null) {
												// && !(queueObj.getTxTimeStamp().trim().isEmpty())) {
												OADevices oaDeviceList = masterCache.getCurrentDeviceStatus(
														queueObj.getDeviceNo(),
														queueObj.getTxTimestamp().toLocalDateTime());
												if (oaDeviceList != null) {

													Integer CurrentDeviceStatus = oaDeviceList.getIagTagStatus();

													if (CurrentDeviceStatus == 1 || CurrentDeviceStatus == 2
															&& CurrentDeviceStatus != null) {

														queueObj.setViolTxStatus(masterCache
																.getCodeID(QatpClassificationConstant.VIOL_TX_STATUS,
																		"SENTTOICTX")
																.getCodeId());
														pushMessageService.pushMessageToAway(queueObj);

													}

													else if (CurrentDeviceStatus == 3 && CurrentDeviceStatus != null) {

														if (queueObj.getPlazaAgencyId() != null) {
															ProcessParameters processParameterList = masterCache
																	.getParamValue(queueObj.getPlazaAgencyId(),
																			"IMAGE_ON_FIRST_NOTICE");
															if (processParameterList != null) {
																String paramValueImageRequired = processParameterList
																		.getParamValue();
																if (paramValueImageRequired.equals("Y")) {

																	queueObj.setViolTxStatus(masterCache.getCodeID(
																			QatpClassificationConstant.VIOL_TX_STATUS,
																			"IMGREVIEWED").getCodeId());
																}
															}
														}

													} else {

														List<OaAddress> oaAddress = new ArrayList<OaAddress>();
														final Logger log = LoggerFactory.getLogger(OaAddressDao.class);
														if (queueObj.getDeviceNo() != null
																&& !(queueObj.getDeviceNo().trim().isEmpty())) {
															oaAddress = oaAddressDao.getAddress(queueObj.getDeviceNo());
															log.info("OA_Address Mapping Fields are: " + oaAddress);

															// insert into t_violator_info

															queueObj.setTxSubtypeInd("U");
															queueObj.setViolTxStatus(masterCache.getCodeID(
																	QatpClassificationConstant.VIOL_TX_STATUS,
																	"ACCTAVAIL").getCodeId());
														}

													}

												}
											}
										} else {

											/*
											 * Integer agencyId = masterCache.getAgencyId("NYSTA").getAgencyId(); if
											 * (agencyId != null && agencyId == queueObj.getPlazaAgencyId() && agencyId
											 * == queueObj.getAccountAgencyId()) {
											 */
											// RESERVED = 'VFEE'
											if (queueObj.getPlazaAgencyId() != null) {
												queueObj.setViolTxStatus(
														masterCache.getCodeID(QatpClassificationConstant.VIOL_TX_STATUS,
																"TAGRETRY").getCodeId());

											} else {
												if (queueObj.getEtcAccountId() != null) {
													EtcAccount etcAccountList = masterCache
															.getEtcAccountInfo((int) (long) queueObj.getEtcAccountId());
													if (etcAccountList != null) {
														String isAccountSuspended = etcAccountList
																.getAccountSuspended();
														if (isAccountSuspended != null
																&& isAccountSuspended.equals("Y")) {
															queueObj.setViolTxStatus(masterCache.getCodeID(
																	QatpClassificationConstant.VIOL_TX_STATUS,
																	"TAGRETRY").getCodeId());
														}
													}
												} else {
													queueObj.setViolTxStatus(masterCache
															.getCodeID("VIOL_TX_STATUS", "IMGREVIEWED").getCodeId());
													if (queueObj.getEtcAccountId() != null) {
														EtcAccount etcAccountList = masterCache.getEtcAccountInfo(
																(int) (long) queueObj.getEtcAccountId());
														if (etcAccountList != null) {
															// rejected
															String isAccountUnregistered = etcAccountList
																	.getUnregistered();
															if (isAccountUnregistered != null
																	&& isAccountUnregistered.equals("Y")) {

																queueObj.setViolTxStatus(masterCache.getCodeID(
																		QatpClassificationConstant.VIOL_TX_STATUS,
																		"IMGREVIEWED").getCodeId());
															}
														}
													}
												}
											}

										}

										// } else if (queueObj.getTxSubtype().equals("F")) {
									} else if (queueObj.getTxSubtypeInd() != null
											&& queueObj.getTxSubtypeInd().equals("F")) {

										queueObj.setViolTxStatus(masterCache
												.getCodeID(QatpClassificationConstant.VIOL_TX_STATUS, "IMGREVIEWED")
												.getCodeId());

										// if (RESERVED = "NPST")

										// queueObj.setViolTxStatus(
										// masterCache.getCodeID(QatpClassificationConstant.VIOL_TX_STATUS, "LICAVAIL")
										// .getCodeId());

									}

									// else if (queueObj.getTxSubtype().equals("Y")) {
									else if (queueObj.getTxSubtypeInd() != null
											&& queueObj.getTxSubtypeInd().equals("Y")) {

										/*
										 * Integer agencyId = masterCache.getAgencyId("NYSTA").getAgencyId(); if
										 * (agencyId != null && agencyId == queueObj.getPlazaAgencyId() && agencyId ==
										 * queueObj.getAccountAgencyId()) {
										 */
										if (queueObj.getPlazaAgencyId() != null) {
											// RESERVED = 'VFEE'
											queueObj.setViolTxStatus(masterCache
													.getCodeID(QatpClassificationConstant.VIOL_TX_STATUS, "TAGRETRY")
													.getCodeId());

										} else {

											queueObj.setViolTxStatus(masterCache
													.getCodeID(QatpClassificationConstant.VIOL_TX_STATUS, "ACCTAVAIL")
													.getCodeId());
										}

										if (queueObj.getPlazaAgencyId() != null) {
											ProcessParameters processParameterListImage = masterCache.getParamValue(
													queueObj.getPlazaAgencyId(), "IMAGE_ON_FIRST_NOTICE");
											ProcessParameters processParameterListTagRetry = masterCache.getParamValue(
													queueObj.getPlazaAgencyId(), "IMAGE_ON_FIRST_NOTICE");
											if (processParameterListTagRetry != null) {
												String paramValueTagRetry = processParameterListTagRetry
														.getParamValue();
												if (paramValueTagRetry.equals("Y")) {

													queueObj.setViolTxStatus(masterCache
															.getCodeID(QatpClassificationConstant.VIOL_TX_STATUS,
																	"TAGRETRY")
															.getCodeId());

												}
											} else if (processParameterListImage != null) {
												String paramValueImageRequired = processParameterListImage
														.getParamValue();
												if (paramValueImageRequired.equals("Y")) {

													queueObj.setViolTxStatus(masterCache
															.getCodeID(QatpClassificationConstant.VIOL_TX_STATUS,
																	"IMGREVIEWED")
															.getCodeId());
												}
											}
										}
										Codes InvalideticCodeId = masterCache
												.getCodeID(QatpClassificationConstant.VIOL_TX_STATUS, "ACCTAVAIL");

										if (InvalideticCodeId != null
												&& queueObj.getViolTxStatus() == InvalideticCodeId.getCodeId()) {

											// insert_record_in_T_VIOLATOR_INFO
										}
										// } else if (queueObj.getTxSubtype().equals("V")) {
									} else if (queueObj.getTxSubtypeInd() != null
											&& queueObj.getTxSubtypeInd().equals("V")) {

										queueObj.setViolTxStatus(masterCache
												.getCodeID(QatpClassificationConstant.VIOL_TX_STATUS, "IMGREVIEWED")
												.getCodeId());
									}

								}

								else if (NoPaymentId != null && queueObj.getViolType() == NoPaymentId) {

									if (masterCache.validateTxSubTypeValue(queueObj.getViolType()).getTxSubtypeInd()
											.equals(queueObj.getTxSubtypeInd())) {

										queueObj.setViolTxStatus(masterCache
												.getCodeID(QatpClassificationConstant.VIOL_TX_STATUS, "MTANOTVTRAN")
												.getCodeId());
										queueObj.setReconDate(DateUtils.parseLocalDate(LocalDate.now(), "yyyy-MM-dd"));
										queueObj.setPostedDate(DateUtils.parseLocalDate(LocalDate.now(), "yyyy-MM-dd"));

									}

								}

								else if (NvtgId != null && queueObj.getViolType() == NvtgId) {

									if (masterCache.validateTxSubTypeValue(queueObj.getViolType()).getTxSubtypeInd()
											.equals(queueObj.getTxSubtypeInd())) {

										queueObj.setViolTxStatus(masterCache
												.getCodeID(QatpClassificationConstant.VIOL_TX_STATUS, "MTANOTVTRAN")
												.getCodeId());
										queueObj.setReconDate(DateUtils.parseLocalDate(LocalDate.now(), "yyyy-MM-dd"));
										queueObj.setPostedDate(null);

									}

								}

								else if (InsfId != null && queueObj.getViolType() == InsfId) {

									if (masterCache.validateTxSubTypeValue(queueObj.getViolType()).getTxSubtypeInd()
											.equals(queueObj.getTxSubtypeInd())) {

										queueObj.setViolTxStatus(masterCache
												.getCodeID(QatpClassificationConstant.VIOL_TX_STATUS, "MTANOTVTRAN")
												.getCodeId());
										queueObj.setReconDate(DateUtils.parseLocalDate(LocalDate.now(), "yyyy-MM-dd"));
										queueObj.setPostedDate(DateUtils.parseLocalDate(LocalDate.now(), "yyyy-MM-dd"));

									}

								}

								else if (NotvId != null && queueObj.getViolType() == NotvId) {

									if (masterCache.validateTxSubTypeValue(queueObj.getViolType()).getTxSubtypeInd()
											.equals(queueObj.getTxSubtypeInd())) {

										queueObj.setViolTxStatus(masterCache
												.getCodeID(QatpClassificationConstant.VIOL_TX_STATUS, "MTANOTVTRAN")
												.getCodeId());
										queueObj.setReconDate(DateUtils.parseLocalDate(LocalDate.now(), "yyyy-MM-dd"));
										queueObj.setPostedDate(DateUtils.parseLocalDate(LocalDate.now(), "yyyy-MM-dd"));

									}

								}

								else if (NoClassId != null && queueObj.getViolType() == NoClassId) {

									if (masterCache.validateTxSubTypeValue(queueObj.getViolType()).getTxSubtypeInd()
											.equals(queueObj.getTxSubtypeInd())) {

										queueObj.setViolTxStatus(masterCache
												.getCodeID(QatpClassificationConstant.VIOL_TX_STATUS, "NONEEDTOREV")
												.getCodeId());
										queueObj.setReconDate(DateUtils.parseLocalDate(LocalDate.now(), "yyyy-MM-dd"));
										queueObj.setPostedDate(DateUtils.parseLocalDate(LocalDate.now(), "yyyy-MM-dd"));

									}

								}

								else if (ColmisclasId != null && queueObj.getViolType() == ColmisclasId) {

									if (masterCache.validateTxSubTypeValue(queueObj.getViolType()).getTxSubtypeInd()
											.equals(queueObj.getTxSubtypeInd())) {

										queueObj.setViolTxStatus(masterCache
												.getCodeID(QatpClassificationConstant.VIOL_TX_STATUS, "COLMISCLAS")
												.getCodeId());
										queueObj.setReconDate(DateUtils.parseLocalDate(LocalDate.now(), "yyyy-MM-dd"));
										queueObj.setPostedDate(DateUtils.parseLocalDate(LocalDate.now(), "yyyy-MM-dd"));

									}

								}

								else if (mgraId != null && queueObj.getViolType() == mgraId) {

									if (masterCache.validateTxSubTypeValue(queueObj.getViolType()).getTxSubtypeInd()
											.equals(queueObj.getTxSubtypeInd())) {

										queueObj.setViolTxStatus(masterCache
												.getCodeID(QatpClassificationConstant.VIOL_TX_STATUS, "COLMISCLAS")
												.getCodeId());
										queueObj.setReconDate(DateUtils.parseLocalDate(LocalDate.now(), "yyyy-MM-dd"));
										queueObj.setPostedDate(DateUtils.parseLocalDate(LocalDate.now(), "yyyy-MM-dd"));

									}

								}
								// TODO: Deepesh check IBTS API call
								// Temporary change for IBTS issue
								// commonNotificationService.pushMessageToViol(queueObj);
							}

						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				}

			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Read messages from OSS classification stream queue
	 * 
	 * @throws IOException
	 **/

	public List<String> getMessages(OssStreamClient ossStreamClient, Integer limit) throws IOException {
		List<String> messageList = ossStreamClient.getMessage(limit);
		List<String> queueMessageList = new ArrayList<>(messageList.size());

		if (messageList == null || messageList.isEmpty()) {
			logger.info("List is empty");
			return Collections.emptyList();
		}

		logger.info("Got {} messages from queue", messageList.size());
		// List<String> queueMessageList=new ArrayList<>(messageList.size());
		QueueMessage queueMessage = null;
		for (String msg : messageList) {
			logger.info("Serializing message {}", msg);
			/*
			 * queueMessage=QueueMessage.dtoFromJson(msg); if(queueMessage!=null) {
			 * logger.info("Serialized message {} ",queueMessage);
			 * queueMessageList.add(queueMessage); } else {
			 * logger.error("Error: Unable to parse queue message {}",msg); }
			 */
			queueMessageList.add(msg);

		}
		logger.info("Received Message List :{}", queueMessageList);
		return queueMessageList;
	}

	public Boolean getViol(QueueMessage queueObj) throws ParseException {
		final String uri = configVariable.getExclusionUrl();

		Exclusion obj = new Exclusion();
		obj.setAgencyId(queueObj.getAccountAgencyId());
		obj.setPlazaId(queueObj.getPlazaId());
		obj.setLaneId(queueObj.getLaneId());
		obj.setLaneMode(queueObj.getLaneMode());
		obj.setViolType(queueObj.getViolType());
		obj.setTollRevenuType(queueObj.getTollRevenueType());
		obj.setExclusionStage(0);
		if (queueObj.getEtcAccountId() != null) {
			obj.setEtcAccountId((int) (long) queueObj.getEtcAccountId());
		}
		obj.setPlateState(queueObj.getPlateState());
		obj.setPlateNumber(queueObj.getPlateNumber());
		obj.setVehicleSpeed(queueObj.getVehicleSpeed());
		// obj.setTxTypeInd(queueObj.getTxTypeInd());
		obj.setTxTypeInd(queueObj.getTxTypeInd() != null ? queueObj.getTxTypeInd() : " ");
		obj.setTxSubtypeInd(queueObj.getTxSubtypeInd() != null ? queueObj.getTxSubtypeInd() : " ");

		// obj.setTxSubtypeInd(queueObj.getTxSubtype());
		if (queueObj.getTxTimestamp() != null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// Date dateTime = (Date) format.parse(queueObj.getTxTimeStamp());
			OffsetDateTime dateTime = queueObj.getTxTimestamp();
			// ZonedDateTime localDateTime =
			// dateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			OffsetDateTime localDateTime = dateTime; // .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			obj.setTrxDateTime(localDateTime);
		}
		Boolean result = restTemplate.postForObject(uri, obj, boolean.class);
		logger.info("Viol exclusion api result:{}", result);
		return result;

	}

	public QueueMessage getAetFlag(QueueMessage queueObj) {
		String laneLpr = null;
		String plazaLpr = null;
		if (queueObj.getLaneId() != null) {
			laneLpr = masterCache.getLane(queueObj.getLaneId().longValue());
		}

		if (queueObj.getPlazaId() != null) {
			plazaLpr = masterCache.getPlaza(queueObj.getPlazaId().longValue());
		}

		if (laneLpr != null && laneLpr.equals("Y")) {

			queueObj.setAetFlag("Y");

		} else if (laneLpr != null && laneLpr.equals("N")) {

			queueObj.setAetFlag("N");

		} else if (plazaLpr != null && plazaLpr.equals("Y")) {

			queueObj.setAetFlag("Y");

		} else if (plazaLpr != null && plazaLpr.equals("N")) {

			queueObj.setAetFlag("N");

		} else {

			queueObj.setAetFlag("N");
		}

		return queueObj;

	}

	public Long getDaysGap(QueueMessage queueObj) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date firstDate = sdf.parse(LocalDate.now().toString());
		if (queueObj.getTxDate() != null && !(queueObj.getTxDate().trim().isEmpty())) {
			Date secondDate = sdf.parse(queueObj.getTxDate());
			long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
			long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
			return diff;
		}
		return null;
	}

	public Integer getViolationTypeValue(String violType, String value) {
		Codes voiltypeCode = masterCache.getCodeID(violType, value);
		Integer voiltypeCodeID = null;
		if (voiltypeCode != null) {
			voiltypeCodeID = voiltypeCode.getCodeId();
		}
		return voiltypeCodeID;

	}

	public Boolean publishMessages(OssStreamClient tollPostingStreamClient, String streamId,
			IbtsViolProcessDTO ibtsViolProcessDTO, Gson gson) {
		byte[] msg = gson.toJson(ibtsViolProcessDTO).getBytes();
		return tollPostingStreamClient.publishMessage(String.valueOf(ibtsViolProcessDTO.getLaneTxId()), msg, streamId);
	}

	@PostConstruct
	public void startJob() {
		Thread t = new Thread(this);
		t.start();
	}
}
