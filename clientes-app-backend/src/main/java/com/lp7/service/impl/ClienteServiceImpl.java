package com.lp7.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lp7.dao.IClienteDao;
import com.lp7.model.Cliente;
import com.lp7.model.Region;
import com.lp7.service.IClienteService;

@Service
public class ClienteServiceImpl implements IClienteService{

	@Autowired
	IClienteDao dao;
	
	@Override
	public List<Cliente> findAll() {
		return dao.findAll();
	}

	@Transactional
	@Override
	public Cliente registrar(Cliente cliente) {
		return dao.save(cliente);
	}

	@Transactional
	@Override
	public Cliente modificar(Cliente cliente) {
		return dao.save(cliente);
	}

	@Override
	public Cliente listarPorId(Integer id) {
		return dao.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public void eliminar(Integer id) {
		dao.deleteById(id);
	}

	@Override
	public Page<Cliente> listarPaginacion(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	public List<Region> findAllRegiones() {
		return dao.findAllRegiones();
	}

}
