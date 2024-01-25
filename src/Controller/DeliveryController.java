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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TreeSet;


import Model.Delivery;
import Model.DeliveryArea;
import Model.DeliveryPerson;
import Model.ExpressDelivery;
import Model.Order;
import Model.RegularDelivery;
import Model.Restaurant;
import View.Feedback;
//delivery page controller
public class DeliveryController implements Initializable{
	//page parameters/fields/buttons/table
	private Delivery del;
	@FXML
	private DatePicker DeliveryDateField;
	@FXML
	private TextField PostageField;
	@FXML
	private CheckBox DeliveredCheck;
	@FXML
	private Button AddDeliveryButton;
	@FXML
	private ListView<Order> OrdersList;
	@FXML
	private ComboBox<DeliveryPerson> DelpersonCombobox;
	@FXML
	private ComboBox<DeliveryArea> DelAreaCombobox;
	@FXML
	private TableView<Delivery> DeliveriesTable;
	@FXML
	private TableColumn<Delivery, String> IDclmn;
	@FXML
	private TableColumn<Delivery, String> typeClmn;
	@FXML
	private TableColumn<Delivery, String> DelpersonClmn;
	@FXML
	private TableColumn<Delivery, String> DelAreaClmn;
	@FXML
	private TableColumn<Delivery, String> DateClmn;
	@FXML
	private TableColumn<Delivery, String> isdeliveredClmn;
	@FXML
	private TableColumn<Delivery, String> postageclmn;
	@FXML
	private TableColumn<Delivery, Void> ordersClmn;
	@FXML
	private Button UpdateDeliveryButton;
	@FXML
	private Button RemoveeliveryButton;
	private boolean upd=false;
	//initialize method fills table and combo boxs,lists.
	public void initialize(URL arg0, ResourceBundle arg1) {
       //fill combo boxs
		ObservableList<DeliveryArea> areas = FXCollections
			.observableArrayList(Restaurant.getInstance().getAreas().values());
		DelAreaCombobox.setItems(areas);
		ObservableList<DeliveryPerson> persons = FXCollections
				.observableArrayList(Restaurant.getInstance().getDeliveryPersons().values());
		DelpersonCombobox.setItems(persons);
		//fill lists
		ObservableList<Order> orders = FXCollections
				.observableArrayList(Restaurant.getInstance().getOrders().values());
		OrdersList.setItems(orders);
		//to choose more than an option
		OrdersList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		fillTableView();
	}
	
	//add delivery button action
	@FXML
	void AddAction(ActionEvent event) {
		//if the manager didn't fill all the fields
		if (DelpersonCombobox.getSelectionModel().getSelectedIndex() == -1 || DeliveryDateField.getValue() == null
				|| DelAreaCombobox.getSelectionModel().getSelectedIndex() == -1
				|| OrdersList.getSelectionModel().getSelectedItems().size() == 0) {
			Feedback.message("Error", "you must fill all the fields!");
			return;
		}
		//if we are not updating a delivery
		  if(upd==true)
		    {
		    	Feedback.message("error", "You cant add while updating!");
		    	return;
		    }
	if(DeliveryDateField.getValue().compareTo(LocalDate.now())>0 && DeliveredCheck.isSelected() )
	{
		Feedback.message("Error", "future deliveries must not be delivered!");
		return;
	}
        //if it is an express delivery
		if (OrdersList.getSelectionModel().getSelectedItems().size() == 1) {
			//automatic postage equal to 5
			if (PostageField.getText().length() == 0) {
				//create new express delivery to add
				 del = new ExpressDelivery(DelpersonCombobox.getSelectionModel().getSelectedItem(),
						DelAreaCombobox.getSelectionModel().getSelectedItem(), DeliveredCheck.isSelected(),
						OrdersList.getSelectionModel().getSelectedItem(), DeliveryDateField.getValue());
				 //add express delivery to restaurant
				if (!Restaurant.getInstance().addDelivery(del)) {
					Feedback.message("error", "express delivery not added");
					return;
				} 
				//fill the table and update file also clear the fields
				Restaurant.updatefile();
					Feedback.message("add", "express delivery added");
					clearFields();
					fillTableView();
					DeliveriesTable.refresh();
					return;
			}
            //if he filled the postage
			double pos = 0;
			//check if the postage is numaric and more than 0
			try {
				pos = Double.valueOf(PostageField.getText());
			} catch (Exception e) {
				Feedback.message("error", "postage field must be numaric");
				return;
			}
			if(pos<=0)
			{Feedback.message("error", "postage field must be more than 0");
			return;
				
			}
			//create new express delivery to add
			 del = new ExpressDelivery(DelpersonCombobox.getSelectionModel().getSelectedItem(),
					DelAreaCombobox.getSelectionModel().getSelectedItem(), DeliveredCheck.isSelected(),
					OrdersList.getSelectionModel().getSelectedItem(), pos, DeliveryDateField.getValue());
			 //add express delivery
			if (!Restaurant.getInstance().addDelivery(del)) {
				Feedback.message("error", "express delivery not added");
				return;
			} 
			//fill the table and update file also clear the fields
			Restaurant.updatefile();
			Feedback.message("add", "express delivery added");
			clearFields();
			fillTableView();
			DeliveriesTable.refresh();

				return;
			
		}
		// if it is not an express delivery
		TreeSet<Order> treeo = new TreeSet<Order>(OrdersList.getSelectionModel().getSelectedItems());
          //create regular delivery to add
		del = new RegularDelivery(treeo, DelpersonCombobox.getSelectionModel().getSelectedItem(),
				DelAreaCombobox.getSelectionModel().getSelectedItem(), DeliveredCheck.isSelected(),
				DeliveryDateField.getValue());
		//add regular delivery 
		if (!Restaurant.getInstance().addDelivery(del)) {
			Feedback.message("error", "regular delivery not added");
			return;
		} 
		//fill the table and update file also clear the fields
		Restaurant.updatefile();
			Feedback.message("add", "regular delivery added");
			clearFields();
			fillTableView();
			DeliveriesTable.refresh();
		
		
	}
//fill table method
	void fillTableView() {
		ArrayList<Delivery> alld=new ArrayList<>();
		for(Delivery d: Restaurant.getInstance().getExpressdeliveries().values())
			alld.add(d);
		for(Delivery d: Restaurant.getInstance().getRegulardeliveries().values())
			alld.add(d);
		ObservableList<Delivery> deldata = FXCollections
				.observableArrayList(alld);
		IDclmn.setCellValueFactory(new PropertyValueFactory<Delivery, String>("id"));
		DelpersonClmn.setCellValueFactory(new PropertyValueFactory<Delivery, String>("deliveryPerson"));
		DelAreaClmn.setCellValueFactory(new PropertyValueFactory<Delivery, String>("area"));
		DateClmn.setCellValueFactory(new PropertyValueFactory<Delivery, String>("deliveredDate"));
		isdeliveredClmn.setCellValueFactory(new PropertyValueFactory<Delivery, String>("isdelivered"));
		postageclmn.setCellValueFactory(new PropertyValueFactory<Delivery, String>("postage"));
         //fill orders column
		Callback<TableColumn<Delivery, Void>, TableCell<Delivery, Void>> cellF = new Callback<TableColumn<Delivery, Void>, TableCell<Delivery, Void>>() {
			@Override
			public TableCell<Delivery, Void> call(final TableColumn<Delivery, Void> param) {
				final TableCell<Delivery, Void> cell = new TableCell<Delivery, Void>() {

					private ComboBox<Order> orders = new ComboBox<>();

					private final HBox pane = new HBox(1, orders);

					{
						//combo box to show orders
						pane.setAlignment(Pos.CENTER);
						orders.addEventHandler(ComboBox.ON_HIDDEN, event -> {
							ObservableList<Order> dldata;
							Delivery data = DeliveriesTable.getItems().get(getIndex());
							if (data instanceof ExpressDelivery) {
								dldata = FXCollections.observableArrayList(((ExpressDelivery) data).getOrder());
							} else {
								dldata = FXCollections.observableArrayList(((RegularDelivery) data).getOrders());
							}
							orders.setItems(dldata);
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
		typeClmn.setCellValueFactory(new PropertyValueFactory<Delivery, String>("type"));
		DeliveriesTable.setItems(deldata);
		ordersClmn.setCellFactory(cellF);
	}
//remover delivery button action
	@FXML
	void Removeaction(ActionEvent event) {
		//check if the manager has Selected a delivery to remove
    	if (DeliveriesTable.getSelectionModel().getSelectedIndex() == -1) {
    		Feedback.message("error", "please choose a Delivery to remove");
    		return;
    	}
    	//check if the manager is updating a delivery
    			if(upd==true)
    	    	{
    	    		Feedback.message("Error", "you can't remove while updating!");
    				return;
    	    	} 
    			//remove delivery
	if (!Restaurant.getInstance().removeDelivery(DeliveriesTable.getSelectionModel().getSelectedItem())) {
		Feedback.message("error", "can not remove Delivery  ");
		return;
	}else {
		Feedback.message("removed!", "Delivery  has been removed!");
	}
	//fill the table and update file also clear the fields
	Restaurant.updatefile();
	fillTableView();
		clearFields();
		DeliveriesTable.refresh();

	}
	//clear fields to fill again method
	public void clearFields() {

		PostageField.clear();
		DelpersonCombobox.getSelectionModel().clearSelection();
		DelAreaCombobox.getSelectionModel().clearSelection();
		OrdersList.getSelectionModel().clearSelection();
		DeliveredCheck.setSelected(false);
		DeliveryDateField.setValue(null);
		}
	//update delivery action button
	@FXML
	void UpdateActions(ActionEvent event) {
		
		//check if the manager has Selected a delivery to update
		if (DeliveriesTable.getSelectionModel().getSelectedIndex() == -1) {
			Feedback.message("error", "please choose a Delivery to update");
			return;
		}
		 //fill the fields with the delivery that the manager has choosen
		Delivery del=DeliveriesTable.getSelectionModel().getSelectedItem();
		if(!upd) {
			DelpersonCombobox.getSelectionModel().select(del.getDeliveryPerson());
			DelAreaCombobox.getSelectionModel().select(del.getArea());
			if(del instanceof ExpressDelivery) {
			OrdersList.getSelectionModel().select(((ExpressDelivery)del).getOrder());
			PostageField.setText(String.valueOf(((ExpressDelivery)del).getPostage()));

			}else {
				for(Order o:((RegularDelivery)del).getOrders())
				OrdersList.getSelectionModel().select(o);
				PostageField.setText("");
			}
			DeliveredCheck.setSelected(del.isDelivered());
			DeliveryDateField.setValue(del.getDeliveredDate());
			upd=true;
			Feedback.message("update", "update related field and click update!");
			return;
		}
		//check if one of the fields is null
		if (DelpersonCombobox.getSelectionModel().getSelectedIndex() == -1 || DeliveryDateField.getValue() == null
				|| DelAreaCombobox.getSelectionModel().getSelectedIndex() == -1
				|| OrdersList.getSelectionModel().getSelectedItems().size() == 0) {
			Feedback.message("Error", "you must fill all the fields!");
			return;
		}
		
		Delivery Delivery1 = DeliveriesTable.getSelectionModel().getSelectedItem();
		int temp=Delivery.getCounter();
        //express delivery
		if (OrdersList.getSelectionModel().getSelectedItems().size() == 1) {
			//automatic postage equal to 5
			if (PostageField.getText().length() == 0) {
				ExpressDelivery ed = new ExpressDelivery(DelpersonCombobox.getSelectionModel().getSelectedItem(),
						DelAreaCombobox.getSelectionModel().getSelectedItem(), DeliveredCheck.isSelected(),
						OrdersList.getSelectionModel().getSelectedItem(), DeliveryDateField.getValue());
				ed.setId(Delivery1.getId());
				Delivery.setCounter(temp);
				//remove current delivery and replace it with the new one
				Restaurant.getInstance().removeDelivery(Delivery1);
				if (!Restaurant.getInstance().addDelivery(ed)) {
					Feedback.message("error", "express delivery not updated");
					return;
				} else {
					Feedback.message("update", "express delivery updated");
					Restaurant.updatefile();
					//fill the table and update file also clear the fields
					fillTableView();
					clearFields();
					DeliveriesTable.refresh();
					DeliveriesTable.refresh();
					upd=false;
					return;
				}
			}
             //postage that manager chose not automatic
			double pos = 0;
			//check if the postage is numaric and more than 0
			try {
				pos = Double.valueOf(PostageField.getText());
			} catch (Exception e) {
				Feedback.message("error", "postage field must be numaric");
				return;
			}
			if(pos<=0)
			{Feedback.message("error", "postage field must be more than 0");
			return;
			}
			//update
			ExpressDelivery ed = new ExpressDelivery(DelpersonCombobox.getSelectionModel().getSelectedItem(),
					DelAreaCombobox.getSelectionModel().getSelectedItem(), DeliveredCheck.isSelected(),
					OrdersList.getSelectionModel().getSelectedItem(), pos, DeliveryDateField.getValue());
			ed.setId(Delivery1.getId());
			Delivery.setCounter(temp);
			Restaurant.getInstance().removeDelivery(Delivery1);
			if (!Restaurant.getInstance().addDelivery(ed)) {
				Feedback.message("error", "express delivery not update");
				return;
			} else {
				Feedback.message("update", "express delivery updated");
				//fill the table and update file also clear the fields
				Restaurant.updatefile();
				DeliveriesTable.refresh();
				fillTableView();
				clearFields();
				upd=false;
				return;
			}
		}
		//regular delivery
		TreeSet<Order> treeo = new TreeSet<Order>(OrdersList.getSelectionModel().getSelectedItems());
		RegularDelivery rd = new RegularDelivery(treeo, DelpersonCombobox.getSelectionModel().getSelectedItem(),
				DelAreaCombobox.getSelectionModel().getSelectedItem(), DeliveredCheck.isSelected(),
				DeliveryDateField.getValue());
		//set id
		rd.setId(Delivery1.getId());
		Delivery.setCounter(temp);
		Restaurant.getInstance().removeDelivery(Delivery1);
		if (!Restaurant.getInstance().addDelivery(rd)) {
			Feedback.message("error", "regular delivery not updated");
			return;
		} else {
			//fill the table and update file also clear the fields
			Feedback.message("update", "regular delivery updated");
			Restaurant.updatefile();
			DeliveriesTable.refresh();
			fillTableView();
			clearFields();
			upd=false;
			return;
		}
	}
	   //back action button to manager home page
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

