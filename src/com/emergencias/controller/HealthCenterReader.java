package com.emergencias.controller;

import com.emergencias.model.centros.Feature;
import com.emergencias.model.centros.GeoJsonData;
import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class HealthCenterReader {

    private final Path filePath;
    private final Gson gson;

    // Constructor con ruta y Gson inyectables
    public HealthCenterReader(Path filePath, Gson gson) {
        this.filePath = filePath;
        this.gson = gson;
    }

    // Constructor por defecto para compatibilidad
    public HealthCenterReader() {
        this.filePath = Path.of("src/resources/ca_centros_salud_20260105.geojson");
        this.gson = new Gson();
    }

    public List<Feature> loadHealthCenters() {
        if (!Files.exists(filePath)) {
            System.err.println("Archivo de centros de salud no encontrado: " + filePath);
            return Collections.emptyList();
        }

        try (FileReader reader = new FileReader(filePath.toFile())) {
            GeoJsonData data = gson.fromJson(reader, GeoJsonData.class);
            if (data != null && data.getFeatures() != null) {
                return data.getFeatures();
            } else {
                System.err.println("Archivo leído pero no contiene datos válidos.");
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de centros de salud: " + e.getMessage());
        }

        return Collections.emptyList();
    }
}
