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


    /*public void sendAlert(EmergencyEvent event) {

        try {
            // Recuperar la gravedad
            String gravedad = event.getSeverity();

            // Recuperar los datos del HERIDO
            String[] datosHerido = event.getInjuredData();

            // Recuperar los datos del USUARIO que alerta
            String[] datosUsuario = event.getUserData();

            // Recuperar ubicación
            String ubicacion = event.getLocation();

            if (datosUsuario != null) {
                System.out.println("\n--- Datos del Usuario que alerta ---");
                System.out.println("Nombre: " + datosUsuario[0]);
                System.out.println("Teléfono: " + datosUsuario[1]);
            }
            if (datosHerido != null) {
                System.out.println("\n--- Datos del Herido ---");
                System.out.println("Nombre: " + datosHerido[0]);
                System.out.println("Teléfono: " + datosHerido[1]);
                if (datosHerido[2] != null && !datosHerido[2].isEmpty()) {
                    System.out.println("DNI: " + datosHerido[2]);
                }
            }
            if (ubicacion != null) {
                System.out.println("\n--- Ubicación ---");
                System.out.println("Ubicación: " + ubicacion);

            }
            System.out.println("Datos almacenados correctamente.");
        }catch(NullPointerException e){
            System.out.println("Error:no se asignan datos.");
        }
    }*/
    public void sendAlert(EmergencyEvent event) {
        // Usamos GsonBuilder para que el JSON se guarde con formato legible ("pretty printing").
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<EmergencyEvent> alertList;

        try {
            // 1. LEER EL FICHERO EXISTENTE
            FileReader reader = new FileReader(ALERTS_FILE_PATH);

            // Definimos el tipo de dato: una Lista de EmergencyEvent. Es necesario para Gson.
            Type eventListType = new TypeToken<ArrayList<EmergencyEvent>>(){}.getType();

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
            System.out.println("*********************************************");
            System.out.println("¡Alerta guardada correctamente en 'alertas.json'!");
            System.out.println("*********************************************");

        } catch (IOException e) {
            System.err.println("Error: No se pudo escribir en el fichero 'alertas.json'.");
            e.printStackTrace();
        }
    }
}