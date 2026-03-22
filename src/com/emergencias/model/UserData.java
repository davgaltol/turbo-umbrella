package com.emergencias.model;

import java.util.List;

public class UserData {
    private String nombre;
    private String apellidos;
    private String dni;
    private String telefono;
    private String edad;
    private String personaContacto;
    private String telefonoContacto;
    private List<String> datosMedicos;

    // Constructor por defecto
    public UserData() {}

    // Constructor de copia, útil para cuando el llamante es el herido
    public UserData(UserData other) {
        this.nombre = other.nombre;
        this.apellidos = other.apellidos;
        this.telefono = other.telefono;
        this.dni = other.dni;
        this.edad = other.edad;
        this.personaContacto = other.personaContacto;
        this.telefonoContacto = other.telefonoContacto;
        this.datosMedicos = other.datosMedicos;
    }

    /**
     * Crea una instancia de UserData para un herido desconocido.
     * Es estático porque no necesita una instancia previa para ser llamado.
     */
    public static UserData unknownInjuredData() {
        UserData datosHerido = new UserData();
        datosHerido.nombre = "Desconocido";
        datosHerido.apellidos = "Desconocido";
        datosHerido.dni = "Desconocido";
        // Inicializar otros campos a valores por defecto si es necesario
        System.out.println("Generando datos para herido desconocido.");
        return datosHerido;
    }

    /**
     * Crea una instancia de UserData para un usuario/llamante desconocido.
     * Es estático porque no necesita una instancia previa para ser llamado.
     */
    public static UserData unknownUserData() {
        UserData datosUsuario = new UserData();
        datosUsuario.nombre = "Desconocido";
        datosUsuario.apellidos = "Desconocido";
        datosUsuario.telefono = "Desconocido";
        System.out.println("Generando datos para usuario desconocido.");
        return datosUsuario;
    }

    // --- Getters y Setters ---

    public String getDni() { return dni; }
    public void setDNI(String dni) { this.dni = dni; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getEdad() { return edad; }
    public void setEdad(String edad) { this.edad = edad; }
    public String getNombreContacto() { return personaContacto; }
    public void setNombreContacto(String personaContacto) { this.personaContacto = personaContacto; }
    public String getTelefonoContacto() { return telefonoContacto; }
    public void setTelefonoContacto(String telefonoContacto) { this.telefonoContacto = telefonoContacto; }
    
    public String getInfoMedicaAsString() {
        if (datosMedicos == null || datosMedicos.isEmpty()) {
            return "Sin datos médicos de interés.";
        }
        return String.join(", ", datosMedicos);
    }
}
