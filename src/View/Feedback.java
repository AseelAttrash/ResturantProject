package View;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
//feedback method which alerts if theres something not right
public class Feedback {

	public static void message(String title,String content) {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(content);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();

		stage.setAlwaysOnTop(true);
		alert.show();

	}

}
