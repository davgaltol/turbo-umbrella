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


//ENVIO DE ALERTA Y ALMACENAMIENTO DE DATOS
public class AlertSender {

    // Define la ruta del fichero donde se guardarán las alertas.
    private static final String ALERTS_FILE_PATH = "./src/resources/alertas.json";

    public void sendAlert(EmergencyEvent event) {
        // Usamos GsonBuilder para que el JSON se guarde con formato legible ("pretty printing").

        if(event==null){
            System.out.println("CIERRE DE LA APLICACIÓN");
        }
        else if(event.getSeverity()==null){ //severidad leve o cancelar alerta
            System.out.println("NO ES NECESARIO CREAR INFORME. CIERRE DE LA APLICACIÓN ");
        }
        else {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            List<EmergencyEvent> alertList;

            try {
                // 1. LEER EL FICHERO EXISTENTE
                FileReader reader = new FileReader(ALERTS_FILE_PATH);

                // Definimos el tipo de dato: una Lista de EmergencyEvent. Es necesario para Gson.
                Type eventListType = new TypeToken<ArrayList<EmergencyEvent>>() {
                }.getType();

                // 2. CONVERTIR EL JSON DEL FICHERO A UNA LISTA DE OBJETOS
                alertList = gson.fromJson(reader, eventListType);
                reader.close();

            } catch (IOException e) {
                // Si el fichero no existe (la primera vez), simplemente creamos una lista vacía.
                System.out.println("Fichero 'alertas.json' no encontrado. Se creará uno nuevo.");
                alertList = new ArrayList<>();
            }

            // Si el fichero estaba vacío o corrupto, gson.fromJson puede devolver null.
            if (alertList == null) {
                alertList = new ArrayList<>();
            }

            // 3. AÑADIR EL NUEVO EVENTO A LA LISTA
            alertList.add(event);

            // 4. ESCRIBIR LA LISTA COMPLETA DE VUELTA AL FICHERO
            try (FileWriter writer = new FileWriter(ALERTS_FILE_PATH)) {
                // Convertimos la lista completa (con el nuevo evento) a un String JSON
                gson.toJson(alertList, writer);
                System.out.println("******************************************************");
                System.out.println("***¡Alerta guardada correctamente en 'alertas.json'***");
                System.out.println("******************************************************");

            } catch (IOException e) {
                System.err.println("Error: No se pudo escribir en el fichero 'alertas.json'.");
                e.printStackTrace();
            }
        }
    }
}