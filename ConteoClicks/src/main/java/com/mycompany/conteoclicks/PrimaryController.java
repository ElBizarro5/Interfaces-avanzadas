
package com.mycompany.conteoclicks;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;

public class PrimaryController {

    @FXML private TextArea txtClicks;
    @FXML private Button btnEsperar;
    @FXML private ProgressBar progTiempo;
    @FXML private Button btnContar;
    private int click = 0;

    @FXML
    private void initialize() {
        txtClicks.setText("0");
        progTiempo.setProgress(0);
    }

    @FXML
    private void btnContar() {
        click++;
        txtClicks.setText(String.valueOf(click));
    }

    @FXML
    private void btnEsperar() {
    progTiempo.setProgress(0);
    btnEsperar.setDisable(true);
    btnContar.setDisable(true);
    Thread hilo = new Thread(() -> {
        for (int i = 1; i <= 100; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            final double progreso = i / 100.0;
            Platform.runLater(() -> {
                progTiempo.setProgress(progreso);
            });
        }
        Platform.runLater(() -> {
            progTiempo.setProgress(1.0);
            btnEsperar.setDisable(false);
            btnContar.setDisable(false);
        });
    });
    hilo.setDaemon(true);
    hilo.start();
}
}
