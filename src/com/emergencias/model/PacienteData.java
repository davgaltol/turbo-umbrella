package com.emergencias.model;

import java.util.List;

public class PacienteData {
    private String nombre;
    private String apellidos;
    private String dni;
    private String telefono;
    private int edad;
    private String personaContacto;
    private String telefonoContacto;
    private List<String> datosMedicos;

    public PacienteData() {
    }

    public PacienteData(String nombre, String apellidos, String dni, String telefono, int edad, String personaContacto, String telefonoContacto, List<String> datosMedicos) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.telefono = telefono;
        this.edad = edad;
        this.personaContacto = personaContacto;
        this.telefonoContacto = telefonoContacto;
        this.datosMedicos = datosMedicos;
    }

    // Getters
    public String getNombre() { return nombre; }
    public String getApellidos() { return apellidos; }
    public String getDni() { return dni; }
    public String getTelefono() { return telefono; }
    public int getEdad() { return edad; }
    public String getNombreContacto() { return personaContacto; }
    public String getTelefonoContacto() { return telefonoContacto; }
    public List<String> getDatosMedicos() { return datosMedicos; }
    public String getInfoMedicaAsString() {
        if (datosMedicos == null || datosMedicos.isEmpty()) {
            return "Sin datos médicos de interés.";
        }
        return String.join(", ", datosMedicos);
    }

    // Setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public void setDni(String dni) { this.dni = dni; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setEdad(int edad) { this.edad = edad; }
    public void setPersonaContacto(String personaContacto) { this.personaContacto = personaContacto; }
    public void setTelefonoContacto(String telefonoContacto) { this.telefonoContacto = telefonoContacto; }
    public void setDatosMedicos(List<String> datosMedicos) { this.datosMedicos = datosMedicos; }
}
