package com.conduent.tpms.ictx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.conduent.tpms.ictx.service.IctxSchedulerService;



/**
 * Ictx Controller
 * 
 * 
 * @author deepeshb
 *
 */
@RestController
//@RequestMapping("/api/ictx/scheduler/")
public class IctxSchedulerController {
	private static final Logger logger = LoggerFactory.getLogger(IctxSchedulerController.class);

	@Autowired
	IctxSchedulerService ictxProcessService;

	/**
	 * Start the process
	 */
	@GetMapping("/api/ictx/scheduler/process")
	public String startProcess(@RequestParam String isHubFile) {
		if(isHubFile.equalsIgnoreCase("N")) {
			ictxProcessService.process("ictx");
		}else if(isHubFile.equalsIgnoreCase("Y")) {
			ictxProcessService.processHubFile("ictx");
		}else {
			return "Job Execution failed : Provide Valid Parameter..";
		}
		logger.info("Process completed..");
		return "Process completed..";
	}
	
	@GetMapping("/api/itxc/scheduler/process/")
	public String startProcessItxc() {
		ictxProcessService.process("itxc");
		logger.info("Process completed..");
		return "Process completed..";
	}

}
