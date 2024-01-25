package View;

import java.util.ArrayList;
import java.util.List;

import Model.Delivery;
import Model.DeliveryPerson;
import Model.Dish;
import Utils.Neighberhood;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
//external window to show lists
public class ExternalWindow {
	//to show component list
	public static void compWindow(List<Model.Component> list) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("details");
		Button closeb = new Button("Close");
		closeb.setOnAction(e -> window.close());
		VBox layout = new VBox(10);
		ListView<Model.Component> lv= new ListView<>();
		ObservableList<Model.Component> types = FXCollections.observableArrayList(list);
		lv.setItems(types);
		layout.getChildren().addAll(lv, closeb);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}
    //to show delivery person list
	public static void deliveryperson(ArrayList<DeliveryPerson> dps) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("details");
		Button closeb = new Button("Close");
		closeb.setOnAction(e -> window.close());
		VBox layout = new VBox(10);
		ListView<Model.DeliveryPerson> lv= new ListView<>();
		ObservableList<Model.DeliveryPerson> types = FXCollections.observableArrayList(dps);
		lv.setItems(types);
		layout.getChildren().addAll(lv, closeb);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();		
	}
	//to show dishes list
	public static void dishes(ArrayList<Dish> dps) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("details");
		Button closeb = new Button("Close");
		closeb.setOnAction(e -> window.close());
		VBox layout = new VBox(10);
		ListView<Model.Dish> lv= new ListView<>();
		ObservableList<Model.Dish> types = FXCollections.observableArrayList(dps);
		lv.setItems(types);
		layout.getChildren().addAll(lv, closeb);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();		
	}
    //to show neighberhood list
	public static void neighboorhoods(ArrayList<Neighberhood> neighs) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("details");
		Button closeb = new Button("Close");
		closeb.setOnAction(e -> window.close());
		VBox layout = new VBox(10);
		ListView<Utils.Neighberhood> lv= new ListView<>();
		ObservableList<Utils.Neighberhood> types = FXCollections.observableArrayList(neighs);
		lv.setItems(types);
		layout.getChildren().addAll(lv, closeb);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();		
	}
    //to show deliveries list
	public static void deliveriess(ArrayList<Delivery> delivereis) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("details");
		Button closeb = new Button("Close");
		closeb.setOnAction(e -> window.close());
		VBox layout = new VBox(10);
		ListView<Model.Delivery> lv= new ListView<>();
		ObservableList<Model.Delivery> types = FXCollections.observableArrayList(delivereis);
		lv.setItems(types);
		layout.getChildren().addAll(lv, closeb);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();			
	}


}
