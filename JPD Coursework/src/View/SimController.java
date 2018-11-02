package View;

import java.io.IOException;
import java.util.ArrayList;

import Model.Actor;
import Model.ChargingPod;
import Model.Location;
import Model.Order;
import Model.PackingStation;
import Model.Robot;
import Model.SimElement;
import Model.StorageShelf;
import Model.Simulation;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class will implement what the user added to the grid and make the Robots
 * move to the StorageShelf and then to the PackingStation where they will
 * assign orders. The robots will go to the Charging Pod to get charged up.
 * 
 * @author Shibu George
 *
 */

public class SimController {

	private int wid;
	private int hei;

	private int slideL;
	private int slideS;

	@FXML
	private GridPane Grid;
	@FXML
	private AnchorPane anchorP;
	@FXML
	private Label Ticks;
	@FXML
	private ListView<String> robotListView;

	@FXML
	private ListView<String> unassignedListView;

	@FXML
	private ListView<String> assignedListView;

	@FXML
	private ListView<String> dispatchedListView;
	@FXML
	private ListView<String> packingListView;
	@FXML
	private Button oneTicks;

	@FXML
	private Button tenTicks;

	@FXML
	private Button EndTicks;

	private Warehouse draw;
	private ArrayList<Actor> actors;

	// A list of all packing stations in the simulation
	private ArrayList<PackingStation> packingStations;
	// A list of all storage shelves in the simulation
	private ArrayList<StorageShelf> shelves;
	// A list of all charging pods in the simulation
	private ArrayList<ChargingPod> chargingpPods;

	private ArrayList<Order> newOrders;

	private ArrayList<Actor> resetActors;

	private Simulation sim;

	public SimController(Warehouse draw, int width, int height, int slidelevel, int slidespeed,
			ArrayList<Order> orders) {

		this.draw = draw;

		wid = width;
		hei = height;
		actors = new ArrayList<Actor>();
		packingStations = new ArrayList<PackingStation>();
		shelves = new ArrayList<StorageShelf>();
		chargingpPods = new ArrayList<ChargingPod>();

		newOrders = orders;
		resetActors = new ArrayList<Actor>();
		slideL = slidelevel;
		slideS = slidespeed;

	}

	@FXML
	public void initialize() {
		for (Order o : newOrders) {
			unassignedListView.getItems().add(o.toString());
		}

		Grid.getColumnConstraints().clear();
		Grid.getColumnConstraints().add(new ColumnConstraints(25));

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
					SimElement se = (SimElement) Warehouse.toDraw[x][y][1];
					chargingpPods.add((ChargingPod) se);

					if (Warehouse.toDraw[x][y][0] instanceof Robot) {

						Label ibl2 = new Label("");
						Image rob = new Image(getClass().getResourceAsStream("robot.png"));
						ImageView robot = new ImageView(rob);
						robot.setImage(rob);
						ibl2.setGraphic(robot);
						sp.getChildren().add(ibl2);
						SimElement see = (SimElement) Warehouse.toDraw[x][y][0];
						actors.add((Robot) see);
						ReturnRobot();

					}

				} else if (Warehouse.toDraw[x][y][1] instanceof StorageShelf) {

					Label ibl1 = new Label("");
					Image shelf = new Image(getClass().getResourceAsStream("shelf.png"));
					ImageView storageshelf = new ImageView(shelf);
					storageshelf.setImage(shelf);
					ibl1.setGraphic(storageshelf);
					sp = new StackPane();
					sp.getChildren().add(ibl1);
					SimElement se = (SimElement) Warehouse.toDraw[x][y][1];
					shelves.add((StorageShelf) se);

				} else if (Warehouse.toDraw[x][y][1] instanceof PackingStation) {

					Label ibl1 = new Label("");
					Image pack = new Image(getClass().getResourceAsStream("packing.png"));
					ImageView packingstation = new ImageView(pack);
					packingstation.setImage(pack);
					ibl1.setGraphic(packingstation);
					sp = new StackPane();
					sp.getChildren().add(ibl1);
					SimElement se = (SimElement) Warehouse.toDraw[x][y][1];
					packingStations.add((PackingStation) se);

				}

				else {

					Label ibl1 = new Label("");
					Image blank = new Image(getClass().getResourceAsStream("blank.png"));
					ImageView whitebackground = new ImageView(blank);
					whitebackground.setImage(blank);
					ibl1.setGraphic(whitebackground);
					sp = new StackPane();
					sp.getChildren().add(ibl1);

				}

				sizes();

				Grid.add(sp, x, y);

			}
		}
		for (Actor a : actors) {
			robotListView.getItems().add(a.toString());

		}
		for (PackingStation ps : packingStations) {
			packingListView.getItems().add(ps.toString());

		}

		sim = new Simulation(actors, packingStations, chargingpPods, newOrders, wid, hei);

	}

	public void updateBoard(ArrayList<Actor> newActors) {
		robotListView.getItems().clear();
		packingListView.getItems().clear();
		unassignedListView.getItems().clear();

		Ticks.textProperty().bind(Bindings.format("Ticks: " + sim.getTicksDuration()));
		Grid.getColumnConstraints().clear();
		Grid.getColumnConstraints().add(new ColumnConstraints(25));

		for (Actor a : newActors) {
			if (a.getClass().getSuperclass().equals(SimElement.class)) {
				SimElement se = (SimElement) a;
				draw.updateToBoard(se, se.getLocation().getX(), se.getLocation().getY(), 0);
			}
		}

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
				} else if (Warehouse.toDraw[x][y][0] instanceof Robot) {

					Label ibl2 = new Label("");
					Image rob = new Image(getClass().getResourceAsStream("robot.png"));
					ImageView robot = new ImageView(rob);
					robot.setImage(rob);
					ibl2.setGraphic(robot);
					sp = new StackPane();
					sp.getChildren().add(ibl2);

				} else if (Warehouse.toDraw[x][y][1] instanceof StorageShelf) {

					Label ibl1 = new Label("");
					Image shelf = new Image(getClass().getResourceAsStream("shelf.png"));
					ImageView storageshelf = new ImageView(shelf);
					storageshelf.setImage(shelf);
					ibl1.setGraphic(storageshelf);
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

				} else if (Warehouse.toDraw[x][y][1] instanceof PackingStation) {

					Label ibl1 = new Label("");
					Image pack = new Image(getClass().getResourceAsStream("packing.png"));
					ImageView packingstation = new ImageView(pack);
					packingstation.setImage(pack);
					ibl1.setGraphic(packingstation);
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

				}

				else {

					Label ibl1 = new Label("");
					Image blank = new Image(getClass().getResourceAsStream("blank.png"));
					ImageView whitebackground = new ImageView(blank);
					whitebackground.setImage(blank);
					ibl1.setGraphic(whitebackground);
					sp = new StackPane();
					sp.getChildren().add(ibl1);

				}

				sizes();
				Grid.add(sp, x, y);

			}
		}
		for (Order o : newOrders) {
			unassignedListView.getItems().add(o.toString());
		}
		for (Actor a : actors) {
			robotListView.getItems().add(a.toString());
		}
		for (PackingStation ps : packingStations) {
			packingListView.getItems().add(ps.toString());

		}
		for (Order assignedOrder : sim.getAssignedOrders()) {
			assignedListView.getItems().add(assignedOrder.toString());
		}
		for (Order o : sim.getDispatchedOrders()) {
			dispatchedListView.getItems().add(o.toString());
		}

		if (sim.isActive() == false) {

			oneTicks.setDisable(true);
			
			tenTicks.setDisable(true);
			
			EndTicks.setDisable(true);
		}
	}

	public void sizes() {
		if (wid > 0 && wid < 10 || hei > 0 && hei < 10) {
			anchorP.setMinWidth(689);
			anchorP.setMinHeight(500);
		}
		if (wid >= 10 && wid < 14 || hei >= 10 && hei < 14) {
			anchorP.setMinWidth(689);
			anchorP.setMinHeight(550);

		}
		if (wid >= 14 && wid < 20 || hei >= 14 && hei < 20) {
			anchorP.setMinWidth(789);
			anchorP.setMinHeight(550);
		}
		if (wid >= 20 && wid < 21 || hei >= 20 && hei < 21) {
			anchorP.setMinWidth(889);
			anchorP.setMinHeight(550);
		}

		anchorP.setPrefSize(wid * 38.2, hei * 26.6);

	}

	@FXML
	public void oneTick() {
		sim.advance(1);
		updateBoard(sim.getActors());

	}

	@FXML
	public void tenTick() {
		for (int i = 0; i < 10; i++) {
			sim.advance(1);
			updateBoard(sim.getActors());
		}

	}

	@FXML
	public void EndTick() {
		sim.setRunToEnd(true);
		sim.advance(0);
		updateBoard(sim.getActors());
	}

	@FXML
	public void Return() {
		sim.reset();
		Grid.getScene().getWindow().hide();
		final FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("mainScene.fxml"));
		final MainController controller = new MainController(wid, hei);
		loader.setController(controller);
		try {
			final Parent root1 = (Parent) loader.load();
			final Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Simulator");
			stage.setScene(new Scene(root1));
			stage.show();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		controller.addFromFile(resetActors, chargingpPods, packingStations, shelves);
		newOrders.clear();

	}

	public void ReturnRobot() {
		for (ChargingPod cp : chargingpPods) {

			int chargeX = cp.getLocation().getX();
			int chargeY = cp.getLocation().getY();
			int chargeid = cp.getIdNum();
			Location l = new Location(chargeX, chargeY);
			Actor r = new Robot(chargeid, l, slideL, slideS);
			resetActors.add(r);

		}

	}

}
