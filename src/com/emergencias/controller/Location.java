package com.emergencias.controller;

import com.emergencias.model.LocationData;
import com.emergencias.model.centros.Feature;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

public class Location {

    private LocationData locationData;
    private Feature nearestCenter;

    public void findLocationAndNearestCenter() {
        String jsonLocation = findLocationJson();
        if (jsonLocation == null) return;

        Gson gson = new Gson();
        try {
            this.locationData = gson.fromJson(jsonLocation, LocationData.class);
            if (this.locationData != null) {
                System.out.println("Ubicación local encontrada: " + this.locationData.getFormattedLocation());
                findNearestCenterInternal();
            }
        } catch (JsonSyntaxException e) {
            System.err.println("Error: El formato del JSON de ubicación es incorrecto.");
        }
    }

    private void findNearestCenterInternal() {
        HealthCenterReader reader = new HealthCenterReader();
        List<Feature> centros = reader.loadHealthCenters();

        HealthCenterLocator locator = new HealthCenterLocator();
        this.nearestCenter = locator.findNearestCenter(this.locationData, centros);

        if (this.nearestCenter != null) {
            System.out.println("Centro más cercano: " + this.nearestCenter.getProperties().getNombre());
            System.out.println("Dirección: " + this.nearestCenter.getProperties().getDireccionCompleta());
        }
    }

    private String findLocationJson() {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        try {
            String apiURL = "http://ip-api.com/json";
            HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(apiURL)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                System.err.println("Error al obtener ubicación. Código: " + response.statusCode());
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error al contactar la API de geolocalización: " + e.getMessage());
            return null;
        }
    }

    // Getters para que EmergencyManager pueda recuperar los resultados
    public LocationData getLocationData() {
        return locationData;
    }

    public Feature getNearestCenter() {
        return nearestCenter;
    }
}
