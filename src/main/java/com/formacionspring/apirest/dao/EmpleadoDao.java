package com.formacionspring.apirest.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.formacionspring.apirest.entity.Empleado;

@Repository
public interface EmpleadoDao extends CrudRepository<Empleado, Long> {

}
