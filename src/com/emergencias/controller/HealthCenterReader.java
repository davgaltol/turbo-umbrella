package com.emergencias.controller;

import com.emergencias.model.centros.Feature;
import com.emergencias.model.centros.GeoJsonData;
import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HealthCenterReader {
    
    private static final String FILE_PATH = "src/resources/ca_centros_salud_20260105.geojson";

    public List<Feature> loadHealthCenters() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(FILE_PATH)) {
            GeoJsonData data = gson.fromJson(reader, GeoJsonData.class);
            if (data != null && data.getFeatures() != null) {
                return data.getFeatures();
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de centros de salud: " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
