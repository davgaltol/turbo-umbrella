package com.emergencias.controller;

import com.emergencias.model.UserData;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RetrieveData {
    public static UserData retrieveInjuredData(String dni) {
        final String PATIENTS_FILE_PATH = "./src/resources/pacientes.json";
        Gson gson = new Gson();
        List<UserData> listaPacientes;

        // 1. LEER Y PARSEAR EL FICHERO JSON
        try (FileReader reader = new FileReader(PATIENTS_FILE_PATH)) {
            // Definimos el tipo: una Lista de PacienteData. Es crucial para que Gson entienda el array JSON.
            Type listType = new TypeToken<ArrayList<UserData>>(){}.getType();
            listaPacientes = gson.fromJson(reader, listType);
        } catch (IOException e) {
            System.err.println("Error: No se pudo encontrar o leer el fichero 'pacientes.json'.");
            return null; // Devuelve null si el fichero no existe
        } catch (JsonSyntaxException e) {
            System.err.println("Error: El formato del fichero 'pacientes.json' es incorrecto.");
            return null; // Devuelve null si el JSON está mal formado
        }

        // Si el fichero está vacío o mal formado, la lista podría ser nula
        if (listaPacientes == null) {
            return null;
        }

        // 2. BUSCAR AL PACIENTE POR DNI
        for (UserData paciente : listaPacientes) {
            // Comparamos el DNI del paciente actual con el DNI buscado (ignorando mayúsculas/minúsculas)
            if (paciente.getDni() != null && paciente.getDni().equalsIgnoreCase(dni)) {
                return paciente; // Devolvemos los datos del paciente encontrado
            }
        }

        // 4. SI NO SE ENCUENTRA, DEVOLVER NULL
        System.out.println("DNI no encontrado en la base de datos de pacientes.");
        return null; // Devuelve null si el DNI no está en la lista
    }
}
