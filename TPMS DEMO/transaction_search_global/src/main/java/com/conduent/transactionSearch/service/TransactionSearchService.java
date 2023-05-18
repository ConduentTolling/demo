package com.conduent.transactionSearch.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.conduent.transactionSearch.model.TranQueryReturn;
import com.conduent.transactionSearch.model.TranQueryReturnFromView;
import com.conduent.transactionSearch.model.TransactionApiResponse;
import com.conduent.transactionSearch.model.TransactionResponse;
import com.conduent.transactionSearch.model.TransactionResponsePOJO;
import com.conduent.transactionSearch.model.TransactionSearchApiResponse;
import com.conduent.transactionSearch.model.TransactionSearchGlobalRequest;
import com.conduent.transactionSearch.model.TransactionSearchRequest;



public interface TransactionSearchService {
	   public List<TransactionResponsePOJO> buildTransactionSearchQuery(TransactionSearchRequest transactionSearchRequest);
       public List<TranQueryReturn> buildTransactionSearchGlobalQuery(
      			TransactionSearchGlobalRequest transactionSearchGlobalRequest) ;
       public TransactionApiResponse buildFinalResponse(List<TransactionResponsePOJO> transactionReponsePOJOList,Object requestObject) ;
       public TransactionSearchApiResponse buildResponseAsPerCRM(List<TransactionResponsePOJO> transactionReponsePOJOList,Object requestObject);
       public void setExceptionResponse(TransactionSearchApiResponse transactionSearchApiResponse);
       public void setRequestBodyMapToRequestValues(TransactionSearchGlobalRequest transactionSearchGlobalRequest,Map<String,String> allParamsMap); 
       public List<TransactionResponsePOJO> transactionResponsePOJO(List<TranQueryReturn> tranQueryReturnList,TransactionSearchGlobalRequest transactionSearchGlobalRequest);	 	    		
   	   public List<TranQueryReturnFromView>  buildTransactionSearchGlobalQueryFromView(TransactionSearchGlobalRequest transactionSearchGlobalRequest);
   	public List<TransactionResponsePOJO> transactionResponsePOJOFromView(List<TranQueryReturnFromView> tranQueryReturnList,TransactionSearchGlobalRequest transactionSearchGlobalRequest) ;
   		

}
