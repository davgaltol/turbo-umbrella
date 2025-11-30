package com.emergencias.model;

import java.util.Scanner;
import com.emergencias.controller.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//CREA LA EMERGENCIA
public class EmergencyEvent {

    private String timestamp;
    private UserData datosHerido;
    private UserData datosUsuario;
    private String ubicacion;
    private String gravedad;

    public EmergencyEvent(String gravedad) {
        String input, ubi;
        boolean cancelada=false;
        Location location= new Location();
        UserData datosUsuario=new UserData(); //nombre, apellidos, telefono
        UserData datosHerido=new UserData();  //nombre, apellidos, dni, teléfono, edad, nom contacto, tlf contacto, info médica
        Scanner sc = new Scanner(System.in);
        try {
            if (gravedad.contains("leve")) {
                //herida leve y no se sigue ejecutando método.
                System.out.println(gravedad);
            } else {
                //Paso a obtener ubicación
                ubi = location.getLocationFromAPI();

                //Paso a recopilar datos
                System.out.println("Sus datos pueden ser importantes para los servicios médicos. ");
                System.out.println("Desea que recopilemos unos datos antes del envío de la alerta?S/N");
                input = sc.nextLine();
                if (ValidaEntrada.validaEntSN(input)) {                             //si es no urgencia extrema se pasa a formularios. Si no se manda ubicación y herido genérico
                    //Paso a recuperar o tomar datos de usuario
                    if (gravedad.contains("Cod01")){
                        input="n";                                      //si está inconsciente no tiene sentido preguntar si es el herido
                    }else {
                        System.out.println("¿Es usted el herido?S/N");
                        input = sc.nextLine();
                    }
                    if (ValidaEntrada.validaEntSN(input)) {
                        System.out.println("Escriba su DNI con número y letra. Pulse cualquier otra tecla si no lo conoce.");

/********************************************************************************/

                        input ="11111111a";
                        //input = sc.nextLine();



/*******************************************************************************/
                        if (ValidaEntrada.validaEntDNI(input)) {                    //se valida DNI
                            input = input.toUpperCase();                            //guardamos el DNI en mayusculas
                            if (RetrieveData.retrieveInjuredData(input) != null) {                     //verificamos si el dni está en el Json
                                System.out.println("DNI encontrado en BBDD. Datos recuperados...");
                            } else {
                                System.out.println("DNI no encontrado en la base de " +   //si no está el DNIen el Json se genera alerta por defecto
                                        "datos. Se genera alerta por defecto.");             //con los datos parciales de herido (que es usuario)
                                System.out.println("Le pedimos datos básicos de contacto:");
                                datosUsuario=datosUsuario.getUserData();
                                datosHerido = datosUsuario;         //Se genera herido por defecto pasando el usuario
                                datosHerido.setDNI(input);                                      //Se añade DNI facilitado

                            }
                        } else {

                            System.out.println("¿Generar alerta por defecto sin DNI?S/N");
                            input=sc.nextLine();
                            if (ValidaEntrada.validaEntSN(input)) {
                                System.out.println("Le pedimos datos básicos:");
                                datosUsuario=datosUsuario.getUserData();
                                datosHerido=datosHerido.unknownInjuredData(datosUsuario);

                            } else {
                                System.out.println("Se cancela aletra. Saliendo...");
                                cancelada=true;
                            }

                        }

                    } else {


                        System.out.println("Escriba el DNI del herido con número y letra. Pulse cualquier otra tecla si no lo conoce.");
                        input = sc.nextLine();
                        if (ValidaEntrada.validaEntDNI(input)) {
                            System.out.println("DNI con formato correcto.");
                            input = input.toUpperCase();   //guardamos el DNI en mayusculas
                            // Se recuperan datos del herido del json
                            if (RetrieveData.retrieveInjuredData(input) != null) {
                                System.out.println("DNI encontrado en BBDD. Recuperando datos...");
                                datosUsuario=datosUsuario.getUserData();   //Se toman los datos del usuario
                                datosHerido.setNombre("Desconocido");
                                datosHerido.setDNI(input);
                            }
                            else{
                                System.out.println("Se genera alerta por defecto.");    //si no está el DNIen el Json se genera alerta por defecto
                                                                                        //con los datos parciales de herido (que es usuario)
                                System.out.println("Le pedimos datos básicos:");
                                datosUsuario=datosUsuario.getUserData();   //Se toman los datos del usuario
                                datosHerido=datosHerido.unknownInjuredData();    // Se generan datos del herido por defecto

                            }
                        } else {
                            System.out.println("DNI con formato incorrecto o desconocido. ¿Generar alerta por defecto?S/N");
                            input=sc.nextLine();
                            if (ValidaEntrada.validaEntSN(input)) {
                                datosUsuario=datosUsuario.getUserData();   //Se toman los datos del usuario
                                datosHerido=datosHerido.unknownInjuredData();    // Se generan datos del herido por defecto

                            } else {
                                System.out.println("Se cancela aletra. Saliendo...");
                                cancelada=true;
                            }

                        }
                    }
                }
                else{
                    datosUsuario=datosUsuario.unknownUserData();   // Se generan datos del usuario por defecto
                    datosHerido=datosHerido.unknownInjuredData();    // Se generan datos del herido por defecto

                }

                if (!cancelada) {
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    this.timestamp = now.format(formatter);

                    this.datosHerido = datosHerido;
                    this.datosUsuario = datosUsuario;
                    this.ubicacion = ubi;
                    this.gravedad = gravedad;
                }
            }
        } catch (IllegalArgumentException e){
            System.out.println("Error: parámetro Gravedad inválido. " + e.getMessage());
        }
    }

    public String getSeverity(){
        return this.gravedad;
    }
    public String getLocation(){
        return this.ubicacion;
    }
}
