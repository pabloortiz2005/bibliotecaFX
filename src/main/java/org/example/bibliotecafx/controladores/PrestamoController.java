package org.example.bibliotecafx.controladores;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.bibliotecafx.DAO.libroDAO;
import org.example.bibliotecafx.DAO.prestamoDAO;
import org.example.bibliotecafx.DAO.socioDAO;
import org.example.bibliotecafx.entities.libro;
import org.example.bibliotecafx.entities.prestamo;
import org.example.bibliotecafx.entities.socio;

import java.io.IOException;
import java.time.LocalDate;
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
    private TableColumn<prestamo, LocalDate> colFechaInicio;

    @FXML
    private TableColumn<prestamo, LocalDate> colFechaDev;

    @FXML
    private TableColumn<prestamo, String> Activo;

    @FXML
    private TextField socioBuscar;

    private final prestamoDAO prestamoDAO;

    public PrestamoController() {
        this.prestamoDAO = new prestamoDAO();
    }
    @FXML
    public void initialize() {
        // Configuración inicial de las columnas de la tabla
        colId.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getIdP()));
        colLibro.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getLibroP().getTitulo()));
        colSocio.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(
                data.getValue().getSocioP() != null ? data.getValue().getSocioP().getNombre() : "Sin Socio"));
        colFechaInicio.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getfP()));
        colFechaDev.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getfD()));
        Activo.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(
                data.getValue().isFinalizado() ? "Finalizado" : "Activo"
        ));

        // Cargar todos los préstamos al iniciar
        listarTodosLosPrestamos();
    }

    // Crear un nuevo Prestamo
    @FXML
    private void crearNuevoPrestamo() {
        // Pedir al usuario los datos del nuevo préstamo
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Añadir Nuevo Préstamo");
        dialog.setHeaderText("Introduce los datos del nuevo préstamo:");

        dialog.setContentText("Título del Libro:");
        String tituloLibro = dialog.showAndWait().orElse(null);

        dialog.setContentText("Nombre del Socio:");
        String nombreSocio = dialog.showAndWait().orElse(null);

        dialog.setContentText("Fecha de Inicio (YYYY-MM-DD):");
        String fechaInicioStr = dialog.showAndWait().orElse(null);

        dialog.setContentText("Fecha de Devolución (YYYY-MM-DD):");
        String fechaDevolucionStr = dialog.showAndWait().orElse(null);

        // Guardar en la base de datos
        try {
            // Validar el título del libro
            if (tituloLibro == null || tituloLibro.isEmpty()) {
                mostrarAlerta("Error", "El título del libro no puede estar vacío.", Alert.AlertType.ERROR);
                return;
            }

            // Validar el nombre del socio
            if (nombreSocio == null || nombreSocio.isEmpty()) {
                mostrarAlerta("Error", "El nombre del socio no puede estar vacío.", Alert.AlertType.ERROR);
                return;
            }

            // Validar y convertir las fechas
            LocalDate fechaInicio;
            LocalDate fechaDevolucion;

            try {
                fechaInicio = LocalDate.parse(fechaInicioStr);
            } catch (Exception e) {
                mostrarAlerta("Error", "La fecha de inicio no tiene el formato correcto (YYYY-MM-DD).", Alert.AlertType.ERROR);
                return;
            }

            try {
                fechaDevolucion = LocalDate.parse(fechaDevolucionStr);
                if (fechaDevolucion.isBefore(fechaInicio)) {
                    mostrarAlerta("Error", "La fecha de devolución debe ser posterior a la fecha de inicio.", Alert.AlertType.ERROR);
                    return;
                }
            } catch (Exception e) {
                mostrarAlerta("Error", "La fecha de devolución no tiene el formato correcto (YYYY-MM-DD).", Alert.AlertType.ERROR);
                return;
            }

            // Validar y buscar el libro
            libroDAO libroDao = new libroDAO();
            libro libro = libroDao.findByTitulo(tituloLibro);
            if (libro == null) {
                mostrarAlerta("Error", "No se encontró el libro con ese título.", Alert.AlertType.ERROR);
                return;
            }

            // Validar y buscar el socio
            socioDAO socioDao = new socioDAO();
            socio socio = socioDao.findByNombre(nombreSocio);
            if (socio == null) {
                mostrarAlerta("Error", "No se encontró el socio con ese nombre.", Alert.AlertType.ERROR);
                return;
            }

            // Crear el nuevo prestamo
            prestamo nuevoPrestamo = new prestamo();
            nuevoPrestamo.setLibroP(libro);
            nuevoPrestamo.setSocioP(socio);
            nuevoPrestamo.setfP(fechaInicio);
            nuevoPrestamo.setfD(fechaDevolucion);

            // Guardar el préstamo en la base de datos
            prestamoDAO prestamoDao = new prestamoDAO();
            prestamoDao.create(nuevoPrestamo);

            // Mostrar éxito
            mostrarAlerta("Éxito", "El préstamo se ha creado correctamente.", Alert.AlertType.INFORMATION);

            // Recargar la lista de préstamos
            listarTodosLosPrestamos();
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo añadir el préstamo. Detalles:\n" + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    @FXML
    private void devolverLibro() {
        // Obtener el préstamo seleccionado de la tabla
        prestamo prestamoSeleccionado = tablaPrestamo.getSelectionModel().getSelectedItem();

        if (prestamoSeleccionado == null) {

            mostrarAlerta("Advertencia", "Por favor, selecciona un préstamo para devolver.", Alert.AlertType.WARNING);
            return;
        }

        try {
            // Obtener el ID del préstamo seleccionado
            Integer idPrestamo = prestamoSeleccionado.getIdP();

            // Llamar al método devolverPrestamo del DAO
            prestamoDAO prestamoDao = new prestamoDAO();
            prestamo prestamoActualizado = prestamoDao.devolverPrestamo(idPrestamo);

            if (prestamoActualizado != null && prestamoActualizado.isFinalizado()) {

                mostrarAlerta("Éxito", "El libro del préstamo seleccionado se ha devuelto correctamente.", Alert.AlertType.INFORMATION);

                // Actualizar la lista en la tabla
                listarTodosLosPrestamos();
            } else {

                mostrarAlerta("Error", "No se pudo devolver el libro. Verifica el registro seleccionado.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {

            mostrarAlerta("Error", "Ocurrió un error al intentar devolver el libro: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
    @FXML
    private void listarTodosLosPrestamosDeUnSocio() {
        String nombreSocio = socioBuscar.getText().trim();

        try {
            if (nombreSocio.isEmpty()) {
                mostrarAlerta("Error", "Debes ingresar el nombre del socio para buscar sus préstamos.", Alert.AlertType.ERROR);
                return;
            }

            socioDAO socioDao = new socioDAO();
            socio socioEncontrado = socioDao.findByNombre(nombreSocio);

            if (socioEncontrado == null) {
                mostrarAlerta("Información", "No se encontró un socio con el nombre proporcionado.", Alert.AlertType.INFORMATION);
                return;
            }

            // Busca los préstamos vinculados al socio
            List<prestamo> prestamos = prestamoDAO.findBySocio(socioEncontrado);
            if (!prestamos.isEmpty()) {
                tablaPrestamo.getItems().setAll(prestamos);
            } else { // Si no hay préstamos, muestra un mensaje
                mostrarAlerta("Información", "No se encontraron préstamos para el socio proporcionado.", Alert.AlertType.INFORMATION);
            }
        } catch (IllegalArgumentException e) {
            mostrarAlerta("Error", "Parámetros inválidos: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        } catch (Exception e) {
            mostrarAlerta("Error", "Hubo un error al buscar los préstamos del socio. Detalles:\n" + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
    @FXML
    private void listarTodosLosPrestamosActivos() {
        try {

            List<prestamo> prestamosActivos = prestamoDAO.findAllPrestados();

            if (!prestamosActivos.isEmpty()) {

                tablaPrestamo.getItems().setAll(prestamosActivos);
            } else {

                mostrarAlerta("Información", "No se encontraron préstamos activos en este momento.", Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {

            mostrarAlerta("Error", "Hubo un error al intentar listar los préstamos activos. Detalles:\n" + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    @FXML
    private void listarTodosLosPrestamos() {
        try {
            List<prestamo> prestamos = prestamoDAO.findAll();
            tablaPrestamo.getItems().setAll(prestamos);
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudieron listar los prestamos.", Alert.AlertType.ERROR);
        }
    }


    @FXML
    public void Volver(ActionEvent event) {
        System.out.println("Botón 'volver' presionado");
        cambiarEscena("/org/example/bibliotecafx/Inicio.fxml", event);
    }

    // Método para cambiar de escena
    @FXML
    private void cambiarEscena(String rutaFXML, ActionEvent event) {
        try {
            // Cargar el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Scene nuevaEscena = new Scene(loader.load());

            // Obtener la ventana actual (Stage)
            Stage stageActual = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            // Establecer la nueva escena
            stageActual.setScene(nuevaEscena);
            stageActual.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarError("No se pudo cargar la vista solicitada: " + rutaFXML);
        }
    }

    @FXML
    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText("Ocurrió un error");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}

