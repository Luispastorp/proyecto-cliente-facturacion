package com.lp7.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lp7.dao.IItemFacturaDao;
import com.lp7.model.ItemFactura;
import com.lp7.service.IItemFacturaService;

@Service
public class ItemFacturaServiceImpl implements IItemFacturaService{
	
	@Autowired
	IItemFacturaDao dao;

	@Override
	public List<ItemFactura> listar() {
		return dao.findAll();
	}

	@Transactional
	@Override
	public ItemFactura registrar(ItemFactura item) {
		return dao.save(item);
	}

	@Transactional
	@Override
	public ItemFactura modificar(ItemFactura item) {
		return dao.save(item);
	}

	@Transactional
	@Override
	public void eliminar(Integer id) {
		dao.deleteById(id);
	}

	@Override
	public ItemFactura listarPorId(Integer id) {
		return dao.findById(id).orElse(null);
	}
	
}
