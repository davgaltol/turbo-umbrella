package com.emergencias.model;

public class LocationData {
    // Los nombres de las variables DEBEN ser idénticos a las claves del JSON
    private double latitude;
    private double longitude;
    private String city;
    private String country;

    // --- Getters para poder acceder a los valores ---
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    // --- Un método útil para mostrar la información de forma legible ---
    public String getFormattedLocation() {
        if (city != null && !city.isEmpty()) {
            return city + ", " + country;
        }
        return "Coordenadas: " + latitude + ", " + longitude;
    }

    @Override
    public String toString() {
        return "LocationData{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}