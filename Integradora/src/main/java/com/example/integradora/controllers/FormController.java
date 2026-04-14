package com.example.integradora.controllers;

import com.example.integradora.model.Libro;
import com.example.integradora.services.BookService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FormController {
    private String[] arrGeneros = {"Fantasía", "Ciencia ficción", "Romance", "Misterio", "Suspenso"};
    private String[] arrDisponible = {"Si", "No"};

    @FXML private Label lblMsg;
    @FXML private TextField txtIsbn, txtTitle, txtAutor, txtYear;
    @FXML private ComboBox<String> cbGenero, cbDisponible;

    private BookService service = new BookService();
    private boolean esEdicion = false;
    private int indexEdicion = -1;

    public void setService(BookService service) {
        this.service = service;
    }

    @FXML
    public void initialize() {
        cbGenero.setItems(FXCollections.observableArrayList(arrGeneros));
        cbDisponible.setItems(FXCollections.observableArrayList(arrDisponible));
    }

    @FXML
    public void onAdd() {
        try {
            String isbn = txtIsbn.getText();
            String titulo = txtTitle.getText();
            String autor = txtAutor.getText();
            String year = txtYear.getText();
            String genero = cbGenero.getValue();
            String disponibilidad = cbDisponible.getValue();

            if (esEdicion) {
                service.updatePerson(indexEdicion, isbn, titulo, autor, year, genero, disponibilidad);
                lblMsg.setText("Libro actualizado con éxito");
                lblMsg.setStyle("-fx-text-fill : green");
            } else {
                Libro book = new Libro(isbn, titulo, autor, year, genero, disponibilidad);
                service.addPerson(book);
                lblMsg.setText("Libro creado con éxito");
                lblMsg.setStyle("-fx-text-fill : green");
            }
            limpiarCampos();

        } catch (Exception e) {
            lblMsg.setText("Error: " + e.getMessage());
            lblMsg.setStyle("-fx-text-fill : red");
        }
    }

    public void cargarDatos(Libro libro, int index) {
        if (libro != null) {
            this.esEdicion = true;
            this.indexEdicion = index;

            txtIsbn.setText(libro.getIsbn());
            txtTitle.setText(libro.getTitle());
            txtAutor.setText(libro.getAutor());
            txtYear.setText(libro.getYear());
            cbGenero.setValue(libro.getGenero());
            cbDisponible.setValue(libro.isDisponible());

            txtIsbn.setEditable(false);
        }
    }

    private void limpiarCampos() {
        txtIsbn.clear();
        txtTitle.clear();
        txtAutor.clear();
        txtYear.clear();
        cbGenero.getSelectionModel().clearSelection();
        cbDisponible.getSelectionModel().clearSelection();
    }

    @FXML
    private void cancelar(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
