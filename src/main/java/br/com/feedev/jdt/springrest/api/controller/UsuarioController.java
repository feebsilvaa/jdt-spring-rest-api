package br.com.feedev.jdt.springrest.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.feedev.jdt.springrest.api.exception.UsuarioNaoEditavelException;
import br.com.feedev.jdt.springrest.api.exception.UsuarioNaoExisteException;
import br.com.feedev.jdt.springrest.api.model.Usuario;
import br.com.feedev.jdt.springrest.api.service.UsuarioService;


@RestController
@RequestMapping(path = "/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Usuario>> listar() {
		List<Usuario> usuarios = this.usuarioService.listar();
		return ResponseEntity.status(HttpStatus.OK).body(usuarios);
	}

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuario> buscarPorId(@PathVariable(name = "id") Long id) {
		Optional<Usuario> usuario = this.usuarioService.buscarPorId(id);
		if (usuario.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(usuario.get());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuario> salvar(@Valid @RequestBody Usuario usuario) {
		Usuario usuarioSalvo = this.usuarioService.salvar(usuario);
		if (usuarioSalvo != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
		}
		return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
	}

	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> atualizar(@Valid @RequestBody Usuario usuario, @PathVariable(name = "id") Long id) {
		Usuario usuarioAtualizado;
		try {
			usuarioAtualizado = this.usuarioService.atualizar(usuario, id);
		} catch (UsuarioNaoEditavelException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (UsuarioNaoExisteException e) {
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(usuarioAtualizado);
	}

	@DeleteMapping(path = "{id}")
	public ResponseEntity<?> remover(@PathVariable(name = "id") Long id) {
		try {
			this.usuarioService.remover(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (UsuarioNaoEditavelException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (UsuarioNaoExisteException e) {
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao tentar remover o usuário.");
		}
	}

}
