package com.lp7.service;

import java.util.List;

import com.lp7.model.Producto;

public interface IProductoService {
	
	List<Producto> listar();
	Producto registrar(Producto producto);
	Producto modificar(Producto producto);
	void eliminar(Integer id);
	Producto listarPorId(Integer id);
	List<Producto> listarPorProducto(String term);

}
