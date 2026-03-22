package com.emergencias.gui;

import com.emergencias.controller.EmergencyManager;
import com.emergencias.model.LocationData;
import com.emergencias.model.UserData;
import com.emergencias.model.centros.Feature;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class UIController {

    // Pestañas
    @FXML private TabPane mainTabPane;
    @FXML private Tab resultsTab;
    @FXML private Tab mapTab;

    // Pestaña 1: Generar Alerta
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

    // Pestaña 2: Resultados
    @FXML private Label dniStatusLabel;
    @FXML private TextArea injuredDataArea;
    @FXML private TextArea callerDataArea;
    @FXML private Label locationLabel;
    @FXML private Label centerLabel;
    @FXML private TextArea logArea;

    // Pestaña 3: Mapa
    @FXML private WebView mapWebView;

    private EmergencyManager manager = new EmergencyManager();
    private LocationData userLocation;
    private Feature nearestCenter;

    @FXML
    public void initialize() {
        handleEsHeridoCheck(null);
        resultsTab.setDisable(true);
        mapTab.setDisable(true);
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
        clearResultsTab();
        
        String gravedadCheck = construirGravedad();
        if (gravedadCheck.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText("No se ha seleccionado ninguna urgencia");
            alert.setContentText("Por favor, seleccione al menos un tipo de urgencia grave para poder generar la alerta.");
            alert.showAndWait();
            return;
        }
        
        UserData datosLlamanteTemp;
        if (esHeridoCheck.isSelected()) {
            datosLlamanteTemp = null;
        } else {
            datosLlamanteTemp = new UserData();
            datosLlamanteTemp.setNombre(callerNameField.getText());
            datosLlamanteTemp.setTelefono(callerPhoneField.getText());
        }

        final String gravedad = gravedadCheck;
        final UserData datosLlamante = datosLlamanteTemp;
        final String dniHerido = dniField.getText().toUpperCase();
        final boolean simular = simulacionCheck.isSelected();

        alertaButton.setDisable(true);
        resultsTab.setDisable(false);
        mainTabPane.getSelectionModel().select(resultsTab);
        dniStatusLabel.setText("Procesando emergencia...");

        Task<Void> emergencyTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                manager.procesarEmergencia(
                        gravedad,
                        datosLlamante,
                        dniHerido,
                        simular,
                        (objetoRecibido) -> Platform.runLater(() -> processManagerOutput(objetoRecibido))
                );
                return null;
            }
        };

        emergencyTask.setOnSucceeded(e -> {
            alertaButton.setDisable(false);
            if (userLocation != null && nearestCenter != null) {
                loadMap();
                mapTab.setDisable(false);
            }
        });
        emergencyTask.setOnFailed(e -> {
            Platform.runLater(() -> {
                dniStatusLabel.setTextFill(Color.RED);
                dniStatusLabel.setText("ERROR INESPERADO");
                logArea.setText(e.getSource().getException().getMessage());
                e.getSource().getException().printStackTrace();
            });
            alertaButton.setDisable(false);
        });

        new Thread(emergencyTask).start();
    }

    private void processManagerOutput(Object objetoRecibido) {
        if (objetoRecibido instanceof UserData) {
            UserData data = (UserData) objetoRecibido;
            dniStatusLabel.setTextFill(Color.GREEN);
            dniStatusLabel.setText("DNI Encontrado en BBDD");
            injuredDataArea.setText(data.toString());
            if (esHeridoCheck.isSelected()) {
                callerDataArea.setText(data.toString());
            }
        } else if (objetoRecibido instanceof LocationData) {
            this.userLocation = (LocationData) objetoRecibido;
            locationLabel.setText(userLocation.getFormattedLocation());
        } else if (objetoRecibido instanceof Feature) {
            this.nearestCenter = (Feature) objetoRecibido;
            centerLabel.setText(nearestCenter.getProperties().getNombre() + "\n" + nearestCenter.getProperties().getDireccionCompleta());
        } else if (objetoRecibido instanceof String) {
            String mensaje = (String) objetoRecibido;
            if (mensaje.contains("DNI del herido no encontrado")) {
                dniStatusLabel.setTextFill(Color.ORANGE);
                dniStatusLabel.setText("DNI No Encontrado en BBDD");
            }
            logArea.appendText(mensaje + "\n");
        }
        
        if (!esHeridoCheck.isSelected()) {
            callerDataArea.setText("Nombre: " + callerNameField.getText() + "\nTeléfono: " + callerPhoneField.getText());
        }
    }
    
    private void clearResultsTab() {
        resultsTab.setDisable(true);
        mapTab.setDisable(true);
        userLocation = null;
        nearestCenter = null;

        dniStatusLabel.setText("-");
        dniStatusLabel.setTextFill(Color.BLACK);
        injuredDataArea.clear();
        callerDataArea.clear();
        locationLabel.setText("-");
        centerLabel.setText("-");
        logArea.clear();
    }

    private void loadMap() {
        try {
            String origin = userLocation.getLatitude() + "," + userLocation.getLongitude();
            String destination = URLEncoder.encode(nearestCenter.getProperties().getDireccionCompleta(), StandardCharsets.UTF_8.toString());
            String mapUrl = String.format(Locale.US,
                "https://www.google.com/maps/dir/?api=1&origin=%s&destination=%s&travelmode=driving&g_ep=1",
                origin, destination
            );
            mapWebView.getEngine().load(mapUrl);
        } catch (Exception e) {
            logArea.appendText("\nError al generar la URL del mapa: " + e.getMessage());
        }
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
