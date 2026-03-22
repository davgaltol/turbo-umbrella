package com.emergencias.controller;

import com.emergencias.alert.AlertSender;
import com.emergencias.alert.CoverageFinder;
import com.emergencias.alert.IAlertSender;
import com.emergencias.model.EmergencyEvent;

/**
 * Gestor principal de la lógica de negocio.
 * Ahora está diseñado para ser llamado tanto desde una consola como desde una GUI.
 */
public class EmergencyManager {

    /**
     * Método principal para procesar una emergencia.
     * Este método contiene la lógica central y devuelve un String con el resultado.
     *
     * @param gravedad El string que describe la gravedad de la herida.
     * @param simularFaltaDeCobertura True si se debe simular la búsqueda de cobertura.
     * @return Un String con el resultado del procesamiento y las indicaciones.
     */
    public String procesarEmergencia(String gravedad, boolean simularFaltaDeCobertura) {
        // La lógica de crear el evento ya no depende de la consola.
        // La GUI nos pasa directamente el string de gravedad.
        EmergencyEvent event = new EmergencyEvent(gravedad);
        
        // Comprobamos si el evento es válido (no es leve, no fue cancelado, etc.)
        // NOTA: La lógica de creación de EmergencyEvent parece que ya no necesita
        // la interacción por consola, lo cual es perfecto para la GUI.
        // Si el constructor de EmergencyEvent devolviera null o un estado inválido,
        // lo manejaríamos aquí. Por ahora, asumimos que si la gravedad no es leve, es procesable.
        if (event.getSeverity() == null || event.getSeverity().contains("leve")) {
            return "El evento no requiere una alerta de emergencia.";
        }

        IAlertSender sender;
        if (simularFaltaDeCobertura) {
            sender = new CoverageFinder();
        } else {
            sender = new AlertSender();
        }

        // Enviamos la alerta (de forma síncrona o asíncrona)
        sender.sendAlert(event);

        // Obtenemos las indicaciones como un String
        String indicaciones = Indicaciones.getIndicaciones(event.getSeverity());

        // Devolvemos el resultado para que la GUI lo muestre.
        return "Alerta en proceso de envío.\n" + indicaciones;
    }

    /**
     * Punto de entrada para la ejecución original por consola.
     * Este método ahora es un simple envoltorio.
     * YA NO SERÁ EL PUNTO DE ENTRADA PRINCIPAL SI USAS LA GUI.
     */
    public void startSystem() {
        System.out.println("Este es el modo consola. Para la interfaz gráfica, ejecute 'com.emergencias.gui.MainGui.java'");
        // Aquí se podría replicar la lógica de preguntas por consola si se quisiera mantener
        // la funcionalidad dual, pero para la integración con la GUI, no es necesario.
    }
}
