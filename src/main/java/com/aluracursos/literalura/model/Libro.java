package com.aluracursos.literalura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")

public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true)
    private String titulo;

    @Enumerated(EnumType.STRING)
    private Idiomas idioma;

    private String sipnosis;

    @Transient
    private Autor autor;
    private Integer noDescargas;


    public Libro(DatosLibro datosLibro){
        this.titulo = datosLibro.titulo();
        this.sipnosis = datosLibro.resumen().get(0);
        this.idioma = Idiomas.fromString(datosLibro.idioma().get(0));
        this.noDescargas = datosLibro.descargas();

        this.autor = new Autor(datosLibro.autores().get(0));
    }

    @Override
    public String toString() {
        return  "##############################" + "\n" +
                "Titulo: " + titulo + "\n" +
                "Autor: " + autor + "\n" +
                "Sipnosis: " + sipnosis + "\n" +
                "Idioma: " + idioma + '\n' +
                "Total de descargas: " + noDescargas + "\n"+
                "###############################";
    }

    // Getters and Setters


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSipnosis() {
        return sipnosis;
    }

    public void setSipnosis(String sipnosis) {
        this.sipnosis = sipnosis;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Integer getNoDescargas() {
        return noDescargas;
    }

    public void setNoDescargas(Integer noDescargas) {
        this.noDescargas = noDescargas;
    }

    public Idiomas getIdioma() {
        return idioma;
    }

    public void setIdioma(Idiomas idioma) {
        this.idioma = idioma;
    }
}
