package com.emergencias.alert;

import com.emergencias.model.EmergencyEvent;
//ENVIO DE ALERTA Y ALMACENAMIENTO DE DATOS
public class AlertSender {

    public void sendAlert(EmergencyEvent event) {

        System.out.println("Enviar evento a 112. COMPLETAR ESTE MÉTODO");
        // --- Recuperas los datos usando los getters del objeto 'event' ---

        // 1. Recuperar la gravedad
        String gravedad = event.getSeverity();
        System.out.println("\n--- Datos de la Emergencia ---");
        System.out.println(gravedad);

        // 2. Recuperar los datos del HERIDO
        String[] datosHerido = event.getInjuredData();

        // 3. Recuperar los datos del USUARIO que alerta
        String[] datosUsuario = event.getUserData();

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
    }
}