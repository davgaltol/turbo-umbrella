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


        try {
            if (gravedad.contains("leve")) {
                //herida leve y cierro programa
                System.out.println(gravedad);
                System.out.println("Cerrando aplicación");
            } else {
                //Paso a obtener ubicación
                ubi = getLocation();

                //Paso a recuperar o tomar datos de usuario
                System.out.println("¿Es usted el herido?S/N");
                input = sc.nextLine();
                if (ValidaEntrada.validaEntSN(input)) {
                    System.out.println("Escriba su DNI con número y letra. Pulse cualquier otra tecla si no lo conoce.");
                    input = sc.nextLine();
                    if (ValidaEntrada.validaEntDNI(input)) {
                        System.out.println("DNI con formato correcto.");
                        input = input.toUpperCase();                            //guardamos el DNI en mayusculas
                        if (user.validateUserData(input)) {                     //verificamos si el dni está en el Json
                            datosHerido = user.retrieveUserData(input);         // si está en el json se recuperan datos del Json
                        }
                        else{
                            System.out.println("DNI no encontrado en la base de + " +   //si no está el DNIen el Json se genera alerta por defecto
                                    "datos. Se genera alerta por defecto");             //con los datos parciales de herido (que es usuario)
                            //recopilacion de datos del usuario: nombre, apellidos y tlf
                            System.out.println("Datos de usuario");
                            System.out.println("Introduzca sus datos para poder contactarle si es necesario:");
                            System.out.println("Introduzca su nombre");
                            datosUsuario[0] = sc.nextLine();
                            System.out.println("Introduzca sus apellidos");
                            datosUsuario[1] = sc.nextLine();
                            System.out.println("Introduzca su teléfono");
                            datosUsuario[2] = sc.nextLine();
                            datosHerido= user.unknownUserData(datosUsuario);
                        }
                    } else {
                        //***revisar entrada rara aqui***///

                        System.out.println("DNI con formato incorrecto o desconocido. ¿Generar alerta por defecto?S/N");
                        if (ValidaEntrada.validaEntSN(input)) {
                            input = "Desconocido";
                            datosHerido = user.unknownUserData();
                        } else {
                            System.out.println("Se cancela aletra. Saliendo...");
                        }

                    }
                    /********************************************************************************************************
                     *              Si es el herido y no sabe su DNI se genera alerta por defecto
                     */


                    //datosHerido = user.getUserData(datosUsuario);
                } else {


                    System.out.println("Escriba el DNI del herido con número y letra. Pulse cualquier otra tecla si no lo conoce.");
                    input = sc.nextLine();
                    if (ValidaEntrada.validaEntDNI(input)) {
                        System.out.println("DNI con formato correcto.");
                        input = input.toUpperCase();   //guardamos el DNI en mayusculas
                        datosHerido = user.retrieveUserData(input);// Se recuperan datos del herido del json
                        //sacar si se recuperan o no los datos

                        //recopilacion de datos del usuario: nombre, apellidos y tlf
                        System.out.println("Datos de usuario");
                        System.out.println("Introduzca sus datos para poder contactarle si es necesario:");
                        System.out.println("Introduzca su nombre");
                        datosUsuario[0] = sc.nextLine();
                        System.out.println("Introduzca sus apellidos");
                        datosUsuario[1] = sc.nextLine();
                        System.out.println("Introduzca su teléfono");
                        datosUsuario[2] = sc.nextLine();

                        //crear método para mandar alerta

                    }
                    else{
                        System.out.println("DNI con formato incorrecto o desconocido. ¿Generar alerta por defecto?S/N");
                        if (ValidaEntrada.validaEntSN(input)) {
                            input = "Desconocido";
                            datosHerido = user.unknownUserData();
                            System.out.println("Datos de usuario");
                            System.out.println("Introduzca sus datos para poder contactarle si es necesario:");
                            System.out.println("Introduzca su nombre");
                            datosUsuario[0] = sc.nextLine();
                            System.out.println("Introduzca sus apellidos");
                            datosUsuario[1] = sc.nextLine();
                            System.out.println("Introduzca su teléfono");
                            datosUsuario[2] = sc.nextLine();

                        } else {
                            System.out.println("Se cancela aletra. Saliendo...");
                        }

                    }
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
        } catch (IllegalArgumentException e){
            System.out.println("Error: parámetro Gravedad inválido. " + e.getMessage());
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
/*
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
 */

}
