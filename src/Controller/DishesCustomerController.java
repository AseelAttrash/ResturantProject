package Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Model.Component;
import Model.Dish;
import Model.Order;
import Model.Restaurant;
import View.Feedback;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
//dishes customer page controller
public class DishesCustomerController implements Initializable{
	//page parameters/fields/buttons/table
	public static Dish dish;
	private ArrayList<Dish> mydishes=new ArrayList<>();
    @FXML
    private TableView<Dish> DishesTable;
    @FXML
    private TableColumn<Dish, String> DishIdClmn;
    @FXML
    private TableColumn<Dish, String> dishNameClmn;
    @FXML
    private TableColumn<Dish, String> TypeClmn;
    @FXML
    private TableColumn<Dish, Void> compsClmn;
    @FXML
    private TableColumn<Dish, String> timeClmn;
    @FXML
    private TableColumn<Dish, String> priceClmn;
    @FXML
    private TableColumn<Dish, String> DishIdClmn1;
    @FXML
    private Button AddDish;
    @FXML
    private Button removeButton;
    @FXML
    private Button BackButton;
    @FXML
    private ComboBox<Dish> DishesCombo;
    
    //add dish to customer order action method
    @FXML
    void AddAction(ActionEvent event) {
    	// if he didn't chose a dish to add
        if(DishesCombo.getSelectionModel().getSelectedIndex() == -1)
        {
  	Feedback.message("Error", "you must choose a Dish to add!");
  	return;
       }
        //create the new dish with the components that has the customer chose
        Dish co=DishesCombo.getSelectionModel().getSelectedItem();
        ArrayList<Component>comps=new ArrayList<>(co.getComponenets());
       Dish di=new Dish(co.getDishName(),co.getType(),comps,co.getTimeToMake());
       //update id
       int id=0;
       ArrayList<Integer>Temp=new ArrayList<Integer>(Restaurant.getInstance().getDishes().keySet());
       for(int i=0;i<Temp.size();i++) {
    	   if(id<Temp.get(i)) {
    		  id=Temp.get(i) ;
    	   }
       }
       di.setId(id);
       //add dish to restaurnat and to customer orders
       Restaurant.getInstance().addDish(di); 
       mydishes.add(di);
       //fill the table and update file also clear the fields
       Restaurant.updatefile();
       fill();
       clearFields();
        DishesTable.refresh();
    }
//remove dish from customer order action method
    @FXML
    void RemoveAction(ActionEvent event) {
    	// if he didn't chose a dish to remove
      	if (DishesTable.getSelectionModel().getSelectedIndex() == -1) {
			Feedback.message("error", "please choose a Dish to remove");
			return;
		}
      	//remove dish from customer dishes
      	mydishes.remove(DishesTable.getSelectionModel().getSelectedItem());
      //fill the table and update file also clear the fields
      	Restaurant.updatefile();
      	fill();
        DishesTable.refresh();
        clearFields();
    }

     
    //initialize method fills the combo box and table 
    public void initialize(URL arg0, ResourceBundle arg1) {
    	DishesCombo.setItems(FXCollections.observableArrayList(Restaurant.getInstance().getDishes().values()));
    	DishIdClmn.setCellValueFactory(new PropertyValueFactory<Dish, String>("id"));
    	dishNameClmn.setCellValueFactory(new PropertyValueFactory<Dish, String>("dishName"));
    	TypeClmn.setCellValueFactory(new PropertyValueFactory<Dish, String>("type"));
    	timeClmn.setCellValueFactory(new PropertyValueFactory<Dish, String>("timeToMake"));
		priceClmn.setCellValueFactory(new PropertyValueFactory<Dish, String>("price"));
		Callback<TableColumn<Dish, Void>, TableCell<Dish, Void>> CompClmn = new Callback<TableColumn<Dish, Void>, TableCell<Dish, Void>>() {
			@Override
			public TableCell<Dish, Void> call(final TableColumn<Dish, Void> param) {
				final TableCell<Dish, Void> cellneigh = new TableCell<Dish, Void>() {
					private final Button showcomp1 = new Button("Show/Edit");
					private final HBox pane1 = new HBox(1, showcomp1);
					{
						pane1.setAlignment(Pos.CENTER);
						showcomp1.setOnAction((ActionEvent event) -> {
							dish=getTableView().getItems().get(getIndex());
							Stage st= new Stage();
							try {
								Parent root = FXMLLoader.load(getClass().getResource("/View/ComponentsCustomer.fxml"));
							
								Scene scene = new Scene(root);
								st.setScene(scene);
								st.setResizable(false);
								st.showAndWait();
							} catch (Exception e) {
								e.printStackTrace();
							}
						});
					}
					@Override
					protected void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						setGraphic(empty ? null : pane1);
					}
				};
				return cellneigh;
			}
		};
		compsClmn.setCellFactory(CompClmn);
	}


//clear fields method
public void clearFields() {
	DishesCombo.valueProperty().set(null);
}
//fill dishes table 
void fill() {
	ObservableList<Dish> types = FXCollections.observableArrayList(mydishes);
	DishesTable.setItems(types);

}
//create order action method
@FXML
void createOrderAction(ActionEvent event)
{
	//if theres no dishes
	if(mydishes.size()==0) {
		Feedback.message("order", "can not make order because no dishes added");
		return;
	}
	//add dishes to restaurant
	for(Dish dis: mydishes) {
		Restaurant.getInstance().addDish(dis);
	} 
	//set id
	 int id=0;
     ArrayList<Integer>Temp=new ArrayList<Integer>(Restaurant.getInstance().getOrders().keySet());
     for(int i=0;i<Temp.size();i++) {
  	   if(id<Temp.get(i)) {
  		  id=Temp.get(i) ;
  	   }
     }
     //create order to add
	Order myorder= new Order(LogInController.currentcustomer,mydishes,null);
	myorder.setId(id+1);
	//add order 
	if(!Restaurant.getInstance().addOrder(myorder)) {
		Feedback.message("add order", "create order was failed");
		return;
	}
	//update file
	Feedback.message("create order", "Done");
	Restaurant.updatefile();
	back();

}
@FXML
void backAction(ActionEvent event) {
	back();

}
//back to shopping card page
void back() {
	mydishes.clear();
	try {
		Parent root = FXMLLoader.load(getClass().getResource("/View/ShoppingCartPage.fxml"));
	
		Scene scene = new Scene(root);
		Main.PrimaryStage.setScene(scene);
	} catch (Exception e) {
		e.printStackTrace();
	}
}
}
