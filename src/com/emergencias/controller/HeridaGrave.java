package com.emergencias.controller;

public class HeridaGrave extends Herida {
    public String codHerida;

    public HeridaGrave(){
        this.codHerida=super.codHerida + " grave:\n";
    }

    public void setCodHerida(int cod) {
        switch (cod) {
            case 1:{
                this.codHerida = this.codHerida + "Cod01. Nivel de consciencia baja/inestable/inconsciente\n";
            }
            break;
            case 2:{
                this.codHerida = this.codHerida + "Cod02. Fallo respiratorio/Vías obstruidas\n";
            }
            break;
            case 3:{
                this.codHerida = this.codHerida + "Cod03. Sangrado abundante\n";
            }
            break;
            case 4:{
                this.codHerida = this.codHerida + "Cod04. Golpe grave de alta intensidad\n";
            }
            break;
            default:{
                this.codHerida = "Error en inserción de datos de herida\n";
            }
        }
    }
    public String getHerida(){
        return this.codHerida;
    }


}

