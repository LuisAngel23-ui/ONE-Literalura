package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.ApiResponse;
import com.aluracursos.literalura.model.DatosLibro;
import com.aluracursos.literalura.model.Libro;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;

public class Principal {

    ConvierteDatos conversor = new ConvierteDatos();

    public void pantallaPrueba() {
            ConsumoAPI pruebaAPI = new ConsumoAPI();
            String json = pruebaAPI.obtenerDatos("https://gutendex.com/books/?search=dickens%20great");

            ApiResponse datos = conversor.obtenerDatos(json, ApiResponse.class);
            DatosLibro datoslibro = datos.respuesta().get(0);
            Libro libro = new Libro(datoslibro);
            System.out.println(libro);

        }

    public void muestraMenu(){
        System.out.println("Aqui va ir el menú");
    }
}
