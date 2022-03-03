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

import com.formacionspring.apirest.entity.Usuario;
import com.formacionspring.apirest.service.UsuarioService;

@RestController
@RequestMapping("/api")
public class UsuarioRestController {

	@Autowired
	private UsuarioService userService;
	
	@GetMapping("/usuarios")
	public List<Usuario> index(){
		return userService.findAll();
	}
	
	@GetMapping("/usuarios/{id}")
	public ResponseEntity<?> findUsuarioById(@PathVariable Long id) {
		Usuario usuario = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			usuario = userService.findById(id);
		} 
		catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar consulta a base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (usuario == null) {
			response.put("mensaje", "El Usuario ID: " + id.toString() + " no existe en la base de datos");
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}
	
	@PostMapping("/usuarios/nuevo")
	public ResponseEntity<?> saveUsuario(@RequestBody Usuario usuario) {
		Usuario usuarioNuevo = null;
		 Map<String, Object> response = new HashMap<>();
		 
		 try {
			 usuarioNuevo = userService.save(usuario);	
		} 
		 catch (DataAccessException e) {
			response.put("mensaje", "Error al insertar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		 
		 response.put("mensaje", "El usuario ha sido creado con éxito!");
		 response.put("usuario", usuarioNuevo);
		 
		 return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/usuarios/actualiza/{id}")
	public ResponseEntity<?> updateUsuario(@RequestBody Usuario usuario, @PathVariable Long id) {
		Usuario usuarioActual = userService.findById(id);
		Map<String, Object> response = new HashMap<>();
		
		if(usuarioActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el usuario con ID: " + id.toString() + " no existe.");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		try {
			usuarioActual.setUser(usuario.getUser());
			usuarioActual.setPass(usuario.getPass());
			
			userService.save(usuarioActual);
		}
		catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el update en base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		 response.put("mensaje", "El usuario ha sido actualizado con éxito!");
		 response.put("usuario", usuarioActual);
		 
		 return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/usuarios/elimina/{id}")
	public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
		Usuario usuarioActual = userService.findById(id);
		Map<String, Object> response = new HashMap<>();
		
		if (usuarioActual == null) {
			response.put("mensaje", "Error: no se pudo eliminar, el usuario ID: " + id.toString() + " no existe.");
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			userService.delete(id);
		}
		catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el delete en base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El usuario ha sido eliminado con éxito!");
		response.put("usuario", usuarioActual);
		 
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}
	
	@PostMapping("/usuarios/login")
	public ResponseEntity<?> loginUsuario(@RequestBody String user, @RequestBody String pass) {
		
		Map<String, Object> response = new HashMap<>();
		
		if (user.isEmpty() || pass.isEmpty()) {
			response.put("mensaje", "Error: Faltan datos.");
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		if (!userService.login(user, pass)) {
			response.put("mensaje", "Error: Usuario incorrecto o Contraseña incorrecta.");
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.UNAUTHORIZED);
		}
		
		response.put("mensaje", "Puedes acceder a la aplicacion!");
		 
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);		
	}
}



