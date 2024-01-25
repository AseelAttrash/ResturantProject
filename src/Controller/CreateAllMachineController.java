package Controller;
//create all machine page controller
import java.net.URL;
import java.util.ResourceBundle;
import java.util.TreeSet;

import Model.Delivery;
import Model.DeliveryArea;
import Model.DeliveryPerson;
import Model.Order;
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
import javafx.scene.control.SelectionMode;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CreateAllMachineController implements Initializable{
	//page parameters/fields/buttons/combox
	  @FXML
	    private Button NextButton;
	    @FXML
	    private ComboBox<DeliveryPerson> DeliveryPersonsCombo;
	    @FXML
	    private ComboBox<DeliveryArea> DelAreaCombo;
	    @FXML
	    private Button BackButton;
	    @FXML
	    private ListView<Order> OrdersList;
	    @FXML
	    
	    //methods
	    
	    // next action which show the results on the screen after clicking it
	    void NextAction(ActionEvent event) {
	    	
	    	//check if the manager didn't fill all the fields
	    	if(DeliveryPersonsCombo.getSelectionModel().getSelectedIndex()==-1|| DelAreaCombo.getSelectionModel().getSelectedIndex()==-1 || OrdersList.getSelectionModel().getSelectedIndex()==-1) {
	    		
	    		Feedback.message("Error", "you must fill all fields!");
	    		return;	
	    	}
	    	//the orders,delivery area ,delivery person that has been chosen
	    	TreeSet<Order>orders=new TreeSet<>(OrdersList.getSelectionModel().getSelectedItems());
	    	DeliveryArea da=DelAreaCombo.getSelectionModel().getSelectedItem();
	    	DeliveryPerson dp=DeliveryPersonsCombo.getSelectionModel().getSelectedItem();
			ListView<Delivery>de=new ListView<>();
			//create machine result
    		ObservableList<Delivery>ops=FXCollections.observableArrayList(Restaurant.getInstance().createAIMacine(dp, da, orders));
        	//show on the screen
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
	//initialize method starts page and fill the comboboxes,order list
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<DeliveryPerson> dels = FXCollections
				.observableArrayList(Restaurant.getInstance().getDeliveryPersons().values());
		DeliveryPersonsCombo.setItems(dels);
		ObservableList<DeliveryArea> areas = FXCollections
				.observableArrayList(Restaurant.getInstance().getAreas().values());
		DelAreaCombo.setItems(areas);
		ObservableList<Order> orders = FXCollections
				.observableArrayList(Restaurant.getInstance().getOrders().values());
		OrdersList.setItems(orders);
	OrdersList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}
		
}
