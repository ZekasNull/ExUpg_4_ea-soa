module se.ltu.transfer_gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.jakartaee.api;
    requires tools.jackson.databind;
    requires java.logging;
    requires io.github.cdimascio.dotenv.java;


    opens se.ltu.transfer_gui to javafx.fxml;
    opens data_objects to tools.jackson.databind;
    exports se.ltu.transfer_gui;
}