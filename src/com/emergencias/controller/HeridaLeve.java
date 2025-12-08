package com.emergencias.controller;

public class HeridaLeve extends Herida{
    public HeridaLeve(){
        this.codHerida=super.codHerida + " leve:\n";
    }

    public void setCodHerida(int cod) {
        switch (cod) {
            case 1:{
                this.codHerida = this.codHerida + "\"Dolor bajo o número fuera de rango. El daño se considera leve\n";
            }
            break;
            default:{
                this.codHerida = "Error en inserción de datos de herida";
            }
        }
    }

}
