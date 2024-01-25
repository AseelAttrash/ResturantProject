package Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
//main launches restaurant fxml pages
public class Main extends Application{
	 static MediaPlayer medip;
	public static void main(String[] args)
	{

		launch(args);
	}
	public static Stage PrimaryStage=null;
	@Override
	public void start(Stage arg0) throws Exception {
		try {
			//login page which starts with
			Parent root = FXMLLoader.load(getClass().getResource("/View/LogIn.fxml"));
			Scene scene = new Scene(root);
			arg0.setScene(scene);
			arg0.setResizable(false);
			PrimaryStage=arg0;
			arg0.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
