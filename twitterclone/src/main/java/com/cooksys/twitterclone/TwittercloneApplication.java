package com.cooksys.twitterclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class TwittercloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(TwittercloneApplication.class, args);
	}
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.forCodeGeneration(true)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}
	
	/**
	 * By default, the SpringFox generated "swagger-ui" will not allow testing of HEAD or OPTIONS http methods. This will enable them.
	 * @return UiConfiguration object used by SpringFox to configure the swagger-ui
	 */
	@Bean
	public UiConfiguration uiConfiguration() {
		return new UiConfiguration(null, new String[] { "get", "post", "put", "delete", "patch", "options", "head" });
	}
}
