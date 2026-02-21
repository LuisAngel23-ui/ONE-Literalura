package com.aluracursos.literalura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// Paquete que envia una solicitud a la API y recibe una respuesta en formato String.

public class ConsumoAPI {
    public String obtenerDatos(String url){
        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest pedido = HttpRequest.newBuilder().
                uri(URI.create(url)).
                build();

        HttpResponse<String> respuesta = null;

        try {
            respuesta = cliente.send(pedido,
                    HttpResponse.BodyHandlers.ofString());
        }catch(IOException e){
            throw new RuntimeException(e);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }

        String json = respuesta.body();
        return json;
    }
}
