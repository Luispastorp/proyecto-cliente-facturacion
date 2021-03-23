package com.lp7.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lp7.dao.IProductoDao;
import com.lp7.model.Producto;
import com.lp7.service.IProductoService;

@Service
public class ProductoServiceImpl implements IProductoService{
	
	@Autowired
	IProductoDao dao;

	@Override
	public List<Producto> listar() {
		return dao.findAll();
	}

	@Transactional
	@Override
	public Producto registrar(Producto producto) {
		return dao.save(producto);
	}

	@Transactional
	@Override
	public Producto modificar(Producto producto) {
		return dao.save(producto);
	}

	@Transactional
	@Override
	public void eliminar(Integer id) {
		dao.deleteById(id);
	}

	@Override
	public Producto listarPorId(Integer id) {
		return dao.findById(id).orElse(null);
	}

	@Override
	public List<Producto> listarPorProducto(String term) {
		return dao.findByNombreContainingIgnoreCase(term);
	}

}
