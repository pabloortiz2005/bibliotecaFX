package org.example.bibliotecafx.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class InicioController {

    @FXML
    private Button btnGestionLibros;

    @FXML
    private Button btnGestionAutores;

    @FXML
    private Button btnGestionSocios;

    @FXML
    private Button btnGestionPrestamos;

    /**
     * Manejar la navegación al módulo "Gestión de Libros".
     *
     * @param event El evento de acción que disparó la navegación.
     */
    @FXML
    private void manejarGestionLibros(ActionEvent event) {
        System.out.println("Botón 'Gestion Libros' presionado");
        cambiarEscena("/org/example/bibliotecafx/LibrosP.fxml", event);
    }

    /**
     * Manejar la navegación al módulo "Gestión de Autores".
     *
     * @param event El evento de acción que disparó la navegación.
     */
    @FXML
    private void manejarGestionAutores(ActionEvent event) {
        System.out.println("Botón 'Gestion Autores' presionado");
        cambiarEscena("/org/example/bibliotecafx/AutoresP.fxml", event);
    }

    /**
     * Manejar la navegación al módulo "Gestión de Socios".
     *
     * @param event El evento de acción que disparó la navegación.
     */
    @FXML
    private void manejarGestionSocios(ActionEvent event) {
        System.out.println("Botón 'Gestion Socios' presionado");
        cambiarEscena("/org/example/bibliotecafx/SociosP.fxml", event);
    }

    /**
     * Manejar la navegación al módulo "Gestión de Préstamos".
     *
     * @param event El evento de acción que disparó la navegación.
     */
    @FXML
    private void manejarGestionPrestamos(ActionEvent event) {
        System.out.println("Botón 'Gestion Prestamos' presionado");
        cambiarEscena("/org/example/bibliotecafx/PrestamosP.fxml", event);
    }

    /**
     * Método genérico para cambiar la escena actual por una nueva.
     *
     * @param rutaFXML La ruta relativa al archivo FXML de la nueva vista, desde `resources`.
     * @param event    El evento de acción que disparó el cambio.
     */
    private void cambiarEscena(String rutaFXML, ActionEvent event) {
        try {
            // Cargar el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Scene nuevaEscena = new Scene(loader.load());

            // Obtener la ventana actual (Stage)
            Stage stageActual = (Stage) ((Button) event.getSource()).getScene().getWindow();
            // Establecer la nueva escena
            stageActual.setScene(nuevaEscena);
            stageActual.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarError("No se pudo cargar la vista solicitada: " + rutaFXML);
        }
    }

    /**
     * Método para mostrar una alerta de error.
     *
     * @param mensaje El mensaje del error a mostrar.
     */
    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText("Ocurrió un error");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}