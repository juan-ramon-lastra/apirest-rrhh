package com.formacionspring.apirest.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="empleados")
public class Empleado implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true)
	private String dni;
	
	private String nombre;
	private double salario;
	private int telefono;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="dpto_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Departamento dpto;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public double getSalario() {
		return salario;
	}
	public void setSalario(double salario) {
		this.salario = salario;
	}
	public int getTelefono() {
		return telefono;
	}
	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}
	public Departamento getDpto() {
		return dpto;
	}
	public void setDpto(Departamento dpto) {
		this.dpto = dpto;
	}
	
	/** */
	private static final long serialVersionUID = 1L;
}
