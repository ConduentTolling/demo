package com.conduent.tollposting.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.conduent.tollposting.constant.Constants;
import com.conduent.tollposting.dto.AccountTollPostDTO;
import com.conduent.tollposting.dto.EtcTransaction;
import com.conduent.tollposting.dto.TollPostResponseDTO;
import com.conduent.tollposting.oss.OssStreamClient;
import com.conduent.tollposting.utility.AllAsyncUtil;
import com.google.gson.Gson;
import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.parser.JSONParser;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private AllAsyncUtil asyncUtil;

	@Autowired
	private Gson gson;

	@Autowired
	OssStreamClient failureQueueClient;

	@Autowired
	OssStreamClient ibtsQueueClient;

	@PostMapping("/api/post-failed-txn/{state}")
	public void retryPosting(@RequestBody Object txn, @PathVariable("state") String state) {
		logger.info("/post-failed-txn triggered..");
		String payload = gson.toJson(txn);
		@SuppressWarnings("deprecation")
		JSONParser parser = new JSONParser();
		JSONArray array = null;
		try {
		array = (JSONArray) parser.parse(payload);
		
		if (null != state && (state.equalsIgnoreCase(Constants.PREPOSTING) || state.equalsIgnoreCase(Constants.TOLLPOSTING))) {
			JSONObject etcObj = (JSONObject) array.get(0); 
			EtcTransaction etc = EtcTransaction.dtoFromJson(String.valueOf(etcObj));
			asyncUtil.processTollPosting(etc, ibtsQueueClient, gson, failureQueueClient);
		}
		else if (null != state && state.equalsIgnoreCase(Constants.AFTERPOSTING)) {
			JSONObject etcObj = (JSONObject) array.get(0); 
			JSONObject tollObj = (JSONObject) array.get(1); 
			JSONObject postResponseObj = (JSONObject) array.get(2);
			
			EtcTransaction etc = EtcTransaction.dtoFromJson(String.valueOf(etcObj));
			AccountTollPostDTO toll = AccountTollPostDTO.dtoFromJson(String.valueOf(tollObj));
			TollPostResponseDTO res = TollPostResponseDTO.dtoFromJson(String.valueOf(postResponseObj));
			asyncUtil.afterPosting(toll, res, etc, ibtsQueueClient, gson, failureQueueClient);
		} 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	} 
	
	@RequestMapping(value = "/api/nysta-toll-post-healthCheck", method = RequestMethod.GET, produces = {MediaType.ALL_VALUE})
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
    @ApiOperation(value="Service status check, returns a string response if toll posting service is running. ")
//	@GetMapping("/api/nysta-toll-post-healthCheck")
	public String healthCheck() {
		logger.info("Health check get URL triggered");
		return "Toll posting service is running";
	}


}
