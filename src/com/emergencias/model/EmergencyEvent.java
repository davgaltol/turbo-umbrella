package com.emergencias.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase de datos (POJO) que representa un evento de emergencia.
 * No contiene lógica de negocio ni interacción con el usuario.
 */
public class EmergencyEvent {

    private String timestamp;
    private UserData datosHerido;
    private UserData datosUsuario;
    private String ubicacion;
    private String gravedad;

    /**
     * Constructor para crear un evento de emergencia con todos los datos ya procesados.
     */
    public EmergencyEvent(String gravedad, String ubicacion, UserData datosHerido, UserData datosUsuario) {
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.gravedad = gravedad;
        this.ubicacion = ubicacion;
        this.datosHerido = datosHerido;
        this.datosUsuario = datosUsuario;
    }

    // Getters para que otras clases puedan acceder a los datos
    public String getTimestamp() {
        return timestamp;
    }

    public UserData getDatosHerido() {
        return datosHerido;
    }

    public UserData getDatosUsuario() {
        return datosUsuario;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getSeverity() {
        return this.gravedad;
    }
}
