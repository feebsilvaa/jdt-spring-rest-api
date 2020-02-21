package br.com.feedev.jdt.springrest.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/hello")
public class IndexController {

	@GetMapping
	public ResponseEntity<?> helloRest(@RequestParam(name = "nome", required = false, defaultValue = "Usuário") String nome) {
		return ResponseEntity.status(HttpStatus.OK).body(String.format("Hello %s Rest Service!", nome));
	}
	
}
