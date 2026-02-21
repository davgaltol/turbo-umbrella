package com.emergencias.controller;

import com.emergencias.model.centros.Feature;
import com.emergencias.model.centros.GeoJsonData;
import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HealthCenterReader {

    private static final String FILE_PATH = "/resources/ca_centros_salud_20260105.geojson";

    public List<Feature> loadHealthCenters() {
        Gson gson = new Gson();
        try (InputStream is = getClass().getResourceAsStream(FILE_PATH)) {
            if (is == null) {
                System.err.println("Error: No se encontró el archivo " + FILE_PATH);
                return new ArrayList<>();
            }

            InputStreamReader reader = new InputStreamReader(is);
            GeoJsonData data = gson.fromJson(reader, GeoJsonData.class);
            if (data != null && data.getFeatures() != null) {
                return data.getFeatures();
            }
        } catch (Exception e) {
            System.err.println("Error al leer el archivo de centros de salud: " + e.getMessage());
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
