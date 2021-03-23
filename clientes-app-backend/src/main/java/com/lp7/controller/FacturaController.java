package com.lp7.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lp7.model.Factura;
import com.lp7.service.IFacturaService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/facturas")
public class FacturaController {
	
	@Autowired
	IFacturaService service;
	
	
	@GetMapping
	public ResponseEntity<?> listar(){
		List<Factura> lista = null;
		Map<String, Object> response = new HashMap<>();
		try {
			lista = service.listar();
		}catch(DataAccessException e) {
			response.put("mensaje: ", "Error al realizar la consulta de facturas");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<Factura>>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> listarPorId(@PathVariable("id") Integer id){
		Factura factura = null;
		Map<String, Object> response = new HashMap<>();
		try {
			factura = service.listarPorId(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "error al realizar la consulta de la factura ");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(factura == null) {
			response.put("mensaje", "El ID: " + id + " no existe");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Factura>(factura, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> registrar(@RequestBody Factura factura){
		Factura fact = null;
		Map<String, Object> response = new HashMap<>();
		try {
			fact = service.registrar(factura);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al registrar factura");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Factura>(fact, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> modificar(@PathVariable("id") Integer id, @RequestBody Factura factura){		
		Factura facturaActual = service.listarPorId(id);
		Factura facturaUpdate = null;
		Map<String, Object> response = new HashMap<>();
		if(facturaActual == null) {
			response.put("mensaje", "El id: " + id + " no existe");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		if(factura.getCliente().getId() == null) {
			response.put("mensaje", "El Cliente id: " + id + " no existe");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			facturaActual.setCliente(factura.getCliente());
			facturaActual.setRuc(factura.getRuc());		
			facturaUpdate = service.modificar(facturaActual);	
		}catch(DataAccessException e){
			response.put("mensaje", "Error al actualizar la factura");
			response.put("error", e.getMessage());
		}
		return new ResponseEntity<Factura>(facturaUpdate, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable("id") Integer id){		
		Factura factura = service.listarPorId(id);
		Map<String, Object> response = new HashMap<>();
		if(factura == null) {
			response.put("mensaje", "El ID: " + id + " no existe");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			service.eliminar(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar la factura");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	

}
