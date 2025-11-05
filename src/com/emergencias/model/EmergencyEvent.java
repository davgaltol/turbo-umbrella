package com.emergencias.model;

import java.util.Scanner;
import com.emergencias.controller.ValidaEntrada;

//CREA LA EMERGENCIA
public class EmergencyEvent {

    public EmergencyEvent(String gravedad) {
        UserData user = new UserData();
        String input, ubi;
        String[] datosHerido, datosUsuario;
        datosUsuario=new String[3]; //nombre, apellidos, telefono
        datosHerido=new String[7];  //nombre, apellidos, teléfono, edad, nom contacto, tlf contacto, info_médica
        Scanner sc = new Scanner(System.in);

        if (gravedad.contains("leve")){
            //herida leve y cierro programa
            System.out.println(gravedad);
            System.out.println("Cerrando aplicación");
        }
        else {
            //Paso a obtener ubicación
            ubi = getLocation();

            //Paso a recuperar o tomar datos de usuario
            System.out.println("¿Es usted el herido?S/N");
            input=sc.nextLine();
            if (validaEntSN(input)) {
                System.out.println("Escriba su DNI con número y letra");
                input=sc.nextLine();
                if (validaEntDNI(input)){

                }

                //datosHerido = user.getUserData(datosUsuario);
            } else {
                //datosHerido = user.getUserData();
            }


/*            System.out.println("Datos de usuario:");
            System.out.println("Introduzca su nombre");
            datosUsuario[0] = sc.nextLine();
            System.out.println("Introduzca sus apellidos");
            datosUsuario[1] = sc.nextLine();
            System.out.println("Introduzca su teléfono");
            datosUsuario[2] = sc.nextLine();
            System.out.println("Es usted el herido?S/N");
            input = sc.nextLine();
            if (validaEntSN(input)) {
                datosHerido = user.getUserData(datosUsuario);
            } else {
                datosHerido = user.getUserData();
            }


            System.out.println("****************************************");
            System.out.println("Emergencia en " + ubi);
            System.out.println("****************************************");
            System.out.println("Gravedad: " + gravedad);
            System.out.println("Herido reportado por " + datosUsuario[0] + " " + datosUsuario[1] + " " + datosUsuario[2]);
            System.out.println("Datos del herido:");
            System.out.println("Nombre: " + datosHerido[0]);
            System.out.println("Apellidos: " + datosHerido[1]);
            System.out.println("Teléfono: " + datosHerido[2]);
            System.out.println("Edad: " + datosHerido[3]);
            System.out.println("Nombre de contacto de emergencia: " + datosHerido[4]);
            System.out.println("Teléfono de contacto de emergencia: " + datosHerido[5]);
            System.out.println("Información médica: " + datosHerido[6]);

*/
        }
    }

    private String getLocation() {
        Scanner sc = new Scanner(System.in);
        String ubi;
        System.out.println("Obtención de ubicación");
        System.out.println("Introduzca su ubicación");           //Crear método para obtener ubicación el lugar de insertar manualmente
        ubi = sc.nextLine();
        System.out.println("Usted se encuentra en " + ubi);
        return ubi;
    }

    private boolean validaEntSN(String entrada) { //Se valida entrada para evitar otro caracter
        boolean ent;
        while (!entrada.equalsIgnoreCase("S") && (!entrada.equalsIgnoreCase("N"))) {
            System.out.println("Entrada incorrecta. Escriba S/N");
            Scanner sc = new Scanner(System.in);
            entrada = sc.nextLine();
        }
        if (entrada.equalsIgnoreCase("S")) {
            ent = true;
        } else {
            ent = false;
        }
        return ent;
    }

}
