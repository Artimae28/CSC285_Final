package patientrecords;

import java.text.NumberFormat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PatientRecordsGUI extends BorderPane {
    /*Define our table, data list, and currency format*/
    private TableView<Patient> table = new TableView<>();
    final ObservableList<Patient> data = FXCollections.observableArrayList();
    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

    /*Create a Vertical Box to display our records */
    private VBox vbox = new VBox();

    /*Create our main menu*/
    private MenuBar menuBar = new MenuBar();
    protected Menu mainMenu = new Menu("Menu");
    protected MenuItem addPatient = new MenuItem("Add Patient");
    protected MenuItem deletePatient = new MenuItem("Delete Patient");
    protected MenuItem resetFields = new MenuItem("Reset Fields");
    protected MenuItem exit = new MenuItem("Exit");

    /*Create and name table columsn */
    TableColumn<Patient, Integer> idCol = new TableColumn<>("ID");
    TableColumn<Patient, String> firstNameCol = new TableColumn<>("First Name");
    TableColumn<Patient, String> lastNameCol = new TableColumn<>("Last Name");
    TableColumn<Patient, Character> sexCol = new TableColumn<>("Sex");
    TableColumn<Patient, Integer> ageCol = new TableColumn<>("Age");
    TableColumn<Patient, Integer> minorCol = new TableColumn<>("Patient is a Minor");
    TableColumn<Patient, Integer> seniorCol = new TableColumn<>("Patient is a Senior");
    TableColumn<Patient, Integer> floorCol = new TableColumn<>("Floor");
    TableColumn<Patient, Integer> roomCol = new TableColumn<>("Room");
    TableColumn<Patient, Double> feeCol = new TableColumn<>("Fee");
    TableColumn<Patient, Double> insuranceCol = new TableColumn<>("Insurance");
    TableColumn<Patient, Double> billCol = new TableColumn<>("Bill");

    /*Create a search bar */
    protected TextField tfSearch = new TextField();
    protected ComboBox<String> cbSearch = new ComboBox<>();
    protected HBox hbSearch = new HBox();
    protected Label searchLabel = new Label("Search");
    protected Button searchButton = new Button("Search");
    protected Button resetButton = new Button("Reset");

    /*Create method to fill our records with data. The SuppressWarnings simply cancels a harmless warning from the getColumns command */
    @SuppressWarnings("unchecked")
    public PatientRecordsGUI() {
        table.getColumns().addAll(idCol, firstNameCol, lastNameCol, sexCol, ageCol, minorCol, seniorCol, floorCol, roomCol, feeCol, insuranceCol, billCol);

        /*These statements enter data into the previously defined table fields, using the variables from the Patient class*/
        idCol.setMinWidth(100);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        sexCol.setMinWidth(100);
        sexCol.setCellValueFactory(new PropertyValueFactory<>("sex"));

        ageCol.setMinWidth(100);
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));

        minorCol.setMinWidth(120);
        minorCol.setCellValueFactory(new PropertyValueFactory<>("minor"));

        seniorCol.setMinWidth(120);
        seniorCol.setCellValueFactory(new PropertyValueFactory<>("senior"));

        floorCol.setMinWidth(100);
        floorCol.setCellValueFactory(new PropertyValueFactory<>("floor"));

        roomCol.setMinWidth(100);
        roomCol.setCellValueFactory(new PropertyValueFactory<>("room"));

        /*setCellFactory allows the TableView to display doubles with the dollar symbol. 
         * NOTE: I'm not 100% sure how exactly it works, I just know that it works the way it's written. - Arti*/
        feeCol.setMinWidth(100);
        feeCol.setCellValueFactory(new PropertyValueFactory<>("fee"));
        feeCol.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Double fee, boolean empty) {
                super.updateItem(fee, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(currencyFormat.format(fee));
                }
            }
        });

        insuranceCol.setMinWidth(100);
        insuranceCol.setCellValueFactory(new PropertyValueFactory<>("insurance"));
        insuranceCol.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Double insurance, boolean empty) {
                super.updateItem(insurance, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(currencyFormat.format(insurance));
                }
            }
        });

        billCol.setMinWidth(100);
        billCol.setCellValueFactory(new PropertyValueFactory<>("bill"));
        billCol.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Double bill, boolean empty) {
                super.updateItem(bill, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(currencyFormat.format(bill));
                }
            }
        });

        table.setItems(data);

        /*Display our menu bar at the top of the app */
        menuBar.getMenus().add(mainMenu);
        mainMenu.getItems().addAll(addPatient, deletePatient, resetFields, exit);
        setTop(menuBar);

        /*Create keyboard shortcuts to use in app */
        addPatient.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCombination.SHORTCUT_DOWN));
        deletePatient.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCombination.SHORTCUT_DOWN));
        resetFields.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.SHORTCUT_DOWN));
        exit.setAccelerator(new KeyCodeCombination(KeyCode.ESCAPE));

        tfSearch.setPrefWidth(200);
        hbSearch.setSpacing(10);
        hbSearch.setPadding(new Insets(10,100,0,0));
        hbSearch.getChildren().addAll(cbSearch, tfSearch, searchButton, resetButton);

        cbSearch.getItems().addAll("ID", "First Name", "Last Name", "Floor");
        cbSearch.setPrefWidth(100);
        cbSearch.setValue("ID");

        /*Use the vBox to display the data in the table */
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10,10,10,10));
        vbox.getChildren().addAll(searchLabel, hbSearch, table);
        setCenter(vbox);
    }
    
}
