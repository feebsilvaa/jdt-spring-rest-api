package br.com.feedev.jdt.springrest.api.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.feedev.jdt.springrest.api.service.UsuarioService;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UsuarioService usuarioService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).disable() // Desativa as configurações padão de memória
			
			// Restringir acessos
			.authorizeRequests() 
				// qualquer usuario acessa a uri principal
				.antMatchers(HttpMethod.GET, "/").permitAll() 
				// todas os requests devem estar autenticados
				.anyRequest().authenticated()
				// redirecionamento após logout
				.and().logout().logoutSuccessUrl("/")
				//mapeia url de logout e invalida o usuario
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Autenticacao com banco de dados
		auth.userDetailsService(this.usuarioService).passwordEncoder(new BCryptPasswordEncoder());
		
		// Autenticacao em memória
		/*
		auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
			.withUser("admin")
			.password("$2a$10$53ES7T8EIW2jt4SRri4byelEjT8U.w5BqANRKqaCxcupkatD0Z6Fa")
			.roles("ADMIN");
		*/
	}
	
}
