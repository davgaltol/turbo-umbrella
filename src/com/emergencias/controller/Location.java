package com.emergencias.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class Location {

    public String findLocation() {
        String location="No encontrada";
        // 1. Crear un cliente HTTP
        // Usamos un try-with-resources para que el cliente se cierre solo
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        try {
            // 2. Definir el endpoint de la API
            // Este servicio (ip-api.com) detecta automáticamente la IP
            // de quien hace la llamada.
            String apiURL = "http://ip-api.com/json";

            // 3. Crear la solicitud (Request)
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(apiURL))
                    .header("Accept", "application/json")
                    .build();

            System.out.println("Obteniendo ubicación basada en IP...");

            // 4. Enviar la solicitud y obtener la respuesta
            // Usamos send() (bloqueante) para simplicidad.
            // Para aplicaciones reales, considera sendAsync() (asíncrono).
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            // 5. Procesar la respuesta
            if (response.statusCode() == 200) {
                System.out.println("Respuesta recibida:");
                location=response.body();

                // Nota: response.body() es un String en formato JSON.
                // Para usar los datos, deberías "parsear" este JSON
                // usando una librería como Jackson o Gson.
                // Por ejemplo: {"status":"success","country":"Spain",
                // "city":"Valencia","lat":39.4699,"lon":-0.3763,...}

            } else {
                System.out.println("Error: No se pudo obtener la ubicación. Código de estado: "
                        + response.statusCode());
            }

        } catch (Exception e) {
            System.err.println("Ha ocurrido un error al contactar la API de geolocalización.");
            e.printStackTrace();
        }
        return location;

    }
}