package com.aluracursos.literalura.principal;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.aluracursos.literalura.model.Libros;
import com.aluracursos.literalura.respository.RepositorioLibros;
import org.hibernate.Hibernate;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;


import java.util.Scanner;

public class Principal {
    private ConsumirAPI consultarAPI = new ConsumirAPI();
    private Scanner in = new Scanner(System.in);
    private String opcionMenu;
    private String URL_BASE = "https://gutendex.com/books";
    private String respuesta;
    private boolean verificador = false;
    private ConversorDatos conversor = new ConversorDatos();
    private RepositorioLibros repoLibros;
    private List <Libros> librosBuscados = new ArrayList<Libros>();
    private String bienvenida = """
			Bienvenido esta librería digital, por favor, elige una de las siguientes opciones:
			""";
    private String menuOpciones = """
			1.- Consultar libro
			2.- Consultar libros por autor
			3.- Encontrar autores vivos por año
			4.- Mostar lista de libros buscados en esta sesión
			5.- Añadir libro a la librería local
			6.- Mostrar libros en la librería local
			7.- Buscar libros almacenados por idiomas
			8.- Listar total de libros por idioma
			9-. Encontrar autores que vivieron en determinado año
			0.- Salir del programa
			""";

    public Consultas (RepositorioLibros repoLibros) {
        this.repoLibros = repoLibros;
    }

    public void menu() {

        System.out.println(bienvenida);
        do {


            System.out.println(menuOpciones);
            opcionMenu = in.nextLine();

            do {
                verificador = false;
                try {
                    Integer.parseInt(opcionMenu);
                } catch (NumberFormatException e) {
                    System.out.println("Ingresa solo números para este menú ");
                    System.out.println(menuOpciones);
                    verificador = true;
                    opcionMenu = in.nextLine();
                }
            } while (verificador == true);

            switch (Integer.parseInt(opcionMenu)) {
                case 1:
                    consultarLibroAPI();
                    break;
                case 2:
                    buscarPorAutorAPI();
                    break;
                case 3:
                    autorePorAnioAPI();
                    break;
                case 4:
                    mostrarLibrosBuscados();
                    break;
                case 5:
                    anadirLibroADB();
                    break;
                case 6:
                    mostrarLibrosEnDB();
                    break;
                case 7:
                    buscarLibrosPorIdiomaDB();
                    break;
                case 8:
                    totalLibrosPorIdiomaDB();
                    break;
                case 9:
                    autoresVivosAnioDB();
                    break;
                case 0:
                    System.out.println("Saliendo del programa, hasta la próxima.");
                    break;
                default:
                    System.out.println("Opción inválida");
            }

        } while (Integer.parseInt(opcionMenu) != 0);

        in.close();

    }

    public List <String> listaDeIdiomas() {
        System.out.println("Idiomas de libros almacenados:");
        String cadenaIdiomas = repoLibros.idiomasDeLibros().toString().replaceAll("\\[|\\]", "");
        List <String> idiomasTotales = new ArrayList <String>();
        for (short i = 0; i < cadenaIdiomas.split(",").length; i++) {
            idiomasTotales.add(cadenaIdiomas.split(",")[i].trim());
        }
        List <String> idiomasUnicos = new ArrayList <String>();
        idiomasTotales.stream().distinct().forEach(i -> idiomasUnicos.add(i));
        return idiomasUnicos;
    }

    public void consultarLibroAPI() {
        System.out.println("Ingresa el nombre de algún libro:");
        respuesta = in.nextLine();
        String json = consultarAPI.obtenerDatos(URL_BASE + "/?search=" + respuesta.replaceAll(" ", "+"));
        var resultado = conversor.convertirAClase(json, Resultados.class);
        Optional<Libros> primerLibro = resultado.libros().stream().filter(l -> l.titulo().equalsIgnoreCase(respuesta))
                .findFirst();
        if (primerLibro.isPresent()) {
            librosBuscados.add(primerLibro.get());
            System.out.println("El resultado encontrado es:");
            primerLibro.stream().forEach(System.out::println);
        } else {
            System.out.println("No se encontraron coincidencias");
        }

    }

    public void buscarPorAutorAPI() {
        System.out.println("Ingresa el nombre del autor del que deseas buscar libros: ");
        respuesta = in.nextLine();
        String json = consultarAPI.obtenerDatos(URL_BASE + "/?search=" + respuesta.replaceAll(" ", "+"));
        var resultado = conversor.convertirAClase(json, Resultados.class);
        List <Libros> librosDelAutor = resultado.libros();
        if (librosDelAutor.isEmpty()) {
            System.out.println("No se han encontrado libros de ese autor.");
        } else {
            librosDelAutor.forEach(System.out::println);
        }
    }

    public void autorePorAnioAPI() {
        System.out.println("Ingresa el año del que quieres consultar los autores vivos del mismo: ");
        respuesta = in.nextLine();
        String json = consultarAPI.obtenerDatos(URL_BASE + "/?author_year_end=" + respuesta.replaceAll(" ", "") + "&author_year_start=" + respuesta.replaceAll(" ", ""));
        var resultado = conversor.convertirAClase(json, Resultados.class);
        System.out.println("Algunos autores vivos en ese año eran: ");
        List <Autores> autoresEncontrados = new ArrayList <Autores>();
        resultado.libros().forEach(l -> autoresEncontrados.addAll(l.autores()));
        if (resultado.libros().isEmpty()) {
            System.out.println("No se han encontrado autores que vivieran en ese año");
        } else {
            autoresEncontrados.stream().distinct().forEach(System.out::println);
        }
        autoresEncontrados.clear();
    }

    public void mostrarLibrosBuscados() {
        if (librosBuscados.size() > 0) {
            librosBuscados.forEach(System.out::println);
        } else {
            System.out.println("No hay libros para mostrar aún.");
        }
    }

    public void anadirLibroADB() {

        System.out.println("Ingresa el nombre del libro que deseas añadir a la biblioteca local:");
        respuesta = in.nextLine();
        String json = consultarAPI.obtenerDatos(URL_BASE + "/?search=" + respuesta.replaceAll(" ", "+"));
        var resultado = conversor.convertirAClase(json, Resultados.class);
        var libroEncontrado = resultado.libros().stream().filter(l -> l.titulo().equalsIgnoreCase(respuesta))
                .findFirst();

        if (libroEncontrado.isPresent()) {

            if (librosBuscados.size() == 0) {
                librosBuscados.add(libroEncontrado.get());
            } else if (librosBuscados.size() >= 1 && librosBuscados.stream().noneMatch(l -> l.titulo().contains(libroEncontrado.get().titulo()))){
                librosBuscados.add(libroEncontrado.get());
            }

            EntidadLibros libro = new EntidadLibros(libroEncontrado.get().titulo(), libroEncontrado.get().autores(), libroEncontrado.get().temas().toString().substring(1, libroEncontrado.get().temas().toString().length() - 1), libroEncontrado.get().idiomas().toString().substring(1, libroEncontrado.get().idiomas().toString().length() - 1), libroEncontrado.get().descargas());

            try {
                repoLibros.save(libro);
            } catch (DataIntegrityViolationException e) {
                System.out.println("Este libro ya se ha añadido a la biblioteca");
            }

            System.out.println("Libro añadido con exito");

        } else {

            System.out.println("No se ha encontrado el libro buscado");

        }

    }

    public void mostrarLibrosEnDB() {
        if (repoLibros.findAll().size() <= 0) {
            System.out.println("No hay libros para mostrar");
        } else {
            repoLibros.findAll().forEach(System.out::println);
        }
    }

    public void buscarLibrosPorIdiomaDB() {

        if (repoLibros.findAll().size() <= 0) {

            System.out.println("No hay libros para mostrar");

        } else {

            System.out.println("Lista de idiomas de los libros almacenados localmente:");
            listaDeIdiomas().forEach(System.out::println);
            System.out.println(
                    "Escribe las iniciales del idioma del que deseas consultar los libros disponibles en el mismo: ");
            respuesta = in.nextLine();
            System.out.println("Libros disponibles en ese idioma: ");
            repoLibros.librosDisponiblesEnIdioma(respuesta).forEach(System.out::println);
            short total = (short) repoLibros.librosDisponiblesEnIdioma(respuesta).size();
            System.out.println("Total de libros en este idioma = " + total);

        }

    }

    public void totalLibrosPorIdiomaDB() {

        if (repoLibros.findAll().size() <= 0) {

            System.out.println("No hay libros para mostrar");

        } else {

            System.out.println("Total de libros guardados por idioma: ");
            String[] idiomasDeLosLibros = listaDeIdiomas().toArray(new String[listaDeIdiomas().size()]);
            int[] librosPorIdioma = new int[idiomasDeLosLibros.length];
            List<EntidadLibros> libros = repoLibros.findAll();

            for (int i = 0; i < idiomasDeLosLibros.length; i++) {

                int numero = 0;

                for (int j = 0; j < libros.size(); j++) {

                    String idiomas = libros.get(j).getIdiomas();

                    if (idiomas.contains(idiomasDeLosLibros[i])) {

                        numero++;

                    }

                }

                librosPorIdioma[i] = numero;

            }

            System.out.println("Libros por idioma: ");

            for (int i = 0; i < librosPorIdioma.length; i++) {

                System.out.println(idiomasDeLosLibros[i] + " = " + librosPorIdioma[i]);

            }

            libros.clear();
        }
    }

    public void autoresVivosAnioDB() {

        List<EntidadAutores> autores = new ArrayList<EntidadAutores>();

        if (repoLibros.findAll().size() <= 0) {

            System.out.println("No hay autores para mostrar");

        } else {

            System.out.println("Ingresa el año del que deseas conocer sus autores convida");
            respuesta = in.nextLine();
            do {
                verificador = false;
                try {
                    Integer.parseInt(respuesta);
                } catch (NumberFormatException e) {
                    System.out.println("Ingresa un año válido");
                    verificador = true;
                    respuesta = in.nextLine();
                }
            } while (verificador == true);
            System.out.println("Autores con vida en ese año:");
            repoLibros.findAll().forEach(l -> autores.addAll(l.getAutores()));

            if (autores.isEmpty()) {

                System.out.println("No se encontraron autores vivos en ese año");

            } else {

                autores.stream().distinct().filter(a -> a.getNacimiento() <= Integer.parseInt(respuesta)
                        && a.getFallecimiento() >= Integer.parseInt(respuesta)).forEach(System.out::println);

            }

            autores.clear();

        }

    }

}
