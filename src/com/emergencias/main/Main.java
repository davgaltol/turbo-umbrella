package com.emergencias.main;

import com.emergencias.controller.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("***************************************************");
        System.out.println("***BIENVENIDO AL SISTEMA DE ALERTA DE EMERGENCIA***");
        System.out.println("***************************************************");
        System.out.println("Escriba la letra 'E' si hay una emergencia o 'Q' para salir");

        String opcion = sc.nextLine();

        if (opcion.equalsIgnoreCase("E")) {
            System.out.println("Nos indica que hay una emergencia. Pasando a toma de datos...");
            System.out.println("***************************************************");
            System.out.println("********SISTEMA DE VERIFICACIÓN DE GRAVEDAD********");
            System.out.println("***************************************************");

            System.out.println("¿Está inconsciente el herido? S/N");
            boolean inconsciente = sc.nextLine().equalsIgnoreCase("S");

            System.out.println("¿Tiene problemas para respirar o vías obstruidas? S/N");
            boolean vias = sc.nextLine().equalsIgnoreCase("S");

            System.out.println("¿Sangra abundantemente? S/N");
            boolean sangrado = sc.nextLine().equalsIgnoreCase("S");

            System.out.println("¿Ha sufrido un golpe? S/N");
            boolean golpe = sc.nextLine().equalsIgnoreCase("S");

            Herida herida;
            int cod = 0;

            if (inconsciente || vias || sangrado || golpe) {
                herida = new HeridaGrave();
                if (inconsciente) cod = 1;
                else if (vias) cod = 2;
                else if (sangrado) cod = 3;
                else if (golpe) cod = 4;
            } else {
                herida = new HeridaLeve();
                cod = 1; // golpe leve por defecto
            }

            herida.setCodHerida(cod);
            System.out.println(herida.getHerida());

            // ======= INTEGRACIÓN DE UBICACIÓN Y CENTRO DE SALUD =======
            Location location = new Location();
            String ubicacion = location.getLocationFromAPI();
            System.out.println("Ubicación detectada: " + ubicacion);

            // Ya dentro de Location se llama a centroCercano(), que imprime el centro más cercano
            System.out.println("******************************************************");
            System.out.println("***¡Datos de la emergencia procesados correctamente!***");
            System.out.println("******************************************************");

        } else {
            System.out.println("No hay emergencia. Saliendo del programa.");
        }

        sc.close();
    }
}
