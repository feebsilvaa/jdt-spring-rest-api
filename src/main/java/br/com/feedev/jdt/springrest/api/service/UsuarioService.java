package br.com.feedev.jdt.springrest.api.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.feedev.jdt.springrest.api.exception.UsuarioNaoEditavelException;
import br.com.feedev.jdt.springrest.api.exception.UsuarioNaoExisteException;
import br.com.feedev.jdt.springrest.api.model.Usuario;
import br.com.feedev.jdt.springrest.api.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	/**
	 * Spring security implementation to load user by username
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> user = this.usuarioRepository.findByLogin(username);
		if (!user.isPresent()) {
			throw new UsernameNotFoundException("Usuário não foi encontrado.");			
		}
		return new User(user.get().getUsername(), user.get().getPassword(), user.get().getAuthorities());
	}
	
	public Optional<Usuario> buscarPorId(Long id) {
		return this.usuarioRepository.findById(id);
	}

	public List<Usuario> listar() {
		return this.usuarioRepository.findAll();
	}

	public Usuario salvar(Usuario usuario) {
		Optional<Usuario> usuarioExistente = this.usuarioRepository.findByLogin(usuario.getLogin());
		if (!usuarioExistente.isPresent()) {
			usuario.getTelefones().forEach(telefone -> {
				telefone.setUsuario(usuario);
			});
			return this.usuarioRepository.save(usuario);			
		}
		return null;
	}

	public Usuario atualizar(@Valid Usuario usuario, Long id) throws UsuarioNaoEditavelException, UsuarioNaoExisteException {
		Optional<Usuario> usuarioExistente = this.usuarioRepository.findById(id);			
		if (usuarioExistente.isPresent()) {
			if(isAdmin(usuarioExistente.get()))
				throw new UsuarioNaoEditavelException("Não é possível alterar usuário administrativo");
			usuario.setId(id);
			usuario.getTelefones().forEach(telefone -> {
				telefone.setUsuario(usuario);
			});
			return this.usuarioRepository.save(usuario);			
		}
		throw new UsuarioNaoExisteException("Usuário não existe");
	}

	public void remover(Long id) throws UsuarioNaoEditavelException, UsuarioNaoExisteException {
		Optional<Usuario> usuarioExistente = this.usuarioRepository.findById(id);			
		if (usuarioExistente.isPresent()) {
			if(isAdmin(usuarioExistente.get()))
				throw new UsuarioNaoEditavelException("Não é possível alterar usuário administrativo");
			this.usuarioRepository.deleteById(id);
			return;
		}
		throw new UsuarioNaoExisteException("Usuário não existe");
	}
	
	private boolean isAdmin(Usuario usuario) {
		return "admin".equalsIgnoreCase(usuario.getLogin());
	}
	
}
