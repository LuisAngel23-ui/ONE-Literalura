package com.aluracursos.literalura.model;

import java.util.List;
import java.util.stream.Collectors;

public class Libro {

    String titulo;
    String sipnosis;
    List<Autor> autores;
    List<String> idioma;
    Integer noDescargas;

    public Libro(DatosLibro datosLibro){
        this.titulo = datosLibro.titulo();
        this.sipnosis = datosLibro.resumen().get(0);
        this.idioma = datosLibro.idioma();
        this.noDescargas = datosLibro.descargas();

        autores = datosLibro.autores().stream().
                map(a-> new Autor(a))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Titulo: " + titulo + "\n " +
                "Autores: " + autores + "\n" +
                "Sipnosis: " + sipnosis + "\n" +
                "Idiomas: " + idioma + '\n' +
                "Total de descargas: " + noDescargas + ".";
    }
}
