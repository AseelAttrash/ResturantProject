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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


import Model.Component;
import Model.Dish;
import Model.Restaurant;
import Utils.DishType;
import View.ExternalWindow;
import View.Feedback;
//dish page controller
public class DishController implements Initializable{
	//page parameters/fields/buttons/table
	private Dish diish;
	@FXML
	private TextField DishNameField;
	@FXML
	private ComboBox<DishType> DishTypeField;
    @FXML
    private TextField Timetomakecomb;
	@FXML
	private Button AddDishButton;
	@FXML
	private Button UpdateDishButton;
	@FXML
	private Button RemoveDishButton;
	@FXML
	private TableView<Dish> table;
	@FXML
	private TableColumn<Dish, String> IdDishclmn;
	@FXML
	private TableColumn<Dish, String> nameclmn;
	@FXML
	private TableColumn<Dish, String> typeclmn;
	@FXML
	private TableColumn<Dish, Void> compsclmn;
	@FXML
	private TableColumn<Dish, String> priceClmn;
	@FXML
	private TableColumn<Dish, String> timetomakeclmn;
	@FXML
	private ListView<Component> checklist;
	private boolean upd=false;
	//name check ,checks if it contains numaric characters or not
	private boolean Namecheck(String s) {
		
	      if (s == null) // checks if null 
	         return false;
	      for (int i = 0; i <= s.length()-1; i++) {
	         // if it is not a letter return false
	    	  if(s.charAt(i) != ' ') {
	         if ((Character.isLetter(s.charAt(i)) == false)) {
	            return false;
	         }}
		}
	      return true;
	   }
	//initialize method starts the page and fills the list of component 
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<DishType> types = FXCollections.observableArrayList(DishType.values());
		DishTypeField.setItems(types);
		ObservableList<Component> comps = FXCollections
				.observableArrayList(Restaurant.getInstance().getComponenets().values());
		//to choose more than one component
		checklist.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		checklist.setItems(comps);
		fill();
	}
    //fill table method
	public void fill() {
		ObservableList<Dish> datadish = FXCollections
				.observableArrayList(Restaurant.getInstance().getDishes().values());
		IdDishclmn.setCellValueFactory(new PropertyValueFactory<Dish, String>("id"));
		nameclmn.setCellValueFactory(new PropertyValueFactory<Dish, String>("dishName"));
		typeclmn.setCellValueFactory(new PropertyValueFactory<Dish, String>("type"));
		timetomakeclmn.setCellValueFactory(new PropertyValueFactory<Dish, String>("timeToMake"));
		priceClmn.setCellValueFactory(new PropertyValueFactory<Dish, String>("price"));
        //fill components column
		Callback<TableColumn<Dish, Void>, TableCell<Dish, Void>> NeighClmn = new Callback<TableColumn<Dish, Void>, TableCell<Dish, Void>>() {
			@Override
			public TableCell<Dish, Void> call(final TableColumn<Dish, Void> param) {
				final TableCell<Dish, Void> cellneigh = new TableCell<Dish, Void>() {
					private final Button showcomp1 = new Button("Show");
					private final HBox pane1 = new HBox(1, showcomp1);

					{
						pane1.setAlignment(Pos.CENTER);
						showcomp1.setOnAction((ActionEvent event) -> {
							Dish data = getTableView().getItems().get(getIndex());
							List<Component> comps = data.getComponenets();
							ArrayList<Component> neighs = new ArrayList<Component>();
							for (Component neigh : comps) {
								neighs.add(neigh);
							}
							ExternalWindow.compWindow(neighs);
						});
					}
					@Override
					protected void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);

						setGraphic(empty ? null : pane1);
					}
				};
				return cellneigh;
			}
		};
		compsclmn.setCellFactory(NeighClmn);
		table.setItems(datadish);
	}
	//clear fields method
	public void clearFields() {
		DishNameField.clear();
		DishTypeField.valueProperty().set(null);
		Timetomakecomb.clear();
		checklist.getSelectionModel().clearSelection();
		}
	//add dish to restaurant action method
    @FXML
    void AddDishAction(ActionEvent event) {
    	//if the manager didn't fill all the fields
    	if(checklist.getSelectionModel().getSelectedIndex()==-1 || Timetomakecomb.getText().length()==0 || DishNameField.getText().length()==0 || DishTypeField.getSelectionModel().getSelectedIndex() == -1 )
    	{
    		Feedback.message("Error", "you must fill all the fields!");
    		return;
    	}
    	//check if the name contains numaric characters or not
    	if(!Namecheck(DishNameField.getText()) )
    	{
    		Feedback.message("error", " Name can only have letters!");
    		return;
    	}
    	//check if the time is numaric and more than 0
    	int d = 0;
		try {
			d = Integer.valueOf(Timetomakecomb.getText());
		} catch (Exception e) {
			Feedback.message("error", "Time to make field must be a numaric");
			return;
		}
		if(d<=0)
		{
				Feedback.message("error", "Time to make field must be more than 0!");
				return;	
		}
          //create new dish to add
		ArrayList<Component>compsarraylist=new ArrayList<>(checklist.getSelectionModel().getSelectedItems());
		Dish dish1 = new Dish(DishNameField.getText(),DishTypeField.getValue(),compsarraylist,d);
		//if we are not updating a dish
		  if(upd==true)
		    {
		    	Feedback.message("error", "You cant add while updating!");
		    	return;
		    }
		  //add dish to restaurant
  	if (!Restaurant.getInstance().addDish(dish1)) {
		Feedback.message("error", "new Dish is not added!");
		return;
  	}
  //fill the table and update file also clear the fields
	Feedback.message("Add", "new Dish has been added");
	Restaurant.updatefile();
	
	fill();
	clearFields();
    }
    //remove dish from restaurant action method
    @FXML
    void RemoveDishAction(ActionEvent event) {
    	//check if the manager has Selected a dish to remove
    	if (table.getSelectionModel().getSelectedIndex() == -1) {
    		Feedback.message("error", "please choose a Dish to remove");
    		return;
    	}
    	//if we are not updating a dish
		  if(upd==true)
		    {
		    	Feedback.message("error", "You cant remove while updating!");
		    	return;
		    }
    	//remove it from restaurant
	if (!Restaurant.getInstance().removeDish(table.getSelectionModel().getSelectedItem())) {
		Feedback.message("error", "can not remove Dish ");
		return;
	}else {
		Feedback.message("removed!", "Dish has been removed!");
	}
	//fill the table and update file also clear the fields
	    Restaurant.updatefile();
		fill();
		clearFields();
		table.refresh();
    }
    //upadte dish method action
    @FXML
    void UpdateDishAction(ActionEvent event) {
    	//check if the manager has Selected a Dish to update
    	if (table.getSelectionModel().getSelectedIndex() == -1) {
    		Feedback.message("error", "please choose a Dish to update");
    		return;
    	}
    	//fill the fields with the Customer that the manager has chose
    	diish = table.getSelectionModel().getSelectedItem();
    	if(!upd) {
    		DishNameField.setText(diish.getDishName());
    		DishTypeField.getSelectionModel().select(diish.getType());
    		Timetomakecomb.setText(Integer.toString(diish.getTimeToMake()));
    		for(Component o:diish.getComponenets()) {
    			
    			checklist.getSelectionModel().select(o);}
			upd=true;
			Feedback.message("update", "update related field and click update!");
			return;
		}
    	//check if one of the fields is null 
      	if(checklist.getSelectionModel().getSelectedIndex()==-1 || Timetomakecomb.getText().length()==0 || DishNameField.getText().length()==0 || DishTypeField.getSelectionModel().getSelectedIndex() == -1 )
    	{
    		Feedback.message("Error", "you must fill all the fields!");
    		return;
    	}

      //check if the time is numaric and more than 0
	int da = 0;
	try {
		da = Integer.valueOf(Timetomakecomb.getText());
	} catch (Exception e) {
		Feedback.message("error", "Time to make field must be a numaric");
		return;
	}
	if(da<=0)
	{
		Feedback.message("error", "Time to make field must be more than 0!");
		return;
	}
	//check if the name contains numaric characters or not
	if(!Namecheck(DishNameField.getText()) )
	{
		Feedback.message("error", " Name can only have letters!");
		return;
	}
		//update dish fields
	diish.setDishName(DishNameField.getText());
	diish.setTimeToMake(da);
	diish.setType(DishTypeField.getValue());
	ArrayList<Component>compsarraylist=new ArrayList<>(checklist.getSelectionModel().getSelectedItems());
	diish.setComponents(compsarraylist);
	upd = false;
	Restaurant.updatefile();
		clearFields();
		Feedback.message("Updated", "Dish Updated!!");
		fill();
		table.refresh();
    }
    
       //back action to manager home page
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
