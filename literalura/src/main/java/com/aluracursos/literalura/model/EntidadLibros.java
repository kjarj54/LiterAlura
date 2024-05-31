package com.aluracursos.literalura.model;


import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;

@Entity
@Table(name = "libros_almacenados")
public class EntidadLibros {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_libro")
    private Long id;
    @Column(unique = true)
    private String titulo;
    @OneToMany(mappedBy = "libroId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List <EntidadAutores> autores;
    /*
    @ElementCollection
    @CollectionTable(name = "temas_del_libro", joinColumns = @jakarta.persistence.JoinColumn(name = "id_libro"))
    private List <String> temas;*/
    @Column(length = 512)
    private String temas;
    /*
    @ElementCollection
    @CollectionTable(name = "idiomas_del_libro", joinColumns = @jakarta.persistence.JoinColumn(name = "id_libro"))
    private List <String> idiomas;
    */
    private String idiomas;
    private double descargas;

    public Long getId() {
        return id;
    }
    public String getTitulo() {
        return titulo;
    }
    public List<EntidadAutores> getAutores() {
        return autores;
    }

    public String getTemas() {
        return temas;
    }
    public String getIdiomas() {
        return idiomas;
    }

    public double getDescargas() {
        return descargas;
    }

    public EntidadLibros() {

    }
	/*
	public EntidadLibros(String titulo, List<Autores> autoresRecord, List<String> temas, List<String> idiomas, double descargas) {
		this.titulo = titulo;
		this.autores = autoresRecord.stream()
                .map(a -> new EntidadAutores(a.nombre(), a.nacimiento(), a.fallecimiento(), this))
                .collect(Collectors.toList());
		this.temas = temas;
		this.idiomas = idiomas;
		this.descargas = descargas;
	}
	*/

    public EntidadLibros(String titulo, List<Autores> autoresRecord, String temas, String idiomas, double descargas) {
        this.titulo = titulo;
        this.autores = autoresRecord.stream()
                .map(a -> new EntidadAutores(a.nombre(), a.nacimiento(), a.fallecimiento(), this))
                .collect(Collectors.toList());
        this.temas = temas;
        this.idiomas = idiomas;
        this.descargas = descargas;
    }

    @Override
    public String toString() {
        return  "\nLibro: \n" +
                "titulo = " + titulo + "\n" +
                "autores = " + autores + "\n" +
                "temas = " + temas + "\n" +
                "idiomas = " + idiomas + "\n" +
                "descargas = " + descargas;
    }

}
