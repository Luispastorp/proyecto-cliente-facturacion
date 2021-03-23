package com.lp7.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lp7.model.ItemFactura;
import com.lp7.service.IItemFacturaService;

@RestController
@RequestMapping("/items")
public class ItemFacturaController {

	@Autowired
	IItemFacturaService service;
	
	@GetMapping
	public ResponseEntity<?> listar(){
		List<ItemFactura> lista = null;
		Map<String, Object> response = new HashMap<>();
		try {
			lista = service.listar();
		}catch(DataAccessException e){
			response.put("mensaje", "Error al listar los items");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<ItemFactura>>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> listarPorId(@PathVariable("id") Integer id){
		ItemFactura item = null;
		Map<String, Object> response = new HashMap<>();
		try {
			item = service.listarPorId(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta del item");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(item == null) {
			response.put("mensaje", "El ID: " + id + " no existe");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ItemFactura>(item, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> registar(@RequestBody ItemFactura item){
		ItemFactura itemFactura = null;
		Map<String, Object> response = new HashMap<>();
		try {
			itemFactura = service.registrar(item);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al registar item");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ItemFactura>(itemFactura, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> modificar(@PathVariable("id") Integer id, @RequestBody ItemFactura item){
		ItemFactura itemActual = service.listarPorId(id);
		ItemFactura itemUpdate = null;
		Map<String, Object> response = new HashMap<>();
		if(itemActual == null) {
			response.put("mensaje", "El ID: " + id + " no existe");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			itemActual.setCantidadProducto(item.getCantidadProducto());
			itemActual.setProducto(item.getProducto());
			itemUpdate = service.modificar(itemActual);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar item");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ItemFactura>(itemUpdate, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Integer id){
		ItemFactura item = service.listarPorId(id);
		Map<String, Object> response = new HashMap<>();
		if(item == null) {
			response.put("mensaje", "El ID: " + id + " no existe");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			service.eliminar(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar item");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
}
