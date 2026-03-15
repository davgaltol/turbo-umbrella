package com.emergencias.alert;

import com.emergencias.model.EmergencyEvent;

/**
 * Implementación "mock" o simulada de IAlertSender.
 * Simula la falta de cobertura y la búsqueda de la misma en segundo plano
 * sin bloquear el flujo principal del programa.
 */
public class CoverageFinder implements IAlertSender {

    @Override
    public void sendAlert(EmergencyEvent event) {
        if (event == null || event.getSeverity() == null || event.getSeverity().contains("leve")) {
            System.out.println("El evento es nulo o no requiere una alerta. La búsqueda de cobertura no es necesaria.");
            return;
        }

        System.out.println("\n----------------------------------------------------");
        System.out.println("AVISO: No se detecta cobertura de red.");
        System.out.println("La alerta se enviará automáticamente cuando se recupere.");
        System.out.println("----------------------------------------------------");

        // Creamos y arrancamos un nuevo hilo para la simulación.
        // Esto es clave para que el programa principal (mostrar indicaciones) no se detenga.
        Thread coverageThread = new Thread(() -> {
            buscadorCobertura(event);
        });
        coverageThread.start();
    }

    /**
     * Simula la búsqueda de cobertura y envía la alerta cuando la encuentra.
     * Este método se ejecuta en un hilo separado.
     */
    private void buscadorCobertura(EmergencyEvent event) {
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println("...Buscando cobertura...");
                Thread.sleep(1500); // Espera 1.5 segundos
            }

            System.out.println("\n¡Cobertura encontrada!");

            // Una vez encontrada la cobertura, usamos una instancia real de AlertSender
            // para efectuar el envío (guardado en el JSON).
            IAlertSender realSender = new AlertSender();
            realSender.sendAlert(event);

        } catch (InterruptedException e) {
            System.err.println("El hilo de búsqueda de cobertura fue interrumpido.");
            Thread.currentThread().interrupt(); // Buena práctica para restaurar el estado de interrupción
        }
    }
}
