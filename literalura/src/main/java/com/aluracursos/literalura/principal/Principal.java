package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.DatosLibro;
import com.aluracursos.literalura.respository.LibroRepository;
import com.aluracursos.literalura.util.ConsumoAPI;
import com.aluracursos.literalura.util.ConvierteDatos;

import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository repositorio;


    public Principal(LibroRepository repository) {
        this.repositorio = repository;
    }

    public void muestraElMenu(){
        var opcion = -1;
        while(opcion != 0){
            var menu = """
                    1.Buscar libro
                    2.Listar libros
                    3.Buscar libros por autor
                    4.Buscar libros por idioma
                    5.Buscar libros por título
                    6.Buscar libros por tema
                    0.Salir
                    """;

            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion){
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private DatosLibro getDatosLibro(){
        System.out.println("Introduce el titulo del libro");
        var titulo = teclado.nextInt();
        teclado.nextLine();
        var url = URL_BASE + titulo;
        var json = consumoApi.obtenerDatos(url);
        return conversor.obtenerDatos(json, DatosLibro.class);
    }

    private void buscarLibro(){
        DatosLibro datosLibro = getDatosLibro();

        System.out.println(datosLibro);
    }

}
