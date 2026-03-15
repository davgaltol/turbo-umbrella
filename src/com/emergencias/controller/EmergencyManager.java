package com.emergencias.controller;

import com.emergencias.alert.AlertSender;
import com.emergencias.alert.CoverageFinder;
import com.emergencias.alert.IAlertSender;
import com.emergencias.detector.EmergencyDetector;
import com.emergencias.model.EmergencyEvent;

import java.util.Scanner;

public class EmergencyManager {

    public void startSystem() {
        EmergencyDetector detector = new EmergencyDetector();
        IAlertSender sender; // La declaramos sin inicializarla

        // 1. Detectar el evento de emergencia
        System.out.println("Iniciando sistema de detección de emergencias...");
        EmergencyEvent event = detector.detectEvent();

        // 2. Si el evento es válido, el flujo principal continúa
        if (event != null && event.getSeverity() != null && !event.getSeverity().contains("leve")) {

            // --- SELECCIÓN DE COMPORTAMIENTO EN TIEMPO DE EJECUCIÓN ---
            System.out.println("\n----------------------------------------------------");
            System.out.print("¿Desea simular falta de cobertura? (S/N): ");
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();
            System.out.println("----------------------------------------------------");

            if (input.equalsIgnoreCase("S")) {
                // Si el usuario quiere simular, usamos CoverageFinder
                sender = new CoverageFinder();
                System.out.println("Modo simulación activado: Se buscará cobertura en segundo plano.");
            } else {
                // Si no, usamos el envío directo
                sender = new AlertSender();
                System.out.println("Modo normal activado: La alerta se enviará directamente.");
            }
            // ----------------------------------------------------------------

            // 3. Enviar la alerta (de forma real o simulada, según la implementación de 'sender')
            // El resto del código no cambia, gracias a la interfaz.
            sender.sendAlert(event);

            // 4. Proporcionar indicaciones de primeros auxilios INMEDIATAMENTE.
            System.out.println("\nEl programa principal continúa mientras se gestiona el envío de la alerta...");
            Indicaciones.darIndicaciones(event.getSeverity());

        } else {
            // Manejar el caso de que no haya emergencia, sea leve, o el usuario cancele.
            System.out.println("\nEl sistema ha finalizado. No se ha generado ninguna alerta de emergencia grave.");
        }
    }
}
