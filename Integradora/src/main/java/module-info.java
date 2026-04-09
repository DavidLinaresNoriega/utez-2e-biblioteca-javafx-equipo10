module com.example.integradora {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.integradora to javafx.fxml;
    opens com.example.integradora.controllers to javafx.fxml;
    opens com.example.integradora.repositories to javafx.fxml;
    opens com.example.integradora.model to javafx.fxml;
    opens com.example.integradora.services to javafx.fxml;
    opens com.example.integradora.views to javafx.fxml;


    exports com.example.integradora;
    exports com.example.integradora.controllers;
    exports com.example.integradora.model;
    exports com.example.integradora.repositories;
    exports com.example.integradora.services;
}