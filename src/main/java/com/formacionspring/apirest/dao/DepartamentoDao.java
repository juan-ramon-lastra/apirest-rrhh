package com.formacionspring.apirest.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.formacionspring.apirest.entity.Departamento;

@Repository
public interface DepartamentoDao extends CrudRepository<Departamento, Long> {

}
