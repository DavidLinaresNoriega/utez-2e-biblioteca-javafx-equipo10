package com.example.integradora.services;

import com.example.integradoraprueba.model.Libro;
import com.example.integradoraprueba.repositories.FileRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookService {
    private final FileRepository repo = new FileRepository();

    private final ObservableList<Libro> librosObservables = FXCollections.observableArrayList();

    public ObservableList<Libro> getLibrosObservables() {
        return librosObservables;
    }

    public void loadDataIntoList() throws IOException {
        List<String> lines = repo.readAllLines();
        librosObservables.clear();

        for(String line : lines){
            if (line == null || line.isBlank()){
                continue;
            }
            String[] parts = line.split(",");
            Libro libro = new Libro(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
            librosObservables.add(libro);
        }
    }

    private List<String> getCleanLines() throws IOException {
        List<String> lines = repo.readAllLines();
        List<String> cleanLines = new ArrayList<>();
        for(String line : lines){
            if(line!=null && !line.isBlank()){
                cleanLines.add(line);
            }
        }
        return cleanLines;
    }

    public void updatePerson(int index, String isbn, String titulo, String autor, String year, String genero, String disponibilidad) throws IOException {
        validate(isbn, titulo, autor, year, genero, disponibilidad, true);
        if(index < 0){
            throw new IllegalArgumentException("El indice es invalido");
        }
        List<String> data = getCleanLines();
        String nuevaLinea = isbn + "," + titulo + "," + autor + "," + year + "," + genero + "," + disponibilidad;
        data.set(index, nuevaLinea);
        repo.saveFile(data);

        librosObservables.set(index, new Libro(isbn, titulo, autor, year, genero, disponibilidad));
    }

    public void removePerson(int index) throws IOException {
        List<String> data = getCleanLines();
        if(index < 0 || index >= data.size()){
            throw new IllegalArgumentException("el indice esta fuera de rango");
        }
        data.remove(index);
        repo.saveFile(data);

        librosObservables.remove(index);
    }

    public void addPerson (Libro libro) throws IOException {
        String isbn = libro.getIsbn();
        String titulo = libro.getTitle();
        String autor = libro.getAutor();
        String year = libro.getYear();
        String genero = libro.getGenero();
        String disponibilidad = libro.isDisponible();

        validate(isbn, titulo, autor, year, genero, disponibilidad, false);

        repo.appendNewLine(isbn + "," + titulo + "," + autor + "," + year + "," + genero + "," + disponibilidad);

        librosObservables.add(libro);
    }

    private void validate(String isbn, String titulo, String autor, String year, String genero, String disponibilidad, boolean esEdicion) {
        String errores = "";

        if (isbn == null || isbn.isBlank() || titulo == null || titulo.isBlank() ||
                autor == null || autor.isBlank() || year == null || year.isBlank() ||
                genero == null || genero.isBlank() || disponibilidad == null || disponibilidad.isBlank()) {
            errores += "- Ningún campo debe estar vacío \n";
        }

        if (isbn != null && isbn.length() != 13) {
            errores += "- El ISBN debe tener 13 dígitos \n";
        }

        if (titulo != null && titulo.length() < 3) {
            errores += "- El título debe tener al menos 3 caracteres \n";
        }

        if (autor != null && autor.length() < 3) {
            errores += "- El autor debe tener al menos 3 caracteres \n";
        }

        try {
            if (year != null && !year.isBlank()) {
                int yearInt = Integer.parseInt(year);
                if (yearInt < 1500 || yearInt > 2026) {
                    errores += "- El año debe estar entre 1500 y 2026 \n";
                }
            }
            if (isbn != null && !isbn.isBlank()) {
                Long.parseLong(isbn);
            }
        } catch (NumberFormatException e) {
            errores += "- El año y el ISBN deben ser números válidos \n";
        }

        if (!esEdicion) {
            boolean existe = librosObservables.stream()
                    .anyMatch(libro -> libro.getIsbn().equals(isbn));
            if (existe) {
                errores += "- El ISBN ya está registrado en otro libro \n";
            }
        }

        if (!errores.isEmpty()) {
            throw new IllegalArgumentException(errores);
        }
    }
}
