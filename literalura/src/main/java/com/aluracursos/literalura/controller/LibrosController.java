package com.aluracursos.literalura.controller;

import com.aluracursos.literalura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/libros")
public class LibrosController {
    @Autowired
    private LibroService libroService;

    @GetMapping
    public String listar() {
        return "Listando libros";
    }
}
