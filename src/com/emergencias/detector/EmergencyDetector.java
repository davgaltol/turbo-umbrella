package com.emergencias.detector;

import com.emergencias.controller.ValidaEntrada;
import com.emergencias.model.EmergencyEvent;

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

            /********************************************************************************/

            input="e";
            //input = sc.nextLine();


            /********************************************************************************/
            if (input.equalsIgnoreCase("Q")) {
                System.out.println("Nos indica que no hay una emergencia. Saliendo...");
                return null;
            }
            if (input.equalsIgnoreCase("E")) {
                System.out.println("Nos indica que hay una emergencia. Pasando a toma de datos...");

       /********************************************************************************/
                //gravedad=validateSeverity();
                gravedad="Mucha";
                /********************************************************************************/

                return new EmergencyEvent(gravedad); //valorar si da tiempo que no pregunte si no hay consciencia si es o no el herido

            }

        } while (!input.equalsIgnoreCase("Q") || !input.equalsIgnoreCase("E"));

        return null;
    }
//VALORACION DE GRAVEDAD DE EMERGENCIA
    private String validateSeverity() {
        boolean pCons = false;
        boolean pResp = false;
        boolean pSang = false;
        boolean pGolp = false;

        Scanner sc = new Scanner(System.in);
        String input;
        String gravedad = "Problema:\n";

        System.out.println("***************************************************");
        System.out.println("********SISTEMA DE VERIFICACIÓN DE GRAVEDAD********");
        System.out.println("***************************************************");
        System.out.println("¿Está inconsciente el herido? S/N");
        input = sc.nextLine();
        if (ValidaEntrada.validaEntSN(input)) {
            pCons = true;
            gravedad=gravedad + "Cod01. Nivel de consciencia baja/inestable/inconsciente\n";
        }
        System.out.println("¿Tiene problemas para respirar o vías obstruidas? S/N");
        input = sc.nextLine();
        if (ValidaEntrada.validaEntSN(input)) {
            pResp = true;
            gravedad= gravedad + "Cod02. Fallo respiratorio/ Vías obstruidas\n";
        }
        System.out.println("¿Sangra abundantemente? S/N");
        input = sc.nextLine();
        if (ValidaEntrada.validaEntSN(input)) {
            pSang = true;
            gravedad= gravedad + "Cod03. Sangrado abundante\n";
        }
        System.out.println("¿Ha sufrido un golpe? S/N");
        input = sc.nextLine();
        if (ValidaEntrada.validaEntSN(input)) {
            System.out.println("Indique gravedad de 1 a 5");
            input = sc.nextLine();
            try {
                boolean numValido=ValidaEntrada.validaEntDolor(input);
                if ((Integer.parseInt(input) > 3)&&numValido) {
                    pGolp = true;
                    gravedad = gravedad + "Cod04. Golpe grave de intensidad " + input + "\n";
                }
                else{
                    System.out.println("Dolor bajo o número fuera de rango. El daño se considera leve");
                }
            }catch (NumberFormatException e) {
                System.out.println("Error: Formato número erróneo. El daño se considera leve");

            }
        }
        if (!pCons && !pResp && !pSang && !pGolp) {
            gravedad="DIAGNÓSTICO: Lesión leve que no requiere asistencia inmediata. Acuda a su centro de salud.";
        }

        return gravedad;

    }

}