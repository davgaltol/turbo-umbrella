package com.emergencias.model;

import java.util.Scanner;

//CLASE PARA OBTENER LOS DATOS DEL HERIDO CUANDO NO ES EL USUARIO A MANO

public class UserData {
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

    //CLASE PARA OBTENER LOS DATOS DEL HERIDO CUANDO ES USUARIO A MANO (SOBRECARGADA)

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

    public String[] getUserData() {
        String[] datosUsuario = new String[3]; //nombre, apellidos, teléfono
        Scanner sc = new Scanner(System.in);

        System.out.println("Introduzca sus datos para poder contactarle si es necesario:");
        System.out.println("Introduzca su nombre");
        datosUsuario[0] = sc.nextLine();
        System.out.println("Introduzca sus apellidos");
        datosUsuario[1] = sc.nextLine();
        System.out.println("Introduzca su teléfono");
        datosUsuario[2] = sc.nextLine();
        return datosUsuario;
    }

    public String[] retrieveInjuredData(String dni) {
        String[] datosHerido=new String[8];

        System.out.println("Recupera datos del herido del Json. COMPLETAR ESTE METODO");

        return datosHerido;
    }

    public boolean validateInjuredData(String dni) {
        boolean isUserDataInJson=false;

        System.out.println("Verificar si el dni aparece en el json. COMPLETAR ESTE METODO");
        return isUserDataInJson;
    }

    //Crea una alerta genérica con herido desconocido

    public String[] unknownInjuredData(){
        String[] datosHerido=new String[8];

        System.out.println("Genera alerta por defecto sin paso de DNI. COMPLETAR ESTE METODO");

        return datosHerido;
    }

    public String[] unknownInjuredData(String[] datosRecibidos){
        String[] datosHerido=new String[8];

        System.out.println("Genera alerta por defecto con paso de datos de usuario. COMPLETAR ESTE METODO");

        return datosHerido;
    }

    public String[] unknownUserData(){
        String[] datosUsuario=new String[3];

        System.out.println("Genera alerta por defecto sin paso de USUARIO. COMPLETAR ESTE METODO");

        return datosUsuario;
    }
}
