package Controller;

//Customer Menu page Controller
import Model.Component;
import Model.Customer;

import Model.Dish;
import Model.Restaurant;
import View.Feedback;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CustomerMenuController {

	//page parameters/fields/buttons
    @FXML
    private Button ShopingcartBtn;
    @FXML
    private Button EditProButton;
    @FXML
    private Button MenuBtn;
    @FXML
    private Button SutableDishesbtn;
    @FXML
    private Button cookbyexpBtn;
    @FXML
    private Button ppulatcompBtn;
    @FXML
    private Button logoutBtn;
    
 //methods

    //moves to shopping card page
    @FXML
    void cshopping(ActionEvent event) {
    	changescreen("ShoppingCartPage");
    }
  //moves to edit profile page
    @FXML
    void editac(ActionEvent event) {
    	changescreen("EditProfilePage");
    }
    //get popular components of the restaurant 
    @FXML
    void getPopCm(ActionEvent event) {
    	//external screen
         //if theres no popular component on the restaurant
      	if(Restaurant.getInstance().getPopularComponents().size()==0) {
    		Feedback.message("Alert!!","There is no popular component yet");
    		return;
    	}
      	//show a list of the popular components
	Stage window=new Stage();
	window.initModality(Modality.APPLICATION_MODAL);
	window.setTitle("get popular component");
	window.setWidth(500);
	window.setHeight(500);
	//popular components list
	ListView<Component>dishesPro=new ListView<>();
	ObservableList<Component>dd=FXCollections.observableArrayList(Restaurant.getInstance().getPopularComponents());
	Scene sce=new Scene(dishesPro);
	window.setScene(sce);
	dishesPro.setItems(dd);
	window.showAndWait();    
	}
    
  //moves to get cook by Expertise page
    @FXML
    void getcookEx(ActionEvent event) {
    	changescreen("CookByExp");
    }

  
  //moves to Restaurant Menu page
    @FXML
    void restmenuac(ActionEvent event) {
    	changescreen("RestaurantMenuPage");
    }
    //show to the customer the dishes hes not sensitive to 
    @FXML
    void suitableDisha(ActionEvent event) 
    {	
    	// current customer
Customer c =LogInController.currentcustomer;
ListView<Dish>de=new ListView<>();
//list of suitable dishes
ObservableList<Dish>ops=FXCollections.observableArrayList(Restaurant.getInstance().getReleventDishList(c));
//show list of dishes
Stage window=new Stage();
window.initModality(Modality.APPLICATION_MODAL);
window.setTitle("Relevant Dishes");
window.setWidth(400);
window.setHeight(300);
Scene scene=new Scene (de);
window.setScene(scene);
de.setItems(ops);
window.showAndWait();
    }
//log out button changes screen to log in main page
    @FXML
    void logoutBtnact(ActionEvent event) {
    	LogInController.currentcustomer=null;
    	changescreen("LogIn");

    }
    //change screen method
    void changescreen(String pa) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("/View/"+pa+".fxml"));
		
			Scene scene = new Scene(root);
			Main.PrimaryStage.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
