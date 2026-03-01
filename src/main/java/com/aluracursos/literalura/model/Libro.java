package com.aluracursos.literalura.model;

import jakarta.persistence.*;

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


    public Libro(){}

    public Libro(DatosLibro datosLibro){
        this.titulo = datosLibro.titulo();
        this.sinopsis = datosLibro.resumen().getFirst();
        this.idioma = Idiomas.fromString(datosLibro.idioma().getFirst());
        this.noDescargas = datosLibro.descargas();
        this.autor = new Autor(datosLibro.autores().getFirst());
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


    public String getTitulo() {
        return titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Idiomas getIdioma() {
        return idioma;
    }

}
