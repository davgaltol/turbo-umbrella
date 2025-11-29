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
    private String nombre;
    private String apellidos;
    private String dni;
    private String telefono;
    private int edad;
    private String personaContacto;
    private String telefonoContacto;
    private List<String> datosMedicos;


    //CREAR CONSTRUCTOR ASIGNE POR DEFECTO
    public UserData(){
        String nombre="Desconocido";
        String apellidos="Desconocido";
        String dni="Desconocido";
        String telefono="Desconocido";
        String personaContacto="Desconocido";
        String telefonoContacto="Desconocido";
    }


    //CLASE PARA OBTENER LOS DATOS DEL HERIDO CUANDO NO ES EL USUARIO A MANO
    // CREADA INICIALMENTE ANTES DE IMPLEMENTAR PASO DESDE JSON. EN DESUSO
    public String[] getInjuredData(){
        String[] datosHerido=new String[8]; //nombre, apellidos, dni, teléfono, edad, nom contacto, tlf contacto, info_médica
        Scanner sc = new Scanner(System.in);

        System.out.println("Ingrese el nombre del herido");
        datosHerido[0]=sc.nextLine();
        System.out.println("Ingrese los apellidos del herido");
        datosHerido[1]=sc.nextLine();
        System.out.println("Ingrese el teléfono del herido");
        datosHerido[2]=sc.nextLine();
        System.out.println("Introduzca edad");
        datosHerido[3]=sc.nextLine();
        System.out.println("Introduzca nombre del contacto de emergencia");
        datosHerido[4]=sc.nextLine();
        System.out.println("Introduzca teléfono del contacto de emergencia");
        datosHerido[5]=sc.nextLine();
        System.out.println("Introduzca información médica relevante del herido si hay");
        datosHerido[6]=sc.nextLine();

        return datosHerido;
    }

    //CLASE PARA OBTENER LOS DATOS DEL HERIDO CUANDO ES USUARIO A MANO (SOBRECARGADA). EN DESUSO
    // CREADA INICIALMENTE ANTES DE IMPLEMENTAR PASO DESDE JSON. EN DESUSO
    public String[] getInjuredData(String[] datosUsuario){
        String[] datosHerido=new String[8]; //nombre, apellidos, dni, teléfono, edad, nom contacto, tlf contacto, info_médica
        Scanner sc = new Scanner(System.in);

        datosHerido[0]=datosUsuario[0];
        datosHerido[1]=datosUsuario[1];
        datosHerido[2]=datosUsuario[3];

        System.out.println("Introduzca edad");
        datosHerido[3]=sc.nextLine();
        System.out.println("Introduzca nombre del contacto de emergencia");
        datosHerido[4]=sc.nextLine();
        System.out.println("Introduzca teléfono del contacto de emergencia");
        datosHerido[5]=sc.nextLine();
        System.out.println("Introduzca información médica relevante del herido si hay");
        datosHerido[6]=sc.nextLine();

        return datosHerido;
    }

    //CLASE PARA OBTENER LOS DATOS DEL USUARIO

    public UserData getUserData() {
        UserData datosUsuario = new UserData(); //nombre, apellidos, teléfono
        Scanner sc = new Scanner(System.in);

        System.out.println("Introduzca sus datos para poder contactarle si es necesario:");
        System.out.println("Introduzca su nombre");
        datosUsuario.nombre = sc.nextLine();
        System.out.println("Introduzca sus apellidos");
        datosUsuario.apellidos = sc.nextLine();
        System.out.println("Introduzca su teléfono");
        datosUsuario.telefono = sc.nextLine();
        return datosUsuario;
    }

    public String[] retrieveInjuredData(String dni) {
        final String PATIENTS_FILE_PATH = "./src/resources/pacientes.json";
        Gson gson = new Gson();
        List<PacienteData> listaPacientes;

        // 1. LEER Y PARSEAR EL FICHERO JSON
        try (FileReader reader = new FileReader(PATIENTS_FILE_PATH)) {
            // Definimos el tipo: una Lista de PacienteData. Es crucial para que Gson entienda el array JSON.
            Type listType = new TypeToken<ArrayList<PacienteData>>(){}.getType();
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
        for (PacienteData paciente : listaPacientes) {
            // Comparamos el DNI del paciente actual con el DNI buscado (ignorando mayúsculas/minúsculas)
            if (paciente.getDni() != null && paciente.getDni().equalsIgnoreCase(dni)) {

                // 3. SI SE ENCUENTRA, CONSTRUIR Y DEVOLVER EL ARRAY DE STRINGS
                String[] datosHerido = new String[8];
                datosHerido[0] = paciente.getNombre();
                datosHerido[1] = paciente.getApellidos();
                datosHerido[2] = paciente.getDni();
                datosHerido[3] = paciente.getTelefono();
                datosHerido[4] = String.valueOf(paciente.getEdad()); // Convertimos el int a String
                datosHerido[5] = paciente.getNombreContacto();
                datosHerido[6] = paciente.getTelefonoContacto();
                datosHerido[7] = paciente.getInfoMedicaAsString();

                return datosHerido; // Devolvemos los datos del paciente encontrado
            }
        }

        // 4. SI NO SE ENCUENTRA, DEVOLVER NULL
        System.out.println("DNI no encontrado en la base de datos de pacientes.");
        return null; // Devuelve null si el DNI no está en la lista
    }


    //Crea una alerta genérica con herido desconocido

    public String[] unknownInjuredData(){
        String[] datosHerido=new String[8];

        for (int i=0;i<8;i++){
            datosHerido[i]="Desconocido";
        }
        System.out.println("Generada alerta por defecto sin paso de DNI.");

        return datosHerido;
    }

    public String[] unknownInjuredData(String[] datosRecibidos){
        String[] datosHerido=new String[8];
        datosHerido[0]=datosRecibidos[0];
        datosHerido[1]=datosRecibidos[1];
        datosHerido[3]=datosRecibidos[2];
        datosHerido[2]="Desconocido";
        for (int i=4;i<8;i++){
            datosHerido[i]="Desconocido";
        }


        return datosHerido;
    }

    public String[] unknownUserData(){
        String[] datosUsuario=new String[3];
        for (int i=0;i<3;i++){
            datosUsuario[i]="Desconocido";
        }
        System.out.println("Generada alerta por defecto sin datos de usuario.");

        return datosUsuario;
    }

    // CLASE MOLDE PARA GSON
    // Representa la estructura de UN objeto paciente en el JSON.
    private static class PacienteData {
        private String nombre;
        private String apellidos;
        private String dni;
        private String telefono;
        private int edad;
        private String personaContacto;
        private String telefonoContacto;
        private List<String> datosMedicos;


        // Getters para acceder a los datos
        public String getDni() { return dni; }
        public String getNombre() { return nombre; }
        public String getApellidos() { return apellidos; }
        public String getTelefono() { return telefono; }
        public int getEdad() { return edad; }
        public String getNombreContacto() { return personaContacto; }
        public String getTelefonoContacto() { return telefonoContacto; }
        //public String getInfoMedica() { return datosMedicos; }
        // Método para convertir la lista de datos médicos a un solo String
        public String getInfoMedicaAsString() {
            if (datosMedicos == null || datosMedicos.isEmpty()) {
                return "Sin datos médicos de interés.";
            }
            return String.join(", ", datosMedicos);
        }
    }

}
