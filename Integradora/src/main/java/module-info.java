module utez.edu.mx.integradora {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens utez.edu.mx.integradora to javafx.fxml;
    exports utez.edu.mx.integradora;
}