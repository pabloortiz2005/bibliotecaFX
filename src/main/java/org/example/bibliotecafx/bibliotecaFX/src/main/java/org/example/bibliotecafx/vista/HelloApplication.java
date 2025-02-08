package org.example.bibliotecafx.vista;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/bibliotecafx/Inicio.fxml"));
            Scene scene = new Scene(loader.load(), 916, 690); // Configurar tamaño si es necesario
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.setTitle("BibliotecaFX");
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}