package com.lp7.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lp7.model.Cliente;

public interface IClienteDao extends JpaRepository<Cliente, Integer>{

}
