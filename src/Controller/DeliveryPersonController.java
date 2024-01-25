package Controller;
//delivery person controller page
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import Model.DeliveryArea;
import Model.DeliveryPerson;
import Model.Restaurant;
import Utils.Gender;
import Utils.Vehicle;
import View.Feedback;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class DeliveryPersonController implements Initializable {
	//page parameters/fields/buttons/table/combobox
	private DeliveryPerson DelPerson;
	@FXML
	private TextField FNameDelPerson;
	@FXML
	private TextField DelPersonLname;
	@FXML
	private DatePicker DelpersonBirthday;
	//fill combobox of gender util
	@FXML
	private ComboBox<Gender> DelPersonGender;
	ObservableList<Gender> GendereDelpList = FXCollections.observableArrayList(Gender.values());
	//fill combobox of Vehicle util
	@FXML
	private ComboBox<Vehicle> VehicleComb;
	ObservableList<Vehicle> VehicleDelpList = FXCollections.observableArrayList(Vehicle.values());
	@FXML
	private ComboBox<DeliveryArea> areaCombo;
	@FXML
	private Button AddDelPersonButton;
	@FXML
	private TableView<DeliveryPerson> DelpersonsTable;
	@FXML
	private TableColumn<DeliveryPerson, String> IDClmn;
	@FXML
	private TableColumn<DeliveryPerson, String> FNAmeCLMN;
	@FXML
	private TableColumn<DeliveryPerson, String> LnameClmn;
	@FXML
	private TableColumn<DeliveryPerson, String> GenderClmn;
	@FXML
	private TableColumn<DeliveryPerson, String> BDAYclmn;
	@FXML
	private TableColumn<DeliveryPerson, String> VehicleClmn;
	@FXML
	private TableColumn<DeliveryPerson, String> DelAreaClmn;
	@FXML
	private Button RemoveDelPersonBtn;
	@FXML
	private Button UpdateDelPersonBtn;
	private boolean upd=false;

	//name check ,checks if it contains numaric characters or not
	private boolean Namecheck(String s) {
	      if (s == null) // checks if the String is null 
	         return false;
	      for (int i = 0; i <= s.length()-1; i++) {
	         // checks whether the character is not a letter
	         // if it is not a letter ,it will return false
	         if ((Character.isLetter(s.charAt(i)) == false)) {
	            return false;
	         }
		}
	      return true;
	   }
	
	// add delivery person to restaurant action
	@FXML
	void AddDelPersonAction(ActionEvent event) {
		//if the manager didn't fill all the fields
		if (FNameDelPerson.getText().length() == 0 || DelPersonLname.getText().length() == 0
				|| DelpersonBirthday.getValue() == null || DelPersonGender.getValue() == null
				|| VehicleComb.getSelectionModel().getSelectedIndex() == -1 || areaCombo.getSelectionModel().getSelectedIndex() == -1) {
			Feedback.message("Error", "you must fill all the fields!");
			return;
		}
		//check if the birthday date is legal
    	if(DelpersonBirthday.getValue().compareTo(LocalDate.now())>0)
    	{
    		Feedback.message("Error", "unavailble birthday Date,please try again!");
			return;
    	}
    	//check if the name contains numaric characters or not
    	if(!Namecheck(FNameDelPerson.getText()) || !Namecheck(DelPersonLname.getText()))
    	{
    		Feedback.message("error", "first and last Name can only have letters!");
    		return;
    	}
    	//create new delivery person to add to restaurant
   DeliveryPerson d = new DeliveryPerson(FNameDelPerson.getText(),DelPersonLname.getText(),DelpersonBirthday.getValue(),DelPersonGender.getValue(),VehicleComb.getValue(),areaCombo.getValue());
   //if we are not updating
   if(upd==true)
    {
    	Feedback.message("error", "You cant add while updating!");
    	return;
    }
   //add delivery person 
  	if (!Restaurant.getInstance().addDeliveryPerson(d,areaCombo.getValue())) {
			Feedback.message("error", "new Delivery Person is not added!");
			return;
		}
  //fill the table and update file also clear the fields
  	Restaurant.updatefile();
	Feedback.message("Add", "new Delivery Person has been added");
	fill();
    clearFields();
    DelpersonsTable.refresh();
	}
	 //remove delivery person from the restaurant method
	@FXML
	void RemoveDelPersonAction(ActionEvent event) {
		//check if the manager has Selected a delivery person to remove
    	if (DelpersonsTable.getSelectionModel().getSelectedIndex() == -1) {
			Feedback.message("error", "please choose a Delivery Person to remove");
			return;
		}
    	//check if the manager is updating a delivery person
		if(upd==true)
    	{
    		Feedback.message("Error", "you can't remove while updating!");
			return;
    	}
    	//remove delivery person
    	if (!Restaurant.getInstance().removeDeliveryPerson(DelpersonsTable.getSelectionModel().getSelectedItem())) {
			Feedback.message("error", "can not remove Delivery Person! ");
			return;
		}
    	//fill the table and update file also clear the fields
    	Restaurant.updatefile();
    	Feedback.message("removed!", "Delivery Person have been removed! ");
		fill();
		clearFields();
		DelpersonsTable.refresh();

	}
   //update delivery person action method
	@FXML
	void UpdateDelPersonAction(ActionEvent event) {
		//check if the manager has Selected a delivery person to update
		if (DelpersonsTable.getSelectionModel().getSelectedIndex() == -1) {
			Feedback.message("error", "please choose a Delivery person to update");
			return;
		}
	 	 DelPerson = DelpersonsTable.getSelectionModel().getSelectedItem();
	 	//fill the fields with the Delivery person that the manager has chose
		if(!upd) {
			FNameDelPerson.setText(DelPerson.getFirstName());
			DelPersonLname.setText(DelPerson.getLastName());
			DelpersonBirthday.setValue(DelPerson.getBirthDay());
			DelPersonGender.getSelectionModel().select(DelPerson.getGender());
			VehicleComb.getSelectionModel().select(DelPerson.getVehicle());
			areaCombo.getSelectionModel().select(DelPerson.getArea());
			upd=true;
			Feedback.message("update", "update related field and click update!");
			return;
		}
		//check if one of the fields is null 
		if (FNameDelPerson.getText().length() == 0 || DelPersonLname.getText().length() == 0
				|| DelpersonBirthday.getValue() == null || DelPersonGender.getValue() == null
				|| VehicleComb.getSelectionModel().getSelectedIndex() == -1 || areaCombo.getSelectionModel().getSelectedIndex() == -1) {
			Feedback.message("Error", "you must fill all the fields!");
			return;
		}
		//check if the birthday date is legal
	 	if(DelpersonBirthday.getValue().compareTo(LocalDate.now())>0)
    	{
    		Feedback.message("Error", "unavailble birthday Date,please try again!");
			return;
    	}
	 	//check if the name contains numaric characters or not
		if(!Namecheck(FNameDelPerson.getText()) || !Namecheck(DelPersonLname.getText()))
    	{
    		Feedback.message("error", "first and last Name can only have letters!");
    		return;
    	}
		//update delivery person items
	 	DelPerson.setBirthDay(DelpersonBirthday.getValue());
	 	DelPerson.setFirstName(FNameDelPerson.getText());
	 	DelPerson.setLastName(DelPersonLname.getText());
	 	DelPerson.setGender(DelPersonGender.getValue());
	 	DelPerson.setArea(areaCombo.getValue());
	 	DelPerson.setVehicle(VehicleComb.getValue());
		upd = false;
		//fill the table and update file also clear the fields
		Restaurant.updatefile();
		clearFields();
		Feedback.message("Updated", "Delivery Person Updated!!");
		fill();
		DelpersonsTable.refresh();

	}
	//fill the table method
	public void fill() {
		ObservableList<DeliveryPerson> dataDelPersons = FXCollections
				.observableArrayList(Restaurant.getInstance().getDeliveryPersons().values());
		IDClmn.setCellValueFactory(new PropertyValueFactory<DeliveryPerson, String>("id"));
		FNAmeCLMN.setCellValueFactory(new PropertyValueFactory<DeliveryPerson, String>("firstName"));
		LnameClmn.setCellValueFactory(new PropertyValueFactory<DeliveryPerson, String>("lastName"));
		BDAYclmn.setCellValueFactory(new PropertyValueFactory<DeliveryPerson, String>("birthDay"));
		GenderClmn.setCellValueFactory(new PropertyValueFactory<DeliveryPerson, String>("gender"));
		VehicleClmn.setCellValueFactory(new PropertyValueFactory<DeliveryPerson, String>("vehicle"));
		DelAreaClmn.setCellValueFactory(new PropertyValueFactory<DeliveryPerson, String>("area"));
		DelpersonsTable.setItems(dataDelPersons);
	}
//clear fields method
	public void clearFields() {
		FNameDelPerson.clear();
		DelPersonLname.clear();
		areaCombo.valueProperty().set(null);
		DelpersonBirthday.setValue(null);
		DelPersonGender.valueProperty().set(null);
		VehicleComb.valueProperty().set(null);
	}
//initialize method starts the page and fills the combo boxs and lists
	public void initialize(URL arg0, ResourceBundle arg1) {
		VehicleComb.setItems(VehicleDelpList);
		DelPersonGender.setItems(GendereDelpList);
		ObservableList<DeliveryArea> areas = FXCollections
				.observableArrayList(Restaurant.getInstance().getAreas().values());
		areaCombo.setItems(areas);
		fill();
	}
      //back action method returns to manager home page
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
