package com.aluracursos.literalura.respository;

import java.util.List;

import com.aluracursos.literalura.model.EntidadAutores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface RepositorioAutores extends JpaRepository <EntidadAutores, Long> {

    @Query("select distinct ea.nombre, ea.nacimiento, ea.fallecimiento from EntidadAutores ea where ea.nacimiento >= :anio and ea.fallecimiento >= :anio")
    List <EntidadAutores> autoresConvida(@Param("anio") int anio);

}
