package com.emergencias.detector;
import com.emergencias.controller.HeridaGrave;
import com.emergencias.controller.HeridaLeve;
import com.emergencias.controller.ValidaEntrada;
import com.emergencias.model.EmergencyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//DETECTOR DE EMERGENCIA. VALORA GRAVEDAD

public class EmergencyDetector {
    public EmergencyEvent detectEvent() {
        String input, gravedad;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("***************************************************");
            System.out.println("***BIENVENIDO AL SISTEMA DE ALERTA DE EMERGENCIA***");
            System.out.println("***************************************************");
            System.out.println("Escriba la letra 'E' si hay una emergencia o 'Q' para salir");

            input = sc.nextLine();

            if (input.equalsIgnoreCase("Q")) {
                System.out.println("Nos indica que no hay una emergencia. Saliendo...");
                return null;
            }
            if (input.equalsIgnoreCase("E")) {
                System.out.println("Nos indica que hay una emergencia. Pasando a toma de datos...");

                gravedad=validateSeverity();

                return new EmergencyEvent(gravedad); //valorar si da tiempo que no pregunte si no hay consciencia si es o no el herido

            }

        } while (!input.equalsIgnoreCase("Q") || !input.equalsIgnoreCase("E"));

        return null;
    }
//VALORACION DE GRAVEDAD DE EMERGENCIA
    private String validateSeverity() {
        Scanner sc = new Scanner(System.in);
        String input;
        String gravedad = "Problema:\n";
        List<Integer> codHerida = new ArrayList<>();
        HeridaGrave herida= new HeridaGrave();
        HeridaLeve heridaL= new HeridaLeve();

        System.out.println("***************************************************");
        System.out.println("********SISTEMA DE VERIFICACIÓN DE GRAVEDAD********");
        System.out.println("***************************************************");
        System.out.println("¿Está inconsciente el herido? S/N");
        input = sc.nextLine();
        if (ValidaEntrada.validaEntSN(input)) {
            codHerida.add(1);
        }
        System.out.println("¿Tiene problemas para respirar o vías obstruidas? S/N");
        input = sc.nextLine();
        if (ValidaEntrada.validaEntSN(input)) {
            codHerida.add(2);
        }
        System.out.println("¿Sangra abundantemente? S/N");
        input = sc.nextLine();
        if (ValidaEntrada.validaEntSN(input)) {
            codHerida.add(3);
        }
        System.out.println("¿Ha sufrido un golpe? S/N");
        input = sc.nextLine();
        if (ValidaEntrada.validaEntSN(input)) {
        System.out.println("Indique gravedad de 1 a 5");
        input = sc.nextLine();
        try {
            boolean numValido=ValidaEntrada.validaEntDolor(input);
            if ((Integer.parseInt(input) > 3)&&numValido) {
                codHerida.add(4);
            }
            else{
                heridaL.setCodHerida(1);
            }
        }catch (NumberFormatException e) {
            System.out.println("Error: Formato número erróneo.");
        }

        //insertamos los codigos de herida en bucle
            for (int i = 0; i < codHerida.size(); i++) {
                herida.setCodHerida(codHerida.get(i));
            }
        }
        gravedad="DIAGNÓSTICO: Lesión leve que no requiere asistencia inmediata. Acuda a su centro de salud.";


        return herida.getHerida();

    }

}