package Controller;

import java.io.File;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
//sing up page controller
public class SingUpController implements Initializable{
    //page buttons/fields/comboboxs
    @FXML
    private TextField FnameFld;

    @FXML
    private TextField LNameFld;

    @FXML
    private TextField UserNameFld;

    @FXML
    private PasswordField PassWFld;

    @FXML
    private DatePicker BdayFld;
    @FXML
    private ProgressBar Strength;
    @FXML
    //fill gender combo box with gender util
    private ComboBox<Gender> GenderFld;
    ObservableList<Gender> GenderList = FXCollections.observableArrayList(Gender.values());
     //fill neighberhood with neighberhood utils
    @FXML
    private ComboBox<Neighberhood> Neighboorcombobox;
    ObservableList<Neighberhood> NeighberhoodList = FXCollections.observableArrayList(Neighberhood.values());
    @FXML
    private CheckBox LactoseCheck;
    @FXML
    private CheckBox GlutenCheck;
    @FXML
    private Button RegisterBtn;
    @FXML
    private Button loginBtn;
    //parameters to video background
	private File file;
	private Media media1;
	private MediaPlayer mediaPlayer;
    @FXML
    private MediaView media;
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
    //register button action
    @FXML
    void RegisterAction(ActionEvent event) {
    	//check if theres one of the fields null
    	if (LNameFld.getText().length() == 0 || FnameFld.getText().length() == 0 || Neighboorcombobox.getSelectionModel().getSelectedIndex() == -1 || BdayFld.getValue() == null ||GenderFld.getSelectionModel().getSelectedIndex() == -1 || UserNameFld.getText().length() == 0 || PassWFld.getText().length() == 0) 
    	{
			Feedback.message("Error", "you must fill all the fields!");
			return;
		}
    	//check if birthday date is legal
    	if(BdayFld.getValue().compareTo(LocalDate.now())>0)
    	{
    		Feedback.message("Error", "unavailble birthday Date,please try again!");
			return;
    	}
    	//check if the name contains numaric characters or not
    	if(!Namecheck(LNameFld.getText()) || !Namecheck(FnameFld.getText()))
    	{
    		Feedback.message("error", "first and last Name can only have letters!");
    		return;
    	}
    	//create new customer to add to restaurant
    	Customer cust = new Customer(UserNameFld.getText(),PassWFld.getText(),FnameFld.getText(),LNameFld.getText(),BdayFld.getValue(),GenderFld.getValue(),Neighboorcombobox.getValue(),LactoseCheck.isSelected(),GlutenCheck.isSelected());
       	//add customer
    	if (!Restaurant.getInstance().addCustomer(cust)) {
    			Feedback.message("error", "new Customer is not added! USERNAME ALREADY EXIST");
    			return;
    		}
    	//move to log in page
     	Feedback.message("Welcome", "Thanks for joining our restaurant please Log in!");
     	Restaurant.updatefile();
     	try {
     		Main.medip.setMute(true);
			Parent root = FXMLLoader.load(getClass().getResource("/View/LogIn.fxml"));
    		Main.medip.setVolume(0);

			Scene scene = new Scene(root);
			Main.PrimaryStage.setScene(scene);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    }
  //move to log in page
    @FXML
    void login(ActionEvent event){
    	try {
    		Main.medip.setMute(true);
			Parent root = FXMLLoader.load(getClass().getResource("/View/LogIn.fxml"));
			Main.medip.setVolume(0);
			Scene scene = new Scene(root);
			Main.PrimaryStage.setScene(scene);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    }
	//check password method used on the progress bar
    private double checkPasswordStrength(String password) {
		 //total strength of password
       double iPasswordScore = 0;
       
       if( password.length() < 4 )
           return 0;
       else if( password.length() >= 8 )
           iPasswordScore += 0.2;
       else 
           iPasswordScore += 0.1;
       
       //if it contains  digit strength
       if( password.matches("(?=.[0-9]).") )
           iPasswordScore += 0.3;
       
       //if it contains one lower case letter to strength
       if( password.matches("(?=.[a-z]).") )
           iPasswordScore += 0.3;
       
       //if it contains one upper case letter, add strength
       if( password.matches("(?=.[A-Z]).") )
           iPasswordScore += 0.2;    
       
       //if it contains one special character, add 0.2 to total score
       if( password.matches("(?=.[~!@#$%^&()_-]).*") )
           iPasswordScore += 0.4;
       return iPasswordScore;    
   }
    //initialize method plays video on sing up page and fills combo box and progress bar of password
    public void initialize(URL arg0, ResourceBundle arg1) {
    	file = new File("RestPromo.mp4");
		media1 = new Media(file.toURI().toString());
		mediaPlayer = new MediaPlayer(media1);
		Main.medip= mediaPlayer;
		media.setMediaPlayer(Main.medip);
		Main.medip.setCycleCount(Integer.MAX_VALUE);
		Main.medip.setVolume(1);
		Main.medip.play();
    	GenderFld.setItems(GenderList);
    	Neighboorcombobox.setItems(NeighberhoodList);
    	//strength of password
    	PassWFld.textProperty().addListener(new ChangeListener<String>() { //a listener that checks password strength
		    @Override
		    public void changed(ObservableValue<? extends String> observable,
		            String oldValue, String newValue) {
		       double passStrength = checkPasswordStrength(newValue);
		       DoubleProperty barUp = new SimpleDoubleProperty(passStrength);
		       Strength.progressProperty().bind(barUp);
		    }
		});
    }	
}
