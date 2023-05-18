package com.conduent.parking.posting.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.conduent.parking.posting.constant.Constants;
import com.conduent.parking.posting.dto.AccountTollPostDTO;
import com.conduent.parking.posting.dto.ParkingTransaction;
import com.conduent.parking.posting.dto.TollPostResponseDTO;
import com.conduent.parking.posting.oss.OssStreamClient;
import com.conduent.parking.posting.utility.AllAsyncUtil;
import com.google.gson.Gson;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

@RestController
@RequestMapping
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	AllAsyncUtil asyncUtil;

	@Autowired
	Gson gson;

	@Autowired
	@Qualifier("failureQueueClient")
	private OssStreamClient failureQueueClient;

	@SuppressWarnings("deprecation")
	@PostMapping("/api/post-parking-txn/{state}")
	public void retryPosting(@RequestBody Object txn, @PathVariable("state") String state) {
		logger.info("/post-failed-txn triggered..");
		String payload = gson.toJson(txn);
		JSONParser parser = new JSONParser();
		JSONArray array = null;
		try {
		array = (JSONArray) parser.parse(payload);
		
		if (null != state && (state.equalsIgnoreCase(Constants.PREPOSTING) || state.equalsIgnoreCase(Constants.TOLLPOSTING))) {
			JSONObject etcObj = (JSONObject) array.get(0); 
			ParkingTransaction etc = ParkingTransaction.dtoFromJson(String.valueOf(etcObj));
			asyncUtil.processTollPosting(etc, gson, failureQueueClient);
		}
		else if (null != state && state.equalsIgnoreCase(Constants.AFTERPOSTING)) {
			JSONObject etcObj = (JSONObject) array.get(0); 
			JSONObject tollObj = (JSONObject) array.get(1); 
			JSONObject postResponseObj = (JSONObject) array.get(2);
			
			ParkingTransaction etc = ParkingTransaction.dtoFromJson(String.valueOf(etcObj));
			AccountTollPostDTO toll = AccountTollPostDTO.dtoFromJson(String.valueOf(tollObj));
			TollPostResponseDTO res = TollPostResponseDTO.dtoFromJson(String.valueOf(postResponseObj));
			asyncUtil.afterPosting(toll, res, etc, gson, failureQueueClient);
		} 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	} 
	
	//@GetMapping("/parkingTollpostHealthCheck")
	@RequestMapping(value = "/parkingPostHealthCheck", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
	@ApiOperation(value="API call for parking posting service status.")
	public String healthCheck() {
		logger.info("Health check get URL triggered");
		return "Parking Toll posting service is running";
	}

}
