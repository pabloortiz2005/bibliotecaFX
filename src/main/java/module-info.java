module org.example.bibliotecafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;

    opens org.example.bibliotecafx to javafx.fxml;
    exports org.example.bibliotecafx;
    exports org.example.bibliotecafx.entities;
    opens org.example.bibliotecafx.entities to javafx.fxml;
    exports org.example.bibliotecafx.vista;
    opens org.example.bibliotecafx.vista to javafx.fxml;
    exports org.example.bibliotecafx.controladores;
    opens org.example.bibliotecafx.controladores to javafx.fxml;
}