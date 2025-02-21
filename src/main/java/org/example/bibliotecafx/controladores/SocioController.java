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
import org.example.bibliotecafx.DAO.socioDAO;
import org.example.bibliotecafx.entities.autor;
import org.example.bibliotecafx.entities.socio;

import java.io.IOException;
import java.util.List;

public class SocioController {
    @FXML
    public TextField NombreBuscar;

    @FXML
    public TextField NTelBuscar;


    @FXML
    private TableView<socio> tablaSocios;

    @FXML
    private TableColumn<socio, Integer> colId;

    @FXML
    private TableColumn<socio, String> colNombre;

    @FXML
    private TableColumn<socio, String> colDireccion;

    @FXML
    private TableColumn<socio, Integer> colNTelefono;

    @FXML
    public TextField nombreBuscar;

    private final socioDAO socioDao;

    public SocioController() {
        this.socioDao = new socioDAO();
    }

    @FXML
    public void initialize() {
        // Configuración inicial de la tabla
        colId.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getIdS()));
        colNombre.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getNombre()));
        colDireccion.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getDireccion()));
        colNTelefono.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getnTel()));
        listarTodosLosSocios(); // Cargar todos los Socios al iniciar
    }

    // Crear un nuevo socio
    @FXML
    private void crearNuevoSocio() {
        // Pedir al usuario los datos del autor
        TextInputDialog dialog = new TextInputDialog();

        dialog.setTitle("Añadir Nuevo Autor");
        dialog.setHeaderText("Introduce los datos del nuevo Socio:");

        dialog.setContentText("Nombre:");
        String nombre = dialog.showAndWait().orElse(null);

        dialog.setContentText("direccion:");
        String direccion = dialog.showAndWait().orElse(null);

        dialog.setContentText("Numero de telefono:");
        String NTel =dialog.showAndWait().orElse(null);

        // Guardar en la base de datos
        try {
            socio nuevoSocio = new socio();
            nuevoSocio.setNombre(nombre);
            nuevoSocio.setDireccion(direccion);
            nuevoSocio.setnTel(Integer.valueOf(NTel));

            socioDao.create(nuevoSocio);
            listarTodosLosSocios();
            mostrarAlerta("Éxito", "El socio ha sido añadido correctamente.", AlertType.INFORMATION);
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo añadir el socio. Detalles:\n" + e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    private void buscarSocio() {
        String nombre = nombreBuscar.getText().trim();
        String telefonoTexto = NTelBuscar.getText().trim();

        if (nombre.isEmpty() && telefonoTexto.isEmpty()) {
            mostrarAlerta("Error", "Para buscar, debes ingresar al menos un criterio.", AlertType.ERROR);
            return;
        }

        if (!nombre.isEmpty() && !telefonoTexto.isEmpty()) {
            mostrarAlerta("Error", "Solo puedes buscar por un criterio a la vez.", AlertType.ERROR);
            return;
        }

        try {
            List<socio> sociosEncontrados;

            if (!nombre.isEmpty()) {
                sociosEncontrados = socioDao.findByNombre(nombre);
            } else {
                Integer telefono = Integer.valueOf(telefonoTexto);
                sociosEncontrados = socioDao.findByTel(telefono);
            }

            if (sociosEncontrados != null && !sociosEncontrados.isEmpty()) {
                tablaSocios.getItems().setAll(sociosEncontrados);
            } else {
                mostrarAlerta("Información", "No se encontraron socios con los criterios proporcionados.", AlertType.INFORMATION);
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El número de teléfono debe ser un valor numérico.", AlertType.ERROR);
        } catch (Exception e) {
            mostrarAlerta("Error", "Hubo un error al buscar socios. Detalles:\n" + e.getMessage(), AlertType.ERROR);
        }
    }



    // Editar un autor seleccionado
    @FXML
    private void editarSocioSeleccionado() {

        TextInputDialog dialog = new TextInputDialog();

        socio socioSeleccionado = tablaSocios.getSelectionModel().getSelectedItem();

        if (socioSeleccionado == null) {
            mostrarAlerta("Advertencia", "Selecciona un socio para editar.", AlertType.WARNING);
            return;
        }

        dialog.setContentText("Nombre:");
        String nombre = dialog.showAndWait().orElse(null);

        dialog.setContentText("Dirección:");
        String direccion = dialog.showAndWait().orElse(null);

        dialog.setContentText("Numero de telefono:");
        String NTel =dialog.showAndWait().orElse(null);

        // Editar el socio
        socioDao.ChangeSocio(socioSeleccionado,nombre,direccion, Integer.valueOf(NTel));
        listarTodosLosSocios();
    }

    // Eliminar un autor seleccionado
    @FXML
    private void eliminarSocioSeleccionado() {
        socio socioSeleccionado = tablaSocios.getSelectionModel().getSelectedItem();

        if (socioSeleccionado == null) {
            mostrarAlerta("Advertencia", "Selecciona un socio para eliminar.", AlertType.WARNING);
            return;
        }

        try {
            socioDao.deleteById(socioSeleccionado.getIdS());
            tablaSocios.getItems().remove(socioSeleccionado);
            mostrarAlerta("Éxito", "El socio ha sido eliminado correctamente.", AlertType.INFORMATION);
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo eliminar el socio. Detalles:\n" + e.getMessage(), AlertType.ERROR);
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
    private void listarTodosLosSocios() {
        try {
            List<socio> socio = socioDao.findAll();
            tablaSocios.getItems().setAll(socio);
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudieron listar los socios.", AlertType.ERROR);
        }
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
            mostrarAlerta("Error", "No se pudo cargar la pantalla de inicio.", Alert.AlertType.ERROR);
        }
    }
}