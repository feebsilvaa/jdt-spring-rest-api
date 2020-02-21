package br.com.feedev.jdt.springrest.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class JdtSpringRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(JdtSpringRestApiApplication.class, args);
	}

}
