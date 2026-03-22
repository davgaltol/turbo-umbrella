package com.emergencias.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MainGui extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Carga el fichero FXML que define la interfaz gráfica
        URL fxmlLocation = getClass().getResource("EmergencyUI.fxml");
        if (fxmlLocation == null) {
            System.err.println("Error: No se pudo encontrar el fichero EmergencyUI.fxml. Asegúrate de que está en el mismo paquete.");
            return;
        }
        Parent root = FXMLLoader.load(fxmlLocation);
        
        primaryStage.setTitle("Sistema de Alerta de Emergencias");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Este método lanza la aplicación JavaFX
        launch(args);
    }
}
