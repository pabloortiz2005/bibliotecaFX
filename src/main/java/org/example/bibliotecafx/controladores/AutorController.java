package org.example.bibliotecafx.controladores;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import org.example.bibliotecafx.DAO.autorDAO;
import org.example.bibliotecafx.DAO.libroDAO;
import org.example.bibliotecafx.entities.autor;
import org.example.bibliotecafx.entities.libro;

import java.util.List;

public class AutorController {

    @FXML
    private TableView<autor> tablaAutores;

    @FXML
    private TableColumn<autor, Integer> colId;

    @FXML
    private TableColumn<autor, String> colNombre;

    @FXML
    private TableColumn<autor, String> colNacionalidad;

    @FXML
    public TextField nombreBuscar;

    private final autorDAO autorDAO;

    public AutorController() {
        this.autorDAO = new autorDAO();
    }

    @FXML
    public void initialize() {
        // Configuración inicial de la tabla
        colId.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getIdA()));
        colNombre.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getNombre()));
        colNacionalidad.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getNacionalidad()));
        listarTodosLosAutores(); // Cargar todos los libros al iniciar
    }

    // Crear un nuevo autor
    @FXML
    private void crearNuevoAutor() {
        // Pedir al usuario los datos del autor
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Añadir Nuevo Autor");
        dialog.setHeaderText("Introduce los datos del nuevo Autor:");

        dialog.setContentText("Nombre:");
        String nombre = dialog.showAndWait().orElse(null);

        dialog.setContentText("Nacionalidad:");
        String nacionalidad = dialog.showAndWait().orElse(null);
        // Guardar en la base de datos
        try {
            autor nuevoAutor = new autor();
            nuevoAutor.setNombre(nombre);
            nuevoAutor.setNacionalidad(nacionalidad);

            autorDAO.create(nuevoAutor);
            listarTodosLosAutores();
            mostrarAlerta("Éxito", "El Autor ha sido añadido correctamente.", AlertType.INFORMATION);
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo añadir el Autor. Detalles:\n" + e.getMessage(), AlertType.ERROR);
        }
    }

    // Buscar libros por nombre
    @FXML
    private void buscarAutor() {
        String nombre = nombreBuscar.getText().trim();
        autor autor = new autor();
        try {
            autor = autorDAO.findByNombre(nombre);
            if (autor != null) {
                tablaAutores.getItems().setAll(autor);
            } else {
                mostrarAlerta("Información", "No se encontraron autores con los criterios proporcionados.", AlertType.INFORMATION);
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Hubo un error al buscar autores. Detalles:\n" + e.getMessage(), AlertType.ERROR);
        }
    }


    // Editar un autor seleccionado
    @FXML
    private void editarAutorSeleccionado() {

        TextInputDialog dialog = new TextInputDialog();

        autor autorSeleccionado = tablaAutores.getSelectionModel().getSelectedItem();

        if (autorSeleccionado == null) {
            mostrarAlerta("Advertencia", "Selecciona un autor para editar.", AlertType.WARNING);
            return;
        }

        dialog.setContentText("Nombre:");
        String nombre = dialog.showAndWait().orElse(null);

        dialog.setContentText("Nacionalidad:");
        String nacionalidad = dialog.showAndWait().orElse(null);
        // Editar el libro
        autorDAO.ChangeAutor(autorSeleccionado,nombre,nacionalidad);
        listarTodosLosAutores();
    }

    // Eliminar un autor seleccionado
    @FXML
    private void eliminarAutorSeleccionado() {
        autor autorSeleccionado = tablaAutores.getSelectionModel().getSelectedItem();

        if (autorSeleccionado == null) {
            mostrarAlerta("Advertencia", "Selecciona un autor para eliminar.", AlertType.WARNING);
            return;
        }

        try {
            autorDAO.deleteById(autorSeleccionado.getIdA());
            tablaAutores.getItems().remove(autorSeleccionado);
            mostrarAlerta("Éxito", "El autor ha sido eliminado correctamente.", AlertType.INFORMATION);
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo eliminar el autor. Detalles:\n" + e.getMessage(), AlertType.ERROR);
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
    private void listarTodosLosAutores() {
        try {
            List<autor> autor = autorDAO.findAll();
            tablaAutores.getItems().setAll(autor);
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudieron listar los autores.", AlertType.ERROR);
        }
    }
}