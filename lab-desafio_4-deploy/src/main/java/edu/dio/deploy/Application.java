package edu.dio.deploy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;

/*
 * Erro CORS
 * https://stackoverflow.com/questions/70843940/springdoc-openapi-ui-how-do-i-set-the-request-to-https
 */
@OpenAPIDefinition(servers = { @Server(url = "/", description = "Default server URL") })
@EnableFeignClients
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
