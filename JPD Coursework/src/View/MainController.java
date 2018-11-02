package View;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Model.Actor;
import Model.ChargingPod;
import Model.InputReader;
import Model.Location;
import Model.Order;
import Model.PackingStation;
import Model.Robot;
import Model.SimElement;
import Model.StorageShelf;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.ConstraintsBase;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The controller for the main screen. PLEASE NOTE REFERNCES TO A DRAW CLASS
 * HAVE BEEN REFACTORED TO THE WAREHOUSE CLASS.
 * 
 * @author Daniel Ahmed and Shibu George
 * @version 3.0
 *
 */

public class MainController extends GridPane {

	// GUI Fields
	@FXML
	private GridPane theGrid;
	@FXML
	private TextField RobotID;
	@FXML
	private TextField RobotX;
	@FXML
	private TextField RobotY;
	@FXML
	private TextField ShelfID;
	@FXML
	private TextField ShelfX;
	@FXML
	private TextField ShelfY;
	@FXML
	private TextField StationID;
	@FXML
	private TextField StationX;
	@FXML
	private TextField StationY;
	@FXML
	private Label gridSize;
	@FXML
	private AnchorPane anchorP;
	@FXML
	private Slider widthSliders;
	@FXML
	private Slider heightSliders;
	@FXML
	private Slider ChargeSpeed;
	@FXML
	private Slider ChargeLevel;
	@FXML
	private Label ChargingS;
	@FXML
	private Label ChargingL;
	@FXML
	private ListView<String> orderGenerated;
	@FXML
	private TextField orderRandom;
	@FXML
	private TextField theSeed;

	private int boardWidth;
	private int boardHeight;
	private Double sliderWidth;
	private Double sliderHeight;
	private Double sliderChargeLevel;
	private Double sliderChargeSpped;
	private int sliderWidthInt;
	private int sliderHeightInt;
	private int slideChargeLevelInt;
	private int sliderChargeSpeedInt;
	private Warehouse draw;
	private ArrayList<Actor> checkActor;
	private ArrayList<ChargingPod> checkChargingPod;
	private ArrayList<StorageShelf> checkStorageShelf;
	private ArrayList<PackingStation> checkPackingStation;
	private ArrayList<Order> orders;

	/**
	 * The Constructor
	 * 
	 * @param weight
	 *            : width of dialog box
	 * @param height
	 *            : height of dialog
	 */
	public MainController(int width, int height) {
		boardWidth = width;
		boardHeight = height;
		checkActor = new ArrayList<Actor>();
		checkChargingPod = new ArrayList<ChargingPod>();
		checkStorageShelf = new ArrayList<StorageShelf>();
		checkPackingStation = new ArrayList<PackingStation>();
		orders = new ArrayList<Order>();
		draw = new Warehouse(boardWidth, boardHeight);
	}

	/**
	 * Automatically shows the board
	 */
	@FXML
	public void initialize() {
		gridSize.textProperty().bind(Bindings.format("Simulator (" + boardWidth + "x" + boardHeight + ")"));
		sizes();
		widthSliders.setValue(boardWidth);
		heightSliders.setValue(boardHeight);
		chargingValues();
		ChargeLevel.setValue(50);
		ChargeSpeed.setValue(2);
		ChargingL.setText("Charging Level: " + 50);
		ChargingS.setText("Charging Speed: " + 2);
		for (int x = 0; x < Warehouse.toDraw.length; x++) {
			for (int y = 0; y < Warehouse.toDraw[x].length; y++) {
				Label emptyLabel;
				Image blank = new Image(getClass().getResourceAsStream("blank.png"));
				ImageView whitebackground = new ImageView(blank);
				whitebackground.setImage(blank);
				emptyLabel = new Label("");
				emptyLabel.setGraphic(whitebackground);

				if (boardHeight > 0 && boardHeight < 10) {
					anchorP.setMinWidth(810);
					anchorP.setMinHeight(451);
				}
				if (boardHeight >= 10 && boardHeight < 13) {
					anchorP.setMinWidth(810);
					anchorP.setMinHeight(521);
				}
				if (boardHeight >= 13 && boardHeight < 20) {
					anchorP.setMinWidth(810);
					anchorP.setMinHeight(660);
				}
				if (boardHeight >= 20 && boardHeight < 21) {
					anchorP.setMinWidth(810);
					anchorP.setMinHeight(691);
				}
				anchorP.setPrefSize(boardWidth * 40.5, boardHeight * 34.5);
				theGrid.add(emptyLabel, x, y);
			}
		}
	}

	public void sizes() {
		sliderWidth = widthSliders.getValue();
		sliderHeight = heightSliders.getValue();
		sliderWidthInt = sliderWidth.intValue();
		sliderHeightInt = sliderHeight.intValue();
	}

	/**
	 * Adds robot to toDraw array
	 */

	@FXML
	public void addRobot() {
		try {
			int robotXn = Integer.parseInt(RobotX.getText());
			int robotYn = Integer.parseInt(RobotY.getText());
			int robotid = Integer.parseInt(RobotID.getText());

			for (Actor r : checkActor) {
				if (((SimElement) r).getIdNum() == robotid) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error!");
					alert.setHeaderText("You have entered a Robot with the same ID");
					alert.setContentText("Please change your input");
					alert.showAndWait();
					return;
				}

			}

			if ((!(Warehouse.toDraw[robotXn][robotYn][0] instanceof SimElement))
					&& (!(Warehouse.toDraw[robotXn][robotYn][1] instanceof SimElement))) {

				Location l = new Location(robotXn, robotYn);
				Robot s = new Robot(robotid, l, slideChargeLevelInt, sliderChargeSpeedInt);
				draw.addToBoard(s, robotXn, robotYn, 0);
				checkActor.add(s);
				ChargingPod c = new ChargingPod(robotid, l, s);
				draw.addToBoard(c, robotXn, robotYn, 1);
				checkChargingPod.add(c);

			} else {
				error();
			}

		} catch (Exception ex) {
			error();
		}
		showBoardChange();

	}

	/**
	 * Adds shelf to toDraw array
	 */
	@FXML
	public void addShelf() {
		try {
			int shelfXn = Integer.parseInt(ShelfX.getText());
			int shelfYn = Integer.parseInt(ShelfY.getText());
			int shelfid = Integer.parseInt(ShelfID.getText());

			for (StorageShelf ss : checkStorageShelf) {
				if (ss.getIdNum() == shelfid) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error!");
					alert.setHeaderText("You have entered a Storage Shelf with the same ID");
					alert.setContentText("Please change your input");
					alert.showAndWait();
					return;
				}

			}

			if (!(Warehouse.toDraw[shelfXn][shelfYn][1] instanceof SimElement)) {

				Location l = new Location(shelfXn, shelfYn);
				StorageShelf s = new StorageShelf(shelfid, l);
				draw.addToBoard(s, shelfXn, shelfYn, 1);
				checkStorageShelf.add(s);
				showBoardChange();

			} else {
				error();
			}

		} catch (Exception ex) {
			error();
		}
	}

	/**
	 * Adds station to toDraw array
	 */
	@FXML
	public void addStation() {
		try {
			int stationXn = Integer.parseInt(StationX.getText());
			int stationYn = Integer.parseInt(StationY.getText());
			int stationid = Integer.parseInt(StationID.getText());

			for (PackingStation ps : checkPackingStation) {
				if (ps.getIdNum() == stationid) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error!");
					alert.setHeaderText("You have entered a Packing Station with the same ID");
					alert.setContentText("Please change your input");
					alert.showAndWait();
					return;
				}

			}

			if (!(Warehouse.toDraw[stationXn][stationYn][1] instanceof SimElement)) {
				Location l = new Location(stationXn, stationYn);
				PackingStation s = new PackingStation(stationid, l);
				draw.addToBoard(s, stationXn, stationYn, 1);
				checkPackingStation.add(s);
				showBoardChange();
			} else {
				error();
			}

		} catch (Exception ex) {
			error();
		}
	}

	/**
	 * Removes robot from toDraw array
	 */
	@FXML
	public void removeRobot() {
		try {
			int robotXn = Integer.parseInt(RobotX.getText());
			int robotYn = Integer.parseInt(RobotY.getText());
			int robotid = Integer.parseInt(RobotID.getText());
			if (Warehouse.toDraw[robotXn][robotYn][0] instanceof Robot
					&& (Warehouse.toDraw[robotXn][robotYn][0].getIdNum() == robotid)) {
				Robot r = (Robot) Warehouse.toDraw[robotXn][robotYn][0];
				checkActor.remove(r);
				draw.removeFromBoard(robotXn, robotYn, 0);
				draw.removeFromBoard(robotXn, robotYn, 1);

			} else {
				error();
			}
		} catch (Exception ex) {
			error();
		}
		showBoardChange();
	}

	/**
	 * Removes shelf from toDraw array
	 */
	@FXML
	public void removeShelf() {
		try {
			int ShelfXCon = Integer.parseInt(ShelfX.getText());
			int ShelfYCon = Integer.parseInt(ShelfY.getText());
			int id = Integer.parseInt(ShelfID.getText());
			if (Warehouse.toDraw[ShelfXCon][ShelfYCon][1] instanceof StorageShelf
					&& (Warehouse.toDraw[ShelfXCon][ShelfYCon][1].getIdNum() == id)) {
				StorageShelf shelf = (StorageShelf) Warehouse.toDraw[ShelfXCon][ShelfYCon][1];
				checkStorageShelf.remove(shelf);
				draw.removeFromBoard(ShelfXCon, ShelfYCon, 1);

			} else {
				error();
			}
		} catch (Exception ex) {
			error();
		}
		showBoardChange();
	}

	/**
	 * Removes station from toDraw array
	 */
	@FXML
	public void removeStation() {
		try {
			int StationXCon = Integer.parseInt(StationX.getText());
			int StationYCon = Integer.parseInt(StationY.getText());
			int id = Integer.parseInt(StationID.getText());
			if (Warehouse.toDraw[StationXCon][StationYCon][1] instanceof PackingStation
					&& (Warehouse.toDraw[StationXCon][StationYCon][1].getIdNum() == id)) {
				PackingStation pack = (PackingStation) Warehouse.toDraw[StationXCon][StationYCon][1];
				checkPackingStation.remove(pack);
				draw.removeFromBoard(StationXCon, StationYCon, 1);
			} else {

				error();
			}
		} catch (Exception ex) {

			error();

		}
		showBoardChange();
	}

	/**
	 * 
	 * Close Method FXML which closes the window once the user goes to file -->
	 * close
	 * 
	 */
	@FXML
	public void close() {
		theGrid.getScene().getWindow().hide();
	}

	/**
	 * 
	 * About us Method FXML opens a new window with the aboutUs FXML file
	 * 
	 */
	@FXML
	public void AboutUs() {
		final FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("aboutUs.fxml"));
		final AboutController controller = new AboutController();
		loader.setController(controller);
		try {
			final Parent root1 = (Parent) loader.load();
			final Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("About Us");
			stage.setScene(new Scene(root1));
			stage.show();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@FXML
	void generateOrder() {
		try {
			if (checkStorageShelf.isEmpty()) {

				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error!");
				alert.setHeaderText("You have not added a Storage Shelf");
				alert.setContentText("Please change your input");
				alert.showAndWait();

			} else {

				int seed = Integer.valueOf(theSeed.getText());
				showBoardChange();
				Random randomNumber = new Random(seed);
				int numberOfTicks = randomNumber.nextInt(Integer.SIZE - 10) + 1;

				int minNumOfShelves = 1;
				int maxNumOfShelves = randomNumber.nextInt(checkStorageShelf.size()) + minNumOfShelves;
				for (int a = 0; a < maxNumOfShelves; a++) {

					ArrayList<StorageShelf> theShelves = new ArrayList<StorageShelf>();
					for (StorageShelf ss : checkStorageShelf) {

						theShelves.add(ss);
					}

					Order randomOrder = new Order(numberOfTicks, theShelves);
					orders.add(randomOrder);
					//System.out.println(orders);
					sendTotalOrder(orders);
				}
				// orderGenerated.getItems().add(randomOrder.toString());

			}
		} catch (Exception e) {
			error();

		}
	}

	/**
	 * 
	 * File upload for users with .text and .sim files Calls the
	 * <code>InputReader</code> class to add Robots, ChargingPods, PackingStations
	 * and StorageShelfs to the Grid
	 * 
	 */
	@FXML
	void uploadFIle() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt", "*.sim"),
				new ExtensionFilter("All Files", "*.*"));
		File selected = fileChooser.showOpenDialog(null);
		if (selected != null) {
			new InputReader(selected);
			anchorP.getScene().getWindow().hide();
		}
	}

	/**
	 * 
	 * After InputReader separates the Robots, ChargingPod, PackingStation and
	 * StorageShelf They will be added to an ArrayList and uses loops to add the
	 * elements to the grid
	 * 
	 * 
	 * @param robotList
	 * @param podList
	 * @param packingList
	 * @param shelfList
	 */

	public void addFromFile(List<Actor> actorList, List<ChargingPod> podList, List<PackingStation> packingList,
			List<StorageShelf> shelfList) {

		for (ChargingPod cp : podList) {
			SimElement se = (SimElement) cp;
			int xCon = cp.getLocation().getX();
			int yCon = cp.getLocation().getY();
			draw.addToBoard(se, xCon, yCon, 1);
			checkChargingPod.add(cp);
			showBoardChange();
		}

		for (Actor r : actorList) {
			SimElement se = (SimElement) r;
			int xCon = ((SimElement) r).getLocation().getX();
			int rCharge = ((Robot) r).getCurrentChargelevel();
			int rSpeed = ((Robot) r).getSpeedlevel();
			int yCon = ((SimElement) r).getLocation().getY();
			draw.addToBoard(se, xCon, yCon, 0);
			checkActor.add((Robot) r);
			ChargeLevel.setValue(rCharge);
			ChargeSpeed.setValue(rSpeed);
		}

		for (PackingStation ps : packingList) {
			SimElement se = (SimElement) ps;
			int xCon = ps.getLocation().getX();
			int yCon = ps.getLocation().getY();

			draw.addToBoard(se, xCon, yCon, 1);
			checkPackingStation.add(ps);
		}
		for (StorageShelf ss : shelfList) {
			SimElement se = (SimElement) ss;
			int xCon = ss.getLocation().getX();
			int yCon = ss.getLocation().getY();

			draw.addToBoard(se, xCon, yCon, 1);
			checkStorageShelf.add(ss);
		}
		showBoardChange();
	}

	public void chargingValues() {
		sliderChargeLevel = ChargeLevel.getValue();
		sliderChargeSpped = ChargeSpeed.getValue();
		slideChargeLevelInt = sliderChargeLevel.intValue();
		sliderChargeSpeedInt = sliderChargeSpped.intValue();
	}

	@FXML
	public void chargingSlide() {
		showBoardChange();
	}

	@FXML
	public void autoResize(MouseEvent event) {
		sizes();
		Stage stage = (Stage) anchorP.getScene().getWindow();
		if (!stage.isMaximized()) {
			if (sliderHeightInt > 0 && sliderHeightInt < 10) {
				anchorP.getScene().getWindow().setHeight(490);
			}
			if (sliderHeightInt >= 10 && sliderHeightInt < 13) {
				anchorP.getScene().getWindow().setHeight(560);
			}
			if (sliderHeightInt >= 13 && sliderHeightInt < 20) {
				anchorP.getScene().getWindow().setHeight(699);
			}
			if (sliderHeightInt >= 20 && sliderHeightInt < 21) {
				anchorP.getScene().getWindow().setHeight(730);
			}
		}
	}

	@FXML
	public void Simulate() {
		if (checkActor.isEmpty() || checkStorageShelf.isEmpty() || checkPackingStation.isEmpty()) {

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("You must have at least 1 Robot, 1 Packing Station and 1 Storage Shelf");
			alert.setContentText("Please add inputs for the simulation to work");
			alert.showAndWait();
		} 
		if(orders.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("You must generate an order for the simulation to work");
			alert.setContentText("Please click generate for the simulation to work");
			alert.showAndWait();
			
		}
		else {
			sizes();
			final FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("simScene.fxml"));
			final SimController controller = new SimController(draw, sliderWidthInt, sliderHeightInt,
					slideChargeLevelInt, sliderChargeSpeedInt, orders);
			loader.setController(controller);
			try {
				final Parent root6 = (Parent) loader.load();
				final Stage stage = new Stage();
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setTitle("Simulator");
				stage.setScene(new Scene(root6));
				stage.show();
			} catch (IOException ex) {
				ex.printStackTrace();
			}

			theGrid.getScene().getWindow().hide();
		}
	}

	public void error() {

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error!");
		alert.setHeaderText("You have entered invalid information");
		alert.setContentText("Please change your input");
		alert.showAndWait();
	}

	@FXML
	void sliderSize(MouseEvent event) {
		sizes();
		theGrid.getChildren().clear();
		orderGenerated.getItems().clear();
		orders.clear();
		checkActor.clear();
		checkChargingPod.clear();
		checkPackingStation.clear();
		checkStorageShelf.clear();
		draw = new Warehouse(sliderWidthInt, sliderHeightInt);
		showBoardChange();
	}

	@FXML
	void clearGrid() {
		sizes();
		theGrid.getChildren().clear();
		orderGenerated.getItems().clear();
		orders.clear();
		checkActor.clear();
		checkChargingPod.clear();
		checkPackingStation.clear();
		checkStorageShelf.clear();
		draw = new Warehouse(sliderWidthInt, sliderHeightInt);
		showBoardChange();

	}

	@FXML
	public void showBoardChange() {
		sizes();
		Random randomNumber = new Random();
		int value = randomNumber.nextInt(165043);
		theSeed.setText(Integer.toString(value));
		gridSize.textProperty().bind(Bindings.format("Simulator (" + sliderWidthInt + "x" + sliderHeightInt + ")"));
		widthSliders.setValue(sliderWidthInt);
		heightSliders.setValue(sliderHeightInt);
		chargingValues();

		ChargingL.textProperty().bind(Bindings.format("Charging Level: " + slideChargeLevelInt));

		ChargingS.textProperty().bind(Bindings.format("Charging Speed: " + sliderChargeSpeedInt));
		ChargeLevel.setValue(slideChargeLevelInt);
		ChargeSpeed.setValue(sliderChargeSpeedInt);

		theGrid.getColumnConstraints().clear();
		theGrid.getColumnConstraints().add(new ColumnConstraints(25));
		for (int x = 0; x < Warehouse.toDraw.length; x++) {
			for (int y = 0; y < Warehouse.toDraw[x].length; y++) {
				StackPane sp;
				if (Warehouse.toDraw[x][y][1] instanceof ChargingPod) {
					Label ibl1 = new Label("");
					Image charging = new Image(getClass().getResourceAsStream("charging.png"));
					ImageView charge = new ImageView(charging);
					charge.setImage(charging);
					ibl1.setGraphic(charge);

					sp = new StackPane();
					sp.getChildren().add(ibl1);

					if (Warehouse.toDraw[x][y][0] instanceof Robot) {
						Label ibl2 = new Label("");
						Image rob = new Image(getClass().getResourceAsStream("robot.png"));

						ImageView robot = new ImageView(rob);

						robot.setImage(rob);
						ibl2.setGraphic(robot);
						sp.getChildren().add(ibl2);

					}

				} else if (Warehouse.toDraw[x][y][1] instanceof StorageShelf) {

					Label ibl1 = new Label("");
					Image shelf = new Image(getClass().getResourceAsStream("shelf.png"));
					ImageView storageshelf = new ImageView(shelf);
					storageshelf.setImage(shelf);
					ibl1.setGraphic(storageshelf);
					sp = new StackPane();
					sp.getChildren().add(ibl1);

				} else if (Warehouse.toDraw[x][y][1] instanceof PackingStation) {
					Label ibl1 = new Label("");
					Image pack = new Image(getClass().getResourceAsStream("packing.png"));
					ImageView packingstation = new ImageView(pack);
					packingstation.setImage(pack);
					ibl1.setGraphic(packingstation);
					sp = new StackPane();
					sp.getChildren().add(ibl1);

				} else {
					Label ibl1 = new Label("");
					Image blank = new Image(getClass().getResourceAsStream("blank.png"));
					ImageView whitebackground = new ImageView(blank);
					whitebackground.setImage(blank);
					ibl1.setGraphic(whitebackground);
					sp = new StackPane();
					sp.getChildren().add(ibl1);
				}

				theGrid.add(sp, x, y);

			}
		}
	}

	public void sendTotalOrder(ArrayList<Order> totalOrders) {
		orders = totalOrders;
		for (Order o : orders) {

			orderGenerated.getItems().add(o.toString());

			// orderLists.add(output);
		}

	}

}