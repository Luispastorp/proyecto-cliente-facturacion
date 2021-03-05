package com.lp7.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lp7.model.Usuario;

public interface IUsuarioDao extends JpaRepository<Usuario, Integer>{
	
	public Usuario findByUsername(String username);

}
