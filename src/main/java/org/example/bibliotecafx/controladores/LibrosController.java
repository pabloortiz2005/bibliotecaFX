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

public class LibrosController {

    @FXML
    private TableView<libro> tablaLibros;

    @FXML
    private TableColumn<libro, Integer> colId;

    @FXML
    private TableColumn<libro, String> colTitulo;

    @FXML
    private TableColumn<libro, String> colAutor;

    @FXML
    private TableColumn<libro, String> colISBN;

    @FXML
    private TableColumn<libro, String> colEditorial;

    @FXML
    private TableColumn<libro, Integer> colAnyo;

    @FXML
    private TextField tituloBuscar;

    @FXML
    private TextField autorBuscar;

    @FXML
    private TextField isbnBuscar;

    private final libroDAO libroDAO;

    public LibrosController() {
        this.libroDAO = new libroDAO();
    }

    @FXML
    public void initialize() {
        // Configuración inicial de la tabla
        colId.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getIdL()));
        colTitulo.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getTitulo()));
        colAutor.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getAutor() != null
                ? data.getValue().getAutor().getNombre() : "Sin Autor"));
        colISBN.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getISBN()));
        colEditorial.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getEditorial()));
        colAnyo.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getAnyoPub()));

        listarTodosLosLibros(); // Cargar todos los libros al iniciar
    }

    // Crear un nuevo libro
    @FXML
    private void crearNuevoLibro() {
        // Pedir al usuario los datos del libro
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Añadir Nuevo Libro");
        dialog.setHeaderText("Introduce los datos del nuevo libro:");

        dialog.setContentText("Título:");
        String titulo = dialog.showAndWait().orElse(null);

        dialog.setContentText("ISBN:");
        String isbn = dialog.showAndWait().orElse(null);

        dialog.setContentText("Editorial:");
        String editorial = dialog.showAndWait().orElse(null);

        dialog.setContentText("Año de Publicación:");
        Integer anioPublicacion = Integer.parseInt(dialog.showAndWait().orElse("2023"));

        dialog.setContentText("Nombre del Autor:");
        String autorNombre = dialog.showAndWait().orElse(null);

        // Guardar en la base de datos
        try {
            libro nuevoLibro = new libro();
            nuevoLibro.setTitulo(titulo);
            nuevoLibro.setISBN(isbn);
            nuevoLibro.setEditorial(editorial);
            nuevoLibro.setAnyoPub(anioPublicacion);

            if (autorNombre != null && !autorNombre.isEmpty()) {
                autorDAO autorDAO = new autorDAO();
                autor autor = autorDAO.findByNombre(autorNombre);

                if (autor == null) {
                    autor = new autor();
                    autor.setNombre(autorNombre);
                    autorDAO.create(autor);
                }
                nuevoLibro.setAutor(autor);
            }

            libroDAO.create(nuevoLibro);
            listarTodosLosLibros();
            mostrarAlerta("Éxito", "El libro ha sido añadido correctamente.", AlertType.INFORMATION);
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo añadir el libro. Detalles:\n" + e.getMessage(), AlertType.ERROR);
        }
    }

    // Buscar libros por criterios de búsqueda: título, autor o ISBN
    @FXML
    private void buscarLibro() {
        String titulo = tituloBuscar.getText().trim();
        String autor = autorBuscar.getText().trim();
        String isbn = isbnBuscar.getText().trim();

        try {
            List<libro> libros = libroDAO.buscarLibro(titulo, autor, isbn);
            if (!libros.isEmpty()) {
                tablaLibros.getItems().setAll(libros);
            } else {
                mostrarAlerta("Información", "No se encontraron libros con los criterios proporcionados.", AlertType.INFORMATION);
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Hubo un error al buscar libros. Detalles:\n" + e.getMessage(), AlertType.ERROR);
        }
    }

    // Listar libros disponibles que no han sido prestados
    @FXML
    private void listarLibrosDisponibles() {
        try {
            List<libro> libros = libroDAO.findAll();
            tablaLibros.getItems().setAll(libros);
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudieron listar los libros.", AlertType.ERROR);
        }
    }

    // Editar un libro seleccionado
    @FXML
    private void editarLibroSeleccionado() {
        libro libroSeleccionado = tablaLibros.getSelectionModel().getSelectedItem();

        if (libroSeleccionado == null) {
            mostrarAlerta("Advertencia", "Selecciona un libro para editar.", AlertType.WARNING);
            return;
        }

        // Editar el libro
        crearNuevoLibro(); // Reutilizamos
    }

    // Eliminar un libro seleccionado
    @FXML
    private void eliminarLibroSeleccionado() {
        libro libroSeleccionado = tablaLibros.getSelectionModel().getSelectedItem();

        if (libroSeleccionado == null) {
            mostrarAlerta("Advertencia", "Selecciona un libro para eliminar.", AlertType.WARNING);
            return;
        }

        try {
            libroDAO.deleteById(libroSeleccionado.getIdL());
            tablaLibros.getItems().remove(libroSeleccionado);
            mostrarAlerta("Éxito", "El libro ha sido eliminado correctamente.", AlertType.INFORMATION);
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo eliminar el libro. Detalles:\n" + e.getMessage(), AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void listarTodosLosLibros() {
        try {
            List<libro> libros = libroDAO.findAll();
            tablaLibros.getItems().setAll(libros);
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudieron listar los libros.", AlertType.ERROR);
        }
    }
}