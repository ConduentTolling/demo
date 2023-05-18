package com.conduent.tpms.qeval.BatchController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import com.conduent.app.timezone.utility.TimeZoneConv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.conduent.tpms.qeval.dao.impl.QEvaluationFpmsDaoImpl;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
@Component
//@EnableScheduling
@RequestMapping("/api/qeval")
@RestController
public class BatchLancherController {
	private static final Logger log = LoggerFactory.getLogger(BatchLancherController.class);
	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job importUserJob;
	@Autowired 
	private QEvaluationFpmsDaoImpl qEval;
	Calendar cal = Calendar.getInstance();
	@Autowired
	TimeZoneConv timeZoneConv;

	//@Scheduled(cron = "0 15 12 * * ?")
	//@Scheduled(fixedDelay = 20000)
	@RequestMapping(value="/invokejob", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Batch job has been invoked",response = String.class )})
    @ApiOperation(value="Q-evaluation Process")
	public String handle() throws Exception {

		MDC.put("logFileName", "Q_EVALUATION_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
        log.info("logFileName: {}",MDC.get("logFileName"));
		List<Integer> runDateList = getRundateList();
		for(Integer item : runDateList) {

			jobLauncher.run(importUserJob, createJobParam(item.toString()));
		}

		return "Batch job has been invoked";
	}

	private JobParameters createJobParam(String dateParam) {
		Map<String, JobParameter> parameters = new HashMap<>();

		JobParameter parameter = new JobParameter(dateParam);
		parameters.put("currentTime", parameter);
		parameters.put("Id",new JobParameter( Math.random()));

		return new JobParameters(parameters);
	}

	private List<Integer> getRundateList() {

		List<Integer> dayList = new ArrayList<Integer>();
		//Get last rundate form DAO
		String lastRun=qEval.getLastEvalRunDate();
		LocalDate lastRunDate = LocalDate.parse(lastRun, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		LocalDate currentDate = LocalDate.now();
		log.info("Last run day is "+lastRunDate);

		long numOfDaysBetween = ChronoUnit.DAYS.between(lastRunDate, currentDate);
		dayList=IntStream.iterate(0, i -> i + 1).limit(numOfDaysBetween) .mapToObj(i -> currentDate.minusDays(i).getDayOfMonth()) .collect(Collectors.toList());
		if(!dayList.isEmpty())
		{
			Collections.reverse(dayList);
			log.info("Days list "+dayList);
		}
		else 
		{
			log.info("Job already executed for "+currentDate);
		}

		return dayList;
	}





}
