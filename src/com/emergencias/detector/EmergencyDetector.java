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
            System.out.println("Escribe 'E' si hay una emergencia o 'Q' para salir");
            input = sc.nextLine();
            if (input.equalsIgnoreCase("Q")) {
                System.out.println("No hay una emergencia. Saliendo...");
                return null;
            }
            if (input.equalsIgnoreCase("E")) {
                System.out.println("Hay una emergencia. Paso a toma de datos");
                gravedad=validateSeverity();

                return new EmergencyEvent(gravedad);

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

        System.out.println("Obtención de validación de gravedad ");

        System.out.println("¿Está inconsciente el herido? S/N");
        input = sc.nextLine();
        if (ValidaEntrada.validaEntSN(input)) {
            pCons = true;
            gravedad=gravedad + "Nivel de consciencia baja/inestable/inconsciente\n";
        }
        System.out.println("¿Tiene problemas para respirar o vías obstruidas? S/N");
        input = sc.nextLine();
        if (ValidaEntrada.validaEntSN(input)) {
            pResp = true;
            gravedad= gravedad + "Fallo respiratorio/ Vías obstruidas\n";
        }
        System.out.println("¿Sangra abundantemente? S/N");
        input = sc.nextLine();
        if (ValidaEntrada.validaEntSN(input)) {
            pSang = true;
            gravedad= gravedad + "Sangrado abundante\n";
        }
        System.out.println("¿Ha sufrido un golpe? S/N");
        input = sc.nextLine();
        if (ValidaEntrada.validaEntSN(input)) {
            System.out.println("Indique gravedad de 1 a 5");
            input = sc.nextLine();
            try {
                if (Integer.parseInt(input) > 3) {
                    pGolp = true;
                    gravedad = gravedad + "Golpe grave de intensidad " + input + "\n";
                }
            }catch (NumberFormatException e) {
                System.out.println("Error: número no válido. Entrada por defecto a 1");
                input = "1";
            }
        }
        if (!pCons && !pResp && !pSang && !pGolp) {
            gravedad="Lesion leve que no requiere asistencia inmediata. Acuda a su centro de salud.";
        }

        //System.out.println(gravedad);
        return gravedad;

    }
}