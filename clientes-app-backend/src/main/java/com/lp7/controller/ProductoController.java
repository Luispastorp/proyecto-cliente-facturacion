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

import com.lp7.model.Producto;
import com.lp7.service.IProductoService;

@RestController
@RequestMapping("/productos")
public class ProductoController {
	
	@Autowired
	IProductoService service;
	
	@GetMapping
	public ResponseEntity<?> listar(){
		List<Producto> lista = null;
		Map<String, Object> response = new HashMap<>();
		try {
			lista = service.listar();
		}catch(DataAccessException e) {
			response.put("mensaje", "error al listar producto");
			response.put("erros", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(lista.isEmpty()) {
			response.put("mensaje", "La lista se encuentra vacia");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Producto>>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> listarPorId(@PathVariable("id") Integer id){
		Producto producto = null;
		Map<String, Object> response = new HashMap<>();
		try {
			producto = service.listarPorId(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta del producto");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(producto == null) {
			response.put("mensaje", "El ID: " + id + " no existe");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Producto>(producto, HttpStatus.OK);
	}
	
	@GetMapping("/filtrar/{term}")
	public ResponseEntity<?> listarPorProducto(@PathVariable("term") String term){
		List<Producto> productos = null;
		Map<String, Object> response = new HashMap<>();
		try {
			productos = service.listarPorProducto(term);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el listado de productos");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(productos.isEmpty()) {
			response.put("mensaje", "No existen productos con esa especificacion");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Producto>>(productos, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> registrar(@RequestBody Producto producto){
		Producto productoNuevo = null;
		Map<String, Object> response = new HashMap<>();
		try {
			productoNuevo = service.registrar(producto);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al registrar el producto");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Producto>(productoNuevo, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> modificar(@PathVariable("id") Integer id, @RequestBody Producto producto){
		Producto productoActual = service.listarPorId(id);
		Producto productoUpdate = null;
		Map<String, Object> response = new HashMap<>();
		if(productoActual == null) {
			response.put("mesaje", "El ID: " + id + " no existe");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			productoActual.setNombre(producto.getNombre());
			productoActual.setPrecio(producto.getPrecio());
			productoUpdate = service.modificar(productoActual);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al alctualizar el producto");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Producto>(productoUpdate, HttpStatus.OK);		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable("id") Integer id){
		Producto producto = service.listarPorId(id);
		Map<String, Object> response = new HashMap<>();
		if(producto == null) {
			response.put("mensaje", "El ID: " + id + " no existe");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			service.eliminar(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar el producto");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
