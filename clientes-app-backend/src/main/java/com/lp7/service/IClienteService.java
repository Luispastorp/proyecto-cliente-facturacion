package com.lp7.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.lp7.model.Cliente;
import com.lp7.model.Region;

public interface IClienteService {

	List<Cliente> findAll();
	Page<Cliente> listarPaginacion(Pageable pageable);
	Cliente registrar(Cliente cliente);
	Cliente modificar(Cliente cliente);
	Cliente listarPorId(Integer id);
	void eliminar(Integer id);
	public List<Region> findAllRegiones();
	
}
