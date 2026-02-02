package com.emergencias.model.centros;

import com.google.gson.annotations.SerializedName;

public class Properties {
    @SerializedName("CEN_DESCLA")
    private String nombre;

    @SerializedName("CEN_NOMBCA")
    private String calle;

    @SerializedName("CEN_NUMCAL")
    private String numero;

    @SerializedName("CEN_CODPOS")
    private String codigoPostal;

    @SerializedName("CEN_LOCA")
    private String localidadCodigo;

    // Getters
    public String getNombre() { return nombre; }
    public String getDireccionCompleta() { 
        String num = (numero == null) ? "S/N" : numero;
        return calle + ", " + num + " (" + codigoPostal + ")"; 
    }
}
