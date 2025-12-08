package com.emergencias.controller;

public class HeridaLeve extends Herida{
    public HeridaLeve(){
        this.codHerida=super.codHerida + " leve:\n";
    }

    public void setCodHerida(int cod) {
        switch (cod) {
            case 1:{
                this.codHerida = this.codHerida + "Dolor bajo. El daño se considera leve\n";
            }
            break;
            case 2:{
                this.codHerida = this.codHerida + "Número fuera de rango.\n";
            }
            default:{
                this.codHerida = "Error en inserción de datos de herida";
            }
        }
    }

}
