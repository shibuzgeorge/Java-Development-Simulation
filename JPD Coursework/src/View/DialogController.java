package View;

import java.io.File;
import java.io.IOException;
import Model.InputReader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * The controller for the grid size dialog box
 * 
 * @author Shibu Geroge
 * @version 1.0
 *
 */

public class DialogController {

	@FXML
	private TextField width;
	@FXML
	private TextField height;
	

	

	/**
	 * 
	 * This method apply after the user presses submit.
	 * It passes the width and height to the Draw class
	 * It loads up the mainScene FXML to open the grid
	 * 
	 */
	
	
	
	
	@FXML
	public void submit() {
		int wid = Integer.parseInt(width.getText());
		int hei = Integer.parseInt(height.getText());
		if (wid > 20 || hei > 20) {
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("You have entered above the maximum grid size!");
			alert.setContentText("Only up to 20 x 20 is allowed");
			alert.showAndWait();
			
		}
		else {
		new Warehouse(wid, hei);

		final FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("mainScene.fxml"));
		final MainController controller = new MainController(wid , hei);
		loader.setController(controller);
		try {
			final Parent root1 = (Parent) loader.load();

			final Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Window");
			stage.setScene(new Scene(root1));
			stage.show();

		} catch (IOException ex) {
			ex.printStackTrace();

		}
		height.getScene().getWindow().hide();
	}
	}
	
	/**
	 * Upload from file option which reads a txt or sim file and send it to <code>InputReader</code>
	 * 
	 */

	@FXML
	void uploadFile() {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt", "*.sim"),
				 new ExtensionFilter("All Files", "*.*"));

		File selected = fileChooser.showOpenDialog(null);
		
		if (selected !=null) {
			new InputReader(selected);
		

		     
		     
		}
		height.getScene().getWindow().hide();

	}
	
}