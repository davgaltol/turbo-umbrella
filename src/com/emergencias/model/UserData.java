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


    //Crea una alerta genérica con herido desconocido
    public UserData unknownInjuredData(){
        UserData datosHerido=new UserData();    //se devuelve usuario construido por defecto (desconocido)

        datosHerido.nombre="Desconocido";

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
        datosUsuario.nombre="Desconocido";
        datosUsuario.apellidos="Desconocido";
        datosUsuario.dni="Desconocido";
        datosUsuario.telefono="Desconocido";
        datosUsuario.edad="Desconocido";
        datosUsuario.personaContacto="Desconocido";
        datosUsuario.telefonoContacto="Desconocido";
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
