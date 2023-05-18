package com.conduent.tpms.NY12.controller;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.conduent.tpms.NY12.model.Ny12ProcessRequest;
import com.conduent.tpms.NY12.service.NY12_ProcessService;
import com.conduent.tpms.NY12.service.impl.NY12_ProcessServiceImpl;
import com.conduent.tpms.NY12.vo.Ny12ProcessFinalResponseVO;
import com.conduent.tpms.NY12.vo.Ny12ProcessResponseVO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api")
public class NY12_Controller {
	//private static Logger logger = LoggerFactory.getLogger(NY12_ProcessServiceImpl.class);

	@Autowired
    NY12_ProcessService ny12ProcessService;

	@GetMapping("/start")
	@ApiResponses( value = { @ApiResponse(code=200, message ="OK", response = Ny12ProcessFinalResponseVO.class) } )
	public ResponseEntity<Ny12ProcessFinalResponseVO> ny12_Process() {
		//logger.info("Class Name :"+this.getClass()+" Method Name : ny12_Process - STARTED");
		 
		return new ResponseEntity<Ny12ProcessFinalResponseVO>(ny12ProcessService.ny12StartProcess(), HttpStatus.OK);

	}
	
}
