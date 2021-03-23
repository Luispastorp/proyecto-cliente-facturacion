package com.lp7.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lp7.model.Factura;

public interface IFacturaDao extends JpaRepository<Factura, Integer>{

}
