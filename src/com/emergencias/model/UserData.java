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

    public UserData() {}

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

    public static UserData unknownInjuredData() {
        UserData datosHerido = new UserData();
        datosHerido.nombre = "Desconocido";
        datosHerido.apellidos = "Desconocido";
        datosHerido.dni = "Desconocido";
        System.out.println("Generando datos para herido desconocido.");
        return datosHerido;
    }

    public static UserData unknownUserData() {
        UserData datosUsuario = new UserData();
        datosUsuario.nombre = "Desconocido";
        datosUsuario.apellidos = "Desconocido";
        datosUsuario.telefono = "Desconocido";
        System.out.println("Generando datos para usuario desconocido.");
        return datosUsuario;
    }

    @Override
    public String toString() {
        return "  - Nombre: " + (nombre != null ? nombre : "N/A") + "\n" +
               "  - Apellidos: " + (apellidos != null ? apellidos : "N/A") + "\n" +
               "  - DNI: " + (dni != null ? dni : "N/A") + "\n" +
               "  - Teléfono: " + (telefono != null ? telefono : "N/A") + "\n" +
               "  - Edad: " + (edad != null ? edad : "N/A") + "\n" +
               "  - Contacto Emerg.: " + (personaContacto != null ? personaContacto : "N/A") + "\n" +
               "  - Tel. Contacto: " + (telefonoContacto != null ? telefonoContacto : "N/A") + "\n" +
               "  - Info Médica: " + getInfoMedicaAsString();
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
