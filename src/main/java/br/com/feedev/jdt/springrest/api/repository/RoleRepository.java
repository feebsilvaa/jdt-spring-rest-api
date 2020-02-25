package br.com.feedev.jdt.springrest.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.feedev.jdt.springrest.api.model.Role;

@Repository
public interface RoleRepository extends JpaRepository <Role, Long> {

}
