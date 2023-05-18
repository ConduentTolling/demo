package com.conduent.tollposting;

import org.springframework.stereotype.Component;

import com.conduent.tollposting.dto.TollPostResponseDTO;
@Component
public class TestConstants {

	//@Value("${config.tollPostingUri}")
	private String tollPostingUri="http://193.122.158.101:9696/fpms/tpmsIntegration/accountToll";

	public String getTollPostingUri() {
		return tollPostingUri;
	}

	public void setTollPostingUri(String tollPostingUri) {
		this.tollPostingUri = tollPostingUri;
	}
	
	public TollPostResponseDTO getValues() {
		TollPostResponseDTO expectedObj = new TollPostResponseDTO();
		expectedObj.setLaneTxId((long) 411);
		expectedObj.setDepositId("2-CH8RpOje");
		expectedObj.setPostedDate("2021-04-21T04:48:50.581+00:00");
		expectedObj.setStatus(1);
		return expectedObj;
	}
	
}
