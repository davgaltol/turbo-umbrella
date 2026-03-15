package com.emergencias.alert;

import com.emergencias.model.EmergencyEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación real de IAlertSender.
 * Guarda el evento de emergencia en un fichero JSON local.
 */
public class AlertSender implements IAlertSender {

    private static final String ALERTS_FILE_PATH = "./src/resources/alertas.json";

    @Override
    public void sendAlert(EmergencyEvent event) {
        if (event == null || event.getSeverity() == null || event.getSeverity().contains("leve")) {
            System.out.println("El evento es nulo o no requiere una alerta formal. No se guardará en el historial.");
            return;
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<EmergencyEvent> alertList;

        try (FileReader reader = new FileReader(ALERTS_FILE_PATH)) {
            Type eventListType = new TypeToken<ArrayList<EmergencyEvent>>() {}.getType();
            alertList = gson.fromJson(reader, eventListType);
        } catch (IOException e) {
            System.out.println("Fichero 'alertas.json' no encontrado. Se creará uno nuevo.");
            alertList = new ArrayList<>();
        }

        if (alertList == null) {
            alertList = new ArrayList<>();
        }

        alertList.add(event);

        try (FileWriter writer = new FileWriter(ALERTS_FILE_PATH)) {
            gson.toJson(alertList, writer);
            System.out.println("\n******************************************************");
            System.out.println("*** ¡Alerta enviada y guardada en 'alertas.json'! ***");
            System.out.println("******************************************************");
        } catch (IOException e) {
            System.err.println("Error: No se pudo escribir en el fichero 'alertas.json'.");
            e.printStackTrace();
        }
    }
}
