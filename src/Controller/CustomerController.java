
package Controller;
//Customer page controller(manager) 
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Model.Cook;
import Model.Customer;
import Model.Restaurant;
import Utils.Gender;
import Utils.Neighberhood;
import View.Feedback;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CustomerController implements Initializable{
	//page parameters/fields/buttons/table
private Customer custmer;
    @FXML
    private TextField FirstnameField;
    @FXML
    private TextField LastnameField;
    @FXML
  //fill the combo box with the utill gender class
    private ComboBox<Gender> GenderCombo;
    ObservableList<Gender> GenderList = FXCollections.observableArrayList(Gender.values());
    @FXML
    private DatePicker BirthdayDate;
    @FXML
  //fill the combo box with the utill Neighberhood class
    private ComboBox<Neighberhood> NeighboorCombo;
    ObservableList<Neighberhood> NeighberhoodList = FXCollections.observableArrayList(Neighberhood.values());
    @FXML
    private CheckBox LactoseCheck;
    @FXML
    private CheckBox GlutenCheck;
    @FXML
    private TextField UserNameField;
    @FXML
    private PasswordField PassWordField;
    @FXML
    private Button AddCustomerButton;
    @FXML
    private TableView<Customer> CustomerTable;
    @FXML
    private TableColumn<Customer, String> IdcustClmn;
    @FXML
    private TableColumn<Customer, String> FNameClmn;
    @FXML
    private TableColumn<Customer, String> LNameClmn;
    @FXML
    private TableColumn<Customer, String> GenderClmn;
    @FXML
    private TableColumn<Customer, String> BdayClmn;
    @FXML
    private TableColumn<Customer, String> CustNeighClmn;
    @FXML
    private TableColumn<Customer, String> CustLactoseClmn;
    @FXML
    private TableColumn<Customer, String> CustGlutenClmn;
    @FXML
    private TableColumn<Customer, String> UserNameClmn;
    @FXML
    private TableColumn<Customer, String> PassWordClmn;
    @FXML
    private Button RemoveCustButton;
    @FXML
    private Button UpdateCustBtn;
    @FXML
    private Button blackList;
   //progress check password strrength
    @FXML
    private ProgressBar Strength;
    @FXML
    private Button addblack;
	private boolean upd=false;
	//name check ,checks if it contains numaric characters or not
	private boolean Namecheck(String s) {
		
	      if (s == null) // checks if  null 
	         return false;
	      for (int i = 0; i <= s.length()-1; i++) {
	         // if it is not a letter return false
	         if ((Character.isLetter(s.charAt(i)) == false)) {
	            return false;
	         }
	      
		}
	      return true;
	   }
	//check password method used on the progress bar
    private double checkPasswordStrength(String password) {
		 //total strength of password
       double iPasswordScore = 0;
       
       if( password.length() < 8 )
           return 0;
       else if( password.length() >= 10 )
           iPasswordScore += 0.2;
       else 
           iPasswordScore += 0.1;
       
       //if it contains one digit, add to strength
       if( password.matches("(?=.[0-9]).") )
           iPasswordScore += 0.2;
       
       //if it contains lower case letter, add strength
       if( password.matches("(?=.[a-z]).") )
           iPasswordScore += 0.2;
       
     //if it contains one upper case letter, add  to strength
       if( password.matches("(?=.[A-Z]).") )
           iPasswordScore += 0.2;    
     //if it contains one special character, add to strength
       if( password.matches("(?=.[~!@#$%^&()_-]).*") )
           iPasswordScore += 0.2;
       return iPasswordScore;
   }
	
	
	

	//methods
//initialize method fills table and comboboxs
public void initialize(URL arg0, ResourceBundle arg1) {
	GenderCombo.setItems(GenderList);
	NeighboorCombo.setItems(NeighberhoodList);
    //fills progress bar strength password
	PassWordField.textProperty().addListener(new ChangeListener<String>() { //checks password strength
	    @Override
	    public void changed(ObservableValue<? extends String> observable,
	            String oldValue, String newValue) {
	       double passStrength = checkPasswordStrength(newValue);
	       DoubleProperty bb = new SimpleDoubleProperty(passStrength);
	       Strength.progressProperty().bind(bb);
	    }
	});
	//fil table
	fill();
}
//add Customer to the restaurant method
    @FXML
    void AddCustomerAction(ActionEvent event) {
    	//if the manager didn't fill all the fields
    	if (FirstnameField.getText().length() == 0 || LastnameField.getText().length() == 0 || GenderCombo.getSelectionModel().getSelectedIndex() == -1 || BirthdayDate.getValue() == null ||NeighboorCombo.getSelectionModel().getSelectedIndex() == -1 || UserNameField.getText().length() == 0 || PassWordField.getText().length() == 0) 
    	{
			Feedback.message("Error", "you must fill all the fields!");
			return;
		}
    	
    	//check if the birthday date is legal
    	if(BirthdayDate.getValue().compareTo(LocalDate.now())>0)
    	{
    		Feedback.message("Error", "unavailble birthday Date,please try again!");
			return;
    	}
    	//check if the name contains numaric characters or not
    	if(!Namecheck(LastnameField.getText()) || !Namecheck(FirstnameField.getText()))
    	{
    		Feedback.message("error", "first and last Name can only have letters!");
    		return;
    	}
    	
    	//if we are not updating a customer
    	  if(upd==true)
    	    {
    	    	Feedback.message("error", "You cant add while updating!");
    	    	return;
    	    }
    	//create a new customer to add to the restaurant
    	Customer cust = new Customer(UserNameField.getText(),PassWordField.getText(),FirstnameField.getText(),LastnameField.getText(),BirthdayDate.getValue(),GenderCombo.getValue(),NeighboorCombo.getValue(),LactoseCheck.isSelected(),GlutenCheck.isSelected());
    	//add it to the restaurant
    	if (!Restaurant.getInstance().addCustomer(cust)) {
    		//if theres an error while adding
    			Feedback.message("error", "new Customer is not added please try again!");
    			fill();
        		clearFields();
    			return;
    		}
    	//fill the table and update file also clear the fields
     	Feedback.message("Add", "new Customer has been added");
		Restaurant.updatefile();
    		fill();
    		clearFields();
    		CustomerTable.refresh();

    	
    }
  //remove customer from the restaurant method
    @FXML
    void RemoveCustomerAction(ActionEvent event) {
    	//check if the manager has Selected a Customer to remove
    	if (CustomerTable.getSelectionModel().getSelectedIndex() == -1) {
			Feedback.message("error", "please choose a Customer to remove");
			return;
		}
    	//check if the manager is updating a customer
		if(upd==true)
    	{
    		Feedback.message("Error", "you can't remove while updating!");
			return;
    	}
		//remove the customer from the restaurant
    	if (!Restaurant.getInstance().removeCustomer(CustomerTable.getSelectionModel().getSelectedItem())) {
			Feedback.message("error", "can not remove Customer! ");
			return;
		}else {
			Feedback.message("removed!", "Customer has been removed!");

		}
    	//fill the table and update file also clear the fields
		Restaurant.updatefile();
		CustomerTable.refresh();
		fill();
		clearFields();
    }
  //update Customer method
    @FXML
    void UpdateCustomerAction(ActionEvent event) {
    	//check if the manager has Selected a Customer to update
    	if (CustomerTable.getSelectionModel().getSelectedIndex() == -1) {
			Feedback.message("error", "please choose a Customer to update");
			return;
		}
    	//fill the fields with the Customer that the manager has chose
		 custmer = CustomerTable.getSelectionModel().getSelectedItem();
		if(!upd) {
			FirstnameField.setText(custmer.getFirstName());
			LastnameField.setText(custmer.getLastName());
			GenderCombo.getSelectionModel().select(custmer.getGender());
			BirthdayDate.setValue(custmer.getBirthDay());
			PassWordField.setText(custmer.getPassword());
			UserNameField.setText(custmer.getUsername());
			NeighboorCombo.getSelectionModel().select(custmer.getNeighberhood());
			GlutenCheck.setSelected(custmer.isSensitiveToGluten());
			LactoseCheck.setSelected(custmer.isSensitiveToLactose());
			upd=true;
			Feedback.message("update", "update related field and click update!");

			return;
		}
		//check if one of the fields is null 
    	if (FirstnameField.getText().length() == 0 || LastnameField.getText().length() == 0 || GenderCombo.getSelectionModel().getSelectedIndex() == -1 || BirthdayDate.getValue() == null ||NeighboorCombo.getSelectionModel().getSelectedIndex() == -1 || UserNameField.getText().length() == 0 || PassWordField.getText().length() == 0) 
    	{
			Feedback.message("Error", "you must fill all the fields!");
			return;
		}
    	//check if the birthday date is legal
       	if(BirthdayDate.getValue().compareTo(LocalDate.now())>0)
    	{
    		Feedback.message("Error", "unavailble birthday Date,please try again!");
			return;
    	}
    	//check if the name contains numaric characters or not
    	if(!Namecheck(LastnameField.getText()) || !Namecheck(FirstnameField.getText()))
    	{
    		Feedback.message("error", "first and last Name can only have letters!");
    		return;
    	}
      //update
       	custmer.setBirthDay(BirthdayDate.getValue());
       	custmer.setFirstName(FirstnameField.getText());
       	custmer.setGender(GenderCombo.getValue());
       	custmer.setNeighberhood(NeighboorCombo.getValue());
       	custmer.setUsername(UserNameField.getText());
       	custmer.setPassword(PassWordField.getText());
       	custmer.setSensitiveToGluten(GlutenCheck.isSelected());
       	custmer.setSensitiveToLactose(LactoseCheck.isSelected());
       	custmer.setLastName(LastnameField.getText());
      //fill the table and update file also clear the fields
		Restaurant.updatefile();
		upd = false;
		Restaurant.updatefile();
		clearFields();
		Feedback.message("Updated", "Customer Updated!!");
		fill();
		CustomerTable.refresh();
    	
    }
    
    
    
    //add customer to black list method
    @FXML
    void addcustomertoBlackAction(ActionEvent event) {
    	//check if the manager has Selected a customer to add to black list
    	if (CustomerTable.getSelectionModel().getSelectedIndex() == -1) {
			Feedback.message("error", "please choose a Customer to add to the black list");
			return;
		}
    	//check if the manager is updating a customer
    	if(upd==true)
    	{
    		Feedback.message("Error", "you can't add while updating!");
			return;
    	}
    	//add it to the black list
    	if (!Restaurant.getInstance().addCustomerToBlackList(CustomerTable.getSelectionModel().getSelectedItem())) {
			Feedback.message("error", "can not add Customer to black list! ");
			return;
		}else {
			Feedback.message("added!", "Customer has been added to black list!");

		}
    	//fill table and clear fields update file
    	Restaurant.updatefile();
		clearFields();
		fill();
		CustomerTable.refresh();
    }

    
    
    //fill the table method
	public void fill() {
		ObservableList<Customer> dataCostumers = FXCollections.observableArrayList(Restaurant.getInstance().getCustomers().values());
		IdcustClmn.setCellValueFactory(new PropertyValueFactory<Customer, String>("id"));
		FNameClmn.setCellValueFactory(new PropertyValueFactory<Customer, String>("firstName"));
		LNameClmn.setCellValueFactory(new PropertyValueFactory<Customer, String>("lastName"));
		BdayClmn.setCellValueFactory(new PropertyValueFactory<Customer, String>("birthDay"));
		GenderClmn.setCellValueFactory(new PropertyValueFactory<Customer, String>("gender"));
		CustNeighClmn.setCellValueFactory(new PropertyValueFactory<Customer, String>("neighberhood"));
		CustLactoseClmn.setCellValueFactory(new PropertyValueFactory<Customer, String>("isLac"));
		CustGlutenClmn.setCellValueFactory(new PropertyValueFactory<Customer, String>("isGlu"));
		UserNameClmn.setCellValueFactory(new PropertyValueFactory<Customer, String>("username"));
		PassWordClmn.setCellValueFactory(new PropertyValueFactory<Customer, String>("password"));
		CustomerTable.setItems(dataCostumers);
	}
	//clear fields method
	public void clearFields() {
		FirstnameField.clear();
		LastnameField.clear();
		LactoseCheck.setSelected(false);
		GlutenCheck.setSelected(false);
		BirthdayDate.setValue(null);
		NeighboorCombo.valueProperty().set(null);
		GenderCombo.valueProperty().set(null);
		UserNameField.clear();
		PassWordField.clear();
		}
	//back to manager menu button
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
	    
	    //show black list action
	    @FXML
	    void blackAction(ActionEvent event) {
	    	Stage window=new Stage();
	    	window.initModality(Modality.APPLICATION_MODAL);
	    	window.setTitle("Black List");
	    	window.setWidth(400);
	    	window.setHeight(300);
	    	//black list to show
	    		ListView<Customer>de=new ListView<>();
	    		ObservableList<Customer>ops=FXCollections.observableArrayList(Restaurant.getInstance().getBlackList());
	    		de.setItems(ops);
	        	Scene scene=new Scene (de);
	        	window.setScene(scene);
	        	window.showAndWait();
	    }
}

    
