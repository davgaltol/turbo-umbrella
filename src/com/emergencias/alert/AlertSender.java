package com.emergencias.alert;

import com.emergencias.model.EmergencyEvent;
//ENVIO DE ALERTA Y ALMACENAMIENTO DE DATOS
public class AlertSender {

    public void sendAlert(EmergencyEvent event) {

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
    }
}