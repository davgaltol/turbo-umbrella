package com.emergencias.controller;

public abstract class Herida {
    protected String codHerida;

    public Herida(){
        this.codHerida = "Se detecta problema";
    }

    public abstract void setCodHerida(int cod);

    // Nuevo método abstracto para que el compilador lo reconozca
    public abstract String getHerida();
}

