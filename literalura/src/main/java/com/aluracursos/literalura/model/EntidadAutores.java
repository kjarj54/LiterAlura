package com.aluracursos.literalura.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "autores_registrados")
public class EntidadAutores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private short nacimiento;
    private short fallecimiento;
    @ManyToOne
    private EntidadLibros libroId;

    public Long getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    public short getNacimiento() {
        return nacimiento;
    }
    public short getFallecimiento() {
        return fallecimiento;
    }

    public EntidadAutores() {

    }

    public EntidadAutores(String nombre, short nacimiento, short fallecimiento, EntidadLibros libro) {
        this.nombre = nombre;
        this.nacimiento = nacimiento;
        this.fallecimiento = fallecimiento;
        this.libroId = libro;
    }

    @Override
    public String toString() {
        return "\nNombre = " + nombre + "\n" +
                "Nacimiento = " + nacimiento + "\n" +
                "Fallecimiento = " + fallecimiento;
    }


}
