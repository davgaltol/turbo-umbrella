package com.emergencias.alert;

import com.emergencias.model.EmergencyEvent;
//ENVIO DE ALERTA Y ALMACENAMIENTO DE DATOS
public class AlertSender {

    private final String[] datosHerido;
    private final String[] datosUsuario;
    private final String gravedad;

    public AlertSender(String[] datosHerido, String[] datosUsuario, String gravedad) {
        this.datosHerido = datosHerido;
        this.datosUsuario = datosUsuario;
        this.gravedad=gravedad;
    }
    
    public void sendAlert(EmergencyEvent event){

            System.out.println("Enviar evento a 112. COMPLETAR ESTE MÉTODO");
        
    }
    
}
