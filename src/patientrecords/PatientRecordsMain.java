/*CSC-285 Final Project "Patient Records" by Arti K. */
package patientrecords;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class PatientRecordsMain extends Application {

    private Connection conn;
    private Statement stm;
    private int [] startingPoint = new int []{0};
    private ArrayList<Patient> patients = new ArrayList<Patient>();
    private PatientRecordsGUI patientRecords = new PatientRecordsGUI();

    @Override
    public void start(Stage primaryStage) throws Exception {
        connectToDataBase();
        listPatients();
        patients.addAll(patientRecords.data);

        System.out.println(patientRecords.data.toString());

        Scene scene = new Scene(patientRecords, 1250, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Patient Records");
        primaryStage.show();

        patientRecords.addPatient.setOnAction(e -> {
            new addPatient(primaryStage, stm, patientRecords.data, startingPoint, patients);
        });

        patientRecords.deletePatient.setOnAction(e -> {
            new deletePatient(primaryStage, stm, patientRecords.data, patients);
        });

        /*Search patient records */
        patientRecords.searchButton.setOnAction(e -> {
            searchRecords(primaryStage);
        });

        patientRecords.tfSearch.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER) {
                searchRecords(primaryStage);
            }
        });

        /*Reset the search fields */
        patientRecords.resetFields.setOnAction(e -> {
            resetFields();
        });
        patientRecords.resetButton.setOnAction(e -> {
            resetFields();
        });

        /*Exit app */
        patientRecords.exit.setOnAction(e -> {
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void resetFields() {
        patientRecords.data.clear();
        patientRecords.data.addAll(patients);
    }

    public void connectToDataBase()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/PatientRecords","root","");
            stm = conn.createStatement();

        }catch(Exception exception)
        {
            System.out.println(exception.getMessage());
        }
    }

    public void listPatients() {
        try {
            String query = "SELECT * FROM patient WHERE id >'"+startingPoint[0]+"'";
            ResultSet resultSet = stm.executeQuery(query);

            while(resultSet.next()) {
                patientRecords.data.add(new Patient(
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
            }
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void searchRecords(Stage primaryStage) {
        int index = -1;
        boolean exist = false;
        ArrayList<Integer> indices = new ArrayList<>();

        if(patientRecords.tfSearch.getText().equals("")) {
            patientRecords.tfSearch.requestFocus();
        }
        else {
            /*Search patient by ID */
            if (patientRecords.cbSearch.getValue().equals("ID")) {
                for (int i = 0; i < patients.size(); i++) {
                    if (patients.get(i).getId() == Integer.parseInt(patientRecords.tfSearch.getText())) {
                        exist = true;
                        index = i;
                    }
                }
                if (exist == true) {
                    patientRecords.data.clear();
                    patientRecords.data.add(patients.get(index));
                    startingPoint[0] = 0;
                }
                else {
                    Alert patientDNE = new Alert(Alert.AlertType.ERROR);
                    patientDNE.setTitle("Search Patient");
                    patientDNE.setHeaderText("ERROR: Patient does not exist!");
                    patientDNE.showAndWait();
                }
            }
            /*Search patient by first name */
            else if (patientRecords.cbSearch.getValue().equals("First Name")) {
                for (int i = 0; i < patients.size(); i++) {
                    if (patients.get(i).getFirstName().equals(patientRecords.tfSearch.getText())) {
                        exist = true;
                        indices.add(i);
                    }
                }
                if (exist == true) {
                    patientRecords.data.clear();
                    for (int i = 0; i < indices.size(); i++) {
                        patientRecords.data.add(patients.get(indices.get(i)));
                    }
                    startingPoint[0] = 0;
                }
                else {
                    Alert patientDNE = new Alert(Alert.AlertType.ERROR);
                    patientDNE.setTitle("Search Patient");
                    patientDNE.setHeaderText("ERROR: Patient does not exist!");
                    patientDNE.showAndWait();
                }
            }
            /*Search patient by last name */
            else if (patientRecords.cbSearch.getValue().equals("Last Name")) {
                for (int i = 0; i < patients.size(); i++) {
                    if (patients.get(i).getLastName().equals(patientRecords.tfSearch.getText())) {
                        exist = true;
                        indices.add(i);
                    }
                }
                if (exist == true) {
                    patientRecords.data.clear();
                    for (int i = 0; i < indices.size(); i++) {
                        patientRecords.data.add(patients.get(indices.get(i)));
                    }
                    startingPoint[0] = 0;
                }
                else {
                    Alert patientDNE = new Alert(Alert.AlertType.ERROR);
                    patientDNE.setTitle("Search Patient");
                    patientDNE.setHeaderText("ERROR: Patient does not exist!");
                    patientDNE.showAndWait();
                }
            }
            /*Search patients by floor */
            else if (patientRecords.cbSearch.getValue().equals("Floor")) {
                for (int i = 0; i < patients.size(); i++) {
                    if (patients.get(i).getFloor() == Integer.parseInt(patientRecords.tfSearch.getText())) {
                        exist = true;
                        indices.add(i);
                    }
                }
                if (exist == true) {
                    patientRecords.data.clear();
                    for (int i = 0; i < indices.size(); i++) {
                        patientRecords.data.add(patients.get(indices.get(i)));
                    }
                    startingPoint[0] = 0;
                }
                else {
                    Alert patientDNE = new Alert(Alert.AlertType.ERROR);
                    patientDNE.setTitle("Search Patient");
                    patientDNE.setHeaderText("ERROR: Patient does not exist!");
                    patientDNE.showAndWait();
                }
            }
        }
    }
}