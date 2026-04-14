package com.example.integradora.controllers;

import com.example.integradora.model.Libro;
import com.example.integradora.services.BookService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.*;
import java.io.IOException;

public class MainController {
    @FXML private Label lblDetalleIsbn;
    @FXML private Label lblDetalleTitulo;
    @FXML private Label lblDetalleAutor;
    @FXML private Label lblDetalleYear;
    @FXML private Label lblDetalleGenero;
    @FXML private Label lblDetalleDisponible;

    @FXML private Label lblMsg;

    @FXML private TableView<Libro> tableBooks;
    @FXML private TableColumn<Libro, String> colIsbn;
    @FXML private TableColumn<Libro, String> colTitle;
    @FXML private TableColumn<Libro, String> colAuthor;
    @FXML private TableColumn<Libro, String> colYear;
    @FXML private TableColumn<Libro, String> colGenre;
    @FXML private TableColumn<Libro, String> colDisponible;

    private BookService service = new BookService();

    @FXML
    public void initialize() {
        tableBooks.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                verDetalle();
            } else {
                limpiarLabelsDetalle();
            }
        });

        colIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colDisponible.setCellValueFactory(new PropertyValueFactory<>("disponible"));

        try {
            service.loadDataIntoList();
            tableBooks.setItems(service.getLibrosObservables());
        } catch (IOException e) {
            lblMsg.setText("Error al cargar datos: " + e.getMessage());
            lblMsg.setStyle("-fx-text-fill: red");
        }
    }

    @FXML
    private void nuevoLibro() {
        Libro objetoSeleccionado = tableBooks.getSelectionModel().getSelectedItem();
        int index = tableBooks.getSelectionModel().getSelectedIndex();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/integradora/views/form-view.fxml"));
            Parent root = loader.load();

            FormController formController = loader.getController();
            formController.setService(this.service);

            if (objetoSeleccionado != null) {
                formController.cargarDatos(objetoSeleccionado, index);
            }

            Stage stage = new Stage();
            stage.setTitle(objetoSeleccionado == null ? "Registrar Nuevo Libro" : "Editar Libro");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            tableBooks.getSelectionModel().clearSelection();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onRemove() {
        int index = tableBooks.getSelectionModel().getSelectedIndex();

        if (index < 0) {
            lblMsg.setText("Error: Selecciona un libro para eliminar");
            lblMsg.setStyle("-fx-text-fill: red");
            return;
        }

        try {
            service.removePerson(index);
            lblMsg.setText("Libro eliminado con éxito");
            lblMsg.setStyle("-fx-text-fill: green");
            tableBooks.getSelectionModel().clearSelection();
        } catch (Exception e) {
            lblMsg.setText("Error: " + e.getMessage());
            lblMsg.setStyle("-fx-text-fill: red");
        }
    }

    @FXML
    private void verDetalle() {
        Libro seleccionado = tableBooks.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            lblDetalleIsbn.setText(seleccionado.getIsbn());
            lblDetalleTitulo.setText(seleccionado.getTitle());
            lblDetalleAutor.setText(seleccionado.getAutor());
            lblDetalleYear.setText(seleccionado.getYear());
            lblDetalleGenero.setText(seleccionado.getGenero());
            lblDetalleDisponible.setText(seleccionado.isDisponible());
        }
    }

    private void limpiarLabelsDetalle() {
        lblDetalleIsbn.setText("---");
        lblDetalleTitulo.setText("---");
        lblDetalleAutor.setText("---");
        lblDetalleYear.setText("---");
        lblDetalleGenero.setText("---");
        lblDetalleDisponible.setText("---");
    }

    private final Path filePath = Paths.get("exports", "booksExports.csv");

    @FXML
    private void exportar() {
        try {
            Path origen = Paths.get("data", "books.csv");

            Path carpeta = Paths.get("exports");
            if (Files.notExists(carpeta)) {
                Files.createDirectories(carpeta);
            }

            Files.copy(origen, filePath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

            lblMsg.setText("Copia generada: " + filePath.getFileName());
            lblMsg.setStyle("-fx-text-fill: green");

        } catch (IOException e) {
            lblMsg.setText("Error al copiar: " + e.getMessage());
            lblMsg.setStyle("-fx-text-fill: red");
        }
    }
}
