package Controller;
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
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import Model.Customer;
import Model.Delivery;
import Model.Dish;
import Model.Order;
import Model.Restaurant;
import View.ExternalWindow;
import View.Feedback;
public class OrderController implements Initializable{
//order page controller
	//page parameters/fields/buttons/table
	@FXML
    private Button AddOrderButton;
    @FXML
    private ListView<Dish> DishesList;
    @FXML
    private TableView<Order> OrdersTable;
    @FXML
    private TableColumn<Order, String> IdClmn;
    @FXML
    private TableColumn<Order, String> CustomerClmn;
    @FXML
    private TableColumn<Order, String> DeliveryClmn;
    @FXML
    private TableColumn<Order, Void> DishesClmn;
    @FXML
    private ComboBox<Delivery> DeliveryCombo;
    @FXML
    private Button RemoveOrderButton;
    @FXML
    private Button UpdateOrderButton;
    @FXML
    private ComboBox<Customer> CustomerCombo;
	private boolean upd=false;
    //fill table and lists ,comboboxs method
	public void fill() {
		ObservableList<Order> datadish = FXCollections
				.observableArrayList(Restaurant.getInstance().getOrders().values());
		IdClmn.setCellValueFactory(new PropertyValueFactory<Order, String>("id"));
		CustomerClmn.setCellValueFactory(new PropertyValueFactory<Order, String>("customer"));
		DeliveryClmn.setCellValueFactory(new PropertyValueFactory<Order, String>("delivery"));

       //fill dishes column in table
		Callback<TableColumn<Order, Void>, TableCell<Order, Void>> cellF = new Callback<TableColumn<Order, Void>, TableCell<Order, Void>>() {
			@Override
			public TableCell<Order, Void> call(final TableColumn<Order, Void> param) {
				final TableCell<Order, Void> cell = new TableCell<Order, Void>() {
                    //show button on table to show a list 
					private final Button showcomp = new Button("Show");

					private final HBox pane = new HBox(1, showcomp);

					{
						pane.setAlignment(Pos.CENTER);

						showcomp.setOnAction((ActionEvent event) -> {
							Order data = getTableView().getItems().get(getIndex());
							List<Dish> Delprsons = data.getDishes();
							ArrayList<Dish> dps = new ArrayList<Dish>();
							for (Dish dp : Delprsons) {
								dps.add(dp);
							}
							//external window to show dishes
							ExternalWindow.dishes(dps);
						});
					}
					@Override
					protected void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);

						setGraphic(empty ? null : pane);
					}
				};
				return cell;
			}
		};
		DishesClmn.setCellFactory(cellF);
		OrdersTable.setItems(datadish);
	}
    
    //initialize method starts the page and fills the table and lists ,comboxos
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<Customer> custs = FXCollections
				.observableArrayList(Restaurant.getInstance().getCustomers().values());
		ArrayList<Delivery> alld=new ArrayList<>();
		for(Delivery d: Restaurant.getInstance().getExpressdeliveries().values())
			alld.add(d);
		for(Delivery d: Restaurant.getInstance().getRegulardeliveries().values())
			alld.add(d);
		ObservableList<Delivery> Delivers = FXCollections
				.observableArrayList(alld);
		CustomerCombo.setItems(custs);
		DeliveryCombo.setItems(Delivers);
		ObservableList<Dish> dishes = FXCollections
				.observableArrayList(Restaurant.getInstance().getDishes().values());
		DishesList.setItems(dishes);
		DishesList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	fill();
	}
	//clear fields method
	public void clearFields() {
		DeliveryCombo.valueProperty().set(null);
		CustomerCombo.valueProperty().set(null);
		DishesList.getSelectionModel().clearSelection();
		}
	//add order action button
    @FXML
    void AddOrderAction(ActionEvent event) {
    	//if the manager didn't fill all the fields
    	if( CustomerCombo.getSelectionModel().getSelectedIndex() == -1 ||DishesList.getSelectionModel().getSelectedIndex()==-1)
    	{
    		Feedback.message("Error", "you must fill all the fields!");
   		return;
    	}
    	//if we are not updating an order
		  if(upd==true)
		    {
		    	Feedback.message("error", "You cant add while updating!");
		    	return;
		    }
        //create new order to add 
		ArrayList<Dish> compsarraylist = new ArrayList<>(DishesList.getSelectionModel().getSelectedItems());
     Order o = new Order(CustomerCombo.getValue(),compsarraylist,null);
     //add order to restaurant
  	if (!Restaurant.getInstance().addOrder(o)) {
		Feedback.message("error", "new Order is not added!");
		return;
  	}
  //fill the table and update file also clear the fields
	Feedback.message("Add", "new Order has been added");
	Restaurant.updatefile();
	fill();
	OrdersTable.refresh();
	clearFields();
    }
    //remove order button action
    @FXML
    void RemoveOrderActiob(ActionEvent event) {
    	//check if the manager has Selected an order to remove
    	if (OrdersTable.getSelectionModel().getSelectedIndex() == -1) {
    		Feedback.message("error", "please choose an Order to remove");
    		return;
    	}
    	//check if the manager is updating an order
		if(upd==true)
    	{
    		Feedback.message("Error", "you can't remove while updating!");
			return;
    	} 
    	//remover order from restaurant
	if (!Restaurant.getInstance().removeOrder(OrdersTable.getSelectionModel().getSelectedItem())) {
		Feedback.message("error", "can not remove Order ");
		return;
	}else {//fill the table and update file also clear the fieldss
		Feedback.message("removed!", "Order has been removed!");
		Restaurant.updatefile();
	}
		fill();
		OrdersTable.refresh();
		clearFields();
    	
    }
      //update order action button
    @FXML
    void UpdateOrderAction(ActionEvent event) {
    	//check if the manager has Selected an order to update
    	if (OrdersTable.getSelectionModel().getSelectedIndex() == -1) {
			Feedback.message("error", "please choose an Order to update");
			return;
		}
    	  //fill the fields with the order that the manager has chose
		Order order1 = OrdersTable.getSelectionModel().getSelectedItem();
		if(!upd) {
			
			for(Dish o:order1.getDishes()) {
				
				DishesList.getSelectionModel().select(o);}
			CustomerCombo.getSelectionModel().select(order1.getCustomer());
			DeliveryCombo.getSelectionModel().select(order1.getDelivery());


			upd=true;
			Feedback.message("update", "update related field and click update!");

			return;
		}
		//check if one of the fields is null 
    	if( CustomerCombo.getSelectionModel().getSelectedIndex() == -1 ||DishesList.getSelectionModel().getSelectedIndex()==-1)
    	{
    		Feedback.message("Error", "you must fill all the fields!");
   		return;
    	}
    	//update order fields
		Restaurant.getInstance().getOrders().get(order1.getId()).setCustomer(CustomerCombo.getValue());;
		Restaurant.getInstance().getOrders().get(order1.getId()).setDelivery(DeliveryCombo.getValue());;
		ArrayList<Dish>Ordsarraylist=new ArrayList<>(DishesList.getSelectionModel().getSelectedItems());
		Restaurant.getInstance().getOrders().get(order1.getId()).SetDishes(Ordsarraylist);;
		//fill the table and update file also clear the fields
		clearFields();
		 upd = false;
		Feedback.message("Updated", "Order Updated!!");
		Restaurant.updatefile();
		fill();
		OrdersTable.refresh();
    }
    

          //back action to manager page home
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
}
