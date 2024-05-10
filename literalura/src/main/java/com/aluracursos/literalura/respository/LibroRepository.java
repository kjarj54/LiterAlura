package com.aluracursos.literalura.respository;

import com.aluracursos.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long>{
}
