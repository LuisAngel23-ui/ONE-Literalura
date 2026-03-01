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

    @Column(columnDefinition = "TEXT")
    private String sinopsis;

    @ManyToOne
    private Autor autor;
    private Integer noDescargas;


    public Libro(){};

    public Libro(DatosLibro datosLibro){
        this.titulo = datosLibro.titulo();
        this.sinopsis = datosLibro.resumen().get(0);
        this.idioma = Idiomas.fromString(datosLibro.idioma().get(0));
        this.noDescargas = datosLibro.descargas();

        this.autor = new Autor(datosLibro.autores().get(0));
    }

    @Override
    public String toString() {
        return  "------------- LIBRO -----------" + "\n" +
                "Titulo: " + titulo + "\n" +
                "Autor: " + autor.getNombre() + "\n" +
                "Sipnosis: " + sinopsis + "\n" +
                "Idioma: " + idioma + '\n' +
                "Total de descargas: " + noDescargas + "\n"+
                "--------------------------\n";
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
        return sinopsis;
    }

    public void setSipnosis(String sipnosis) {
        this.sinopsis = sipnosis;
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
