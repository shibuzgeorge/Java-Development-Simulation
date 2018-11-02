package View;

import javafx.fxml.FXML;
import javafx.stage.Stage;

import javafx.scene.control.Button;
/**
 * The controller for the about us dialog box. 
 * 
 * @author Shibu Geroge
 * @version 1.0
 *
 */




public class AboutController {
	@FXML
	private Button closeButton;
	
	@FXML
	public void close() {
		
		Stage stage = (Stage) closeButton.getScene().getWindow();
	    // do what you have to do
	    stage.close();
		
	}

}
