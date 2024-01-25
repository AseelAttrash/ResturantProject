package Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
//edit profile page controller
public class EditProfileController implements Initializable {
	//page parameters/fields/buttons/table
    @FXML
    private TextField FnameField;
    private Customer cust;
    @FXML
    private TextField LnameField;
    @FXML
    private TextField UserNameField;
    @FXML
    private PasswordField PassWordField;
    @FXML
    private CheckBox LactoseCheck;
    @FXML
    private CheckBox GlutenCheck;
    @FXML
    private DatePicker BdayField;
    @FXML
    private ProgressBar Strength;
    @FXML
    //fill gender combo box with gender utils values
    private ComboBox<Gender> GenderCombo;
    ObservableList<Gender> GenderList = FXCollections.observableArrayList(Gender.values());
    // fill neighberhood combo box with neighberhood util values
    @FXML
    private ComboBox<Neighberhood> NeighboorCombo;
    ObservableList<Neighberhood> NeighberhoodList = FXCollections.observableArrayList(Neighberhood.values());
    @FXML
    private Button EditButton;
    @FXML
    private Button backButton;
    
  //name check ,checks if it contains numaric characters or not
	private boolean Namecheck(String s) {
		
	      if (s == null) // checks if null 
	         return false;
	      for (int i = 0; i <= s.length()-1; i++) {
	         // if it is not a letter return false
	         if ((Character.isLetter(s.charAt(i)) == false)) {
	            return false;
	         }
		}
	      return true;
	   }
    
    
    
    //edit profile fields actioon
    @FXML
    void EditAction(ActionEvent event) {
    	//check if one of the fields is null
    	if (FnameField.getText().length() == 0 || LnameField.getText().length() == 0 || GenderCombo.getSelectionModel().getSelectedIndex() == -1 || BdayField.getValue() == null ||NeighboorCombo.getSelectionModel().getSelectedIndex() == -1 || UserNameField.getText().length() == 0 || PassWordField.getText().length() == 0) 
    	{
			Feedback.message("Error", "you must fill all the fields!");
			return;
		}
    	//check if the birthday date is legal
    	if(BdayField.getValue().compareTo(LocalDate.now())>0)
    	{
    		Feedback.message("Error", "unavailble birthday Date,please try again!");
			return;
    	}
    	//check if the name contains numaric characters or not
    	if(!Namecheck(FnameField.getText()) || !Namecheck(LnameField.getText()))
    	{
    		Feedback.message("error", "first and last Name can only have letters!");
    		return;
    	}
    	//set customer details
    	cust = LogInController.currentcustomer;
    	cust.setBirthDay(BdayField.getValue());
    	cust.setFirstName(FnameField.getText());
    	cust.setGender(GenderCombo.getValue());
    	cust.setNeighberhood(NeighboorCombo.getValue());
    	cust.setUsername(UserNameField.getText());
    	cust.setPassword(PassWordField.getText());
    	cust.setSensitiveToGluten(GlutenCheck.isSelected());
    	cust.setSensitiveToLactose(LactoseCheck.isSelected());
    	cust.setLastName(LnameField.getText());
		Feedback.message("Done", "you have updated your details");
		Restaurant.updatefile();
    }
   //back action return to customer home page
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
    //check password strength method
    private double checkPasswordStrength(String password) {
    	//total strength of passwor
       double iPasswordScore = 0;
       
       if( password.length() < 4 )
           return 0;
       else if( password.length() >= 8 )
           iPasswordScore += 0.2;
       else 
           iPasswordScore += 0.1;
       
     //if it contains one digit, add to strength
       if( password.matches("(?=.[0-9]).") )
           iPasswordScore += 0.3;
       
     //if it contains lower case letter, add strength
       if( password.matches("(?=.[a-z]).") )
           iPasswordScore += 0.3;
       
     //if it contains one upper case letter, add  to strength
       if( password.matches("(?=.[A-Z]).") )
           iPasswordScore += 0.2;    
       
       //if it contains one special character, add to strength
       if( password.matches("(?=.[~!@#$%^&()_-]).*") )
           iPasswordScore += 0.4;      
       return iPasswordScore;
   }
    //initialize method fills the fields with current customer details
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		GenderCombo.setItems(GenderList);
		NeighboorCombo.setItems(NeighberhoodList);
		FnameField.setText(LogInController.currentcustomer.getFirstName());
		LnameField.setText(LogInController.currentcustomer.getLastName());
		UserNameField.setText(LogInController.currentcustomer.getUsername());
		PassWordField.setText(LogInController.currentcustomer.getPassword());
		LactoseCheck.setSelected(LogInController.currentcustomer.isSensitiveToLactose());
		GlutenCheck.setSelected(LogInController.currentcustomer.isSensitiveToGluten());
		BdayField.setValue(LogInController.currentcustomer.getBirthDay());
		GenderCombo.setValue(LogInController.currentcustomer.getGender());
		NeighboorCombo.setValue(LogInController.currentcustomer.getNeighberhood());
		//fills progress bar strength password
		PassWordField.textProperty().addListener(new ChangeListener<String>() { // checks password strenght 
		    @Override
		    public void changed(ObservableValue<? extends String> observable,
		            String oldValue, String newValue) {
		       double pasdStrength = checkPasswordStrength(newValue);
		       DoubleProperty bb = new SimpleDoubleProperty(pasdStrength);
		       Strength.progressProperty().bind(bb);
		    }
		});
	
	}

}
