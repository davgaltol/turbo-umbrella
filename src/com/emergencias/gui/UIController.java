package com.emergencias.gui;

import com.emergencias.controller.EmergencyManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;

public class UIController {

    // Estos nombres DEBEN coincidir con los fx:id que pusiste en Scene Builder
    @FXML private CheckBox cod01Check;
    @FXML private CheckBox cod02Check;
    @FXML private CheckBox cod03Check;
    @FXML private CheckBox cod04Check;
    @FXML private CheckBox simulacionCheck;
    @FXML private Button alertaButton;
    @FXML private TextArea resultadoArea;

    private EmergencyManager manager = new EmergencyManager();

    // Este método se llama cuando se pulsa el botón
    @FXML
    private void handleGenerarAlerta(ActionEvent event) {
        // 1. Construir el string de gravedad a partir de los checkboxes
        StringBuilder gravedadBuilder = new StringBuilder("Se detecta problema grave:\n");
        boolean hayHerida = false;

        if (cod01Check.isSelected()) {
            gravedadBuilder.append("Cod01. Nivel de consciencia baja/inestable/inconsciente\n");
            hayHerida = true;
        }
        if (cod02Check.isSelected()) {
            gravedadBuilder.append("Cod02. Fallo respiratorio/Vías obstruidas\n");
            hayHerida = true;
        }
        if (cod03Check.isSelected()) {
            gravedadBuilder.append("Cod03. Sangrado abundante\n");
            hayHerida = true;
        }
        if (cod04Check.isSelected()) {
            gravedadBuilder.append("Cod04. Golpe grave de alta intensidad\n");
            hayHerida = true;
        }

        if (!hayHerida) {
            resultadoArea.setText("Por favor, seleccione al menos un tipo de herida.");
            return;
        }

        // 2. Llamar a la lógica de negocio existente
        String gravedad = gravedadBuilder.toString();
        boolean simular = simulacionCheck.isSelected();
        
        // Limpiamos el área de texto para nuevos resultados
        resultadoArea.setText("Procesando emergencia...\n");

        // Llamamos al método refactorizado del EmergencyManager
        String resultado = manager.procesarEmergencia(gravedad, simular);
        
        // 3. Mostrar el resultado en el TextArea
        resultadoArea.appendText(resultado);
    }
}
