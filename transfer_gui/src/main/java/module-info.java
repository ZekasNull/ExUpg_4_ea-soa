module se.ltu.transfer_gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.jakartaee.api;
    requires tools.jackson.databind;
    requires java.logging;


    opens se.ltu.transfer_gui to javafx.fxml;
    exports se.ltu.transfer_gui;
}