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

import com.formacionspring.apirest.entity.Empleado;
import com.formacionspring.apirest.service.EmpleadoService;

@RestController
@RequestMapping("/api")
public class EmpleadoRestController {

	@Autowired
	private EmpleadoService e_service;
	
	@GetMapping("/empleados")
	public List<Empleado> index(){
		return e_service.findAll();
	}
	
	@GetMapping("/empleados/{id}")
	public ResponseEntity<?>  findEmpleadoById(@PathVariable Long id) {
		Empleado empleado = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			empleado = e_service.findById(id);
		} 
		catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar consulta a base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (empleado == null) {
			response.put("mensaje", "El empleado ID: " + id.toString() + " no existe en la base de datos");
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Empleado>(empleado, HttpStatus.OK);
	}
	
	@PostMapping("/empleados/nuevo")
	public ResponseEntity<?> saveEmpleado(@RequestBody Empleado empleado) {
		 Empleado empleadoNuevo = null;
		 Map<String, Object> response = new HashMap<>();
		 
		 try {
			 empleadoNuevo = e_service.save(empleado);	
		} 
		 catch (DataAccessException e) {
			response.put("mensaje", "Error al insertar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		 
		 response.put("mensaje", "El empleado ha sido creado con éxito!");
		 response.put("empleado",empleadoNuevo);
		 
		 return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/empleados/actualiza/{id}")
	public ResponseEntity<?> updateEmpleado(@RequestBody Empleado empleado, @PathVariable Long id) {
		Empleado empleadoActual = e_service.findById(id);
		Map<String, Object> response = new HashMap<>();
		
		if(empleadoActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el empleado con ID: " + id.toString() + " no existe.");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		try {
			empleadoActual.setDni(empleado.getDni());
			empleadoActual.setNombre(empleado.getNombre());
			empleadoActual.setSalario(empleado.getSalario());
			empleadoActual.setTelefono(empleado.getTelefono());
			empleadoActual.setDpto(empleado.getDpto());
			
			e_service.save(empleadoActual);
		}
		catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el update en base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		 response.put("mensaje", "El empleado ha sido actualizado con éxito!");
		 response.put("empleado", empleadoActual);
		 
		 return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/empleados/elimina/{id}")
	public ResponseEntity<?> deleteEmpleado(@PathVariable Long id) {
		Empleado empleadoActual = e_service.findById(id);
		Map<String, Object> response = new HashMap<>();
		
		if (empleadoActual == null) {
			response.put("mensaje", "Error: no se pudo eliminar, el empleado ID: " + id.toString() + " no existe.");
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			e_service.delete(id);
		}
		catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el delete en base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El empleado ha sido eliminado con éxito!");
		response.put("empleado", empleadoActual);
		 
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}
}



