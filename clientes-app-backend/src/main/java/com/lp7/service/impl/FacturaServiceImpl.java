package com.lp7.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lp7.dao.IFacturaDao;
import com.lp7.model.Factura;
import com.lp7.service.IFacturaService;

@Service
public class FacturaServiceImpl implements IFacturaService{
	
	@Autowired
	IFacturaDao dao;

	@Override
	public List<Factura> listar() {
		return dao.findAll();
	}

	@Transactional
	@Override
	public Factura registrar(Factura factura) {
		return dao.save(factura);
	}

	@Transactional
	@Override
	public Factura modificar(Factura factura) {
		return dao.save(factura);
	}

	@Transactional
	@Override
	public void eliminar(Integer id) {
		dao.deleteById(id);
	}

	@Override
	public Factura listarPorId(Integer id) {
		return dao.findById(id).orElse(null);
	}

}
