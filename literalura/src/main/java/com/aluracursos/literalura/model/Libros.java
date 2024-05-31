package com.aluracursos.literalura.model;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public record Libros(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List <Autores> autores,
        @JsonAlias("subjects") List <String> temas,
        @JsonAlias("languages") List <String> idiomas,
        @JsonAlias("download_count") double descargas) {

    @Override
    public String toString() {
        return "Libro: \n" +
                "titulo = " + titulo + "\n" +
                "autores = " + autores + "\n" +
                "temas = " + temas + "\n" +
                "idiomas = " + idiomas + "\n" +
                "descargas = " + descargas;
    }

    public String titulo() {
        return titulo;
    }

    public List<Autores> autores() {
        return autores;
    }

    public List<String> temas() {
        return temas;
    }

    public List<String> idiomas() {
        return idiomas;
    }

    public double descargas() {
        return descargas;
    }



}