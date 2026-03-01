package com.aluracursos.literalura.model;

import jakarta.persistence.*;

import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name="autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    private String nombre;
    private Integer fechaNacimiento;
    private Integer fechaMuerte;

    @OneToMany(mappedBy = "autor",fetch = FetchType.EAGER)
    private List<Libro> libros;


    public Autor(){}

    Autor(DatosAutor datos){
        this.nombre = datos.nombre();
        this.fechaNacimiento = datos.fechaNacimiento();
        this.fechaMuerte = datos.fechaMuerte();
    }

    @Override
    public String toString() {
        return  "Nombre: " + nombre + "\n" +
                "Fecha de nacimiento: " + fechaNacimiento + "\n" +
                "Fecha de muerte: " +  fechaMuerte + "\n";

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Integer fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getFechaMuerte() {
        return fechaMuerte;
    }

    public void setFechaMuerte(Integer fechaMuerte) {
        this.fechaMuerte = fechaMuerte;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }
}
