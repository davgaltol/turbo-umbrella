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
    public static boolean validaEntDNI(String entrada) { //Se valida entrada para verificar formato de DNI
        boolean ent;
        String dni= "^\\d{8}[A-Z]$";
        if (dni != null && dni.toUpperCase().matches(dni)) {
            System.out.println("Formato de DNI correcto");
            ent = true;
        }
        else{
            System.out.println("Formato de DNI incorrecto");
            ent=false;
        }
        return ent;
    }

}
