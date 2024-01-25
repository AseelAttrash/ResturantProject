package Controller;
import java.net.URL;
import java.util.ResourceBundle;

import Model.Cook;
import Model.Restaurant;
import Utils.Expertise;
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
//get cooks by expertise page controller
public class GetCookByExpertiseController implements Initializable{
	//page parameters/fields/buttons
    @FXML
    private ComboBox<Expertise> cookexpComb;
    @FXML
    private Button backID;
    @FXML
    private Button getcookButton;

    
    
    //initialize method , fills combo box with cook expertises
	public void initialize(URL arg0, ResourceBundle arg1) {
		  ObservableList<Expertise> ExpertiseList = FXCollections.observableArrayList(Expertise.values());
		  
	cookexpComb.setItems(ExpertiseList);
	}
	
	
	
	//back actiong method , returns to manager or customer home page
	@FXML
    void backAction(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("/View/ManagerPage.fxml"));
			if(LogInController.currentcustomer!=null)
				root = FXMLLoader.load(getClass().getResource("/View/CustomerMenu.fxml"));
			Scene scene = new Scene(root);
			Main.PrimaryStage.setScene(scene);
		} catch (Exception e) {

			e.printStackTrace();
		}
    }
	//get cook by expertise button
    @FXML
    void getCookAction(ActionEvent event) {
    	//if he didnt choose an expertise
    	if(cookexpComb.getSelectionModel().getSelectedIndex() == -1 ) {
        	
    		Feedback.message("Error", "you must fill all fields!");
    		return;
    	}
    	Expertise dp = cookexpComb.getSelectionModel().getSelectedItem();
    	Stage window=new Stage();
    	window.initModality(Modality.APPLICATION_MODAL);
    	window.setTitle("Cooks By Expertise");
    	window.setWidth(400);
    	window.setHeight(300);
    	// list of the cooks
    		ListView<Cook>de=new ListView<>();
    		ObservableList<Cook>ops=FXCollections.observableArrayList(Restaurant.getInstance().GetCooksByExpertise(dp));
    		//show list of cooks
    		de.setItems(ops);
        	Scene scene=new Scene (de);
        	window.setScene(scene);
        	window.showAndWait();
    }
	
	
}
