package com.emergencias.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.emergencias.model.LocationData;


public class Location {

    private String findLocation() {
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
    public String getLocationFromAPI() {
        String ubi;
        Location location = new Location();

        ubi=location.findLocation();

        Gson gson = new Gson();

        try {
            // 3. LA MAGIA OCURRE AQUÍ:
            //    Convierte el String JSON a un objeto de la clase LocationData
            LocationData datosDeUbicacion = gson.fromJson(ubi, LocationData.class);

            // 4. ¡LISTO! Ahora puedes usar el objeto y sus métodos
            String ubicacionFormateada = datosDeUbicacion.getFormattedLocation();
            System.out.println("Ubicación encontrada: " + ubicacionFormateada);

            return ubicacionFormateada;
        } catch (JsonSyntaxException e) {
            // Este bloque se ejecuta si el JSON está mal formado
            System.out.println("Error: El formato del JSON de ubicación es incorrecto.");
            return "Ubicación desconocida (error de formato)";
        } catch (Exception e) {
            // Captura cualquier otro error (ej. si findLocation falla)
            System.out.println("Error inesperado al obtener la ubicación: " + e.getMessage());
            return "Ubicación desconocida (error general)";
        }


    }
}