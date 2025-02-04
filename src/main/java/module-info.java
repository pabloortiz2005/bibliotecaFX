module org.example.bibliotecafx {
    // Requiere módulos externos
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.media;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;

    // Abre los paquetes para que puedan ser accedidos en tiempo de ejecución
    opens org.example.bibliotecafx.controladores to javafx.fxml;
    opens org.example.bibliotecafx.DAO to javafx.fxml; // Si algún controlador necesita acceso
    opens org.example.bibliotecafx.entities to org.hibernate.orm.core, javafx.fxml;
    opens org.example.bibliotecafx.util to javafx.fxml;
    opens org.example.bibliotecafx.vista to javafx.fxml;

    // Exporta los paquetes que necesitan ser accesibles para otros módulos
    exports org.example.bibliotecafx.controladores;
    exports org.example.bibliotecafx.DAO;
    exports org.example.bibliotecafx.entities;
    exports org.example.bibliotecafx.util;
    exports org.example.bibliotecafx.vista;
}