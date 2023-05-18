package com.conduent.transactionSearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.conduent.app.timezone.dao.PlazaDao;
import com.conduent.app.timezone.dao.ProcessParamDao;
import com.conduent.app.timezone.dao.impl.PlazaDaoImpl;
import com.conduent.app.timezone.dao.impl.ProcessParamDaoImpl;
import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.transactionSearch.config.LoadApplicationContext;
import com.conduent.transactionSearch.constants.TransactionSearchConstants;
import com.conduent.transactionSearch.controller.TransactionSearchController;
import com.conduent.transactionSearch.dao.AgencyDao;
import com.conduent.transactionSearch.dao.impl.AgencyDaoImpl;
import com.conduent.transactionSearch.exception.TPMSGlobalException;
import com.conduent.transactionSearch.model.PageBreakPOJO;
import com.conduent.transactionSearch.model.TranDetailClass;
import com.conduent.transactionSearch.model.Transaction;
import com.conduent.transactionSearch.model.TransactionApiResponse;
import com.conduent.transactionSearch.model.TransactionResponsePOJO;
import com.conduent.transactionSearch.model.TransactionSearchFilter;
import com.conduent.transactionSearch.model.TransactionSearchRequest;
import com.conduent.transactionSearch.service.impl.TransactionSearchServiceImpl;
import com.conduent.transactionSearch.utility.MasterCache;
import com.conduent.transactionSearch.utility.TransactionSearchUtility;
import com.conduent.transactionSearch.validation.TransactionSearchValidation;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { ErrorMvcAutoConfiguration.class })
public class TransactionSearchApplication {

	@Bean
	public TimeZoneConv timeZoneConv() {
		return new TimeZoneConv();
	}																																																								

	@Bean
	public ProcessParamDao processParamDao() {
		return new ProcessParamDaoImpl();
	}

	@Bean
	public PlazaDao plazaDao() {
		return new PlazaDaoImpl();
	}

	@Bean
	public AgencyDao agencyDao() {
		return new AgencyDaoImpl();
	}

	@Bean
	public TransactionSearchConstants transactionSearchConstants() {
		return new TransactionSearchConstants();
	}

	@Bean
	public TranDetailClass tranDetailClass() {
		return new TranDetailClass();
	}

	@Bean
	public TransactionSearchValidation transactionSearchValidation() {
		return new TransactionSearchValidation();
	}

	@Bean
	public TransactionSearchServiceImpl transactionSearchServiceImpl() {
		return new TransactionSearchServiceImpl();
	}

	@Bean
	public ApplicationContext loadJpaApplicationContext() {
		return new LoadApplicationContext().getApplicationContext();
	}

	@Bean
	public TransactionResponsePOJO transactionResponsePOJO() {
		return new TransactionResponsePOJO();
	}

	@Bean
	public TransactionSearchUtility transactionSearchUtility() {
		return new TransactionSearchUtility();
	}

	@Bean
	public TransactionSearchFilter transactionSearchFilter() {
		return new TransactionSearchFilter();
	}

	@Bean
	public TransactionSearchRequest transactionSearchRequest() {
		return new TransactionSearchRequest();
	}

	@Bean
	public TransactionApiResponse transactionApiResponse() {
		return new TransactionApiResponse();
	}

	@Bean
	public TransactionSearchController transactionSearchController() {
		return new TransactionSearchController();
	}

	@Bean
	public MasterCache masterCache() {
		return new MasterCache();
	}

	@Bean
	public PageBreakPOJO pageBreakPOJO() {
		return new PageBreakPOJO();
	}

	@Bean
	public TPMSGlobalException tPMSGlobalException() {
		return new TPMSGlobalException(null, null, null);
	}

	@Bean
	public Transaction transaction() {
		return new Transaction();
	}

	public static void main(String[] args) {
		SpringApplication.run(TransactionSearchApplication.class, args);
	}

}
