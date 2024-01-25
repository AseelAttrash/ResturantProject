package Controller;

import Model.Restaurant;
import View.Feedback;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
//get deliveries by per type page controller
public class getnumofdelspertypeController {
    //page buttons
    @FXML
    private Button expressDeliveryBtn;

    @FXML
    private Button regularDeliveryBtn;
        
    
    
    
    @FXML
    //back to Manager page Button
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
    
    
    //shows number of express deliveries
    @FXML
    void expressDeliveryAction(ActionEvent event) {
    	Feedback.message("Express deliveries", "Number of Express Delivery: "+Restaurant.getInstance().getNumberOfDeliveriesPerType().get("Express delivery"));
    }
    //shows number of regular deliveries
    @FXML
    void regularDeliveryAction(ActionEvent event) {
    	Feedback.message("Regular deliveries", "Number of Regular Delivery: "+Restaurant.getInstance().getNumberOfDeliveriesPerType().get("Regular delivery"));
    }
}
