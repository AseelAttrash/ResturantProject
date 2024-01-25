package Controller;
//delivery area page controller
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
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;


import Model.Delivery;
import Model.DeliveryArea;
import Model.DeliveryPerson;
import Model.Restaurant;

import Utils.Neighberhood;
import View.ExternalWindow;
import View.Feedback;

public class DeliveryAreaController implements Initializable {
	//page parameters/fields/buttons/table/combobox

	//new delivery area to change after remove
	DeliveryArea oldel=null;
     private DeliveryArea DA;
	@FXML
	private TextField DelAreaName;
    @FXML
    private TextField deliverTimeDelArea;
	@FXML
	private Button AddDelAreaButton;
	@FXML
	private TableView<DeliveryArea> DeliveryAreaTable;
	@FXML
	private TableColumn<DeliveryArea, String> IdClmn;
	@FXML
	private TableColumn<DeliveryArea, String> areanameClmn;
	@FXML
	private TableColumn<DeliveryArea, String> DeliveryTimeClmn;
	@FXML
	private TableColumn<DeliveryArea, Void> NeighboorhoodsClmn;
	@FXML
	private TableColumn<DeliveryArea, Void> DelpersonsClmn;
	@FXML
	private TableColumn<DeliveryArea, Void> DeliveriesClmn;
	@FXML
	private ListView<Neighberhood> NeighList1;
	@FXML
	private Button UpdateDelAreabutton;
	@FXML
	private Button RemoveDelareaBtn;
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
//add delivery area button action
	@FXML
	void AddDeliveryAreaAction(ActionEvent event) {
		oldel=null;
        //check if if the manager didn't fill all the fields
    	if( deliverTimeDelArea.getText().length()==0 || DelAreaName.getText().length()==0  ||NeighList1.getSelectionModel().getSelectedIndex()==-1 )
    	{
    		Feedback.message("Error", "you must fill all the fields!");
    		return;
    	}
    	//check deliver time if numaric and more than 0
    	int d = 0;
		try {
			d = Integer.valueOf(deliverTimeDelArea.getText());
		} catch (Exception e) {
			Feedback.message("error", "Delivery Time field must be a numaric and not double!");
			return;
		}
		if(d<=0)
		{
			Feedback.message("error", "Delivery Time field must be more than 0");
			return;
		}
		//check if the name contains numaric characters or not
		if(!Namecheck(DelAreaName.getText()) )
    	{
    		Feedback.message("error", " Name can only have letters!");
    		return;
    	}
		//neighboorhood has been chosen
    	ArrayList<Neighberhood>arr=new ArrayList<>(NeighList1.getSelectionModel().getSelectedItems());
		HashSet<Neighberhood> NeighberhoodSet = new HashSet<>(arr);
         //create new delivery area to add
		DeliveryArea Da = new  DeliveryArea(DelAreaName.getText(),NeighberhoodSet,d);
		//check if we are updating 
		if(upd==true)
		    {
		    	Feedback.message("error", "You cant add while updating!");
		    	return;
		    }
		//adding delivery area
  	if (!Restaurant.getInstance().addDeliveryArea(Da)) {
		Feedback.message("error", "new Delivery Area is not added!");
		return;
  	}
  //fill the table and update file also clear the fields
  	Restaurant.updatefile();
	Feedback.message("Add", "new Delivery Area has been added");
	fill();
	clearFields();
	DeliveryAreaTable.refresh();
	}
    //remove delivery area button action
	@FXML
	void RemoveDeliveryAreaAction(ActionEvent event) {
		//check if the manager has Selected a delivery area to remove
    	if (DeliveryAreaTable.getSelectionModel().getSelectedIndex() == -1) {
    		Feedback.message("error", "please choose a Delivery Area to remove");
    		return;
    	}
    	//check if the manager is updating a delivery area
		if(upd==true)
    	{
    		Feedback.message("Error", "you can't remove while updating!");
			return;
    	}
    	//new area to set 
    	if(oldel==null) {
    	oldel=DeliveryAreaTable.getSelectionModel().getSelectedItem();
		Feedback.message("update step", "please choose the new delivery Area");
		return;
    	}
    	//remove delivery area
	if (!Restaurant.getInstance().removeDeliveryArea(oldel,DeliveryAreaTable.getSelectionModel().getSelectedItem())) {
		Feedback.message("error", "can not remove Delivery Area ");
		return;
	}else {
		Feedback.message("removed!", "Delivery Area has been removed!");
		oldel=null;
	}
	//fill the table and update file also clear the fields
	Restaurant.updatefile();
		fill();
		clearFields();
		DeliveryAreaTable.refresh();

	}
//update delivery area button
	@FXML
	void UpdateDeliveryAreaAction(ActionEvent event) {
		//check if the manager has Selected a Delivery Area to update
		if (DeliveryAreaTable.getSelectionModel().getSelectedIndex() == -1) {
			Feedback.message("error", "please choose a Delivery Area to update");
			return;
		}
		 DA = DeliveryAreaTable.getSelectionModel().getSelectedItem();
		int time = DA.getDeliverTime();
		//fill the fields with the Delivery Area that the manager has choosen
		if(!upd) {
			DelAreaName.setText(DA.getAreaName());
			for(Neighberhood o:DA.getNeighberhoods()) {
			NeighList1.getSelectionModel().select(o);}
			deliverTimeDelArea.setText(Integer.toString(time));
			upd=true;
			Feedback.message("update", "update related field and click update!");

			return;
		}
		//check if one of the fields is null 
		if(  DelAreaName.getText().length()==0  ||NeighList1.getSelectionModel().getSelectedIndex()==-1 )
    	{
    		Feedback.message("Error", "you must fill all the fields!");
    		return;
    	}
		//check the deliver time,cause it is final ,cant update it
		int time2 = Integer.valueOf(deliverTimeDelArea.getText());
		if(time2 != time)
		{
			Feedback.message("Error", "you can't update a final field! ,please do not change the value of the Delivery Time field");
    		return;
		}
		//check if the name contains numaric characters or not
		if(!Namecheck(DelAreaName.getText()) )
    	{
    		Feedback.message("error", " Name can only have letters!");
    		return;
    	}
		//update delivery area fields
		DA.setAreaName(DelAreaName.getText());
	ArrayList<Neighberhood>arr=new ArrayList<>(NeighList1.getSelectionModel().getSelectedItems());
	HashSet<Neighberhood> NeighberhoodSet = new HashSet<>(arr);
	DA.setNeighberhood(NeighberhoodSet);
	oldel=null;
	clearFields();
	Feedback.message("Updated", "Delivery Area Updated!!");
	upd = false;
	Restaurant.updatefile();
	clearFields();
	fill();
	}
	//clear fields method
	public void clearFields() {
		DelAreaName.clear();
		deliverTimeDelArea.clear();
		NeighList1.getSelectionModel().clearSelection();
		}
	//initialize method starts the page and fills the lists,table,combobox
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<Neighberhood> Neighberhoodss = FXCollections.observableArrayList(Neighberhood.values());
		NeighList1.setItems(Neighberhoodss);
		oldel=null;
		NeighList1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	fill();
	DeliveryAreaTable.refresh();
	}
//fill table method
	public void fill() {
		ObservableList<DeliveryArea> delareadata = FXCollections
				.observableArrayList(Restaurant.getInstance().getAreas().values());
		IdClmn.setCellValueFactory(new PropertyValueFactory<DeliveryArea, String>("id"));
		areanameClmn.setCellValueFactory(new PropertyValueFactory<DeliveryArea, String>("areaName"));
		DeliveryTimeClmn.setCellValueFactory(new PropertyValueFactory<DeliveryArea, String>("deliverTime"));
		//fill Delivery person column in table
		Callback<TableColumn<DeliveryArea, Void>, TableCell<DeliveryArea, Void>> cellF = new Callback<TableColumn<DeliveryArea, Void>, TableCell<DeliveryArea, Void>>() {
			@Override
			public TableCell<DeliveryArea, Void> call(final TableColumn<DeliveryArea, Void> param) {
				final TableCell<DeliveryArea, Void> cell = new TableCell<DeliveryArea, Void>() {
					private final Button showcomp = new Button("Show");
					private final HBox pane = new HBox(1, showcomp);
					{
						pane.setAlignment(Pos.CENTER);
						showcomp.setOnAction((ActionEvent event) -> {
							DeliveryArea data = getTableView().getItems().get(getIndex());
							Set<DeliveryPerson> Delprsons = data.getDelPersons();
							ArrayList<DeliveryPerson> dps = new ArrayList<DeliveryPerson>();
							for (DeliveryPerson dp : Delprsons) {
								dps.add(dp);
							}
							ExternalWindow.deliveryperson(dps);

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
		DelpersonsClmn.setCellFactory(cellF);
		//fill Neighboorhoods column in table
		Callback<TableColumn<DeliveryArea, Void>, TableCell<DeliveryArea, Void>> NeighClmn = new Callback<TableColumn<DeliveryArea, Void>, TableCell<DeliveryArea, Void>>() {
			@Override
			public TableCell<DeliveryArea, Void> call(final TableColumn<DeliveryArea, Void> param) {
				final TableCell<DeliveryArea, Void> cellneigh = new TableCell<DeliveryArea, Void>() {
					private final Button showcomp1 = new Button("Show");
					private final HBox pane1 = new HBox(1, showcomp1);
					{
						pane1.setAlignment(Pos.CENTER);
						showcomp1.setOnAction((ActionEvent event) -> {
							DeliveryArea data = getTableView().getItems().get(getIndex());
							Set<Neighberhood> Neighberhoodsss = data.getNeighberhoods();
							ArrayList<Neighberhood> neighs = new ArrayList<Neighberhood>();
							for (Neighberhood neigh : Neighberhoodsss) {
								neighs.add(neigh);
							}
							ExternalWindow.neighboorhoods(neighs);
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
		NeighboorhoodsClmn.setCellFactory(NeighClmn);
		//fill Deliveries column in table
		Callback<TableColumn<DeliveryArea, Void>, TableCell<DeliveryArea, Void>> DelivesClmn = new Callback<TableColumn<DeliveryArea, Void>, TableCell<DeliveryArea, Void>>() {
			@Override
			public TableCell<DeliveryArea, Void> call(final TableColumn<DeliveryArea, Void> param) {
				final TableCell<DeliveryArea, Void> cellDel = new TableCell<DeliveryArea, Void>() {
					private final Button showcomp11 = new Button("Show");
					private final HBox pane11 = new HBox(1, showcomp11);
					{
						pane11.setAlignment(Pos.CENTER);
						showcomp11.setOnAction((ActionEvent event) -> {
							DeliveryArea data = getTableView().getItems().get(getIndex());
							Set<Delivery> deliveries = data.getDelivers();
							ArrayList<Delivery> delivereis = new ArrayList<Delivery>();
							for (Delivery del : deliveries) {
								delivereis.add(del);
							}
							ExternalWindow.deliveriess(delivereis);
						});
					}
					@Override
					protected void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						setGraphic(empty ? null : pane11);
					}
				};
				return cellDel;
			}
		};
		DeliveriesClmn.setCellFactory(DelivesClmn);	
		DeliveryAreaTable.setItems(delareadata);
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
