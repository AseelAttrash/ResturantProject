package Controller;
import java.net.URL;
import java.util.ResourceBundle;

import Model.Customer;
import Model.Dish;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
//get relevant dishes page controller (manager)
public class getdishcontroller  implements Initializable{
   //page buttons and combo box
    @FXML
    private Button backBtn;
    @FXML
    private ComboBox<Customer> customerCombo;
    @FXML
    private Button nextBtn;

    
    //methods
    
    //next action shows relevant dishes to the chosen customer
    @FXML
    void NextAction(ActionEvent event) {
    	// if he didn't chose a customer
	if(customerCombo.getSelectionModel().getSelectedIndex() == -1 ) {
        	
    		Feedback.message("Error", "you must fill all fields!");
    		return;
    	}
	
	Customer dp = customerCombo.getSelectionModel().getSelectedItem();
	Stage window=new Stage();
	window.initModality(Modality.APPLICATION_MODAL);
	window.setTitle("Relevant Dishes");
	window.setWidth(400);
	window.setHeight(300);
	//call method and get a list of relevant dishes
		ListView<Dish>de=new ListView<>();
		ObservableList<Dish>ops=FXCollections.observableArrayList(Restaurant.getInstance().getReleventDishList(dp));
		//show list on the screen
		de.setItems(ops);
    	Scene scene=new Scene (de);
    	window.setScene(scene);
    	window.showAndWait();
    	customerCombo.valueProperty().set(null);
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
   //initialize method fills commbo box
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<Customer> dels = FXCollections
				.observableArrayList(Restaurant.getInstance().getCustomers().values());
		customerCombo.setItems(dels);
		
	}

}
