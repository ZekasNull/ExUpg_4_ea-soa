/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.ltu.transfer_gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class CanvasTransferController {

    @FXML
    private Text aktivitetText;

    @FXML
    private Button angraKalenderRedigeringButton;

    @FXML
    private TableColumn<?, ?> datumColumn;

    @FXML
    private ScrollPane detaljDataScrollPane;

    @FXML
    private AnchorPane formularAnchorPane;

    @FXML
    private Button hamtaButton;

    @FXML
    private TextField hamtningsLankTextField;

    @FXML
    private TableView<?> kalenderTable;

    @FXML
    private Text kommentarText;

    @FXML
    private TableColumn<?, ?> kurskodColumn;

    @FXML
    private Text larareText;

    @FXML
    private TableColumn<?, ?> lokalColumn;

    @FXML
    private Text lokalText;

    @FXML
    private TableColumn<?, ?> lärareColumn;

    @FXML
    private Text moteslankText;

    @FXML
    private Button overforButton;

    @FXML
    private Button redigaraKalenderHandelserButton;

    @FXML
    private TableColumn<?, ?> tidColumn;

    @FXML
    private TextField updateraLararTextField;

    @FXML
    private TextField updateraLokalTextField;

    @FXML
    private TextField uppdateraAktivitetTextField;

    @FXML
    private Button uppdateraKalenderHandelserButton;

    @FXML
    private TextField uppdateraKommentarTextField;

    @FXML
    private TextField uppdateraMoteslankTextField;

    @FXML
    private Button valjAllaButton;

    @FXML
    private TableColumn<?, ?> valjColumn;

    @FXML
    void angraClicked(MouseEvent event) {

    }

    @FXML
    void hamtaKalenderHandelserClicked(MouseEvent event) {

    }

    @FXML
    void overforKalenderHandelserClicked(MouseEvent event) {

    }

    @FXML
    void redigeraKalenderHandelserClicked(MouseEvent event) {

    }

    @FXML
    void uppdateraClicked(MouseEvent event) {

    }

    @FXML
    void valjAllaClicked(MouseEvent event) {

    }

}

