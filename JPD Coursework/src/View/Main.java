package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main method to start the application
 * @author Daniel Ahmed
 * @version 1.0
 *
 */

public class Main extends Application {
	
	@Override
	/**
	 * Displays scene
	 * @param primaryStage the first window
	 */
	public void start(Stage primaryStage){
		try {
			final FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("dialogBox.fxml"));
			loader.setController(new DialogController());
			final Parent root = loader.load();

			
		
			
			final Scene scene = new Scene(root);
			primaryStage.setTitle("Title");
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * launches the program
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
		launch(args);
	}
}
