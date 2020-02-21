package br.com.feedev.jdt.springrest.api.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import br.com.feedev.jdt.springrest.api.model.Telefone;
import br.com.feedev.jdt.springrest.api.model.Usuario;
import br.com.feedev.jdt.springrest.api.repository.TelefoneRepository;
import br.com.feedev.jdt.springrest.api.repository.UsuarioRepository;

@Component
@Profile("dev")
public class DataInitializr implements ApplicationListener<ContextRefreshedEvent> {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private UsuarioRepository usuarioRepository;
//	private RoleRepository roleRepository;
//	private ProfissaoRepository profissaoRepository;
//	private PessoaRepository pessoaRepository;
	private TelefoneRepository telefoneRepository;

	@Autowired
	public DataInitializr(UsuarioRepository usuarioRepository,
			TelefoneRepository telefoneRepository) {
		this.usuarioRepository = usuarioRepository;
		this.telefoneRepository = telefoneRepository;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
//		log.info("Inserindo Roles");
//		Role roleAdmin = new Role("ROLE_ADMIN");
//		Role roleGerente = new Role("ROLE_GERENTE");
//		Role roleOpCaixa = new Role("ROLE_OPCAIXA");
//		List<Role> roles = Arrays.asList(roleAdmin, roleGerente, roleOpCaixa);
//		roles.forEach(role -> {
//			try {
//				this.roleRepository.save(role);
//			} catch (DataIntegrityViolationException e) {
//			}
//		});
//		roles = roleRepository.findAll();
//		for (Role role : roles) {
//			if (role.equals(roleAdmin))
//				roleAdmin = role;
//			if (role.equals(roleGerente))
//				roleGerente = role;
//			if (role.equals(roleOpCaixa))
//				roleOpCaixa = role;
//		}

		log.info("Inserindo usuários");
//		List<Usuario> usuarios = Arrays.asList(
//				new Usuario("admin", BCryptUtils.criptografar("admin"), Arrays.asList(roleAdmin, roleGerente)),
//				new Usuario("user01", BCryptUtils.criptografar("user01"), Arrays.asList(roleOpCaixa)));
//		usuarios.forEach(usuario -> {
//			try {
//				this.usuarioRepository.save(usuario);
//			} catch (DataIntegrityViolationException e) {
//			}
//		});
		List<Usuario> usuarios = Arrays.asList(
				new Usuario("admin", BCryptUtils.criptografar("admin"), "Admin"),
				new Usuario("user01", BCryptUtils.criptografar("user01"), "Admin"));
		usuarios.forEach(usuario -> {
			try {
				List<Telefone> telefones = Arrays.asList(
						new Telefone(String.valueOf(new Random().nextInt(10)), usuario),
						new Telefone(String.valueOf(new Random().nextInt(10)), usuario),
						new Telefone(String.valueOf(new Random().nextInt(10)), usuario));
				usuario.setTelefones(telefones);
				this.usuarioRepository.save(usuario);
			} catch (DataIntegrityViolationException e) {
			}
		});
	}

}
