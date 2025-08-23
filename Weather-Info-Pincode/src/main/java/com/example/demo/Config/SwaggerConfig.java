package com.example.demo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	@Bean
	OpenAPI weatherAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Weather Info Pincode API")
						.description("API for fetching weather information by pincode and date")
						.version("1.0.0"));
	}
}
