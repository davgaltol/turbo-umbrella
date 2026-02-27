package com.emergencias.controller;

public class HeridaLeve extends Herida {
    public HeridaLeve(){
        this.codHerida = super.codHerida + " leve:\n";
    }

    public void setCodHerida(int cod) {
        switch (cod) {
            case 1:
                this.codHerida += "Herida por golpe de baja intensidad\n";
                break;
            case 2:
                this.codHerida += "Número fuera de rango.\n";
                break;
            default:
                this.codHerida = "Err01: Error en inserción de datos de herida";
        }
    }

    public String getHerida(){
        return this.codHerida;
    }
}
