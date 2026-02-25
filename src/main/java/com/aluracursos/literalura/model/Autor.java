package com.aluracursos.literalura.model;

public class Autor {
    private String nombre;
    private Integer fechaNacimiento;
    private Integer fechaMuerte;

    Autor(DatosAutor datos){
        this.nombre = datos.nombre();
        this.fechaNacimiento = datos.fechaNacimiento();
        this.fechaMuerte = datos.fechaMuerte();
    }

    @Override
    public String toString() {
        return nombre;
    }
}
