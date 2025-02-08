package org.example.bibliotecafx.controladores;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import org.example.bibliotecafx.DAO.autorDAO;
import org.example.bibliotecafx.DAO.libroDAO;
import org.example.bibliotecafx.entities.autor;
import org.example.bibliotecafx.entities.libro;

import java.io.IOException;
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

            if (titulo.isEmpty() && autor.isEmpty() && isbn.isEmpty()) {
                mostrarAlerta("Error", "Debes rellenar al menos un campo para realizar una búsqueda.", Alert.AlertType.ERROR);
                return;
            }


            List<libro> libros = libroDAO.buscarLibro(titulo, autor, isbn);


            if (!libros.isEmpty()) {

                tablaLibros.getItems().setAll(libros);
            } else {

                mostrarAlerta("Información", "No se encontraron libros con los criterios proporcionados.", Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {

            mostrarAlerta("Error", "Hubo un error al buscar los libros. Detalles:\n" + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
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

        // Crear los dialog para editar los datos del libro
        TextInputDialog tituloDialog = new TextInputDialog(libroSeleccionado.getTitulo());
        tituloDialog.setTitle("Editar Libro");
        tituloDialog.setHeaderText("Modificar el Título");
        tituloDialog.setContentText("Nuevo Título:");
        String nuevoTitulo = tituloDialog.showAndWait().orElse(libroSeleccionado.getTitulo());

        TextInputDialog isbnDialog = new TextInputDialog(libroSeleccionado.getISBN());
        isbnDialog.setTitle("Editar Libro");
        isbnDialog.setHeaderText("Modificar el ISBN");
        isbnDialog.setContentText("Nuevo ISBN:");
        String nuevoISBN = isbnDialog.showAndWait().orElse(libroSeleccionado.getISBN());

        TextInputDialog editorialDialog = new TextInputDialog(libroSeleccionado.getEditorial());
        editorialDialog.setTitle("Editar Libro");
        editorialDialog.setHeaderText("Modificar la Editorial");
        editorialDialog.setContentText("Nueva Editorial:");
        String nuevaEditorial = editorialDialog.showAndWait().orElse(libroSeleccionado.getEditorial());

        TextInputDialog anioDialog = new TextInputDialog(String.valueOf(libroSeleccionado.getAnyoPub()));
        anioDialog.setTitle("Editar Libro");
        anioDialog.setHeaderText("Modificar el Año de Publicación");
        anioDialog.setContentText("Nuevo Año:");
        int nuevoAnioPub;
        try {
            nuevoAnioPub = Integer.parseInt(anioDialog.showAndWait().orElse(String.valueOf(libroSeleccionado.getAnyoPub())));
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El año de publicación debe ser un número entero válido.", AlertType.ERROR);
            return;
        }

        String autorActual = libroSeleccionado.getAutor() != null ? libroSeleccionado.getAutor().getNombre() : "";
        TextInputDialog autorDialog = new TextInputDialog(autorActual);
        autorDialog.setTitle("Editar Libro");
        autorDialog.setHeaderText("Modificar el Autor");
        autorDialog.setContentText("Nuevo Autor:");
        String nuevoAutorNombre = autorDialog.showAndWait().orElse(autorActual);


        libroSeleccionado.setTitulo(nuevoTitulo);
        libroSeleccionado.setISBN(nuevoISBN);
        libroSeleccionado.setEditorial(nuevaEditorial);
        libroSeleccionado.setAnyoPub(nuevoAnioPub);

        if (nuevoAutorNombre != null && !nuevoAutorNombre.trim().isEmpty()) {
            autorDAO autorDAO = new autorDAO();
            autor nuevoAutor = autorDAO.findByNombre(nuevoAutorNombre);
            if (nuevoAutor == null) {
                nuevoAutor = new autor();
                nuevoAutor.setNombre(nuevoAutorNombre);
                autorDAO.create(nuevoAutor);
            }
            libroSeleccionado.setAutor(nuevoAutor);
        }


        libroDAO LibroDAO = new libroDAO();
        try {
            LibroDAO.ChangeLibro(libroSeleccionado);
            listarTodosLosLibros();
            mostrarAlerta("Éxito", "El libro ha sido actualizado correctamente.", AlertType.INFORMATION);
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo actualizar el libro. Detalles: " + e.getMessage(), AlertType.ERROR);
        }
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
        Alert alerta = new Alert(AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText("Ocurrió un error");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}