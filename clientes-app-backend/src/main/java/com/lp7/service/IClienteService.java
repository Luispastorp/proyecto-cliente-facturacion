package com.lp7.service;

import java.util.List;

import com.lp7.model.Cliente;

public interface IClienteService {

	List<Cliente> findAll();
	Cliente registrar(Cliente cliente);
	Cliente modificar(Cliente cliente);
	Cliente listarPorId(Integer id);
	void eliminar(Integer id);
	
}
