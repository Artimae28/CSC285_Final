package patientrecords;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class addPatient {
    /*Define our Text Fields */
    private TextField tfFirstName = new TextField();
    private TextField tfLastName = new TextField();
    /*Define the Sex field as a ComboBox */
    ObservableList<Character> cbList = FXCollections.observableArrayList('M', 'F', 'X');
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private ComboBox cbSex = new ComboBox(cbList);
    private TextField tfAge = new TextField();
    private TextField tfFloor = new TextField();
    private TextField tfRoom = new TextField();
    private TextField tfFee = new TextField();
    private TextField tfInsurance = new TextField();

    /*Define the labels used in the Add Patient screen */
    private ArrayList<Label> labels = new ArrayList<Label>(Arrays.asList(
        new Label("First name: "), new Label("Last name: "), new Label("Sex: "), new Label("Age: "),
        new Label("Floor: "), new Label("Room: "), new Label("Fee: "), new Label("Insurance: ")
    ));

    /*Define buttons for Add Patient screen */
    private Button addButton = new Button("Add");
    private Button clearButton = new Button("Clear");
    private Button cancelButton = new Button("Cancel");

    /*Define the visual aspect of the Add Patient screen */
    private GridPane gridPane = new GridPane();
    private VBox vbox = new VBox();
    private HBox hbox = new HBox();



    public addPatient(Stage primaryStage, java.sql.Statement stm, ObservableList<Patient> data, int [] startingPoint, ArrayList<Patient> patients) {
        /*Set the size of the Buttons */
        addButton.setPrefWidth(100);
        clearButton.setPrefWidth(100);
        cancelButton.setPrefWidth(100);

        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);
        
        gridPane.add(labels.get(0), 0, 0);
        gridPane.add(tfFirstName, 1, 0);
        
        gridPane.add(labels.get(1), 0, 1);
        gridPane.add(tfLastName, 1, 1);    

        gridPane.add(labels.get(2), 0, 2);
        gridPane.add(cbSex, 1, 2);

        gridPane.add(labels.get(3), 0, 3);
        gridPane.add(tfAge, 1, 3);

        gridPane.add(labels.get(4), 0, 4);
        gridPane.add(tfFloor, 1, 4);

        gridPane.add(labels.get(5), 0, 5);
        gridPane.add(tfRoom, 1, 5);

        gridPane.add(labels.get(6), 0, 6);
        gridPane.add(tfFee, 1, 6);

        gridPane.add(labels.get(7), 0, 7);
        gridPane.add(tfInsurance, 1, 7);

        gridPane.add(hbox, 0, 8);

        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(20);
        hbox.getChildren().addAll(addButton, clearButton, cancelButton);

        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10,15,0,0));
        vbox.getChildren().addAll(gridPane,hbox);

        Scene scene=new Scene(vbox,400,350);
        final Stage mainStage=new Stage();
        mainStage.initModality(Modality.APPLICATION_MODAL);
        mainStage.initOwner(primaryStage);
        
        mainStage.setScene(scene);
        mainStage.setTitle("Add Patient");
        mainStage.show();

        /*Run the method to add a patient when the Add button is pressed. */
        addButton.setOnAction(e -> {
            try {
                addNewPatient(primaryStage, stm, data, startingPoint, patients);
            } catch(Exception ex) {
                System.out.println(ex.getMessage());
            }
        });

        /*Run the method to clear the data fields of the Add Patient screen */
        clearButton.setOnAction(e -> {
            clearTextFields();
        });

        /*Close the Add Patient screen when pressing the cancel button */
        cancelButton.setOnAction(e -> {
            mainStage.close();
        });

        vbox.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ESCAPE) {
                mainStage.close();
            }
            else if(e.getCode() == KeyCode.ENTER) {
                try {
                    addNewPatient(primaryStage, stm, data, startingPoint, patients);
                } catch(Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
    }
    /*Leaving this in. If you can figure out how to calculate the bill in real time, then godspeed to you. */
    /*public double calculateBill() {
        double feeDouble = Double.parseDouble(tfFee.getText());
        double insDouble = Double.parseDouble(tfInsurance.getText());

        double calcBill = feeDouble - insDouble;
        return calcBill;
    }*/

    /*Clear the Add fields */
    public void clearTextFields() {
        tfFirstName.clear();
        tfLastName.clear();
        tfAge.clear();
        cbSex.getSelectionModel().clearSelection();
        tfFloor.clear();
        tfRoom.clear();
        tfFee.clear();
        tfInsurance.clear();
    }

    public void addNewPatient(Stage primaryStage, java.sql.Statement stm, ObservableList<Patient> data, int [] startingPoint, ArrayList<Patient> patients) throws Exception {
        if (tfFirstName.getText().equals("")) {
            tfFirstName.requestFocus();
        }
        else if (tfLastName.getText().equals("")) {
            tfLastName.requestFocus();
        }
        else if (tfAge.getText().equals("")) {
            tfAge.requestFocus();
        } 
        else if (tfFloor.getText().equals("")) {
            tfFloor.requestFocus();
        } 
        else if (tfRoom.getText().equals("")) {
            tfRoom.requestFocus();
        } 
        else if (tfFee.getText().equals("")) {
            tfFee.requestFocus();
        } 
        else if (tfInsurance.getText().equals("")) {
            tfInsurance.requestFocus();
        }
        else {
            //calculateBill();
            String query = "insert into patient (firstname, lastname, sex, age, floor, room, fee, insurance) values "
            + "('"+tfFirstName.getText()+"','"+tfLastName.getText()+"','"+cbSex.getValue()+"','"+tfAge.getText()+"','"+tfFloor.getText()+"','"+tfRoom.getText()+"','"+tfFee.getText()+"','"+tfInsurance.getText()+"')";
            stm.executeUpdate(query);
            clearTextFields();

            query ="select * from patient where id>'"+startingPoint[0]+"'";
            ResultSet resultSet=stm.executeQuery(query);

            while(resultSet.next()) {
                data.add(new Patient(
                    Integer.parseInt(resultSet.getString(1)),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4).charAt(0),
                    Integer.parseInt(resultSet.getString(5)),
                    Integer.parseInt(resultSet.getString(6)),
                    Integer.parseInt(resultSet.getString(7)),
                    resultSet.getDouble(8),
                    resultSet.getDouble(9)
                    ));

                patients.add(new Patient(
                    Integer.parseInt(resultSet.getString(1)),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4).charAt(0),
                    Integer.parseInt(resultSet.getString(5)),
                    Integer.parseInt(resultSet.getString(6)),
                    Integer.parseInt(resultSet.getString(7)),
                    resultSet.getDouble(8),
                    resultSet.getDouble(9)
                    ));
                    startingPoint[0]=Integer.parseInt(resultSet.getString(1));
                    /*NOTE: Bill fields return 0 until program is reloaded. Unknown how to fix. */
            }
        }
    }
}
