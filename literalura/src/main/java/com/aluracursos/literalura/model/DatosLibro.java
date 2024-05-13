package com.aluracursos.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") String[] autores,
        @JsonAlias("languages") String[] idiomas,
        @JsonAlias("subjects") String[] temas,
        @JsonAlias("download_count") int descargas,
        @JsonAlias("media_type") String tipoMedio
) {
}
