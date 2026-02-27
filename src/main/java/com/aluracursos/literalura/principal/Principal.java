package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.ApiResponse;
import com.aluracursos.literalura.model.DatosLibro;
import com.aluracursos.literalura.model.Libro;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;

import java.util.Scanner;

public class Principal {

    private Scanner entradaTeclado = new Scanner(System.in);

    private ConvierteDatos conversor = new ConvierteDatos();
    private ConsumoAPI consumoAPI = new ConsumoAPI();

    private LibroRepository repositorio;

    private final String URL_BASE = "https://gutendex.com/books/";

    private final String mensajeBienvenida = """
                    
                    **********
                    
                    Literalura
                    
                    ***********
           
                """;
    private final String menu = """
                   
                   ************************
                   1. Buscar libro por titulo.
                   
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
                case 0:
                    System.out.println("Cerrando aplicación");
                    break;
            }
        }

    }

    // ############## Opciones de menu ###############



    private void buscarLibroWeb(){

        System.out.println("Escribe el titulo del libro que deseas buscar");
        var nombreLibro = entradaTeclado.nextLine();

        DatosLibro datos = getDatosLibro(nombreLibro);
        Libro libro = new Libro(datos);
        System.out.println(libro);


        repositorio.save(libro);

    }



    // ############### Funciones internas ###############

    private DatosLibro getDatosLibro(String nombreLibro) {
        // Busqueda en API
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + nombreLibro);

        // Transformación json a clase
        ApiResponse datosAPI = conversor.obtenerDatos(json, ApiResponse.class);
        DatosLibro datosLibro = datosAPI.respuesta().get(0);

        return datosLibro;
    }


}
