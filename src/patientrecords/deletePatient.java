package patientrecords;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class deletePatient {
    private TextField tfID = new TextField();
    private Label idLabel = new Label("ID: ");

    private Button deleteButton = new Button ("Delete");
    private Button cancelButton = new Button("Cancel");

    /*Define the visual aspect of the Delete Patient screen */
    private GridPane gridPane = new GridPane();
    private VBox vbox = new VBox();
    private HBox hbox = new HBox();


    public deletePatient(Stage primaryStage, java.sql.Statement stm, ObservableList<Patient> data, ArrayList<Patient> patients) {
        deleteButton.setPrefWidth(100);
        cancelButton.setPrefWidth(100);

        hbox.setSpacing(20);
        hbox.getChildren().addAll(deleteButton, cancelButton);

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(15);
        gridPane.setVgap(20);
        gridPane.setPadding (new Insets(10,20,0,0));
        gridPane.add(idLabel,0,0);
        gridPane.add(tfID,1,0);
        gridPane.add(hbox,1,1);
        GridPane.setHalignment(hbox,HPos.RIGHT);

        vbox.setSpacing(25);
        vbox.setPadding(new Insets(10,20,0,0));
        vbox.getChildren().addAll(gridPane);

        Scene scene = new Scene(vbox,300,150);
        Stage mainStage = new Stage();
        mainStage.initModality(Modality.APPLICATION_MODAL);
        mainStage.initOwner(primaryStage);
        
        mainStage.setScene(scene);
        mainStage.setTitle("Delete Patient");
        mainStage.show();

        deleteButton.setOnAction(e -> {
            try {
                deletePatient(primaryStage, stm, data, patients);
            }catch (Exception ex) {
                ex.getMessage();
            }
        });

        /*Close the Delete Patient screen when pressing the cancel button */
        cancelButton.setOnAction(e -> {
            mainStage.close();
        });

        vbox.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ESCAPE) {
                mainStage.close();
            }
            else if(e.getCode() == KeyCode.ENTER) {
                try {
                    deletePatient(primaryStage, stm, data, patients);
                } catch(Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
    }

    /*Method to delete patient from record */
    public void deletePatient(Stage primaryStage, java.sql.Statement stm, ObservableList<Patient> data, ArrayList<Patient> patients) throws Exception {
        boolean exist = false;
        String query = "DELETE FROM patient WHERE ID = '"+Integer.parseInt(tfID.getText())+"'";

        /*Create a dialog box which confirms whether you want to delete the record. */
        int result = JOptionPane.showOptionDialog(null, "Are you sure you want to delete this record?","Delete Record",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (result == JOptionPane.YES_OPTION){
            for (int i=0;i<data.size();i++){
                if (data.get(i).getId()==Integer.parseInt(tfID.getText()))
                {
                    stm.execute(query);
                    data.remove(data.get(i));
                    patients.remove(patients.get(i));
                    exist = true;
                    break;
              
                }
            }
           }
           /*If the record exists, delete it. */
            if (exist == true){
                tfID.clear();
                Alert success = new Alert(Alert.AlertType.CONFIRMATION);
                success.setTitle("Delete Patient");
                success.setHeaderText("Patient successfully deleted!");
                success.showAndWait();
            }
            /*If the record does not exist, throw an error. */
            else{
                Alert patientDNE = new Alert(Alert.AlertType.ERROR);
                patientDNE.setTitle("Delete Patient");
                patientDNE.setHeaderText("ERROR: Patient does not exist!");
                patientDNE.showAndWait();
            }  
        }
    }
