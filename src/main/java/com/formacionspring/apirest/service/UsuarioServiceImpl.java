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
	public boolean login(String user, String pass) {
		
		boolean logueado = false;
		
		for (Usuario usuario : userDao.findAll()) {
			if (usuario.getUser().equals(user) && usuario.getPass().equals(pass)) {
				logueado = true;
				break;
			}
		}
		
		return logueado;
	}

}


