package com.emergencias.model;

public class LocationData {
    // Los nombres de las variables DEBEN ser idénticos a las claves del JSON
    private double lat;
    private double lon;
    private String city;
    private String country;

    // --- Getters para poder acceder a los valores ---
    public double getLatitude() {
        return lat;
    }

    public double getLongitude() {
        return lon;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    // --- Un método útil para mostrar la información de forma legible ---
    public String getFormattedLocation() {
        if (city != null && !city.isEmpty() && lat != 0 && lon != 0) {
            return city + ", " + country + ". Coordenadas: " + lat + ", " + lon;
        }
        return "Coordenadas: " + lat + ", " + lon;
    }

    @Override
    public String toString() {
        return "LocationData{" +
                "latitude=" + lat +
                ", longitude=" + lon +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}