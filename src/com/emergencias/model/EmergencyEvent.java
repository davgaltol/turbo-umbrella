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
        datosHerido=new String[8];  //nombre, apellidos, teléfono, dni, edad, nom contacto, tlf contacto, info_médica
        Scanner sc = new Scanner(System.in);


        try {
            if (gravedad.contains("leve")) {
                //herida leve y cierro programa
                System.out.println(gravedad);
                System.out.println("Cerrando aplicación");
            } else {
                //Paso a obtener ubicación
                ubi = getLocation();
                System.out.println("Sus datos pueden ser importantes para los servicios médicos. ");
                System.out.println("Desea que recopilemos unos datos antes del envío de la alerta?S/N");
                input = sc.nextLine();
                if (ValidaEntrada.validaEntSN(input)) {                             //si es no urgencia extrema se pasa a formularios. Si no se manda ubicación y herido genérico
                    //Paso a recuperar o tomar datos de usuario
                    System.out.println("¿Es usted el herido?S/N");
                    input = sc.nextLine();
                    if (ValidaEntrada.validaEntSN(input)) {
                        System.out.println("Escriba su DNI con número y letra. Pulse cualquier otra tecla si no lo conoce.");
                        input = sc.nextLine();
                        if (ValidaEntrada.validaEntDNI(input)) {                    //se valida DNI
                            input = input.toUpperCase();                            //guardamos el DNI en mayusculas
                            if (user.validateInjuredData(input)) {                     //verificamos si el dni está en el Json
                                System.out.println("DNI encontrado en BBDD. Recuperando datos...");
                                datosHerido = user.retrieveInjuredData(input);         // si está en el json se recuperan datos del Json
                                datosUsuario[0]=datosHerido[0];                        //se recuperan los datos de usuario de los de herido. son el mismo
                                datosUsuario[1]=datosHerido[1];
                                datosUsuario[2]=datosHerido[3];
                                /************GENERAR ALERTSENDER*********/

                            } else {
                                System.out.println("DNI no encontrado en la base de + " +   //si no está el DNIen el Json se genera alerta por defecto
                                        "datos. Se genera alerta por defecto.");             //con los datos parciales de herido (que es usuario)
                                System.out.println("Le pedimos datos básicos:");
                                datosUsuario= user.getUserData();
                                datosHerido = user.unknownInjuredData(datosUsuario);         //Se genera herido por defecto pasando el usuario
                                datosHerido[2] = input;                                      //Se añade DNI facilitado

                                /************GENERAR ALERTSENDER*********/

                            }
                        } else {

                            System.out.println("¿Generar alerta por defecto sin DNI?S/N");
                            sc.nextLine();
                            if (ValidaEntrada.validaEntSN(input)) {
                                System.out.println("Le pedimos datos básicos:");
                                datosUsuario= user.getUserData();
                                datosHerido = user.unknownInjuredData(datosUsuario);

                                /************GENERAR ALERTSENDER*********/


                            } else {
                                System.out.println("Se cancela aletra. Saliendo...");
                            }

                        }

                    } else {


                        System.out.println("Escriba el DNI del herido con número y letra. Pulse cualquier otra tecla si no lo conoce.");
                        input = sc.nextLine();
                        if (ValidaEntrada.validaEntDNI(input)) {
                            System.out.println("DNI con formato correcto.");
                            input = input.toUpperCase();   //guardamos el DNI en mayusculas
                            if (user.validateInjuredData(input)){
                                System.out.println("DNI encontrado en BBDD. Recuperando datos...");
                                datosHerido = user.retrieveInjuredData(input);
                                datosUsuario= user.getUserData();
                            }
                            else{
                                System.out.println("DNI no encontrado en la base de + " +   //si no está el DNIen el Json se genera alerta por defecto
                                        "datos. Se genera alerta por defecto.");             //con los datos parciales de herido (que es usuario)
                                System.out.println("Le pedimos datos básicos:");
                                datosUsuario= user.getUserData();
                                datosHerido = user.unknownInjuredData();
                            }

                            datosHerido = user.retrieveInjuredData(input);// Se recuperan datos del herido del json
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

                        } else {
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
                }
                else{
                    user.unknownUserData();

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
