package org.example.bibliotecafx.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class InicioController {

    // Definir los botones de la interfaz
    @FXML
    private Button btnGestionLibros;

    @FXML
    private Button btnGestionAutores;

    @FXML
    private Button btnGestionSocios;

    @FXML
    private Button btnGestionPrestamos;



    @FXML
    private void manejarGestionLibros(ActionEvent event) {
        System.out.println("Botón 'Gestion Libros' presionado");


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/LibrosP.fxml"));
            VBox vbox = loader.load();


            Scene newScene = new Scene(vbox);


            Stage currentStage = (Stage) vbox.getScene().getWindow();
            currentStage.setScene(newScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void manejarGestionAutores(ActionEvent event) {
        System.out.println("Botón 'Gestion Autores' presionado");
    }

    @FXML
    private void manejarGestionSocios(ActionEvent event) {
        System.out.println("Botón 'Gestion Socios' presionado");
    }

    @FXML
    private void manejarGestionPrestamos(ActionEvent event) {
        System.out.println("Botón 'Gestion Prestamos' presionado");
    }
}
