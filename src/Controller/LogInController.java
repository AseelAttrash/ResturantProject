package Controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import Model.Customer;
import Model.Restaurant;
import View.Feedback;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
//login page controller
public class LogInController implements Initializable {
	//page parameters/fields/buttons/table
	//current customer who logged in
	public static Customer currentcustomer = null;
	@FXML
	private MediaView media;
	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passField;
	@FXML
	private Button signbtn;
	@FXML
	private Button signupbtn;
    //parameters to video background
	private File file;
	private Media media1;
	private MediaPlayer mediaPlayer;
	//sing in button
	@FXML
	void SigninHandler(ActionEvent event) {
		//check if the customer filled all the fields
		if (usernameField.getText().length() == 0 || passField.getText().length() == 0) {
			Feedback.message("error", "please fill the fields!!");
			return;
		}
		//if the manager and the password is wrong
		if (usernameField.getText().equals("manager") && !passField.getText().equals("manager")) {
			Feedback.message("error logIn", "Password incorrect");
			return;
		}
		//manager log in goes to manager home page 
		if (usernameField.getText().equals("manager") && passField.getText().equals("manager")) {
			try {
				Main.medip.setVolume(0);
				Parent root = FXMLLoader.load(getClass().getResource("/View/ManagerPage.fxml"));

				Scene scene = new Scene(root);
				Main.PrimaryStage.setScene(scene);

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return;
		}
		//check customer user name and password
		String username = usernameField.getText(), password = passField.getText();
		//if the user name and the password are right goes to customer page
		for (Customer co : Restaurant.getInstance().getCustomers().values()) {
			if (co.getUsername().equals(username)) {
				if (co.getPassword().equals(password)) {
					LogInController.currentcustomer = co;
					try {
						Main.medip.setVolume(0);
						Parent root = FXMLLoader.load(getClass().getResource("/View/CustomerMenu.fxml"));
						Main.medip.setVolume(0);

						Scene scene = new Scene(root);
						Main.PrimaryStage.setScene(scene);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					return;
				} else {
					//password is wrong
					Feedback.message("error logIn", "Password incorrect");
					return;
				}
			}
		}
		//if the user name does not exist on the restaurant
		int count = 0;
		for (Customer co : Restaurant.getInstance().getCustomers().values()) {
			if (!co.getUsername().equals(username)) {
				count++;
			}
		}
		if (count == Restaurant.getInstance().getCustomers().size()) {
			Feedback.message("error logIn", "Username is not existed ,please try again");
			return;
		}
	}
	//sign up button goes to sign up page
	@FXML
	void SignupHandler(ActionEvent event) {
		try {
			Main.medip.setMute(true);
			Parent root = FXMLLoader.load(getClass().getResource("/View/SingUpPage.fxml"));
			Scene scene = new Scene(root);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
   //initialize method which plays video on the background of the page
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		file = new File("RestPromo.mp4");
		media1 = new Media(file.toURI().toString());
		mediaPlayer = new MediaPlayer(media1);
		Main.medip= mediaPlayer;
		media.setMediaPlayer(Main.medip);
		Main.medip.setCycleCount(Integer.MAX_VALUE);
		Main.medip.setVolume(1);
		Main.medip.play();
	}
}
