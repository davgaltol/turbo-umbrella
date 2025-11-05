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
        datosHerido=new String[8];  //nombre, apellidos, dni,  teléfono, edad, nom contacto, tlf contacto, info_médica
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
                            input=sc.nextLine();
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
                                datosHerido = user.retrieveInjuredData(input);  // Se recuperan datos del herido del json
                                datosUsuario= user.getUserData();   //Se toman los datos del usuario

                                /************GENERAR ALERTSENDER*********/

                            }
                            else{
                                System.out.println("DNI no encontrado en la base de + " +   //si no está el DNIen el Json se genera alerta por defecto
                                        "datos. Se genera alerta por defecto.");             //con los datos parciales de herido (que es usuario)
                                System.out.println("Le pedimos datos básicos:");
                                datosUsuario= user.getUserData();   //Se toman los datos del usuario
                                datosHerido = user.unknownInjuredData();    // Se generan datos del herido por defecto

                                /************GENERAR ALERTSENDER*********/

                            }
                        } else {
                            System.out.println("DNI con formato incorrecto o desconocido. ¿Generar alerta por defecto?S/N");
                            if (ValidaEntrada.validaEntSN(input)) {
                                input = "Desconocido";
                                datosUsuario= user.getUserData();   //Se toman los datos del usuario
                                datosHerido = user.unknownInjuredData();    // Se generan datos del herido por defecto

                                /************GENERAR ALERTSENDER*********/


                            } else {
                                System.out.println("Se cancela aletra. Saliendo...");
                            }

                        }
                    }
                }
                else{
                    datosUsuario= user.unknownUserData();   // Se generan datos del usuario por defecto
                    datosHerido = user.unknownInjuredData();    // Se generan datos del herido por defecto

                    /************GENERAR ALERTSENDER*********/



                }


/*

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

}
