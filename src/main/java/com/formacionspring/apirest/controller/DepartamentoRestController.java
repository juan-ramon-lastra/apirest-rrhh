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

import com.formacionspring.apirest.entity.Departamento;
import com.formacionspring.apirest.service.DepartamentoService;

@RestController
@RequestMapping("/api")
public class DepartamentoRestController {

	@Autowired
	private DepartamentoService d_service;
	
	@GetMapping("/departamentos")
	public List<Departamento> index(){
		return d_service.findAll();
	}
	
	@GetMapping("/departamentos/{id}")
	public ResponseEntity<?>  findDepartamentoById(@PathVariable Long id) {
		Departamento departamento = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			departamento = d_service.findById(id);
		} 
		catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar consulta a base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (departamento == null) {
			response.put("mensaje", "El departamento ID: " + id.toString() + " no existe.");
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Departamento>(departamento, HttpStatus.OK);
	}
	
	@PostMapping("/departamentos/nuevo")
	public ResponseEntity<?> saveDepartamento(@RequestBody Departamento departamento) {
		Departamento departamentoNuevo = null;
		 Map<String, Object> response = new HashMap<>();
		 
		 try {
			 departamentoNuevo = d_service.save(departamento);	
		} 
		 catch (DataAccessException e) {
			response.put("mensaje", "Error al insertar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		 
		 response.put("mensaje", "El departamento ha sido creado con éxito!");
		 response.put("departamento", departamentoNuevo);
		 
		 return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/departamentos/actualiza/{id}")
	public ResponseEntity<?> updateDepartamento(@RequestBody Departamento departamento, @PathVariable Long id) {
		Departamento departamentoActual = d_service.findById(id);
		Map<String, Object> response = new HashMap<>();
		
		if(departamentoActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el empleado con ID: " + id.toString() + " no existe.");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		try {
			departamentoActual.setNombre(departamento.getNombre());
			departamentoActual.setUbicacion(departamento.getUbicacion());
			
			d_service.save(departamentoActual);
		}
		catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el update en base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		 response.put("mensaje", "El departamento ha sido actualizado con éxito!");
		 response.put("departamento", departamentoActual);
		 
		 return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/departamentos/elimina/{id}")
	public ResponseEntity<?> deleteDepartamento(@PathVariable Long id) {
		Departamento departamentoActual = d_service.findById(id);
		Map<String, Object> response = new HashMap<>();
		
		if (departamentoActual == null) {
			response.put("mensaje", "Error: no se pudo eliminar, el departamento ID: " + id.toString() + " no existe.");
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			d_service.delete(id);
		}
		catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el delete en base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El departamento ha sido eliminado con éxito!");
		response.put("departamento", departamentoActual);
		 
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}
}



