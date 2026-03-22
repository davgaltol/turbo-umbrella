package com.emergencias.gui;

import com.emergencias.controller.EmergencyManager;
import com.emergencias.model.LocationData;
import com.emergencias.model.UserData;
import com.emergencias.model.centros.Feature;
import com.emergencias.util.CoordinateConverter;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

import java.util.Locale;

public class UIController {

    @FXML private TabPane mainTabPane;
    @FXML private Tab mapTab;
    @FXML private WebView mapWebView;
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
    private LocationData userLocation;
    private Feature nearestCenter;

    @FXML
    public void initialize() {
        handleEsHeridoCheck(null);
        mapTab.setDisable(true); // La pestaña del mapa empieza desactivada
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
        mapTab.setDisable(true); // Reseteamos la pestaña del mapa en cada alerta
        userLocation = null;
        nearestCenter = null;
        
        String gravedadCheck = construirGravedad();
        if (gravedadCheck.isEmpty()) {
            resultadoArea.setText("Por favor, seleccione al menos un tipo de herida.");
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

        resultadoArea.setText("Procesando emergencia...\n");
        alertaButton.setDisable(true);

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
                mainTabPane.getSelectionModel().select(mapTab); // Cambiamos a la pestaña del mapa
            }
        });
        emergencyTask.setOnFailed(e -> {
            Platform.runLater(() -> {
                resultadoArea.appendText("\nERROR INESPERADO: " + e.getSource().getException().getMessage());
                e.getSource().getException().printStackTrace();
            });
            alertaButton.setDisable(false);
        });

        new Thread(emergencyTask).start();
    }

    private void processManagerOutput(Object objetoRecibido) {
        if (objetoRecibido instanceof UserData) {
            resultadoArea.appendText("----------------------------------------\n");
            resultadoArea.appendText(objetoRecibido.toString() + "\n");
            resultadoArea.appendText("----------------------------------------\n");
        } else if (objetoRecibido instanceof LocationData) {
            this.userLocation = (LocationData) objetoRecibido;
            resultadoArea.appendText("Datos de ubicación del usuario recibidos.\n");
        } else if (objetoRecibido instanceof Feature) {
            this.nearestCenter = (Feature) objetoRecibido;
            resultadoArea.appendText("Datos del centro de salud recibidos.\n");
        } else {
            resultadoArea.appendText(objetoRecibido.toString() + "\n");
        }
    }

    private void loadMap() {
        double userLat = userLocation.getLatitude();
        double userLon = userLocation.getLongitude();

        double[] centerLatLon = CoordinateConverter.utmToLatLon(
            nearestCenter.getGeometry().getUtmX(),
            nearestCenter.getGeometry().getUtmY()
        );
        double centerLat = centerLatLon[0];
        double centerLon = centerLatLon[1];

        String mapUrl = String.format(Locale.US,
            "https://www.google.com/maps/dir/?api=1&origin=%f,%f&destination=%f,%f&travelmode=driving",
            userLat, userLon, centerLat, centerLon
        );

        mapWebView.getEngine().load(mapUrl);
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
