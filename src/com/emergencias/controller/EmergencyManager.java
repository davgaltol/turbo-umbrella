package com.emergencias.controller;

import com.emergencias.alert.AlertSender;
import com.emergencias.alert.CoverageFinder;
import com.emergencias.alert.IAlertSender;
import com.emergencias.model.EmergencyEvent;
import com.emergencias.model.LocationData;
import com.emergencias.model.UserData;
import com.emergencias.model.centros.Feature;

import java.util.function.Consumer;

public class EmergencyManager {

    public void procesarEmergencia(
            String gravedad,
            UserData datosLlamanteExternos,
            String dniHerido,
            boolean simularCobertura,
            Consumer<Object> outputConsumer
    ) {
        // --- 1. OBTENER UBICACIÓN Y CENTRO CERCANO ---
        outputConsumer.accept("Obteniendo ubicación y centro de salud más cercano...");
        Location location = new Location();
        location.findLocationAndNearestCenter(); // Este método ahora hace todo el trabajo
        
        LocationData miUbicacion = location.getLocationData();
        Feature centroCercano = location.getNearestCenter();

        if (miUbicacion != null) {
            outputConsumer.accept("Ubicación encontrada: " + miUbicacion.getFormattedLocation());
            outputConsumer.accept(miUbicacion); // Enviamos el objeto para el mapa
        }
        if (centroCercano != null) {
            outputConsumer.accept("Centro más cercano: " + centroCercano.getProperties().getNombre());
            outputConsumer.accept(centroCercano); // Enviamos el objeto para el mapa
        }

        // --- 2. GESTIONAR DATOS DE USUARIO Y HERIDO ---
        UserData datosHerido;
        UserData datosLlamante;

        outputConsumer.accept("\nBuscando DNI del herido (" + dniHerido + ") en la base de datos...");
        datosHerido = RetrieveData.retrieveInjuredData(dniHerido);

        if (datosHerido != null) {
            outputConsumer.accept("DNI encontrado. Datos del herido recuperados.");
            outputConsumer.accept(datosHerido);
        } else {
            outputConsumer.accept("DNI del herido no encontrado. Se creará un registro parcial.");
            datosHerido = new UserData();
            datosHerido.setDNI(dniHerido);
        }

        if (datosLlamanteExternos == null) {
            datosLlamante = new UserData(datosHerido);
        } else {
            datosLlamante = datosLlamanteExternos;
        }

        // --- 3. CREAR EL EVENTO DE EMERGENCIA ---
        String ubiString = (miUbicacion != null) ? miUbicacion.getFormattedLocation() : "Ubicación desconocida";
        EmergencyEvent event = new EmergencyEvent(gravedad, ubiString, datosHerido, datosLlamante);

        // --- 4. ENVIAR ALERTA ---
        IAlertSender sender = simularCobertura ? new CoverageFinder() : new AlertSender();
        sender.sendAlert(event);
        outputConsumer.accept("\nAlerta enviada (o en proceso de envío si no hay cobertura).\n");

        // --- 5. DAR INDICACIONES ---
        String indicaciones = Indicaciones.getIndicaciones(gravedad);
        outputConsumer.accept(indicaciones);
    }
}
