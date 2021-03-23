package com.lp7.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lp7.model.Producto;

public interface IProductoDao extends JpaRepository<Producto, Integer>{
	
	public List<Producto> findByNombreContainingIgnoreCase(String term);

}
