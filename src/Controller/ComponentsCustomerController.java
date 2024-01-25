
package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import Model.Component;
import Model.Dish;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
//Customer dish component
public class ComponentsCustomerController implements Initializable{
	//page parameters/fields/buttons/table
    @FXML
    private TableView<Component> ComponentTable;
    @FXML
    private Button backbtn;
    @FXML
    private TableColumn<Component, String> IDClmn;
    @FXML
    private TableColumn<Component, String> NameClmn;
    @FXML
    private TableColumn<Component, String> LactoseClmn;
    @FXML
    private TableColumn<Component, String> GlutenClmn;
    @FXML
    private TableColumn<Component, String> PriceClmn;
    @FXML
    private Button addCompBtn;
    @FXML
    private Button removeCompBtn;
    @FXML
    private ComboBox<Component> CompsCombobox;
    @FXML
    private Button backButton;
  
    //action methods
    
    
    
    //add component to Customer Dish method
    @FXML
    void AddAction(ActionEvent event) {
    	//check if the customer had chose a component to add
      if(CompsCombobox.getSelectionModel().getSelectedIndex() == -1)
      {
	Feedback.message("Error", "you must choose a component to add!");
	return;
     }
      //add component to the dish
      DishesCustomerController.dish.addComponent(CompsCombobox.getSelectionModel().getSelectedItem());
      Restaurant.updatefile();
      fill();
		clearFields();
    }

    @FXML
    void RemoveAction(ActionEvent event) {
    	//check if the customer had chose a component to remove
      	if (ComponentTable.getSelectionModel().getSelectedIndex() == -1) {
    			Feedback.message("error", "please choose a Delivery Person to remove");
    			return;
    		}
      //remove component from the dish
        DishesCustomerController.dish.removeComponent(ComponentTable.getSelectionModel().getSelectedItem());
        Restaurant.updatefile();
        fill();
		clearFields();
    }

    
    
    //back method to Customer Dishes page
    @FXML
    void backAction(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("/View/DishesCustomer.fxml"));
		
			Scene scene = new Scene(root);
			Main.PrimaryStage.setScene(scene);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    }
    
    
    //initialize method starts page with filling the combobox and the table
	public void initialize(URL arg0, ResourceBundle arg1) {
		//fill the combo box with the restaurant components
		ObservableList<Component> comps = FXCollections
				.observableArrayList(Restaurant.getInstance().getComponenets().values());
		CompsCombobox.setItems(comps);
		
		fill();

	}
	
	
	//fill table method with the components of the customer dish
	public void fill() {
		//customer dish
		Dish d=DishesCustomerController.dish;
		ObservableList<Component> data = FXCollections
				.observableArrayList(d.getComponenets());
		IDClmn.setCellValueFactory(new PropertyValueFactory<Component, String>("id"));
		NameClmn.setCellValueFactory(new PropertyValueFactory<Component, String>("componentName"));
		PriceClmn.setCellValueFactory(new PropertyValueFactory<Component, String>("price"));
		LactoseClmn.setCellValueFactory(new PropertyValueFactory<Component, String>("hasLactose"));
		GlutenClmn.setCellValueFactory(new PropertyValueFactory<Component, String>("hasGluten"));
		ComponentTable.setItems(data);
	}
    
	//clear combobox
	public void clearFields() {
	
		CompsCombobox.valueProperty().set(null);
	}
  

}
