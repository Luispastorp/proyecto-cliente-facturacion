package com.lp7.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lp7.model.Cliente;
import com.lp7.service.IClienteService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	IClienteService service;
	
	@GetMapping
	public List<Cliente> listar(){
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> listarporId(@PathVariable("id") Integer id) {
		Cliente cliente = service.listarPorId(id);
		
		Map<String, Object> response = new HashMap<>(); 
		if(cliente == null) {
			response.put("mensaje", "El ID ".concat(id.toString()).concat(" no existe"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente registrar(@RequestBody Cliente cliente) {
		return service.registrar(cliente);
	}
	
	@PutMapping("/{id}")
	public Cliente modificar(@RequestBody Cliente cliente,@PathVariable("id") Integer id) {
		Cliente clienteActual = service.listarPorId(id);
		clienteActual.setApellido(cliente.getApellido());
		clienteActual.setNombre(cliente.getNombre());
		clienteActual.setEmail(cliente.getEmail());
		return service.modificar(clienteActual);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminar(@PathVariable("id") Integer id) {
		service.eliminar(id);
	}

}
