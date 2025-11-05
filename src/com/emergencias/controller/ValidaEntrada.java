package com.emergencias.controller;

//CLASE PARA VALIDAR QUE LA ENTRADA ES VALIDA

public class ValidaEntrada {
    public static boolean validaEntSN(String entrada) { //Se valida entrada para evitar otro caracter
        boolean ent;
        while (!entrada.equalsIgnoreCase("S") && (!entrada.equalsIgnoreCase("N"))) {
            System.out.println("Entrada incorrecta. Escriba S/N");
            java.util.Scanner sc = new java.util.Scanner(System.in);
            entrada = sc.nextLine();
        }
        if (entrada.equalsIgnoreCase("S")) {
            ent = true;
        } else {
            ent = false;
        }
        return ent;
    }

}
