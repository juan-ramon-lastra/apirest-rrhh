package com.formacionspring.apirest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.formacionspring.apirest.dao.UsuarioDao;
import com.formacionspring.apirest.entity.Usuario;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioDao userDao;
	
	@Override
	public List<Usuario> findAll() {
		return (List<Usuario>) userDao.findAll();
	}

	@Override
	public Usuario findById(Long id) {
		return userDao.findById(id).orElse(null);
	}

	@Override
	public Usuario save(Usuario usuario) {
		return userDao.save(usuario);
	}

	@Override
	public void delete(Long id) {
		userDao.deleteById(id); 
	}

	@Override
	public Usuario login(Usuario usuario) {
		
		boolean userValid = userDao.existsById(usuario.getId());
		
		if (userValid) {
			return usuario;
		}

		return null;
	}
}


