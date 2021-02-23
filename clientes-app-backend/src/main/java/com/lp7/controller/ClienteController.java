package com.lp7.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lp7.model.Cliente;
import com.lp7.service.IClienteService;
import com.lp7.service.IUploadFileService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private IClienteService service;
	
	@Autowired
	private IUploadFileService uploadService;
	
	@GetMapping
	public ResponseEntity<List<Cliente>> listar(){
		List<Cliente> response = service.findAll();
		return new ResponseEntity<List<Cliente>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> listarporId(@PathVariable("id") Integer id) {
		
		Cliente cliente = null;
		Map<String, Object> response = new HashMap<>(); 
		try {
			cliente = service.listarPorId(id);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if(cliente == null) {
			response.put("mensaje", "El ID ".concat(id.toString()).concat(" no existe"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}
	
	@GetMapping("/page/{page}")
	public ResponseEntity<Page<Cliente>> listarPegeable(@PathVariable("page") Integer page){
		Pageable pageable = PageRequest.of(page, 3);
		Page<Cliente> response = service.listarPaginacion(pageable);
		return new ResponseEntity<Page<Cliente>>(response, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> registrar(@Valid @RequestBody Cliente cliente, BindingResult result) {
		Cliente clienteNew = null;
		Map<String, Object> response = new HashMap<>();
		if(result.hasErrors()) {
			/*List<String> errors = new ArrayList<>();
			for(FieldError err: result.getFieldErrors()) {
				errors.add("El campo '"+ err.getField()+ "' "+ err.getDefaultMessage());*/
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '"+ err.getField()+ "' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			clienteNew =  service.registrar(cliente);
		}catch(DataAccessException e){
			response.put("mensaje", "error al realizar insert cliente en la base de datos");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Cliente>(clienteNew, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> modificar(@Valid @RequestBody Cliente cliente,@PathVariable("id") Integer id, BindingResult result) {
		
		Cliente clienteActual = service.listarPorId(id);
		Cliente clienteUpdate = null;
		Map<String, Object> response = new HashMap<>();
		if(result.hasErrors()) {
			/*List<String> errors = new ArrayList<>();
			for(FieldError err: result.getFieldErrors()) {
				errors.add("El campo '"+ err.getField()+ "' "+ err.getDefaultMessage());*/
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '"+ err.getField()+ "' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(clienteActual == null) {
			response.put("mensaje", "El ID ".concat(id.toString()).concat(" no existe"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			clienteActual.setApellido(cliente.getApellido());
			clienteActual.setNombre(cliente.getNombre());
			clienteActual.setEmail(cliente.getEmail());
			clienteUpdate = service.modificar(clienteActual);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al actualizar cliente en la base de datos");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
		return new ResponseEntity<Cliente>(clienteUpdate, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable("id") Integer id) {
		Map<String, Object> response = new HashMap<>();
		try {
			Cliente cliente = service.listarPorId(id);
			String fotoAnterior = cliente.getFoto();
			uploadService.eliminar(fotoAnterior);
			if(fotoAnterior != null && fotoAnterior.length() >0)			
			service.eliminar(id);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al eliminar cliente en la base de datos");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Cliente se eliminó con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Integer id){
		Map<String, Object> response = new HashMap<>();
		Cliente cliente = service.listarPorId(id);
		
		if(!archivo.isEmpty()) {
			String nombreArchivo = null;
			try {
				nombreArchivo = uploadService.copiar(archivo);
			} catch (IOException e) {
				response.put("mensaje", "Error al subir la imagen");
				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}			
			String nombreFoto = cliente.getFoto();
			uploadService.eliminar(nombreFoto);			
			cliente.setFoto(nombreArchivo);
			service.registrar(cliente);
			response.put("cliente", cliente);
			response.put("mensaje", "haz subido correctamente la imagen " + nombreArchivo);
		}				
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/uploads/img/{nombreFoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto){
		Resource recurso = null;
		try {
			recurso = uploadService.cargar(nombreFoto);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename =\"" + recurso.getFilename() + "\"");
		return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
	}

}
