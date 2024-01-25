package Controller;
import java.net.URL;
import java.util.ResourceBundle;

import Model.Delivery;
import Model.DeliveryPerson;
import Model.Restaurant;
import View.Feedback;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
//get Deliveries By Person Controller
public class getDeliveriesByPersonController implements Initializable{
	//page parameters/fields/buttons/table
    @FXML
    private Spinner<Integer> monthSpinner;
    @FXML
    private Button NextButton;
    @FXML
    private ComboBox<DeliveryPerson> DelPersonCombo;
    @FXML
    private Button BackButton;
 
    
    
    //get deliveries by person button action
    @FXML
    void Getdelsbypersonbutton(ActionEvent event) {
        //check if the manager filled all the fields
    	if(DelPersonCombo.getSelectionModel().getSelectedIndex() == -1 ) {
    	
    		Feedback.message("Error", "you must fill all fields!");
    		return;
    	}
    	   
    		DeliveryPerson dp = DelPersonCombo.getSelectionModel().getSelectedItem();
    		
    		ListView<Delivery>de=new ListView<>();
    		//use the method from the class restaurant and show it on the screen
    		ObservableList<Delivery>ops=FXCollections.observableArrayList(Restaurant.getInstance().getDeliveriesByPerson(dp, monthSpinner.getValue()));
        	Stage window=new Stage();
        	window.initModality(Modality.APPLICATION_MODAL);
        	window.setTitle("Deliveries By person");
        	window.setWidth(400);
        	window.setHeight(300);
        	Scene scene=new Scene (de);
        	window.setScene(scene);
        	de.setItems(ops);
        	window.showAndWait();
    	
    	
    	
    }

    
    
         //back to manager page button
        @FXML
        void backAction(ActionEvent event) {
        	try {
    			Parent root = FXMLLoader.load(getClass().getResource("/View/ManagerPage.fxml"));
    		
    			Scene scene = new Scene(root);
    			Main.PrimaryStage.setScene(scene);
    		} catch (Exception e) {
    			// TODO: handle exception
    			e.printStackTrace();
    		}
        }
        
        
        
        
        
        
        //initialize method fills the combo box
    	public void initialize(URL arg0, ResourceBundle arg1) {
    		ObservableList<DeliveryPerson> dels = FXCollections
    				.observableArrayList(Restaurant.getInstance().getDeliveryPersons().values());
    		DelPersonCombo.setItems(dels);
    		
    		letsFillSPin();
    	

    	}
    	//fill spin method from 1 to 12
    	private void letsFillSPin() {
    		SpinnerValueFactory<Integer>in=new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12);
    		in.setValue(1);
    		monthSpinner.setValueFactory(in);
    	}
}
