package com.formacionspring.apirest.controller;

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

import com.formacionspring.apirest.entity.Jefe;
import com.formacionspring.apirest.service.JefeService;

@RestController
@RequestMapping("/api")
public class JefeRestController {

	@Autowired
	private JefeService j_service;
	
	@GetMapping("/jefes")
	public List<Jefe> index(){
		return j_service.findAll();
	}
	
	@GetMapping("/jefes/{id}")
	public ResponseEntity<?>  findJefeById(@PathVariable Long id) {
		Jefe jefe = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			jefe = j_service.findById(id);
		} 
		catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar consulta a base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (jefe == null) {
			response.put("mensaje", "El jefe ID: " + id.toString() + " no existe en la base de datos");
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Jefe>(jefe, HttpStatus.OK);
	}
	
	@PostMapping("/jefes/nuevo")
	public ResponseEntity<?> saveJefe(@RequestBody Jefe jefe) {
		Jefe jefeNuevo = null;
		 Map<String, Object> response = new HashMap<>();
		 
		 try {
			 jefeNuevo = j_service.save(jefe);	
		} 
		 catch (DataAccessException e) {
			response.put("mensaje", "Error al insertar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		 
		 response.put("mensaje", "El jefe ha sido creado con éxito!");
		 response.put("jefe", jefeNuevo);
		 
		 return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/jefes/actualiza/{id}")
	public ResponseEntity<?> updateJefe(@RequestBody Jefe jefe, @PathVariable Long id) {
		Jefe jefeActual = j_service.findById(id);
		Map<String, Object> response = new HashMap<>();
		
		if(jefeActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el jefe ID: " + id.toString() + " no existe.");
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			jefeActual.setDni(jefe.getDni());
			jefeActual.setNombre(jefe.getNombre());
			jefeActual.setSalario(jefe.getSalario());
			jefeActual.setTelefono(jefe.getTelefono());
			jefeActual.setDpto(jefe.getDpto());
			
			j_service.save(jefeActual);
		}
		catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el update en base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		 response.put("mensaje", "El jefe ha sido actualizado con éxito!");
		 response.put("jefe", jefeActual);
		 
		 return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/jefes/elimina/{id}")
	public ResponseEntity<?> deleteJefe(@PathVariable Long id) {
		Jefe jefeActual = j_service.findById(id);
		Map<String, Object> response = new HashMap<>();
		
		if (jefeActual == null) {
			response.put("mensaje", "Error: no se pudo eliminar, el jefe ID: " + id.toString() + " no existe.");
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			j_service.delete(id);
		}
		catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el delete en base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El jefe ha sido eliminado con éxito!");
		response.put("jefe", jefeActual);
		 
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}
}



