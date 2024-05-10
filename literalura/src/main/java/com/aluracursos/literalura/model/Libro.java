package com.aluracursos.literalura.model;


import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    public Libro() {

    }

    public Libro(Long id) {
        this.Id = id;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }
}
