package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.service.ConsumoAPI;

public class Principal {


    public void pantallaPrueba() {
            ConsumoAPI pruebaAPI = new ConsumoAPI();
            String json = pruebaAPI.obtenerDatos("https://gutendex.com/books/?search=dickens%20great");
            System.out.println(json);
        }

    public void muestraMenu(){
        System.out.println("Aqui va ir el menú");
    }
}
