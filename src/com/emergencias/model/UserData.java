package com.emergencias.model;

import java.util.Scanner;
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


    //CREAR CONSTRUCTOR ASIGNE POR DEFECTO
    public UserData(){
        String nombre="Desconocido";
        String apellidos="Desconocido";
        String dni="Desconocido";
        String telefono="Desconocido";
        String edad="Desconocido";
        String personaContacto="Desconocido";
        String telefonoContacto="Desconocido";
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

/*    public UserData retrieveInjuredData(String dni) {
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
                /*
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

                return paciente; // Devolvemos los datos del paciente encontrado
            }
        }

        // 4. SI NO SE ENCUENTRA, DEVOLVER NULL
        System.out.println("DNI no encontrado en la base de datos de pacientes.");
        return null; // Devuelve null si el DNI no está en la lista
    }
*/

    //Crea una alerta genérica con herido desconocido
    public UserData unknownInjuredData(){
        UserData datosHerido=new UserData();    //se devuelve usuario construido por defecto (desconocido)

        System.out.println("Generada alerta por defecto sin paso de DNI.");

        return datosHerido;
    }

    public UserData unknownInjuredData(UserData datosRecibidos){
        UserData datosHerido=new UserData();
        datosHerido.nombre=datosRecibidos.nombre;
        datosHerido.apellidos=datosRecibidos.apellidos;
        datosHerido.telefono=datosRecibidos.telefono;

        return datosHerido;
    }

    public UserData unknownUserData(){
        UserData datosUsuario=new UserData();

        System.out.println("Generada alerta por defecto sin datos de usuario.");

        return datosUsuario;
    }

    // Getters para acceder a los datos. Muchos no se usan. Creados a futuro
    public String getDni() { return dni; }
    public String getNombre() { return nombre; }
    public String getApellidos() { return apellidos; }
    public String getTelefono() { return telefono; }
    public String getEdad() { return edad; }
    public String getNombreContacto() { return personaContacto; }
    public String getTelefonoContacto() { return telefonoContacto; }
    // Método para convertir la lista de datos médicos a un solo String
    public String getInfoMedicaAsString() {
        if (datosMedicos == null || datosMedicos.isEmpty()) {
            return "Sin datos médicos de interés.";
        }
        return String.join(", ", datosMedicos);
    }


    // Setters para acceder a los datos. Muchos no se usan. Creados a futuro
    public void setDNI(String dni) { this.dni=dni; }
    public void setNombre(String nombre) { this.nombre=nombre; }
    public void setApellidos(String apellidos) { this.apellidos=apellidos; }
    public void setTelefono(String telefono) { this.telefono=telefono; }
    public void setEdad(String edad) { this.edad=edad; }
    public void setNombreContacto(String personaContacto) { this.personaContacto=personaContacto; }
    public void setTelefonoContacto(String telefonoContacto) { this.telefonoContacto=telefonoContacto; }


}
