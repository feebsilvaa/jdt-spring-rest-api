
package br.com.feedev.jdt.springrest.api.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import br.com.feedev.jdt.springrest.api.PasswordUtil;

@Entity
@Table(name = "usuario")
//@Table(name = "usuario", uniqueConstraints = @UniqueConstraint(columnNames = "login", name = "login_uk" ))
public class Usuario implements Serializable {

	private static final long serialVersionUID = -444998333137088243L;

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

	public Usuario() {
	}
	
	public Usuario(String login, String senha, String nome, List<Telefone> telefones) {
		this.login = login;
		this.senha = senha;
		this.nome = nome;
		this.telefones = telefones;
	}

	public Usuario(String login, String senha, String nome) {
		this.login = login;
		this.senha = senha;
		this.nome = nome;
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

}
