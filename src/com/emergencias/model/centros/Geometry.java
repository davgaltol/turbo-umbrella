package com.emergencias.model.centros;

import java.util.List;

public class Geometry {
    private String type;
    private List<Double> coordinates; // [Longitud (X), Latitud (Y)] en UTM

    public double getUtmX() {
        return coordinates.get(0);
    }

    public double getUtmY() {
        return coordinates.get(1);
    }
}
