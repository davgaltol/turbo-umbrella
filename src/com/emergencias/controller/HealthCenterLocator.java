package com.emergencias.controller;

import com.emergencias.model.centros.Feature;
import com.emergencias.model.LocationData;
import com.emergencias.util.CoordinateConverter;
import java.util.List;

public class HealthCenterLocator {

    public Feature findNearestCenter(LocationData currentLocation, List<Feature> centers) {
        if (centers == null || centers.isEmpty() || currentLocation == null) {
            return null;
        }

        Feature nearest = null;
        double minDistance = Double.MAX_VALUE;

        double userLat = currentLocation.getLatitude();
        double userLon = currentLocation.getLongitude();

        for (Feature center : centers) {
            // 1. Obtener coordenadas UTM del centro
            double utmX = center.getGeometry().getUtmX();
            double utmY = center.getGeometry().getUtmY();

            // 2. Convertir UTM a Lat/Lon usando nuestra utilidad
            double[] latLon = CoordinateConverter.utmToLatLon(utmX, utmY);
            double centerLat = latLon[0];
            double centerLon = latLon[1];

            // 3. Calcular distancia (Fórmula de Haversine para mayor precisión en esfera)
            double distance = calculateHaversineDistance(userLat, userLon, centerLat, centerLon);

            if (distance < minDistance) {
                minDistance = distance;
                nearest = center;
            }
        }

        return nearest;
    }

    // Fórmula de Haversine para calcular distancia en km entre dos puntos geográficos
    private double calculateHaversineDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radio de la Tierra en km

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }
}
