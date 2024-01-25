
package Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Model.Cook;
import Model.Restaurant;
import Utils.Expertise;
import Utils.Gender;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
//Cook page controller
public class CookController implements Initializable{
	//page parameters/fields/buttons/table
    private Cook cook1;
    @FXML
    private TextField CookIdFName;
    @FXML
    private TextField CookLName;
    @FXML
    private DatePicker CookBirht;
    @FXML
    //fill the combo box with the utill gender class
    private ComboBox<Gender> CookGender;
    ObservableList<Gender> GenderList2 = FXCollections.observableArrayList(Gender.values());
    //fill the combo box with the utill Expertise class
    @FXML
    private ComboBox<Expertise> CookExp;
    ObservableList<Expertise> ExpertiseList = FXCollections.observableArrayList(Expertise.values());
    @FXML
    private CheckBox IsChefCook;
    @FXML
    private Button AddcookButton;
    @FXML
    private TableView<Cook> tableCook;
    @FXML
    private TableColumn<Cook, String> CookIdclmn;
    @FXML
    private TableColumn<Cook, String> FnameCookclmn;
    @FXML
    private TableColumn<Cook, String> LnameCookclmn;
    @FXML
    private TableColumn<Cook, String> Cookbrithdayclmn;
    @FXML
    private TableColumn<Cook, String> CookGenderclmn;
    @FXML
    private TableColumn<Cook, String> IschefClm;
    @FXML
    private TableColumn<Cook, String> ExpertiseClmn;
    @FXML
    private Button UpdateCookBtn;
    @FXML
    private Button RemoveCookbtn;
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
    //methods
	//add cook to the restaurant method
    @FXML
    void AddCookAcn(ActionEvent event) {
		//if the manager didn't fill all the fields
    	if (CookIdFName.getText().length() == 0 || CookLName.getText().length() == 0 || CookBirht.getValue() == null || CookGender.getSelectionModel().getSelectedIndex() == -1 ||CookExp.getSelectionModel().getSelectedIndex()==-1) 
    	{
			Feedback.message("Error", "you must fill all the fields!");
			return;
		}
    	//if we are not updating a component
    	if(upd==true)
    	{
    		Feedback.message("Error", "you can't add while updating!");
			return;
    	}
    	//check if the birthday date is legal
    	if(CookBirht.getValue().compareTo(LocalDate.now())>0)
    	{
    		Feedback.message("Error", "unavailble birthday Date,please try again!");
			return;
    	}
    	//check if the name contains numaric characters or not
    	if(!Namecheck(CookIdFName.getText()) || !Namecheck(CookLName.getText()))
    	{
    		Feedback.message("error", "first and last Name can only have letters!");
    		return;
    	}
    	//create a new cook to add to the restaurant
    	Cook cook = new Cook(CookIdFName.getText(),CookLName.getText(),CookBirht.getValue(),CookGender.getSelectionModel().getSelectedItem(),CookExp.getSelectionModel().getSelectedItem(),IsChefCook.isSelected());
    	//add it to the restaurant
    	if (!Restaurant.getInstance().addCook(cook)) {
			Feedback.message("error", "new cook is not added");
			return;
		}
    	//fill the table and update file also clear the fields
    	Restaurant.updatefile();
    	Feedback.message("Add", "new Cook has been added");
		tableCook.refresh();
		fill();
		clearFields();

    }
  //remove cook from the restaurant method
    @FXML
    void RemoveCookAction(ActionEvent event) {
    	//check if the manager has Selected a cook to remove
		if (tableCook.getSelectionModel().getSelectedIndex() == -1) {
			Feedback.message("error", "please choose a Cook to remove");
			return;
		}
		//check if the manager is updating a cook
		if(upd==true)
    	{
    		Feedback.message("Error", "you can't remove while updating!");
			return;
    	}
		//remove the cook from the restaurant
		if (!Restaurant.getInstance().removeCook(tableCook.getSelectionModel().getSelectedItem())) {
			Feedback.message("error", "can not remove Cook! ");
			return;
		}else {
			Feedback.message("removed!", "Cook has been removed!");
			//update file
			Restaurant.updatefile();


		}
		//fill table and clear fields
		fill();
		tableCook.refresh();
		clearFields();
    }

    
	//update cook method
    @FXML
    void UpdateCookAction(ActionEvent event) {
    	//check if the manager has Selected a cook to update
    	if (tableCook.getSelectionModel().getSelectedIndex() == -1) {
			Feedback.message("error", "please choose a Cook to update");
			return;
		}
    	//fill the fields with the cook that the manager has chose
		 cook1 = tableCook.getSelectionModel().getSelectedItem();
		if(!upd) {
			CookLName.setText(cook1.getLastName());
			CookBirht.setValue(cook1.getBirthDay());
			CookGender.getSelectionModel().select(cook1.getGender());
			CookExp.getSelectionModel().select(cook1.getExpert());
			IsChefCook.setSelected(cook1.isChef());
			CookIdFName.setText(cook1.getFirstName());
			
			upd=true;
			Feedback.message("update", "update related field and click update!");

			return;
		}
    	
    	
		//check if one of the fields is null 
     	if (CookIdFName.getText().length() == 0 || CookLName.getText().length() == 0 || CookBirht.getValue() == null || CookGender.getSelectionModel().getSelectedIndex() == -1 ||CookExp.getSelectionModel().getSelectedIndex() == -1 ) 
    	{
			Feedback.message("Error", "you must fill all the fields to update!");
			return;
    	}
     	//check if the birthday date is legal
    	if(CookBirht.getValue().compareTo(LocalDate.now())>0)
    	{
    		Feedback.message("Error", "unavailble birthday Date,please try again!");
			return;
    	}
    	//check if the name contains numaric characters or not
    	if(!Namecheck(CookIdFName.getText()) || !Namecheck(CookLName.getText()))
    	{
    		Feedback.message("error", "first and last Name can only have letters!");
    		return;
    	}
		//update
    	cook1.setBirthDay(CookBirht.getValue());
    	cook1.setChef(IsChefCook.isSelected());
    	cook1.setExpert(CookExp.getValue());
    	cook1.setFirstName(CookIdFName.getText());
    	cook1.setGender(CookGender.getValue());
    	cook1.setLastName(CookLName.getText());
    	//fill the table and update file also clear the fields
    	Restaurant.updatefile(); 
    	upd = false;
		clearFields();
		fill();
		tableCook.refresh();
		Feedback.message("Updated", "Cook Updated!!");
    }
    //initialize fills the table,combobox
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		CookGender.setItems(GenderList2);
		CookExp.setItems(ExpertiseList);
		fill();
	}
	
	//fill table method
	public void fill() {
		ObservableList<Cook> dataCooks = FXCollections
				.observableArrayList(Restaurant.getInstance().getCooks().values());
		CookIdclmn.setCellValueFactory(new PropertyValueFactory<Cook, String>("id"));
		FnameCookclmn.setCellValueFactory(new PropertyValueFactory<Cook, String>("firstName"));
		LnameCookclmn.setCellValueFactory(new PropertyValueFactory<Cook, String>("lastName"));
		Cookbrithdayclmn.setCellValueFactory(new PropertyValueFactory<Cook, String>("birthDay"));
		CookGenderclmn.setCellValueFactory(new PropertyValueFactory<Cook, String>("gender"));
		IschefClm.setCellValueFactory(new PropertyValueFactory<Cook, String>("chef"));
		ExpertiseClmn.setCellValueFactory(new PropertyValueFactory<Cook, String>("expert"));
		tableCook.setItems(dataCooks);
	}
	
	//clear fields method
	public void clearFields() {
		CookIdFName.clear();
		CookLName.clear();
		IsChefCook.setSelected(false);
		CookBirht.setValue(null);
		CookExp.valueProperty().set(null);
		CookGender.valueProperty().set(null);
	}
	//back to the manager page
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
