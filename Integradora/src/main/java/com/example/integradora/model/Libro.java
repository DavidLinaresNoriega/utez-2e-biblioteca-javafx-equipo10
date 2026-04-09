package com.example.integradoraprueba.model;

public class Libro {
    private String isbn;
    private String title;
    private String autor;
    private String year;
    private String genero;
    private String disponible;

    public Libro(){
    }

    public Libro(String isbn, String title, String autor, String year, String genero, String disponible) {
        this.isbn = isbn;
        this.title = title;
        this.autor = autor;
        this.year = year;
        this.genero = genero;
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "isbn=" + isbn +
                ", title='" + title + '\'' +
                ", autor='" + autor + '\'' +
                ", year=" + year +
                ", genero='" + genero + '\'' +
                ", disponible=" + disponible +
                '}';
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String isDisponible() {
        return disponible;
    }

    public void setDisponible(String disponible) {
        this.disponible = disponible;
    }
}
