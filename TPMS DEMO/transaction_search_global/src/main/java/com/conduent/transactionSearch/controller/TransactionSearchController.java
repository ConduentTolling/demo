package com.conduent.transactionSearch.controller;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.transactionSearch.constants.TransactionSearchConstants;
import com.conduent.transactionSearch.exception.TPMSGlobalException;
import com.conduent.transactionSearch.model.Pageable;
import com.conduent.transactionSearch.model.SortClause;
import com.conduent.transactionSearch.model.TranQueryReturn;
import com.conduent.transactionSearch.model.TranQueryReturnFromView;
import com.conduent.transactionSearch.model.TransactionApiResponse;
import com.conduent.transactionSearch.model.TransactionResponse;
import com.conduent.transactionSearch.model.TransactionResponsePOJO;
import com.conduent.transactionSearch.model.TransactionSearchApiResponse;
import com.conduent.transactionSearch.model.TransactionSearchGlobalRequest;
import com.conduent.transactionSearch.model.TransactionSearchRequest;
import com.conduent.transactionSearch.service.TransactionSearchService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;


@RestController
@RequestMapping("/api")
public class TransactionSearchController {
	private static final Logger logger = LoggerFactory.getLogger(TransactionSearchController.class);

	@Autowired
	TransactionSearchService transactionSearchService;

	@Autowired
	TimeZoneConv timeZoneConv;

		//@PostMapping("/transaction-search")
	//	@ApiIgnore
	//	@RequestMapping(value = "/transaction-search", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<TransactionSearchApiResponse> startProcess(@RequestBody TransactionSearchRequest transactionSearchRequest) {
		logger.info("Class Name : "+this.getClass()+" Method Name : startProcess started");
		TransactionSearchConstants.RESPONSE_ERROR_MESSAGE="";
		TransactionSearchConstants.RESPONSE_FIELD_ERRORS=new ArrayList<String>();
		TransactionSearchApiResponse transactionSearchApiResponse = new TransactionSearchApiResponse();
		TransactionApiResponse transactionApiResponse = new TransactionApiResponse();
		try 
		{
			MDC.put("logFileName", "TRANSACTION_SEARCH_GLOBAL_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
	        logger.info("logFileName: {}",MDC.get("logFileName"));
			List<TransactionResponsePOJO> transactionReponsePOJOList = transactionSearchService.buildTransactionSearchQuery(transactionSearchRequest);
			transactionSearchApiResponse = transactionSearchService.buildResponseAsPerCRM( transactionReponsePOJOList,transactionSearchRequest);
			return new ResponseEntity<>(transactionSearchApiResponse, HttpStatus.OK);

		} catch (TPMSGlobalException tpmsEx) {
			transactionSearchService.setExceptionResponse(transactionSearchApiResponse);
			return new ResponseEntity<>(transactionSearchApiResponse, HttpStatus.OK);
		}
		catch (Exception e) 
		{
			logger.info(e.getMessage());
			e.getMessage();
			transactionSearchService.setExceptionResponse(transactionSearchApiResponse);
			logger.info("Class Name : "+this.getClass()+" Method Name : startProcess  Error occured. ");
			return new ResponseEntity<>(transactionSearchApiResponse, HttpStatus.OK);
		}		
	}	
		
	//@ApiIgnore	
	//@PostMapping("/transaction-search-global-requestbody")
	public ResponseEntity<TransactionSearchApiResponse> startGlobalProcess(@RequestBody TransactionSearchGlobalRequest transactionSearchGlobalRequest
			, @RequestParam Map<String,String> allParams) {
		TransactionSearchApiResponse transactionSearchApiResponse = new TransactionSearchApiResponse();
		TransactionApiResponse transactionApiResponse = new TransactionApiResponse();


		Set allKeys = allParams.keySet();
		Iterator itr = allKeys.iterator();
		logger.info("TRANSACTION SEARCH GLOBAL REQUEST : Request Parameter : started");
		logger.info(allParams.toString());
		logger.info(transactionSearchGlobalRequest.toString());
		while (itr.hasNext()) {
			logger.info("TRANSACTION SEARCH GLOBAL REQUEST : Request Parameter : "+itr.next());
		}
		logger.info("TRANSACTION SEARCH GLOBAL REQUEST : Request Parameter : ended");
		TransactionSearchConstants.RESPONSE_ERROR_MESSAGE="";
		TransactionSearchConstants.RESPONSE_FIELD_ERRORS= new ArrayList<String>();
		try 
		{
			MDC.put("logFileName", "TRANSACTION_SEARCH_GLOBAL_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
	        logger.info("logFileName: {}",MDC.get("logFileName"));
			List<TranQueryReturn> queryReturns = transactionSearchService.buildTransactionSearchGlobalQuery(transactionSearchGlobalRequest);
			List<TransactionResponsePOJO> transactionReponsePOJOList = transactionSearchService.transactionResponsePOJO(queryReturns, transactionSearchGlobalRequest);
			transactionSearchApiResponse = transactionSearchService.buildResponseAsPerCRM( transactionReponsePOJOList,transactionSearchGlobalRequest);
			return new ResponseEntity<>(transactionSearchApiResponse, HttpStatus.OK);
		}
		catch (Exception e) 
		{
			logger.info(e.getMessage());
			e.getMessage();
			transactionSearchService.setExceptionResponse(transactionSearchApiResponse);
			return new ResponseEntity<>(transactionSearchApiResponse, HttpStatus.OK);
		}		
	}
	
//	@ApiIgnore
//	@PostMapping("/transaction-search-global-old")
	public ResponseEntity<TransactionSearchApiResponse> startGlobalProcess(@RequestBody Map<String,String> allParametersMap) {
		TransactionSearchApiResponse transactionSearchApiResponse = new TransactionSearchApiResponse();
		TransactionSearchGlobalRequest transactionSearchGlobalRequest=new TransactionSearchGlobalRequest();
		TransactionResponse transactionResponse = new TransactionResponse();
		TransactionSearchConstants.RESPONSE_ERROR_MESSAGE="";
		TransactionSearchConstants.RESPONSE_FIELD_ERRORS= new ArrayList<String>();


		try 
		{
			MDC.put("logFileName", "TRANSACTION_SEARCH_GLOBAL_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
	        logger.info("logFileName: {}",MDC.get("logFileName"));
			transactionSearchService.setRequestBodyMapToRequestValues(transactionSearchGlobalRequest,allParametersMap) ;
			List<TranQueryReturn> queryReturns = transactionSearchService.buildTransactionSearchGlobalQuery(transactionSearchGlobalRequest);
			List<TransactionResponsePOJO> transactionReponsePOJOList = transactionSearchService.transactionResponsePOJO(queryReturns, transactionSearchGlobalRequest);
			transactionSearchApiResponse = transactionSearchService.buildResponseAsPerCRM( transactionReponsePOJOList,transactionSearchGlobalRequest);
			int totalElements = queryReturns.get(0).getTotalElements();
			int totalPages = Math.round(totalElements/transactionSearchGlobalRequest.getSize());
			int fetchedResultCount = transactionReponsePOJOList.size();
			//transactionResponse.setFetchedResultCount(fetchedResultCount);
			transactionResponse.setTotalElements(totalElements);
			transactionResponse.setTotalPages(totalPages);
			transactionResponse.setContent(transactionReponsePOJOList);
			transactionSearchApiResponse.setResult(transactionResponse);
			return new ResponseEntity<>(transactionSearchApiResponse, HttpStatus.OK);
		}
		catch (Exception e) 
		{
			logger.info(e.getMessage());
			e.getMessage();
			transactionSearchService.setExceptionResponse(transactionSearchApiResponse);
			return new ResponseEntity<>(transactionSearchApiResponse, HttpStatus.OK);
		}		
	}

//	@PostMapping("/transaction-search-global")
	
	@RequestMapping(value = "/transaction-search-global", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = ResponseEntity.class )})
	@ApiOperation(value="API call for Global transaction search.")
	public ResponseEntity<TransactionSearchApiResponse> startGlobalProcessFromView(@RequestBody Map<String,String> allParametersMap) {
		TransactionSearchApiResponse transactionSearchApiResponse = new TransactionSearchApiResponse();
		TransactionSearchGlobalRequest transactionSearchGlobalRequest=new TransactionSearchGlobalRequest();
		TransactionResponse transactionResponse = new TransactionResponse();
		Pageable pageable = new Pageable();
		SortClause sortClause = new SortClause();
		TransactionSearchConstants.RESPONSE_ERROR_MESSAGE="";
		TransactionSearchConstants.RESPONSE_FIELD_ERRORS= new ArrayList<String>();


		try 
		{
			MDC.put("logFileName", "TRANSACTION_SEARCH_GLOBAL_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
	        logger.info("logFileName: {}",MDC.get("logFileName"));
			transactionSearchService.setRequestBodyMapToRequestValues(transactionSearchGlobalRequest,allParametersMap) ;
			List<TranQueryReturnFromView> queryReturnsFromView = transactionSearchService.buildTransactionSearchGlobalQueryFromView(transactionSearchGlobalRequest);
		    logger.info("TESTING COUNT: ");
			logger.info(" TOTAL ROWS RETURNED FROM QUERY queryReturnsFromView : "+queryReturnsFromView.size());
			List<TransactionResponsePOJO> transactionReponsePOJOList = transactionSearchService.transactionResponsePOJOFromView(queryReturnsFromView, transactionSearchGlobalRequest);
			transactionSearchApiResponse = transactionSearchService.buildResponseAsPerCRM( transactionReponsePOJOList,transactionSearchGlobalRequest);
			int totalElements = queryReturnsFromView.get(0).getTotalElements();
			int totalPages = Math.round(totalElements/transactionSearchGlobalRequest.getSize())+1;
			int fetchedResultCount = transactionReponsePOJOList.size();
			//transactionResponse.setFetchedResultCount(fetchedResultCount);
			transactionResponse.setTotalElements(totalElements);
			transactionResponse.setTotalPages(totalPages);
			transactionResponse.setContent(transactionReponsePOJOList);
			sortClause.setEmpty(totalElements != 0?false:true);
			sortClause.setSorted(transactionSearchGlobalRequest.getSortBy()==null?false:true);
			sortClause.setUnsorted(transactionSearchGlobalRequest.getSortBy()==null?true:false);
			pageable.setSort(sortClause);
			
			
			/*private SortClause sort;
			private int offset;
			private int pageNumber;
			private int pageSize;
			private boolean paged;
			private boolean unpaged;
			*/
			int offset = 0;
			int pageNumberMinusOne = transactionSearchGlobalRequest.getPage()>0?((transactionSearchGlobalRequest.getPage())-1):0;
			offset = pageNumberMinusOne * transactionSearchGlobalRequest.getSize();
		
				
			pageable.setOffset(offset);
			pageable.setPaged(totalPages<=1?false:true);
			pageable.setUnpaged(totalPages<=1?true:false);
			pageable.setPageNumber(transactionSearchGlobalRequest.getPage());
			pageable.setPageSize(transactionSearchGlobalRequest.getSize());
			transactionResponse.setPageable(pageable);
			/*private int totalElements;
		    private boolean last;
		    private int totalPages;
			private int size;
			private int number;
			private SortClause sort;
			private int numberOfElements;
			private boolean first;
			private boolean empty;
			*/
				
			transactionResponse.setLast(totalPages==transactionSearchGlobalRequest.getPage()?true:false);
			transactionResponse.setTotalElements(totalElements);
			transactionResponse.setTotalPages(totalPages);
			transactionResponse.setSize(transactionSearchGlobalRequest.getSize());
			transactionResponse.setNumber(transactionSearchGlobalRequest.getPage());
			transactionResponse.setSort(sortClause);
			transactionResponse.setNumberOfElements(totalElements);
			transactionResponse.setFirst(transactionSearchGlobalRequest.getPage()==1?true:false);
			transactionResponse.setEmpty(totalElements != 0?false:true);
			
				
			transactionSearchApiResponse.setResult(transactionResponse);
			return new ResponseEntity<>(transactionSearchApiResponse, HttpStatus.OK);
		}
		catch (Exception e) 
		{
			logger.info(e.getMessage());
			e.getMessage();
			transactionSearchService.setExceptionResponse(transactionSearchApiResponse);
			return new ResponseEntity<>(transactionSearchApiResponse, HttpStatus.OK);
		}		
	}

	
}
