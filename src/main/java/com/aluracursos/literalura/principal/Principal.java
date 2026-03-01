package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.*;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class Principal {

    private final Scanner entradaTeclado = new Scanner(System.in);

    private final ConvierteDatos conversor = new ConvierteDatos();
    private final ConsumoAPI consumoAPI = new ConsumoAPI();


    private final LibroRepository libroRepository;
    private final AutorRepository autorRepositorio;

    public Principal(LibroRepository repository, AutorRepository autorRepository){
        this.libroRepository = repository;
        this.autorRepositorio = autorRepository;
    }

    // ################ Interfaz de usuario #######################

    public void Menu(){

        final String mensajeBienvenida = """
                    ************
                     Literalura
                    ************
                """;
        final String menu = """
                   ************************
                   1. Buscar libro por titulo.
                   2. Listar libros guardados.
                   3. Listar autores registrados.
                   4. Listar autores vivos en determinado año.
                   5. Listar libros por idioma.
                   0. Cerrar aplicación.
                    ***********************
                """;


        var opcion = -1;

        System.out.println(mensajeBienvenida);
        while (opcion != 0){
            System.out.println(menu);
            opcion = entradaTeclado.nextInt();
            entradaTeclado.nextLine();

            switch (opcion){
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:
                    enlistarLibros();
                    break;
                case 3:
                    enlistarAutores();
                    break;
                case 4:
                    autorVivoEnAno();
                    break;
                case 5:
                    enlistarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando aplicación");
                    break;
                default:
                    System.out.println("Opción no disponible.");
            }
        }

    }

    private Idiomas opcionesIdiomasABuscar() {

        String mensaje = """
                - es - Español
                - en - Ingles
                - fr - Francés
                - pt - portugués
                """;
        while(true) {
            System.out.println("Idiomas dispobles.");
            System.out.println(mensaje);
            System.out.println("Escriba la abreviación del idioma correspondiente");

            String idiomaNombre = entradaTeclado.nextLine();

            try {
                return Idiomas.fromString(idiomaNombre);

            } catch (IllegalArgumentException e) {
                System.out.println(idiomaNombre + " no es válido.");
            }
        }
    }
    // ############## Opciones de menu ###############

    private void buscarLibroWeb(){

        System.out.println("Escribe el titulo del libro que deseas buscar");
        var nombreLibro = entradaTeclado.nextLine();

        DatosLibro datos = getDatosLibro(nombreLibro);


        if (datos != null) {
            Libro libro = new Libro(datos);
            System.out.println(libro);
            System.out.println("¿Quieres guardar el libro? \nPresiona 1 para si \nPresiona cualquier otro numero para no");
            int guardar = entradaTeclado.nextInt();
            entradaTeclado.nextLine();

            if (guardar == 1) {
                // Verificacion de autor en base de datos
                Autor autorAPI = libro.getAutor();
                Optional<Autor> autorBuscado = autorRepositorio.findByNombreIgnoreCase(autorAPI.getNombre());
                if (autorBuscado.isPresent()) {
                    libro.setAutor(autorBuscado.get());
                } else {
                    autorRepositorio.save(autorAPI);
                }


                libroRepository.save(libro);
                System.out.println("Libro guardado en base de datos.");
            }
        }

    }


    private void enlistarLibros() {
        List<Libro> listaLibros = libroRepository.findAll();
        listaLibros.stream()
                .sorted(Comparator.comparing(Libro::getIdioma))
                .forEach(System.out::println);

    }

    private void enlistarAutores() {
        List<Autor> listaAutores= autorRepositorio.findAll();
        imprimirAutores(listaAutores);
    }

    private void autorVivoEnAno() {
        System.out.println("Año de busqueda:");
        var ano = entradaTeclado.nextInt();
        entradaTeclado.nextLine();
        List<Autor> autores = autorRepositorio.autorVivoEnFecha(ano);
        imprimirAutores(autores);

    }

    private void enlistarLibrosPorIdioma() {
        Idiomas idioma = opcionesIdiomasABuscar();
        System.out.println(idioma);
        List<Libro> librosBuscados = libroRepository.findByIdioma(idioma);
        if (librosBuscados.isEmpty()){
            System.out.println("No hay libros en este idioma guardados en el registro.");
        }else {
            librosBuscados.forEach(System.out::println);
        }
    }

    // ############### Funciones internas ###############

    private DatosLibro getDatosLibro(String nombreLibro) {
        final String URL_BASE = "https://gutendex.com/books/";
        // Busqueda en API
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ","%20").toLowerCase());

        // Transformación json a clase
        ApiResponse datosAPI = conversor.obtenerDatos(json, ApiResponse.class);

        // Verificación de respuesta
        if (datosAPI != null && datosAPI.respuesta() != null && !datosAPI.respuesta().isEmpty()) {
            return datosAPI.respuesta().getFirst();
        } else {
            System.out.println("No se encontró ningún libro con ese título.");
            return null;
        }
    }

    private void imprimirAutores(List<Autor> autores){
        autores.stream()
                .sorted(Comparator.comparing(Autor::getFechaNacimiento))
                .forEach(autor -> {
                    System.out.println("----- AUTOR -----");
                    System.out.println("Nombre: " + autor.getNombre());
                    System.out.println("Fecha de Nacimiento: " + autor.getFechaNacimiento());
                    System.out.println("Fecha de Fallecimiento: " + autor.getFechaMuerte());

                    List<String> titulosLibros = autor.getLibros().stream()
                            .map(Libro::getTitulo)
                            .toList();

                    System.out.println("Libros: " + titulosLibros);
                    System.out.println("-----------------\n");
                });
    }



}
