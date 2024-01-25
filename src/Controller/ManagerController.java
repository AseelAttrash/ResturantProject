package Controller;
import Model.Component;
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
//manager home page controller
public class ManagerController {
    //page buttons
    @FXML
    private Button compsBtn;
    @FXML
    private Button CooksBtn;
    @FXML
    private Button CustomersBtn;
    @FXML
    private Button deliverBtn;
    @FXML
    private Button DelyPersonsBtn;
    @FXML
    private Button DelyAreasBtn;
    @FXML
    private Button DishesBtn;
    @FXML
    private Button OrdersBtn;
    @FXML
    private Button DeliveriesBtn;
    @FXML
    private Button getdeliveriesbypersonbutton;
    @FXML
    private Button profitRelButton;
    @FXML
    private Button MenuBtn;
    @FXML
    private Button EditProfileBtn;
    @FXML
    private Button NumOfDeliveriesBtn;
    @FXML
    private Button ByexpertBtn;
    @FXML
    private Button AllMachineBtn;
    @FXML
    private Button PopularCompBtn;
    @FXML
    private Button revenueBtn;
    @FXML
    private Button logoutButtn;
    @FXML
    private Button getrelvantBtn;
   
    
    //methods 
    
    //move to manage component page
    @FXML
    void ComponentsAction(ActionEvent event) {
    	changescreen("ComponentPage");

    }
    //move to manage cooks page
    @FXML
    void CooksAction(ActionEvent event) {
    	changescreen("CookPage");

    }
    //move to manage customer page
    @FXML
    void CustomerAction(ActionEvent event) {
    	changescreen("CustomerPage");

    }
    //move to manage delivery areas page
    @FXML
    void DelAreasAction(ActionEvent event) {
    	changescreen("DeliveryAreaPage");

    }
   // move to manager delivery persons page
    @FXML
    void DelPersonsAction(ActionEvent event) {
    	changescreen("DeliveryPersonPage");

    }
    //move to manage deliveries page
    @FXML
    void DeliveriesAction(ActionEvent event) {
    	changescreen("DeliveryPage");

    }
    //move to manage dishes page 
    @FXML
    void DishesAction(ActionEvent event) {
    	changescreen("DishPage");

    }
    //move to manage orders page
    @FXML
    void OrdersAction(ActionEvent event) {
    	changescreen("OrderPage");

    }
  //get popular components of the restaurant
    @FXML
    void PopularComponents(ActionEvent event) {
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
	//popular components lis
	ListView<Component>dishesPro=new ListView<>();
	ObservableList<Component>dd=FXCollections.observableArrayList(Restaurant.getInstance().getPopularComponents());
	Scene sce=new Scene(dishesPro);
	window.setScene(sce);
	dishesPro.setItems(dd);
	window.showAndWait();
    
    }
    //move to create all machine page
    @FXML
    void createAllMachineAction(ActionEvent event) {
    	changescreen("CreateMachine");

    }
    //move to get cooks by expertise page
    @FXML
    void getCooksByexpAction(ActionEvent event) {
    	changescreen("CookByExp");

    }
    //move to get num of deliveries per type page
    @FXML
    void getNumOfDeliveriesAction(ActionEvent event) {
    	changescreen("getnumofdelspertype");

    }
    //move to deliver page
    @FXML
    void DeliverAction(ActionEvent event) {
    	changescreen("Deliver");
    }
    //move to get deliveries by person page
    @FXML
    void getdeliveriesbypersonAction(ActionEvent event) {
    	changescreen("getDeliveriesByPersonPage");
    }

     //move to get relevant dish page
    @FXML
    void GetrelevantdishAction(ActionEvent event) {
    	changescreen("getRelevantDish");
    }
     //shows profit relation on the screen
    @FXML
    void profitRelationAction(ActionEvent event) {
    	//external screen
    	Stage window=new Stage();
    	window.initModality(Modality.APPLICATION_MODAL);
    	window.setTitle("profit relation");
    	window.setWidth(400);
    	window.setHeight(300);
    	//show profit relation list on screen
    	ListView<Dish>dishesPro=new ListView<>();
    	ObservableList<Dish>dd=FXCollections.observableArrayList(Restaurant.getInstance().getProfitRelation());
    	Scene sce=new Scene(dishesPro);
    	window.setScene(sce);
    	dishesPro.setItems(dd);
    	window.showAndWait();
    	
    }
     //shows revenue from express deliveries
    @FXML
    void revenueAction(ActionEvent event) {

    Feedback.message("Revenue From express delviery", ""+Restaurant.getInstance().revenueFromExpressDeliveries());
    }
    //log out button returns to log in main page
    @FXML
    void logoutt(ActionEvent event) {
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
			// TODO: handle exception
			e.printStackTrace();
		}
    }
}
