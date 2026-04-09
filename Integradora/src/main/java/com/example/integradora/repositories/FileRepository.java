package com.example.integradora.repositories;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileRepository {
    private final Path filePath = Paths.get("data", "books.csv");

    private void ensureFile() throws IOException {
        if(Files.notExists(filePath)){
            Files.createFile(filePath);
        }

    }

    public List<String> readAllLines() throws IOException {
        ensureFile();
        return Files.readAllLines(filePath);
    }

    public void appendNewLine(String line) throws IOException {
        ensureFile();
        Files.writeString(filePath, line + System.lineSeparator(), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
    }
    public void saveFile(List<String> lines) throws IOException {
        ensureFile();
        Files.write(filePath, lines, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
    }

}
