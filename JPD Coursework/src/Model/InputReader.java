package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import View.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * The class contains several functions, which read specific parts of the
 * <code>.sim</code> or <code>.txt</code> files that are passed into them as
 * parameters. They then return this information for it to be processed and
 * implemented into the simulation.
 * 
 * @author Azhar Zaman and Shibu George
 * @version 2.0
 * @since 16/03/2018
 */
public class InputReader {

	private File simulationFile; // This is a temporary variable which can holds file
	private ArrayList<String> unsortedList; // All the data in the file is loaded into this ArrayList
	private ArrayList<String> sortedList; // The data from the file is formatted into a list

	/*
	 * The orderDetail arraylist stores all the Order objects The robotList
	 * arraylist stores all the Robot objects The podList arraylist stores all the
	 * ChargingPod objects The stationList arraylist stores all the PackingStation
	 * objects The shelfList arraylist stores all the Shelf objects
	 */
	private ArrayList<String> orderDetail;
	private ArrayList<Actor> actorList;
	private ArrayList<ChargingPod> podList;
	private ArrayList<PackingStation> stationList;
	private ArrayList<StorageShelf> shelfList;

	private ArrayList<Order> totalOrders;
	private Simulation sim;
	private BufferedReader getText; // This is used to read from the file
	private String txtInformation;// This is a temporary variable which holds the next line

	// The following variables store their counterparts from the files
	private int format;
	private int width;
	private int height;
	private int capacity;
	private int chargeSpeed;
	private int chargingPodId;
	private int robotId;
	private int robotXCoord;
	private int robotYCoord;
	private int shelfId;
	private int shelfXCoord;
	private int shelfYCoord;
	private int stationId;
	private int stationXCoord;
	private int stationYCoord;

	private ArrayList<StorageShelf> shelvesToVisit;
	private int shelfIdToAdd;
	private int processingTime;
	private String output;
	private ArrayList<String> listToMainController;
	private ArrayList<StorageShelf> shelvesrandom;

	public InputReader(File filePath) {

		/*
		 * An object of the file is created to be processed, and both ArrayLists as well
		 * as the variables are initialised.
		 */

		simulationFile = filePath;
		unsortedList = new ArrayList<String>();
		sortedList = new ArrayList<String>();
		orderDetail = new ArrayList<String>();
		actorList = new ArrayList<Actor>();
		podList = new ArrayList<ChargingPod>();
		stationList = new ArrayList<PackingStation>();
		shelfList = new ArrayList<StorageShelf>();
		totalOrders = new ArrayList<Order>();
		shelvesToVisit = new ArrayList<StorageShelf>();
		listToMainController = new ArrayList<String>();
		shelvesrandom = new ArrayList<StorageShelf>();

		try {

			/*
			 * The file is read using buffered reader and the first line is stored in
			 * txtInformation
			 */
			getText = new BufferedReader(new FileReader(simulationFile));
			txtInformation = getText.readLine();

			while (txtInformation != null) { // null means there is no more text in the file

				/*
				 * Continue adding the text to the array unsortedList until the file is empty.
				 * Read the next line and check the while loop condition again.
				 */
				unsortedList.add(txtInformation);
				txtInformation = getText.readLine();
			}

			/*
			 * To prevent the unnecessary use of resources, BufferedReader is closed after
			 * it has been used
			 */

		} catch (FileNotFoundException e) {
			System.err.println("File not found");
		} catch (IOException e) {
			System.err.println("Unable to read the file.");
		} finally {

			try {

				if (getText != null)
					getText.close();

			} catch (Exception e) {

			}

		}
		for (int x = 0; x < unsortedList.size(); x++) {

			/*
			 * This method splits every element from the file by identifying the blank //
			 * spaces between eah e;ement. The elements are then placed on separate lines
			 * and stored in the arraylist sortedList.
			 */
			String[] splited = unsortedList.get(x).split("\\s+");
			for (String part : splited) {

				sortedList.add(part);

			}

		}

		getOrderInformation();
		processFile();

	}

	/**
	 * Returns an ArrayList containing the processing times, and the shelf IDs which
	 * need to be visited by the robot, that can then be used by the Simulation and
	 * Robot classes to run the simulation.
	 * <p>
	 * This method takes in the filepath for the relevant .txt or .sim file, the
	 * order details are extracted from.
	 *
	 * @param filePath
	 *            The file path to the .sim or .txt file that needs to be opened
	 * @return An ArrayList containing the processing times and shelf IDs.
	 */
	public void getOrderInformation() {

		for (int x = 0; x < unsortedList.size(); x++) {

			/*
			 * The ArrayList is searched through and the information related to the
			 * totalOrders are extracted
			 */
			if (unsortedList.get(x).contains("order")) {

				/*
				 * Using the substring method ensures that the headers before each line from the
				 * file are removed
				 * 
				 * .substring(6
				 */
				String[] splited = unsortedList.get(x).split("\\s+");
				for (String part : splited) {
					orderDetail.add(part);

				}
				// After "order", the next line is always the processing time

				// Then, each subsequent line is an id of the shelf to visit until we reach the
				// next "order"

			}

		}
	}

	/**
	 * Parses the orders from file into separate {@link Order} objects
	 */

	public void getOrders() {

		for (int i = 0; i < orderDetail.size(); i++) {
			if (orderDetail.contains("order")) {
				int index = orderDetail.indexOf("order");
				int j = index + 1;

				processingTime = Integer.parseInt(orderDetail.get(j));
				Order o = new Order(processingTime);

				int k = index + 2;

				while (k < orderDetail.size()) {
					String word = orderDetail.get(k);
					if ("order".equals(word)) {
						totalOrders.add(o);
						break;
					} else if (word.contains("ss")) {
						shelfIdToAdd = Integer.parseInt(word.substring(2));

						for (StorageShelf sh : shelfList) {

							if (sh.getIdNum() == shelfIdToAdd) {

								o.addShelfToVisit(sh);

							}

						}
					}

					k++;

				}

				orderDetail.remove(index);

			}

		}


	}

	/**
	 * This method goes through an {@link ArrayList} using a 'switch case', to extract all
	 * the values required and store them in their relevant variables so that they
	 * can then be used to create objects which can be used by the Simulation. Then
	 * all the objects are made and
	 */
	public void processFile() {

		for (int i = 0; i < sortedList.size(); i++) {

			switch (sortedList.get(i)) {

			case "format":
				format = Integer.parseInt(sortedList.get(i + 1));
				break;
			case "width":
				width = Integer.parseInt(sortedList.get(i + 1));
				break;
			case "height":
				height = Integer.parseInt(sortedList.get(i + 1));
				break;
			case "capacity":
				capacity = Integer.parseInt(sortedList.get(i + 1));
				break;
			case "chargeSpeed":
				chargeSpeed = Integer.parseInt(sortedList.get(i + 1));
				break;
			case "podRobot":
				String tempChargeId = sortedList.get(i + 1).substring(1);
				chargingPodId = Integer.parseInt(tempChargeId);
				String tempRobotId = sortedList.get(i + 2).substring(1);
				robotId = Integer.parseInt(tempRobotId);
				robotXCoord = Integer.parseInt(sortedList.get(i + 3));
				robotYCoord = Integer.parseInt(sortedList.get(i + 4));

				Location podRobotLocation = new Location(robotXCoord, robotYCoord);
				Robot robot = new Robot(robotId, podRobotLocation, capacity, chargeSpeed);
				ChargingPod pod = new ChargingPod(chargingPodId, podRobotLocation, robot);
				actorList.add(robot);
				podList.add(pod);

				break;
			case "shelf":
				String tempShelfId = sortedList.get(i + 1).substring(2);
				shelfId = Integer.parseInt(tempShelfId);
				shelfXCoord = Integer.parseInt(sortedList.get(i + 2));
				shelfYCoord = Integer.parseInt(sortedList.get(i + 3));

				Location shelfLocation = new Location(shelfXCoord, shelfYCoord);
				StorageShelf shelf = new StorageShelf(shelfId, shelfLocation);
				shelfList.add(shelf);

				break;
			case "station":
				String tempStationId = sortedList.get(i + 1).substring(2);
				stationId = Integer.parseInt(tempStationId);
				stationXCoord = Integer.parseInt(sortedList.get(i + 2));
				stationYCoord = Integer.parseInt(sortedList.get(i + 3));

				Location stationLocation = new Location(stationXCoord, stationYCoord);
				PackingStation station = new PackingStation(stationId, stationLocation);
				stationList.add(station);

				break;
			default:
				break;

			}

		}

		getOrders();
		Execute();
	}

	public void Execute() { // TODO Auto-generated method stub FXMLLoader loader
		final FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/View/mainScene.fxml"));

		MainController controller = new MainController(width, height);
		loader.setController(controller);
		try {

			Parent root6 = (Parent) loader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Simulator");
			stage.setScene(new Scene(root6));
			stage.show();

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		controller.addFromFile(actorList, podList, stationList, shelfList);
		controller.sendTotalOrder(totalOrders);
		for (String sh : listToMainController) {
			System.out.println(sh);
		}

	}

}