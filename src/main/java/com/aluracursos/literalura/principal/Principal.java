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
import java.util.stream.Collectors;

public class Principal {

    private Scanner entradaTeclado = new Scanner(System.in);

    private ConvierteDatos conversor = new ConvierteDatos();
    private ConsumoAPI consumoAPI = new ConsumoAPI();


    private final String URL_BASE = "https://gutendex.com/books/";

    private LibroRepository libroRepository;
    private AutorRepository autorRepositorio;

    public Principal(LibroRepository repository, AutorRepository autorRepository){
        this.libroRepository = repository;
        this.autorRepositorio = autorRepository;
    }

    // ################ Interfaz de usuario #######################

    private final String mensajeBienvenida = """
                    
                    **********
                    
                    Literalura
                    
                    ***********
           
                """;
    private final String menu = """
                   
                   ************************
                   1. Buscar libro por titulo.
                   
                   2. Listar libros guardados.
                   
                   3. Listar autores registrados.
                   
                   4. Listar autores vivos en determinado año.
                   
                   5. Listar libros por idioma.
                   
                   0. Cerrar aplicación.
                    ***********************
                """;


    // ############### UI ###############
    public void Menu(){
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
                case 0:
                    System.out.println("Cerrando aplicación");
                    break;
            }
        }

    }

    private Idiomas opcionesIdiomasABuscar(){
        String mensaje = """
                - es - Español
                - en - Ingles
                - fr - Francés
                - pt - portugués
                """;
        System.out.println("Idiomas dispobles");
        System.out.println(mensaje);
        System.out.println("Ingrese el idioma a buscar.");

        String idiomaNombre = entradaTeclado.nextLine();
        Idiomas idioma = Idiomas.fromString(idiomaNombre);

        return idioma;
    }

    // ############## Opciones de menu ###############



    private void buscarLibroWeb(){

        System.out.println("Escribe el titulo del libro que deseas buscar");
        var nombreLibro = entradaTeclado.nextLine();

        DatosLibro datos = getDatosLibro(nombreLibro);
        Libro libro = new Libro(datos);
        System.out.println(libro);
        System.out.println("¿Quieres guardar el libro? \n Presiona 1 para si \n Presiona cualquier otro numero para no");
        Integer guardar = entradaTeclado.nextInt();
        entradaTeclado.nextLine();

        if (guardar == 1){
            // Verificacion de autor en base de datos
            Autor autorAPI = libro.getAutor();
            Optional<Autor> autorBuscado = autorRepositorio.findByNombreIgnoreCase(autorAPI.getNombre());
            if (autorBuscado.isPresent()){
                libro.setAutor(autorBuscado.get());
            }else{
                autorRepositorio.save(autorAPI);
            }


            libroRepository.save(libro);
            System.out.println("Libro guardado en base de datos.");
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
        System.out.println(idioma.toString());
        List<Libro> librosBuscados = libroRepository.findByIdioma(idioma);
        librosBuscados.stream()
                .forEach(System.out::println);
    }

    // ############### Funciones internas ###############

    private DatosLibro getDatosLibro(String nombreLibro) {
        // Busqueda en API
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ","%20").toLowerCase());

        // Transformación json a clase
        ApiResponse datosAPI = conversor.obtenerDatos(json, ApiResponse.class);
        DatosLibro datosLibro = datosAPI.respuesta().get(0);

        return datosLibro;
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
                            .collect(Collectors.toList());

                    System.out.println("Libros: " + titulosLibros);
                    System.out.println("-----------------\n");
                });
    }



}
