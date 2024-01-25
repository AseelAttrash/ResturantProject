package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import Model.Dish;
import Model.Restaurant;
import View.ExternalWindow;
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
//restaurant menu page controller
public class RestaurantMenuController implements Initializable{
//page parameters/fields/buttons/table
    @FXML
    private TableView<Dish> DishesTable;
    @FXML
    private TableColumn<Dish, String> IddishClmn;
    @FXML
    private TableColumn<Dish, String> DishNameClmn;
    @FXML
    private TableColumn<Dish, String> DishTypeClmn;
    @FXML
    private TableColumn<Dish, Void> DishCompsClmn;
    @FXML
    private TableColumn<Dish, String> PriceClmn;
    @FXML
    private Button BackButton;
    @FXML
    private TableColumn<Dish, String> TimeTomakeClmn;
    
    //methods
        //back action button to customer home page
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

         //fil table method
    	public void fill() {
    		ObservableList<Dish> datadish = FXCollections
    				.observableArrayList(Restaurant.getInstance().getDishes().values());
    		IddishClmn.setCellValueFactory(new PropertyValueFactory<Dish, String>("id"));
    		DishNameClmn.setCellValueFactory(new PropertyValueFactory<Dish, String>("dishName"));
    		DishTypeClmn.setCellValueFactory(new PropertyValueFactory<Dish, String>("type"));
    		TimeTomakeClmn.setCellValueFactory(new PropertyValueFactory<Dish, String>("timeToMake"));
    		PriceClmn.setCellValueFactory(new PropertyValueFactory<Dish, String>("price"));
    		//fill components column
    		Callback<TableColumn<Dish, Void>, TableCell<Dish, Void>> cellF = new Callback<TableColumn<Dish, Void>, TableCell<Dish, Void>>() {

    		@Override
    		public TableCell<Dish, Void> call(final TableColumn<Dish, Void> param) {
    			final TableCell<Dish, Void> cell = new TableCell<Dish, Void>() {
                   //show button to show a list
    				private final Button showcomp = new Button("Show");

    				private final HBox pane = new HBox(1, showcomp);

    				{
    					pane.setAlignment(Pos.CENTER);
    				
    					showcomp.setOnAction((ActionEvent event) -> {
    						Dish data = getTableView().getItems().get(getIndex());
    						ExternalWindow.compWindow(data.getComponenets());

    					});

    				}
    			
    			};
    			return cell;
    		}
    	};
    	DishCompsClmn.setCellFactory(cellF);
    	DishesTable.setItems(datadish);
    	}

       //initialize method fills the table
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			fill();
		}
}
