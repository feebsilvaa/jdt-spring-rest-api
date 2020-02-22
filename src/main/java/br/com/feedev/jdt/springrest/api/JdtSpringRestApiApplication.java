package br.com.feedev.jdt.springrest.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableTransactionManagement
public class JdtSpringRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(JdtSpringRestApiApplication.class, args);
	}
	
}
