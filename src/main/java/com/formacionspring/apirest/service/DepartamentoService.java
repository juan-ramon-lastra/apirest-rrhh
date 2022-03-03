package com.formacionspring.apirest.service;

import java.util.List;

import com.formacionspring.apirest.entity.Departamento;

public interface DepartamentoService {

	public List<Departamento> findAll();
	
	public Departamento findById(Long id);
	
	public Departamento save(Departamento departamento);
	
	public void delete(Long id);
}
