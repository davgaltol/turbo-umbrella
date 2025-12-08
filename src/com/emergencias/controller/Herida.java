package com.emergencias.controller;

abstract class Herida {
    protected String codHerida;

    public Herida(){
        this.codHerida="Se detecta problema";
    }
    public abstract void setCodHerida(int cod);
}
