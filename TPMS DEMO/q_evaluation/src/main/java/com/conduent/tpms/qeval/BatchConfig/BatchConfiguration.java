package com.conduent.tpms.qeval.BatchConfig;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;

import com.conduent.tpms.qeval.BatchModel.AccountInfo;
import com.conduent.tpms.qeval.BatchModel.StatisticsData;
import com.conduent.tpms.qeval.BatchService.BatchService;
import com.conduent.tpms.qeval.config.LoadJpaQueries;
import com.conduent.tpms.qeval.constants.Constants;

@Configuration

public class BatchConfiguration {

	private static final Logger log = LoggerFactory.getLogger(BatchConfiguration.class);
	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	@Autowired
	BatchService batchService;
	/*
	 * @Autowired private QEvaluationFpmsDaoImpl qEval;
	 */

	/*
	 * @Autowired MasterDataCache masterCatch;
	 */
	
	@Autowired
	StatementJobResultListener statementJobResultListener;

	@Autowired
	private StatisticsData statisticsData;
	@Autowired
	private CustomStepListener customStepListener;
	
	/*
	 * @Value("#{jobParameters['currentTime']}") private String lastRunDateParam;
	 */
	
	private String query = LoadJpaQueries.getQueryById("GET_ACCOUNT_INFO");

	/* @Bean
  public FlatFileItemReader<Person> reader() {
    return new FlatFileItemReaderBuilder<Person>()
      .name("accountItemReader")
      .resource(new ClassPathResource("sample-data.csv"))
      .delimited()
      .names(new String[]{"firstName", "lastName"})
      .fieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
        setTargetType(Person.class);
      }})
      .build();
  }
	 */

	@Bean
	@StepScope
	public JdbcCursorItemReader<AccountInfo> customerItemReader(DataSource dataSource, @Value("#{jobParameters['currentTime']}") String lastRunDateParam) {
	
		log.info("Reader  Start");
		log.info("Job Running for "+lastRunDateParam+" Day ");
		
		//String lastRunDate=qEval.getLastEvalRunDate();
		Map<String,	Object> namedParameters	= new HashMap<String,Object>();
		namedParameters.put(Constants.ANNIVERSAY_DOM,lastRunDateParam);
		namedParameters.put(Constants.ACCT_ACT_STATUS,Constants.ACCT_ACT_STATUS_VALUE);

		//System.out.println("namedParameters-----------"+namedParameters);
		String preparedSql = NamedParameterUtils.substituteNamedParameters(query,
				new MapSqlParameterSource(namedParameters));

		PreparedStatementSetter preparedStatementSetter = new ArgumentPreparedStatementSetter(
				NamedParameterUtils.buildValueArray(query, namedParameters));	
		
		log.info("Writer End");

		return new JdbcCursorItemReaderBuilder<AccountInfo>().name("AccountInfo").dataSource(dataSource)
				.sql(preparedSql).preparedStatementSetter(preparedStatementSetter)
				.verifyCursorPosition(false)
				.rowMapper(new BeanPropertyRowMapper<>(AccountInfo.class))
				.build();
		
		/*	return new JdbcCursorItemReaderBuilder<AccountInfo>()
  			.dataSource(this.dataSource)
  			.name("Customer")
  			.verifyCursorPosition(false)
  			.sql(sqlQuery)
  			.rowMapper(new CustomerRowMapper())
  			.build();
		 */
	}


	@Bean
	public PersonItemProcessor processor() {
		return new PersonItemProcessor();
	}

	@Bean
	public ItemWriter<AccountInfo> customerItemWriter(){


		return items -> 
		{
			log.info("Writer Start");
			
			batchService.processUpdatedRecords(items, statisticsData);
			log.info("Writer End");

		};
	}




	/* @Bean 
  public JdbcBatchItemWriter<Customer> writer(DataSource dataSource) {
	  return new JdbcBatchItemWriterBuilder<Customer>()
			  .itemSqlParameterSourceProvider(new
					  BeanPropertyItemSqlParameterSourceProvider<>())
			  .sql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)"
					  ) .dataSource((javax.sql.DataSource) dataSource) .build(); }*/


	@Bean
	public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
		return jobBuilderFactory.get("importUserJob")
				.incrementer(new RunIdIncrementer())
				.listener(statementJobResultListener)
				.flow(step1)
				.end()
				.build();
	}

	@Bean
	public Step step1(@Qualifier("customerItemReader") ItemReader<AccountInfo> readerRef,ItemWriter<AccountInfo> writer) {
		return stepBuilderFactory.get("step1")
				.<AccountInfo, AccountInfo> chunk(5)
				.reader(readerRef)
				.processor(processor())
				.writer(writer)
				.listener(customStepListener)
				.build();
	}

	@Bean
	NamedParameterJdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}

}
