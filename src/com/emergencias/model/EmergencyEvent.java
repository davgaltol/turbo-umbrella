package com.emergencias.model;

import java.util.Scanner;
import com.emergencias.controller.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EmergencyEvent {

    private String timestamp;
    private PacienteData datosHerido;
    private PacienteData datosUsuario;
    private String ubicacion;
    private String gravedad;

    public EmergencyEvent(String gravedad) {
        UserData user = new UserData();
        String input, ubi;
        Location location= new Location();
        PacienteData datosHerido, datosUsuario;
        Scanner sc = new Scanner(System.in);
        try {
            if (gravedad.contains("leve")) {
                System.out.println(gravedad);
            } else {
                ubi = location.getLocationFromAPI();

                System.out.println("Sus datos pueden ser importantes para los servicios médicos. ");
                System.out.println("Desea que recopilemos unos datos antes del envío de la alerta?S/N");
                input = sc.nextLine();
                if (ValidaEntrada.validaEntSN(input)) {
                    if (gravedad.contains("Cod01")){
                        input="n";
                    }else {
                        System.out.println("¿Es usted el herido?S/N");
                        input = sc.nextLine();
                    }
                    if (ValidaEntrada.validaEntSN(input)) {
                        System.out.println("Escriba su DNI con número y letra. Pulse cualquier otra tecla si no lo conoce.");
                        input = sc.nextLine();
                        if (ValidaEntrada.validaEntDNI(input)) {
                            input = input.toUpperCase();
                            datosHerido = user.retrieveInjuredData(input);
                            if (datosHerido != null) {
                                System.out.println("DNI encontrado en BBDD. Recuperando datos...");
                                datosUsuario = new PacienteData();
                                datosUsuario.setNombre(datosHerido.getNombre());
                                datosUsuario.setApellidos(datosHerido.getApellidos());
                                datosUsuario.setTelefono(datosHerido.getTelefono());
                            } else {
                                System.out.println("DNI no encontrado en la base de datos. Se genera alerta por defecto.");
                                System.out.println("Le pedimos datos básicos de contacto:");
                                datosUsuario= user.getUserData();
                                datosHerido = user.unknownInjuredData(datosUsuario);
                                datosHerido.setDni(input);
                            }
                        } else {
                            System.out.println("¿Generar alerta por defecto sin DNI?S/N");
                            input=sc.nextLine();
                            if (ValidaEntrada.validaEntSN(input)) {
                                System.out.println("Le pedimos datos básicos:");
                                datosUsuario= user.getUserData();
                                datosHerido = user.unknownInjuredData(datosUsuario);
                            } else {
                                System.out.println("Se cancela aletra. Saliendo...");
                            }
                        }
                    } else {
                        System.out.println("Escriba el DNI del herido con número y letra. Pulse cualquier otra tecla si no lo conoce.");
                        input = sc.nextLine();
                        if (ValidaEntrada.validaEntDNI(input)) {
                            System.out.println("DNI con formato correcto.");
                            input = input.toUpperCase();
                            datosHerido = user.retrieveInjuredData(input);

                            if (datosHerido != null) {
                                System.out.println("DNI encontrado en BBDD. Recuperando datos...");
                                datosUsuario= user.getUserData();
                            }
                            else{
                                System.out.println("Se genera alerta por defecto.");
                                System.out.println("Le pedimos datos básicos:");
                                datosUsuario= user.getUserData();
                                datosHerido = user.unknownInjuredData();
                            }
                        } else {
                            System.out.println("DNI con formato incorrecto o desconocido. ¿Generar alerta por defecto?S/N");
                            input=sc.nextLine();
                            if (ValidaEntrada.validaEntSN(input)) {
                                input = "Desconocido";
                                datosUsuario= user.getUserData();

                                datosHerido = user.unknownInjuredData();
                            } else {
                                System.out.println("Se cancela aletra. Saliendo...");
                            }
                        }
                    }
                }
                else{
                    datosUsuario= user.unknownUserData();
                    datosHerido = user.unknownInjuredData();
                }

                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                this.timestamp = now.format(formatter);

                //this.datosHerido = datosHerido;
                //this.datosUsuario = datosUsuario;
                this.ubicacion=ubi;
                this.gravedad=gravedad;
            }
        } catch (IllegalArgumentException e){
            System.out.println("Error: parámetro Gravedad inválido. " + e.getMessage());
        }
    }

    public String getTimestamp() {
        return timestamp; }

    public PacienteData getInjuredData() {
        return this.datosHerido;
    }
    public PacienteData getUserData() {
        return this.datosUsuario;
    }
    public String getSeverity(){
        return this.gravedad;
    }
    public String getLocation(){
        return this.ubicacion;
    }
}
