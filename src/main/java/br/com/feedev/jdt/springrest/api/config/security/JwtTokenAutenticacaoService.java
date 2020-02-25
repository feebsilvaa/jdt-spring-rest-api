package br.com.feedev.jdt.springrest.api.config.security;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.feedev.jdt.springrest.api.model.Usuario;
import br.com.feedev.jdt.springrest.api.repository.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtTokenAutenticacaoService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	// Tempo de validade do token (ms)
	private static final Long EXPIRATION_TIME = 172_800_000L;
	
	// Senha unica para compor e assinar o token
	private static final String SECRET_KEY = "091a6be31fc72cb0f74a2ee2ee354c84538c7561c7b7132e16d15d2c3a87d11feb4047c87b21e7b15423eb904e37b080c437b385bdafc07a791052b172371bc6";
	
	private static final String TOKEN_PREFIX = "Bearer";
	
	private static final String HEADER_STRING = "Authorizaion";
	
	public void addAuthentication(HttpServletResponse response, String username) throws IOException {
		String JWT = Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
		
		String token = String.format("%s %s", TOKEN_PREFIX, JWT);
		response.addHeader(HEADER_STRING, token);
		response.getWriter().write(String.format("{\"%s\": \"%s\"}", HEADER_STRING, token));
	}
	
	public Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		
		if (token != null) {
			String user = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token.replace("Bearer", ""))
					.getBody().getSubject();
			if (user != null) {
				Optional<Usuario> userByLogin = this.usuarioRepository.findByLogin(user);
				if (userByLogin.isPresent()) {
					return new UsernamePasswordAuthenticationToken(
							userByLogin.get().getLogin(), 
							userByLogin.get().getPassword(), 
							userByLogin.get().getAuthorities());
				}
			}
		}
		
		return null;
	}
}



