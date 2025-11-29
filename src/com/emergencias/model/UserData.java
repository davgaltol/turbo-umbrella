package com.emergencias.model;

import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserData {

    public PacienteData getUserData() {
        PacienteData pacienteData = new PacienteData();
        Scanner sc = new Scanner(System.in);

        System.out.println("Introduzca sus datos para poder contactarle si es necesario:");
        System.out.println("Introduzca su nombre");
        pacienteData.setNombre(sc.nextLine());
        System.out.println("Introduzca sus apellidos");
        pacienteData.setApellidos(sc.nextLine());
        System.out.println("Introduzca su teléfono");
        pacienteData.setTelefono(sc.nextLine());
        return pacienteData;
    }

    public PacienteData retrieveInjuredData(String dni) {
        final String PATIENTS_FILE_PATH = "./src/resources/pacientes.json";
        Gson gson = new Gson();
        List<PacienteData> listaPacientes;

        try (FileReader reader = new FileReader(PATIENTS_FILE_PATH)) {
            Type listType = new TypeToken<ArrayList<PacienteData>>(){}.getType();
            listaPacientes = gson.fromJson(reader, listType);
        } catch (IOException e) {
            System.err.println("Error: No se pudo encontrar o leer el fichero 'pacientes.json'.");
            return null;
        } catch (JsonSyntaxException e) {
            System.err.println("Error: El formato del fichero 'pacientes.json' es incorrecto.");
            return null;
        }

        if (listaPacientes == null) {
            return null;
        }

        for (PacienteData paciente : listaPacientes) {
            if (paciente.getDni() != null && paciente.getDni().equalsIgnoreCase(dni)) {
                return paciente;
            }
        }

        System.out.println("DNI no encontrado en la base de datos de pacientes.");
        return null;
    }

    public PacienteData unknownInjuredData(){
        PacienteData pacienteData = new PacienteData();
        pacienteData.setNombre("Desconocido");
        pacienteData.setApellidos("Desconocido");
        pacienteData.setDni("Desconocido");
        pacienteData.setTelefono("Desconocido");
        pacienteData.setEdad(0);
        pacienteData.setPersonaContacto("Desconocido");
        pacienteData.setTelefonoContacto("Desconocido");
        pacienteData.setDatosMedicos(new ArrayList<>());
        System.out.println("Generada alerta por defecto sin paso de DNI.");
        return pacienteData;
    }

    public PacienteData unknownInjuredData(PacienteData datosRecibidos){
        PacienteData pacienteData = new PacienteData();
        pacienteData.setNombre(datosRecibidos.getNombre());
        pacienteData.setApellidos(datosRecibidos.getApellidos());
        pacienteData.setTelefono(datosRecibidos.getTelefono());
        pacienteData.setDni("Desconocido");
        pacienteData.setEdad(0);
        pacienteData.setPersonaContacto("Desconocido");
        pacienteData.setTelefonoContacto("Desconocido");
        pacienteData.setDatosMedicos(new ArrayList<>());
        return pacienteData;
    }

    public PacienteData unknownUserData(){
        PacienteData pacienteData = new PacienteData();
        pacienteData.setNombre("Desconocido");
        pacienteData.setApellidos("Desconocido");
        pacienteData.setTelefono("Desconocido");
        System.out.println("Generada alerta por defecto sin datos de usuario.");
        return pacienteData;
    }
}
