package test;

import com.emergencias.controller.HealthCenterReader;
import com.emergencias.model.centros.Feature;

import java.util.List;

public class HealthCenterReaderTest {

    public static void main(String[] args) {
        HealthCenterReader reader = new HealthCenterReader();
        List<Feature> centros = reader.loadHealthCenters();

        if (centros.isEmpty()) {
            System.out.println("❌ Error: No se cargaron centros de salud.");
        } else {
            System.out.println("✅ Se cargaron " + centros.size() + " centros de salud.");
            // Mostrar los 3 primeros para comprobación rápida
            for (int i = 0; i < Math.min(3, centros.size()); i++) {
                System.out.println("- " + centros.get(i).getProperties().getNombre());
            }
        }
    }
}