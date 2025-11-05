package com.emergencias.main;

import com.emergencias.controller.EmergencyManager;

//CLASE PRINCIPAL

public class Main {

    public static void main(String[] args) {
        EmergencyManager manager = new EmergencyManager();
        manager.startSystem();
    }
}
