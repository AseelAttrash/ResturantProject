package Controller;
//deliver page controller
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Model.Customer;
import Model.Delivery;
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


public class DeliverController implements Initializable{
   //page buttons/combo box
    @FXML
    private Button backBtn;
    @FXML
    private ComboBox<Delivery> deliveriesCombo;
    @FXML
    private Button nextBtn;
    @FXML
    
    
    
    
    //next button which manager has to click after choosing the delivery
    void NextAction(ActionEvent event) {
    	//check if he chose a delivery
if(deliveriesCombo.getSelectionModel().getSelectedIndex() == -1 ) {
        	
    		Feedback.message("Error", "you must choose a delivery!");
    		return;
    	}
//call the method from the class restaurant
Delivery dp = deliveriesCombo.getSelectionModel().getSelectedItem();
Restaurant.getInstance().deliver(dp);
Restaurant.updatefile();
Feedback.message("Done!", "Delivery has been selected as Delivered!");
deliveriesCombo.valueProperty().set(null);
    }
      //back button to manager home page
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
    //initialize method fills the combobox with the restaurant deliveries
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ArrayList<Delivery> alld=new ArrayList<>();
		for(Delivery d: Restaurant.getInstance().getExpressdeliveries().values())
			alld.add(d);
		for(Delivery d: Restaurant.getInstance().getRegulardeliveries().values())
			alld.add(d);
		ObservableList<Delivery> Delivers = FXCollections
				.observableArrayList(alld);
		deliveriesCombo.setItems(Delivers);
	}

}
