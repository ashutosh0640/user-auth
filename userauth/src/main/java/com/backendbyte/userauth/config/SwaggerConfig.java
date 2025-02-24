package com.backendbyte.userauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
	
	
	@Bean
	public OpenAPI customOenAPI() {
		return new OpenAPI().info(
				new Info()
				.title("User authentication API")
				.version("v1.0")
				.description("API for user authentication and management using JWT")
				.contact(new Contact()
                        .name("Your Name")
                        .email("ashulook1013@gmail.com")
                        .url("https://backendbyte-userauth.com")));
	}

}
