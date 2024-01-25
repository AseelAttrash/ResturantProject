
package Controller;
//Component Page Controller
import java.net.URL;
import java.util.ResourceBundle;

import Model.Component;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ComponentController implements Initializable{
//page parameters/fields/buttons/table
	private Component c;
	@FXML
	private TextField lacto;
	@FXML
	private TextField ComponentName;
	@FXML
	private TextField ComponentPrice;
	@FXML
	private CheckBox ComponentGluten;
	@FXML
	private CheckBox ComponentLactose;
	@FXML
	private Button addCompButton;
	@FXML
	private TableView<Component> tablec;
	@FXML
	private TableColumn<Component, String> idcolumn;
	@FXML
	private TableColumn<Component, String> namecolumn;
	@FXML
	private TableColumn<Component, String> pricecolumn;
	@FXML
	private TableColumn<Component, String> lactosecolumn;
	@FXML
	private TableColumn<Component, String> glutencolumn;
	@FXML
	private Button removeBtn;
	@FXML
	private Button updateBtn;
	private boolean upd=false;

	//name check ,checks if it contains numaric characters or not
	private boolean Namecheck(String s) {
		
	      if (s == null) // checks if  null 
	         return false;
	      for (int i = 0; i <= s.length()-1; i++) {
	         // if it is not a letter ,it will return false
	    	  if(s.charAt(i) != ' ') {
	         if ((Character.isLetter(s.charAt(i)) == false)) {
	            return false;
	         }}
	      
		}
	      return true;
	   }
	
	//methods
	
	//initialize method start the page with fill  table
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fill();
	}

	//add component to the restaurant method
	@FXML
	void addAction(ActionEvent event) {
		//if the manager didn't fill all the fields
		if (ComponentName.getText().length() == 0 || ComponentPrice.getText().length() == 0) {
			Feedback.message("Error", "you must fill");
			return;
		}
		//check if the price is numaric 
		double d = 0;
		try {
			d = Double.valueOf(ComponentPrice.getText());
		} catch (Exception e) {
			Feedback.message("error", "price field must be a numaric");
			return;
		}
		//check if the name contains numaric characters or not
		if(!Namecheck(ComponentName.getText()) )
    	{
    		Feedback.message("error", " Name can only have letters!");
    		return;
    	}
		//price must be more than 0
		if(d==0 || d<0)
		{
			Feedback.message("error", "price must be more than 0!");
			return;
		}
		
		//if we are not updating a component
		  if(upd==true)
		    {
		    	Feedback.message("error", "You cant add while updating!");
		    	return;
		    }
		  //create a new component to add to the restaurant
		Component c = new Component(ComponentName.getText(), ComponentLactose.isSelected(),
				ComponentGluten.isSelected(), d);
		//add it to the restaurant
		if (!Restaurant.getInstance().addComponent(c)) {
			Feedback.message("error", "new component not added");
			return;
		}
		//fill the table and update file also clear the fields
		Restaurant.updatefile();
		Feedback.message("Add", "new component added");
		clearFields();
		fill();
		tablec.refresh();
	}

	
	
	//remove component from the restaurant method
	@FXML
	void removeAction(ActionEvent event) {
		//check if the manager has Selected a component to remove
		if (tablec.getSelectionModel().getSelectedIndex() == -1) {
			Feedback.message("error", "please choose a component to remove");
			return;
		}
		//check if the manager is updating a component
		if(upd==true)
    	{
    		Feedback.message("Error", "you can't remove while updating!");
			return;
    	} 
		Component c = tablec.getSelectionModel().getSelectedItem();
		//remove the component from the restaurant
		if (!Restaurant.getInstance().removeComponent(c)) {
			Feedback.message("error", "can not remove component ");
			return;
		}else {
			Feedback.message("removed!", "Component has been removed!");
		}
		//fill the table and update file also clear the fields
		Restaurant.updatefile();
		fill();
		clearFields();
		tablec.refresh();
	}

	
	
	//update component method
	@FXML
	void updateAction(ActionEvent event) {
		//check if the manager has Selected a component to update
		if (tablec.getSelectionModel().getSelectedIndex() == -1) {
			Feedback.message("error", "please choose a component to update");
			return;
		}
	    //fill the fields with the component that the manager has choosen
		if(!upd)
		 c = tablec.getSelectionModel().getSelectedItem();
		if(!upd) {
			ComponentName.setText(c.getComponentName());
			ComponentPrice.setText(String.valueOf(c.getPrice()));
			ComponentGluten.setSelected(c.isHasGluten());
			ComponentLactose.setSelected(c.isHasLactose());
			upd=true;
			Feedback.message("update", "update related field and click update!");

			return;
		}
		//check if one of the fields is null 
		if (ComponentName.getText().length() == 0 || ComponentPrice.getText().length() == 0) {
			Feedback.message("Error", "you must fill all the fields!");
			return;
		}
		//check if the name contains numaric characters or not
		if(!Namecheck(ComponentName.getText()) )
    	{
    		Feedback.message("error", " Name can only have letters!");
    		return;
    	}
		//check if the price is numaric or not
		double d = 0;
		try {
			d = Double.valueOf(ComponentPrice.getText());
		} catch (Exception e) {
			Feedback.message("error", "price field must be a numaric");
			return;
		}
		//update the component
		c.setComponentName(ComponentName.getText());
		c.setHasGluten(ComponentGluten.isSelected());
		c.setHasLactose(ComponentLactose.isSelected());
		c.setPrice(d);
		//fill the table and update file also clear the fields
        upd = false;
		Restaurant.updatefile();
		fill();
		tablec.refresh();
		clearFields();
		Feedback.message("Updated", "Component Updated!!");

	}
	
	
	
    //clear fields 
	void clearFields() {
		ComponentName.clear();
		ComponentPrice.clear();
		ComponentLactose.setSelected(false);
		ComponentGluten.setSelected(false);
	}

	
	//fill the table
	void fill() {
		ObservableList<Component> data = FXCollections
				.observableArrayList(Restaurant.getInstance().getComponenets().values());
		idcolumn.setCellValueFactory(new PropertyValueFactory<Component, String>("id"));
		namecolumn.setCellValueFactory(new PropertyValueFactory<Component, String>("componentName"));
		pricecolumn.setCellValueFactory(new PropertyValueFactory<Component, String>("price"));
		lactosecolumn.setCellValueFactory(new PropertyValueFactory<Component, String>("hasLactose"));
		glutencolumn.setCellValueFactory(new PropertyValueFactory<Component, String>("hasGluten"));
		tablec.setItems(data);
	}
	
	
	
	//back button to manager menu page
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
