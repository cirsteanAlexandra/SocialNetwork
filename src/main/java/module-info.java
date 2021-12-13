module com.example {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.postgresql.jdbc;
    requires java.sql;

    requires org.junit.jupiter.api;
    requires java.desktop;
    opens com.example.ador_testele to javafx.fxml;
    exports com.example.ador_testele;
}