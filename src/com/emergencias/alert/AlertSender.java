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
    }
}