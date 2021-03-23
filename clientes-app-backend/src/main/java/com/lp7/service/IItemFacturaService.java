package com.lp7.service;

import java.util.List;

import com.lp7.model.ItemFactura;

public interface IItemFacturaService {

	List<ItemFactura> listar();
	ItemFactura registrar(ItemFactura item);
	ItemFactura modificar(ItemFactura item);
	void eliminar(Integer id);
	ItemFactura listarPorId(Integer id);
	
}
