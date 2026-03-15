package com.emergencias.alert;

import com.emergencias.model.EmergencyEvent;

/**
 * Interfaz que define el contrato para cualquier clase que pueda enviar alertas de emergencia.
 * El uso de una interfaz permite desacoplar el gestor de emergencias de la implementación
 * concreta del envío, facilitando pruebas y simulaciones como la búsqueda de cobertura.
 */
public interface IAlertSender {

    /**
     * Envía el evento de emergencia especificado.
     * La implementación decidirá cómo y cuándo se realiza el envío.
     *
     * @param event El evento de emergencia a enviar.
     */
    void sendAlert(EmergencyEvent event);
}
