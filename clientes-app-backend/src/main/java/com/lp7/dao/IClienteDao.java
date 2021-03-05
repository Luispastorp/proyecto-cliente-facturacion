package com.lp7.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lp7.model.Cliente;
import com.lp7.model.Region;

public interface IClienteDao extends JpaRepository<Cliente, Integer>{
	
	@Query("from Region")
	public List<Region> findAllRegiones();

}
