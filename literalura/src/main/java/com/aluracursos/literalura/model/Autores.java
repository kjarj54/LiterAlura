package com.aluracursos.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Autores(
		@JsonAlias("name") String nombre,
		@JsonAlias("birth_year") short nacimiento,
		@JsonAlias("death_year") short fallecimiento
		) {

	public String nombre() {
		return nombre;
	}

	public short nacimiento() {
		return nacimiento;
	}

	public short fallecimiento() {
		return fallecimiento;
	}

	@Override
	public String toString() {
		return "\n" + nombre + "\n" +
			   "nacimiento = " + nacimiento + "\n" +
			   "fallecimiento = " + fallecimiento;
	}
	
	
	
}