package com.aluracursos.literalura.service;

import com.aluracursos.literalura.respository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroService {
    @Autowired
    private LibroRepository libroRepository;
}
