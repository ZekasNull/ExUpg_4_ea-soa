module se.ltu.transfer_gui {
    requires javafx.controls;
    requires javafx.fxml;


    opens se.ltu.transfer_gui to javafx.fxml;
    exports se.ltu.transfer_gui;
}