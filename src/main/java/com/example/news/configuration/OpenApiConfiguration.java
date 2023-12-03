package com.example.news.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

	@Bean
	public OpenAPI openApiDescription(){
		Server localhostServer = new Server();
		localhostServer.setUrl("http://localhost:8080");
		localhostServer.setDescription("Local env");

		Contact contact = new Contact();
		contact.setName("Мозгин Владимир");
		contact.setEmail("v.mosgin@gmail.com");
		contact.setUrl("http://some.url");

		License mitLicense = new License().name("GNU AGPLv3")
				.url("https://chooseLicense.com/license/agpl-3.0/");

		Info info = new Info()
				.title("News service api")
				.version("1.0")
				.contact(contact)
				.description("API for news service")
				.termsOfService("http://someterms.url")
				.license(mitLicense);

		return new OpenAPI().info(info).servers(List.of(localhostServer));
	}
}
