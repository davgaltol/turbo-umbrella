package com.emergencias.controller;

import com.emergencias.alert.AlertSender;
import com.emergencias.alert.CoverageFinder;
import com.emergencias.alert.IAlertSender;
import com.emergencias.model.EmergencyEvent;
import com.emergencias.model.UserData;

import java.util.function.Consumer;

public class EmergencyManager {

    public void procesarEmergencia(
            String gravedad,
            UserData datosLlamanteExternos, // Puede ser null si el llamante es el herido
            String dniHerido,
            boolean simularCobertura,
            Consumer<String> outputConsumer
    ) {
        // --- 1. OBTENER UBICACIÓN Y CENTRO CERCANO ---
        outputConsumer.accept("Obteniendo ubicación...");
        Location location = new Location();
        String ubi = location.getLocationFromAPI();
        outputConsumer.accept("Ubicación encontrada: " + ubi + "\n");

        // --- 2. GESTIONAR DATOS DE USUARIO Y HERIDO ---
        UserData datosHerido;
        UserData datosLlamante;

        outputConsumer.accept("Buscando DNI del herido (" + dniHerido + ") en la base de datos...");
        datosHerido = RetrieveData.retrieveInjuredData(dniHerido);

        if (datosHerido == null) {
            outputConsumer.accept("DNI del herido no encontrado. Se creará un registro parcial.");
            datosHerido = new UserData();
            datosHerido.setDNI(dniHerido); // Guardamos al menos el DNI
        } else {
            outputConsumer.accept("DNI encontrado. Datos del herido recuperados.");
        }

        // Ahora, definimos los datos del llamante
        if (datosLlamanteExternos == null) { // Marcador para "el llamante es el herido"
            outputConsumer.accept("El llamante es el herido. Copiando datos.");
            datosLlamante = new UserData(datosHerido);
        } else {
            outputConsumer.accept("El llamante es un informante. Usando datos proporcionados.");
            datosLlamante = datosLlamanteExternos;
        }

        // --- 3. CREAR EL EVENTO DE EMERGENCIA ---
        outputConsumer.accept("\nCreando evento de emergencia...");
        EmergencyEvent event = new EmergencyEvent(gravedad, ubi, datosHerido, datosLlamante);

        // --- 4. ENVIAR ALERTA ---
        IAlertSender sender = simularCobertura ? new CoverageFinder() : new AlertSender();
        sender.sendAlert(event);
        outputConsumer.accept("Alerta enviada (o en proceso de envío si no hay cobertura).\n");

        // --- 5. DAR INDICACIONES ---
        String indicaciones = Indicaciones.getIndicaciones(gravedad);
        outputConsumer.accept(indicaciones);
    }
}
