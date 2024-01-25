package Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Model.Dish;
import Model.Order;
import Model.Restaurant;
import View.ExternalWindow;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
//shopping card page controller
public class ShoppingCartController implements Initializable{
      //page parameters/fields/buttons/table
    @FXML
    private TableView<Order> tableshopping;
    @FXML
    private TableColumn<Order, String> orderidClmn;
    @FXML
    private TableColumn<Order, Void> dishesClmn;
    @FXML
    private TableColumn<Order, String> deliveryClmn;
    @FXML
    private Button backButton;
    @FXML
    private Button order;
    @FXML
    private Button order1;
    //back to customer home page button
    @FXML
    void backAction(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("/View/CustomerMenu.fxml"));
		
			Scene scene = new Scene(root);
			Main.PrimaryStage.setScene(scene);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    }
    
    //make order button
    @FXML
    void makeorderAction(ActionEvent event) {
    	//choose dishes page
      	try {
    			Parent root = FXMLLoader.load(getClass().getResource("/View/DishesCustomer.fxml"));
    		
    			Scene scene = new Scene(root);
    			Main.PrimaryStage.setScene(scene);
    		} catch (Exception e) {
    			// TODO: handle exception
    			e.printStackTrace();
    		}
    }
    //delete order button action
    @FXML
    void DeleteOrderAction(ActionEvent event)
    {
    	//check if he didnt choose an order to delete
    	if(tableshopping.getSelectionModel().getSelectedIndex()==-1) {
    		Feedback.message("remove", "please choose order to remove");
    		return;
    	}
    	//remove order
    	if(!Restaurant.getInstance().removeOrder(tableshopping.getSelectionModel().getSelectedItem())) {
    		Feedback.message("remove", "error while removing your order");
    		return;
    	}
    	//fill the table and update file also clear the fields
    	Restaurant.updatefile();
    	Feedback.message("remove", "your order removed");
    	tableshopping.refresh();
		return;

    }


   //initialize method fills order table
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		orderidClmn.setCellValueFactory(new PropertyValueFactory<Order, String>("id"));
		deliveryClmn.setCellValueFactory(new PropertyValueFactory<Order, String>("delivery"));
		Callback<TableColumn<Order, Void>, TableCell<Order, Void>> cellF = new Callback<TableColumn<Order, Void>, TableCell<Order, Void>>() {
             //fill dishes column
			@Override
			public TableCell<Order, Void> call(final TableColumn<Order, Void> param) {
				final TableCell<Order, Void> cell = new TableCell<Order, Void>() {
                   //button to show dishes list
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
		dishesClmn.setCellFactory(cellF);
		ArrayList<Order>arr=new ArrayList<>();
		for(Order d: Restaurant.getInstance().getOrders().values()) {
			if(d.getCustomer().equals(LogInController.currentcustomer))
				arr.add(d);
		}
		ObservableList<Order> orde = FXCollections
				.observableArrayList(arr);
		tableshopping.setItems(orde);
		tableshopping.refresh();
	}
}
