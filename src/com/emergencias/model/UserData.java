package com.emergencias.model;

import java.util.Scanner;

//CLASE PARA OBTENER LOS DATOS DEL USUARIO Y PACIENTE

public class UserData {
    public String[] getUserData(){
        String[] datosHerido=new String[7]; //nombre, apellidos, teléfono, edad, nom contacto, tlf contacto, info_médica
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
    public String[] getUserData(String[] datosUsuario){
        String[] datosHerido=new String[7]; //nombre, apellidos, teléfono, edad, nom contacto, tlf contacto, info_médica
        Scanner sc = new Scanner(System.in);

        datosHerido[0]=datosUsuario[0];
        datosHerido[1]=datosUsuario[1];
        datosHerido[2]=datosUsuario[2];

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


    //Recuperar del json los datos del herido sabiendo su DNI
    public String[] retrieveUserData(String dni) {
        String[] datosHerido=new String[7];

        System.out.println("Recupera del Json. COMPLETAR ESTE METODO");
        return datosHerido;
    }

    //Crea una alerta genérica con herido desconocido

    public String[] unknownUserData(){
        String[] datosHerido=new String[7];

        System.out.println("Genera alerta por defecto. COMPLETAR ESTE METODO");

        return datosHerido;
    }
}
