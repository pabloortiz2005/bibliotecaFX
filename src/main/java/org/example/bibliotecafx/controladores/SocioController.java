package org.example.bibliotecafx.controladores;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

    // Buscar socios por nombre
    @FXML
    private void buscarSocio() {
        String nombre = NombreBuscar.getText().trim();
        String nTelStr = NTelBuscar.getText().trim();

        try {
            // Validar entradas del usuario
            if (nombre.isEmpty() && nTelStr.isEmpty()) {
                mostrarAlerta("Error", "Debes rellenar al menos un campo para buscaer.", AlertType.ERROR);
                return;
            }

            if (!nombre.isEmpty() && !nTelStr.isEmpty()) {
                mostrarAlerta("Error", "Por favor, rellena solo un campo para buscar.", AlertType.ERROR);
                return;
            }


            if (!nombre.isEmpty()) {
                socio socio = socioDao.findByNombre(nombre);
                if (socio != null) {
                    tablaSocios.getItems().setAll(List.of(socio));
                    tablaSocios.getSelectionModel().select(socio);
                } else {
                    mostrarAlerta("Información", "No se encontraron socios con el nombre proporcionado.", AlertType.INFORMATION);
                }
            } else if (!nTelStr.isEmpty()) {

                try {
                    Integer nTel = Integer.parseInt(nTelStr);
                    socio socio = socioDao.findByTel(nTel);
                    if (socio != null) {
                        tablaSocios.getItems().setAll(List.of(socio));
                        tablaSocios.getSelectionModel().select(socio);
                    } else {
                        mostrarAlerta("Información", "No se encontraron socios con el número de teléfono proporcionado.", AlertType.INFORMATION);
                    }
                } catch (NumberFormatException e) {
                    mostrarAlerta("Error", "El número de teléfono debe ser válido.", AlertType.ERROR);
                }
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Hubo un error al realizar la búsqueda. Detalles: " + e.getMessage(), AlertType.ERROR);
            e.printStackTrace();
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