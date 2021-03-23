package com.lp7.service;

import java.util.List;

import com.lp7.model.Factura;

public interface IFacturaService {

	List<Factura> listar();
	Factura listarPorId(Integer id);
	Factura registrar(Factura factura);
	Factura modificar(Factura factura);
	void eliminar(Integer id);
	
}
