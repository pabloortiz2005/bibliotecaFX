package org.example.bibliotecafx.controladores;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import org.example.bibliotecafx.DAO.libroDAO;
import org.example.bibliotecafx.DAO.prestamoDAO;
import org.example.bibliotecafx.DAO.socioDAO;
import org.example.bibliotecafx.entities.libro;
import org.example.bibliotecafx.entities.prestamo;
import org.example.bibliotecafx.entities.socio;

import java.io.IOException;
import java.util.List;

public class PrestamoController {
    @FXML
    private TableView<prestamo> tablaPrestamo;

    @FXML
    private TableColumn<prestamo, Integer> colId;
    @FXML
    private TableColumn<prestamo, String> colLibro;
    @FXML
    private TableColumn<prestamo, String> colSocio;
    @FXML
    private TableColumn<prestamo, String> colFechaInicio;
    @FXML
    private TableColumn<prestamo, String> colFechaDev;
    @FXML
    private TableColumn<prestamo, String> Activo;

    @FXML
    private TextField socioBuscar;

    private final prestamoDAO prestamoDAO = new prestamoDAO();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getIdP()));
        colLibro.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getLibroP().getTitulo()));
        colSocio.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getSocioP().getNombre()));
        colFechaInicio.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getfP()));
        colFechaDev.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getfD()));
        Activo.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().isFinalizado() ? "Finalizado" : "Activo"));

        listarTodosLosPrestamos();
    }

    @FXML
    private void listarTodosLosPrestamos() {
        List<prestamo> prestamos = prestamoDAO.findAll();
        tablaPrestamo.getItems().setAll(prestamos);
    }

    @FXML
    private void listarTodosLosPrestamosActivos() {
        List<prestamo> prestamos = prestamoDAO.findAllPrestados();
        tablaPrestamo.getItems().setAll(prestamos);
    }

    @FXML
    private void listarTodosLosPrestamosDeUnSocio() {
        String nombreSocio = socioBuscar.getText().trim();
        if (nombreSocio.isEmpty()) {
            mostrarAlerta("Advertencia", "Por favor, ingrese el nombre del socio.", AlertType.WARNING);
            return;
        }

        socioDAO socioDAO = new socioDAO();
        List<socio> sociosEncontrados = socioDAO.findByNombre(nombreSocio);

        if (sociosEncontrados.isEmpty()) {
            mostrarAlerta("Error", "No se encontró un socio con ese nombre.", AlertType.ERROR);
            return;
        }

        if (sociosEncontrados.size() > 1) {
            mostrarAlerta("Advertencia", "Se encontraron múltiples socios con ese nombre. Especifique mejor.", AlertType.WARNING);
            return;
        }

        socio socioEncontrado = sociosEncontrados.get(0);
        List<prestamo> prestamos = prestamoDAO.findBySocio(socioEncontrado);
        tablaPrestamo.getItems().setAll(prestamos);
    }

    @FXML
    private void devolverLibro() {
        prestamo prestamoSeleccionado = tablaPrestamo.getSelectionModel().getSelectedItem();
        if (prestamoSeleccionado == null) {
            mostrarAlerta("Advertencia", "Selecciona un préstamo para devolver.", AlertType.WARNING);
            return;
        }

        prestamoDAO.devolverPrestamo(prestamoSeleccionado.getIdP());
        listarTodosLosPrestamos();
        mostrarAlerta("Éxito", "El libro ha sido devuelto correctamente.", AlertType.INFORMATION);
    }

    @FXML
    private void crearNuevoPrestamo() {
        try {
            // Pedir al usuario los datos del préstamo
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Añadir Nuevo Préstamo");
            dialog.setHeaderText("Introduce los datos del nuevo préstamo:");

            dialog.setContentText("Fecha de inicio (YYYY-MM-DD):");
            String fechaInicio = dialog.showAndWait().orElse(null);

            dialog.setContentText("Fecha de devolución (YYYY-MM-DD):");
            String fechaDevolucion = dialog.showAndWait().orElse(null);

            dialog.setContentText("Nombre del Socio:");
            String nombreSocio = dialog.showAndWait().orElse(null);

            dialog.setContentText("Título del Libro:");
            String tituloLibro = dialog.showAndWait().orElse(null);

            // Validaciones básicas
            if (fechaInicio == null || fechaDevolucion == null || nombreSocio == null || tituloLibro == null ||
                    fechaInicio.isEmpty() || fechaDevolucion.isEmpty() || nombreSocio.isEmpty() || tituloLibro.isEmpty()) {
                mostrarAlerta("Error", "Todos los campos deben estar completos.", Alert.AlertType.ERROR);
                return;
            }

            // Buscar el socio por nombre
            socioDAO socioDAO = new socioDAO();
            List<socio> sociosEncontrados = socioDAO.findByNombre(nombreSocio);

            if (sociosEncontrados.isEmpty()) {
                mostrarAlerta("Error", "No se encontró un socio con ese nombre.", Alert.AlertType.ERROR);
                return;
            }
            if (sociosEncontrados.size() > 1) {
                mostrarAlerta("Advertencia", "Se encontraron múltiples socios con ese nombre. Especifique mejor.", Alert.AlertType.WARNING);
                return;
            }
            socio socioEncontrado = sociosEncontrados.getFirst();

            // Buscar el libro por título
            libroDAO libroDAO = new libroDAO();
            List<libro> librosEncontrados = libroDAO.findByTitulo(tituloLibro);

            if (librosEncontrados.isEmpty()) {
                mostrarAlerta("Error", "No se encontró un libro con ese título.", Alert.AlertType.ERROR);
                return;
            }
            if (librosEncontrados.size() > 1) {
                mostrarAlerta("Advertencia", "Se encontraron múltiples libros con ese título. Especifique mejor.", Alert.AlertType.WARNING);
                return;
            }
            libro libroEncontrado = librosEncontrados.getFirst();

            // Crear y guardar el nuevo préstamo
            prestamo nuevoPrestamo = new prestamo(fechaDevolucion, fechaInicio, socioEncontrado, libroEncontrado, false);
            prestamoDAO prestamoDAO = new prestamoDAO();
            prestamo resultado = prestamoDAO.create(nuevoPrestamo);

            if (resultado != null) {
                mostrarAlerta("Éxito", "Préstamo creado correctamente.", Alert.AlertType.INFORMATION);
                listarTodosLosPrestamos();
            } else {
                mostrarAlerta("Error", "No se pudo crear el préstamo.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al crear el préstamo:\n" + e.getMessage(), Alert.AlertType.ERROR);
        }
    }



    private void mostrarAlerta(String titulo, String mensaje, AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    @FXML
    public void Volver(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/bibliotecafx/Inicio.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la pantalla de inicio.", AlertType.ERROR);
        }
    }
}
