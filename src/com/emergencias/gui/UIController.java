package com.emergencias.gui;

import com.emergencias.controller.EmergencyManager;
import com.emergencias.model.UserData;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class UIController {

    @FXML private CheckBox cod01Check;
    @FXML private CheckBox cod02Check;
    @FXML private CheckBox cod03Check;
    @FXML private CheckBox cod04Check;
    @FXML private CheckBox esHeridoCheck;
    @FXML private VBox callerInfoBox;
    @FXML private TextField callerNameField;
    @FXML private TextField callerPhoneField;
    @FXML private Label dniLabel;
    @FXML private TextField dniField;
    @FXML private CheckBox simulacionCheck;
    @FXML private Button alertaButton;
    @FXML private TextArea resultadoArea;

    private EmergencyManager manager = new EmergencyManager();

    @FXML
    public void initialize() {
        handleEsHeridoCheck(null);
    }

    @FXML
    private void handleEsHeridoCheck(ActionEvent event) {
        boolean esElHerido = esHeridoCheck.isSelected();
        callerInfoBox.setVisible(!esElHerido);
        callerInfoBox.setManaged(!esElHerido);
        dniLabel.setText(esElHerido ? "Mi DNI:" : "DNI del Herido:");
        if (esElHerido) {
            cod01Check.setSelected(false);
            cod01Check.setDisable(true);
        } else {
            cod01Check.setDisable(false);
        }
    }

    @FXML
    private void handleGenerarAlerta(ActionEvent event) {
        resultadoArea.clear();
        
        String gravedadCheck = construirGravedad();
        if (gravedadCheck.isEmpty()) {
            resultadoArea.setText("Por favor, seleccione al menos un tipo de herida.");
            return;
        }
        
        // --- SOLUCIÓN AL ERROR "effectively final" ---
        // 1. Preparamos las variables
        UserData datosLlamanteTemp;
        if (esHeridoCheck.isSelected()) {
            datosLlamanteTemp = null;
        } else {
            datosLlamanteTemp = new UserData();
            datosLlamanteTemp.setNombre(callerNameField.getText());
            datosLlamanteTemp.setTelefono(callerPhoneField.getText());
        }

        // 2. Creamos copias 'final' de todas las variables que usará el hilo
        final String gravedad = gravedadCheck;
        final UserData datosLlamante = datosLlamanteTemp;
        final String dniHerido = dniField.getText().toUpperCase();
        final boolean simular = simulacionCheck.isSelected();
        // -----------------------------------------

        resultadoArea.setText("Procesando emergencia...\n");
        alertaButton.setDisable(true);

        Task<Void> emergencyTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // 3. Usamos las variables 'final' dentro de la tarea
                manager.procesarEmergencia(
                        gravedad,
                        datosLlamante,
                        dniHerido,
                        simular,
                        (mensaje) -> Platform.runLater(() -> resultadoArea.appendText(mensaje + "\n"))
                );
                return null;
            }
        };

        emergencyTask.setOnSucceeded(e -> alertaButton.setDisable(false));
        emergencyTask.setOnFailed(e -> {
            Platform.runLater(() -> {
                resultadoArea.appendText("\nERROR INESPERADO: " + e.getSource().getException().getMessage());
                e.getSource().getException().printStackTrace();
            });
            alertaButton.setDisable(false);
        });

        new Thread(emergencyTask).start();
    }

    private String construirGravedad() {
        StringBuilder gravedadBuilder = new StringBuilder();
        if (cod01Check.isSelected()) gravedadBuilder.append("Cod01. Nivel de consciencia baja/inestable/inconsciente\n");
        if (cod02Check.isSelected()) gravedadBuilder.append("Cod02. Fallo respiratorio/Vías obstruidas\n");
        if (cod03Check.isSelected()) gravedadBuilder.append("Cod03. Sangrado abundante\n");
        if (cod04Check.isSelected()) gravedadBuilder.append("Cod04. Golpe grave de alta intensidad\n");

        return gravedadBuilder.length() > 0 ? "Se detecta problema grave:\n" + gravedadBuilder.toString() : "";
    }
}
