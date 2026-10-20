
package com.mycompany.carrera;

import java.util.concurrent.atomic.AtomicBoolean;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;

public class PrimaryController {

    @FXML private Button btnIniciar;
    @FXML private TextArea txtLiebre;
    @FXML private TextArea txtTortuga;
    @FXML private ProgressBar progLiebre;
    @FXML private ProgressBar progTortuga;
    private boolean carreraIniciada = false;

    @FXML
    private void initialize() {
        txtLiebre.setText("¡Está corriendo!");
        txtTortuga.setText("¡Está corriendo!");
        progLiebre.setProgress(0);
        progTortuga.setProgress(0);
    }

    @FXML
    private void btnIniciar() {
        if (carreraIniciada) {
            return;
        }
        carreraIniciada = true;
        btnIniciar.setDisable(true);
        progLiebre.setProgress(0);
        progTortuga.setProgress(0);
        txtLiebre.setText("¡Está corriendo!");
        txtTortuga.setText("¡Está corriendo!");
        AtomicBoolean carreraTerminada = new AtomicBoolean(false);
        Thread liebre = new Thread(() -> {
            double avance = 0;
            while (avance < 1 && !carreraTerminada.get()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                avance += 0.025;
                if (avance >= 0.30) {
                    avance = 0.30;
                    Platform.runLater(() ->
                        txtLiebre.setText("¡Se durmió!")
                    );
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                    Platform.runLater(() ->
                        txtLiebre.setText("¡Despertó y sigue corriendo!")
                    );
                    while (avance < 1 && !carreraTerminada.get()) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                        avance += 0.025;
                        double progreso = Math.min(avance, 1.0);
                        Platform.runLater(() -> {
                            if (!carreraTerminada.get()) {
                                progLiebre.setProgress(progreso);
                            }
                        });
                        if (progreso >= 1) {
                            if (carreraTerminada.compareAndSet(false, true)) {
                                Platform.runLater(() -> {
                                    txtLiebre.setText("¡La liebre llegó a la meta!");
                                    txtTortuga.setText("La carrera ha terminado.");
                                    btnIniciar.setDisable(false);
                                    carreraIniciada = false;
                                });
                            }
                        }
                    }

                    return;
                }
                double progreso = avance;
                Platform.runLater(() ->
                    progLiebre.setProgress(progreso)
                );
            }
        });
        Thread tortuga = new Thread(() -> {
            double avance = 0;
            while (avance < 1 && !carreraTerminada.get()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                avance += 0.015;
                double progreso = Math.min(avance, 1.0);
                Platform.runLater(() -> {
                    if (!carreraTerminada.get()) {
                        progTortuga.setProgress(progreso);
                    }
                });
                if (progreso >= 1) {
                    if (carreraTerminada.compareAndSet(false, true)) {
                        Platform.runLater(() -> {
                            progTortuga.setProgress(1);
                            txtTortuga.setText("¡La tortuga ganó la carrera!");
                            txtLiebre.setText("La liebre se quedó dormida y perdió.");
                            btnIniciar.setDisable(false);
                            carreraIniciada = false;
                        });
                    }

                    return;
                }
            }
        });

        liebre.setDaemon(true);
        tortuga.setDaemon(true);
        liebre.start();
        tortuga.start();
    }
}
