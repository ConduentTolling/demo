package com.conduent.tpms.qatp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


@Configuration
@EnableWebMvc
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

	@Value("${swagger.host.url}")
	private String swaggerHostUrl;
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.host(swaggerHostUrl);
	}
    
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");    
   }
}



//////////////////////////////////////////////////////////////////////////////////////////////////////
/*
 * package com.conduent.tpms.qatp.config;
 * 
 * 
 * 
 * import org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
 * import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
 * 
 * import springfox.documentation.builders.PathSelectors; import
 * springfox.documentation.builders.RequestHandlerSelectors; import
 * springfox.documentation.spi.DocumentationType; import
 * springfox.documentation.spring.web.plugins.Docket;
 * 
 * @Configuration public class SwaggerConfig implements WebMvcConfigurer {
 * 
 * 
 * @Bean public Docket api() { return new Docket(DocumentationType.SWAGGER_2)
 * .select() .apis(RequestHandlerSelectors.any()) .paths(PathSelectors.any())
 * .build(); }
 * 
 * @Override public void addResourceHandlers(ResourceHandlerRegistry registry) {
 * 
 * registry .addResourceHandler("swagger-ui.html")
 * .addResourceLocations("classpath:/META-INF/resources/");
 * 
 * registry .addResourceHandler("/webjars/**")
 * .addResourceLocations("classpath:/META-INF/resources/webjars/"); }
 * 
 * }
 */