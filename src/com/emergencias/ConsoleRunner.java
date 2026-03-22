package com.emergencias;

import com.emergencias.controller.EmergencyManager;
import com.emergencias.model.UserData;
import java.util.Scanner;
import java.util.function.Consumer;

public class ConsoleRunner {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EmergencyManager manager = new EmergencyManager();

        System.out.println("--- MODO CONSOLA DE EMERGENCIAS GRAVES ---");

        // 1. Construir el string de gravedad
        StringBuilder gravedadBuilder = new StringBuilder("Se detecta problema grave:\n");
        boolean hayHerida = false;

        System.out.println("Seleccione los tipos de herida (separe con comas, ej: 1,3):");
        System.out.println("1. Inconsciencia (Cod01)");
        System.out.println("2. Fallo Respiratorio (Cod02)");
        System.out.println("3. Sangrado Abundante (Cod03)");
        System.out.println("4. Golpe Grave (Cod04)");
        System.out.print("Su selección: ");
        
        String[] seleccion = sc.nextLine().split(",");

        for (String s : seleccion) {
            switch (s.trim()) {
                case "1":
                    gravedadBuilder.append("Cod01. Nivel de consciencia baja/inestable/inconsciente\n");
                    hayHerida = true;
                    break;
                case "2":
                    gravedadBuilder.append("Cod02. Fallo respiratorio/Vías obstruidas\n");
                    hayHerida = true;
                    break;
                case "3":
                    gravedadBuilder.append("Cod03. Sangrado abundante\n");
                    hayHerida = true;
                    break;
                case "4":
                    gravedadBuilder.append("Cod04. Golpe grave de alta intensidad\n");
                    hayHerida = true;
                    break;
            }
        }

        if (!hayHerida) {
            System.out.println("No se seleccionó ninguna herida válida. Saliendo.");
            return;
        }
        String gravedad = gravedadBuilder.toString();

        // 2. Recopilar datos de llamante y herido
        System.out.print("\n¿Es usted el herido/a? (S/N): ");
        boolean esElHerido = sc.nextLine().equalsIgnoreCase("S");

        UserData datosLlamante = new UserData();
        if (esElHerido) {
            datosLlamante = null; // Marcador para que EmergencyManager sepa que son la misma persona
        } else {
            System.out.print("Introduzca su nombre como informante: ");
            datosLlamante.setNombre(sc.nextLine());
            System.out.print("Introduzca su teléfono de contacto: ");
            datosLlamante.setTelefono(sc.nextLine());
        }

        System.out.print(esElHerido ? "Introduzca su DNI: " : "Introduzca el DNI del herido: ");
        String dniHerido = sc.nextLine().toUpperCase();

        // 3. Preguntar por la simulación
        System.out.print("¿Desea simular falta de cobertura? (S/N): ");
        boolean simular = sc.nextLine().equalsIgnoreCase("S");

        // 4. Definir el consumidor de salida para la consola
        Consumer<String> consoleOutput = System.out::println;

        // 5. Llamar a la lógica de negocio
        System.out.println("\nProcesando emergencia grave...");
        manager.procesarEmergencia(gravedad, datosLlamante, dniHerido, simular, consoleOutput);
    }
}
