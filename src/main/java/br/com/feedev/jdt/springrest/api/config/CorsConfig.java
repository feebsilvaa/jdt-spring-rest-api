package br.com.feedev.jdt.springrest.api.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class CorsConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry
			.addMapping("/**") // endpoint  ex: /usuario
			.allowedMethods("*")  // métodos http  ex: GET, POST, PUT, DELETE
			.allowedOrigins("*");  // origens ex: https://meu-front.com.br
	}
	
}
