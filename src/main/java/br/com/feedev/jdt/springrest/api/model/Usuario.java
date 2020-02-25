
package br.com.feedev.jdt.springrest.api.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.feedev.jdt.springrest.api.PasswordUtil;

@Entity
@Table(name = "usuario")
//@Table(name = "usuario", uniqueConstraints = @UniqueConstraint(columnNames = "login", name = "login_uk" ))
public class Usuario implements UserDetails {

	private static final long serialVersionUID = 7147024309325234419L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@NotEmpty(message = "Login é obrigatório.")
	@Column(unique = true)
	private String login;

	@NotEmpty(message = "Senha é obrigatória.")
	private String senha;

	@NotEmpty(message = "Nome é obrigatório.")
	private String nome;

	@OneToMany(mappedBy = "usuario", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Telefone> telefones;

	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_role",
		joinColumns = @JoinColumn(
				referencedColumnName = "id",
				table = "usuario", 
				updatable = false,
				unique = false),
		inverseJoinColumns = @JoinColumn(
				referencedColumnName = "id",
				table = "role", 
				updatable = false,
				unique = false),
	uniqueConstraints = @UniqueConstraint(columnNames = { "usuario_id", "roles_id" }, name = "unique_role_user"))
	private List<Role> roles;

	public Usuario() {
	}
	
	public Usuario(String login, String senha, String nome, List<Telefone> telefones, List<Role> roles) {
		this.login = login;
		this.senha = senha;
		this.nome = nome;
		this.telefones = telefones;
		this.roles = roles;
	}

	public Usuario(String login, String senha, String nome, List<Role> roles) {
		this.login = login;
		this.senha = senha;
		this.nome = nome;
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return PasswordUtil.encrypt(senha);
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", login=" + login + ", senha=" + senha + ", nome=" + nome + ", telefones="
				+ telefones + "]";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
