package org.example.bibliotecafx.controladores;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.bibliotecafx.DAO.libroDAO;
import org.example.bibliotecafx.entities.libro;

import java.util.List;

public class LibrosController {

    // Definimos los elementos del FXML
    @FXML
    private Button btnCrear;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnListarAutor;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnListarTodos;
    @FXML
    private TableView<libro> tablaResultados;
    @FXML
    private TableColumn<libro, String> colTitulo;
    @FXML
    private TableColumn<libro, String> colISBN;
    @FXML
    private TableColumn<libro, String> colAutor;
    @FXML
    private TableColumn<libro, String> colEditorial;
    @FXML
    private TableColumn<libro, Integer> colAnyo;


    private libroDAO libroDAO = new libroDAO();


    private void cargarLibros(List<libro> libros) {
        tablaResultados.getItems().clear();
        tablaResultados.getItems().addAll(libros);
    }


    @FXML
    private void crearLibro(ActionEvent event) {
        libro nuevoLibro = libroDAO.create(null);
        if (nuevoLibro != null) {
            libroDAO.create(nuevoLibro); // Guardamos el libro en la base de datos
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Libro creado");
            alert.setHeaderText("El libro ha sido creado correctamente");
            alert.showAndWait();
        }
    }


    @FXML
    private void modificarLibro(ActionEvent event) {

        libro libroModificado = libroDAO.ChangeLibro(1);
        if (libroModificado != null) {
            libroDAO.create(libroModificado);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Libro modificado");
            alert.setHeaderText("El libro ha sido modificado correctamente");
            alert.showAndWait();
        }
    }


    @FXML
    private void buscarLibro(ActionEvent event) {

        libro libroEncontrado = libroDAO.findByTitulo("Título Ejemplo");
        if (libroEncontrado != null) {
            cargarLibros(List.of(libroEncontrado));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Libro no encontrado");
            alert.setHeaderText("No se encontró el libro con ese título");
            alert.showAndWait();
        }
    }


    @FXML
    private void listarLibrosPorAutor(ActionEvent event) {

        List<libro> librosPorAutor = libroDAO.findByAutor(null);
        cargarLibros(librosPorAutor);
    }


    @FXML
    private void eliminarLibro(ActionEvent event) {

        libroDAO.deleteById(1);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Libro eliminado");
        alert.setHeaderText("El libro ha sido eliminado correctamente");
        alert.showAndWait();
    }


    @FXML
    private void listarTodosLosLibros(ActionEvent event) {
        List<libro> todosLosLibros = libroDAO.findAll();
        cargarLibros(todosLosLibros);
    }

    @FXML
    public void initialize() {

        colTitulo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
        colISBN.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getISBN()));
        colAutor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAutor().toString()));
        colEditorial.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEditorial()));
        colAnyo.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAnyoPub()).asObject());
    }

}
