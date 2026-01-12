package se.ltu.transfer_gui;

import data_objects.TimeEditCalendarEntry;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import network.CanvasCalendarSender;
import network.TimeEditFetcher;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CanvasTransferController {

    //textfält för inmatning av url
    @FXML
    private TextField hamtningsLankTextField;

    // kalender och kolumner
    @FXML
    private TableView<TimeEditCalendarEntry> kalenderTable;

    @FXML
    private TableColumn<TimeEditCalendarEntry, String> datumColumn;

    @FXML
    private TableColumn<TimeEditCalendarEntry, String> tidColumn;

    @FXML
    private TableColumn<TimeEditCalendarEntry, String> idColumn;

    @FXML
    private TableColumn<TimeEditCalendarEntry, Boolean> valjColumn;

    @FXML
    private TableColumn<TimeEditCalendarEntry, String> kurskodColumn;

    @FXML
    private TableColumn<TimeEditCalendarEntry, String> lärareColumn;

    @FXML
    private TableColumn<TimeEditCalendarEntry, String> aktivitetColumn;

    @FXML
    private TableColumn<TimeEditCalendarEntry, String> möteslänkColumn;

    @FXML
    private TableColumn<TimeEditCalendarEntry, String> kommentarColumn;

    @FXML
    private TableColumn<TimeEditCalendarEntry, String> lokalColumn;

    @FXML
    private TableView<String[]> statusTable;

    @FXML
    private TableColumn<String[], String> overfforIDColumn;

    @FXML
    private TableColumn<String[], String> statusColumn;

    // redigeringsformulär
    @FXML
    private Text idText;

    @FXML
    private GridPane redigeraGridPane;

    @FXML
    private TextArea redigeraAktivitetTextArea;

    @FXML
    private TextArea redigeraKommentarTextArea;

    @FXML
    private TextArea redigeraLarareTextArea;

    @FXML
    private TextArea redigeraMoteslankTextArea;

    // används för att söka efter nycklar i detaildInformation
    private static final String key_aktivitet = "Aktivitet";
    private static final String key_kommentar = "Kommentar, kommentar";
    private static final String key_larare = "Anställd, Student";
    private static final String key_moteslank= "Möteslänk";
    private static final String key_kurskod = "Kurskod";
    private static final String key_lokal = "Plats, Lokal";

    //skapar en observablelist med timeEditCalenderEntry's
    private ObservableList<TimeEditCalendarEntry> entriesList = FXCollections.observableArrayList();
    private ObservableList<String[]> statusList = FXCollections.observableArrayList();

    // hämtar timeEditCalenderEntry's och lägger dem i observable list
    // ger lite olika felmeddelandenden vid diverse fel som kan uppstå
    @FXML
    void hamtaKalenderHandelserClicked(MouseEvent event) {
        if (hamtningsLankTextField.getText().isEmpty()) {
            showErrorPopup("Du måste mata in en URL");
            return;
        }

        try {
            TimeEditCalendarEntry[] entries = TimeEditFetcher.getTimeEditBookings(hamtningsLankTextField.getText());
            entriesList.clear();
            entriesList.addAll(Arrays.asList(entries));

            if (entriesList.isEmpty()){
                showErrorPopup("Inga kalenderhändelser hittades");
                return;
            }
        }

        catch (Exception e) {
            showErrorPopup(e.getMessage());
        }
    }

    // metod för att markera alla valda
    @FXML
    void valjAllaClicked(MouseEvent event) {
        kalenderTable.getItems().forEach(entry -> entry.setVald(true));
        kalenderTable.refresh();
    }
    // metod för att avmarkera alla valda
    @FXML
    void avValjAllaClicked(MouseEvent event) {
        kalenderTable.getItems().forEach(entry -> entry.setVald(false));
        kalenderTable.refresh();
    }

    // metod för att ladda formuläret som används vid selected row i kalendertabellen och vid angraredigering
    private void laddaFormularData(TimeEditCalendarEntry entry) {
        Map<String, String> entryDetails = entry.getDetailedInformation();
        redigeraAktivitetTextArea.setText(entryDetails.get(key_aktivitet));
        redigeraKommentarTextArea.setText(entryDetails.get(key_kommentar));
        redigeraLarareTextArea.setText(entryDetails.get(key_larare));
        redigeraMoteslankTextArea.setText(entryDetails.get(key_moteslank));
        idText.setText(entry.getId());
    }

    //metod för att rensa formulärdata i alla noder som har textfält som ingår i gridPane
    private void rensaFormularData() {
        idText.setText("id");
        for (Node area : redigeraGridPane.getChildren()) {
            if (area instanceof TextInputControl input) {
                input.clear();
            }
        }
    }

    @FXML
    void angraKalenderHändelserButtonClicked(MouseEvent event) {
        laddaFormularData(kalenderTable.getSelectionModel().getSelectedItem());
    }

    // metod som rensar och laddar om  redigeraformulären med data vid klick i händelsertabellen
    @FXML
    void handelserTableClicked(MouseEvent event) {
        rensaFormularData();
        laddaFormularData(kalenderTable.getSelectionModel().getSelectedItem());
        redigeraGridPane.setDisable(false);
    }

    @FXML
    void redigeraKalenderHandelserClicked(MouseEvent event) {
        TimeEditCalendarEntry entry = kalenderTable.getSelectionModel().getSelectedItem();
        Map<String, String> redigeradHandelse = entry.getDetailedInformation();
        redigeradHandelse.put(key_aktivitet, redigeraAktivitetTextArea.getText());
        redigeradHandelse.put(key_kommentar, redigeraKommentarTextArea.getText());
        redigeradHandelse.put(key_larare, redigeraLarareTextArea.getText());
        redigeradHandelse.put(key_moteslank, redigeraMoteslankTextArea.getText());
        kalenderTable.refresh();
    }

    @FXML
    public void initialize() {

        kalenderTable.setItems(entriesList);
        datumColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty((cellData.getValue().getStartdate())));
        idColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty((cellData.getValue().getId())));
        tidColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty((cellData.getValue().getStarttime() + " - " + cellData.getValue().getEndtime())));
        kurskodColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty((cellData.getValue().getDetailedInformation().get(key_kurskod))));
        möteslänkColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty((cellData.getValue().getDetailedInformation().get(key_moteslank))));
        aktivitetColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty((cellData.getValue().getDetailedInformation().get(key_aktivitet))));
        lokalColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty((cellData.getValue().getDetailedInformation().get(key_lokal))));
        kommentarColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty((cellData.getValue().getDetailedInformation().get(key_kommentar))));
        lärareColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty((cellData.getValue().getDetailedInformation().get(key_larare))));
        valjColumn.setCellValueFactory(cellData -> cellData.getValue().getVald());
        valjColumn.setCellFactory(CheckBoxTableCell.forTableColumn(valjColumn));



        overfforIDColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue()[0]));

        statusColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue()[1]));

        redigeraGridPane.setDisable(true);

        //välj wrapping metoden på de metoder som måste anpassas för att passa i rutan
        enableWrapping(kurskodColumn);
        enableWrapping(lärareColumn);

    }

    // denna borde
    @FXML
    void overforKalenderHandelser(MouseEvent event) {
        List<TimeEditCalendarEntry> selectedEntries = entriesList.stream()
                .filter(entry -> entry.getVald().get())
                .toList();
        if (selectedEntries.isEmpty()) {
            showErrorPopup("Du har inga valda kalenderhändelser");
            return;
        }

        statusList.clear();

        for (TimeEditCalendarEntry entry : selectedEntries) {
            boolean success = CanvasCalendarSender.sendTimeEditCalendarEntryToCanvas(entry); //  ellesr ska det va canvas?
            String statusText = success ? "Lyckades" : "Misslyckades";

            statusList.add(new String[]{
                    new String(entry.getId()),
                    new String(statusText)
            });

            }
        statusTable.setItems(statusList);

        }

    //metod som kan återanvändas för att visa errormedelanden
    private void showErrorPopup(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fel");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

//100% ai för att göra så texten får bättre plats i tabellen
private void enableWrapping(TableColumn<TimeEditCalendarEntry, String> column) {
    column.setCellFactory(tc -> new TableCell<>() {
        private final Text text = new Text();

        {
            text.wrappingWidthProperty().bind(tc.widthProperty().subtract(10));
            setGraphic(text);
            setPrefHeight(Control.USE_COMPUTED_SIZE);
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            text.setText(empty || item == null ? null : item);
        }
    });

    //forloopa
}


}
