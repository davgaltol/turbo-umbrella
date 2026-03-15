package com.emergencias.controller;

import com.emergencias.alert.AlertSender;
import com.emergencias.detector.EmergencyDetector;
import com.emergencias.model.EmergencyEvent;

public class EmergencyManager {

    public void startSystem() {
        EmergencyDetector detector = new EmergencyDetector();
        AlertSender sender = new AlertSender();

        // 1. Detectar el evento de emergencia
        EmergencyEvent event = detector.detectEvent();

        // 2. Si el evento es válido, enviar la alerta y dar indicaciones
        if (event != null && event.getSeverity() != null && !event.getSeverity().contains("leve")) {
            // Enviar la alerta a los servicios de emergencia
            sender.sendAlert(event);

            // Proporcionar indicaciones de primeros auxilios basadas en la gravedad
            Indicaciones.darIndicaciones(event.getSeverity());
        } else {
            // Manejar el caso de que no haya emergencia o sea leve
            System.out.println("El sistema ha finalizado. No se ha generado ninguna alerta de emergencia grave.");
        }
    }
}
