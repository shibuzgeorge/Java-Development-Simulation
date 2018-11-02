package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import View.MainController;

/**
 * An automated warehouse simulation of robots picking items from shelves to be
 * packed
 * 
 * @author Arun Bahra and Shibu George
 * @version 1.0
 *
 */

public class Simulation {
	// The duration of the simulation
	private int ticksDuration;
	// maintains whether the simulation is running or is finished
	private boolean active;
	// A list of all moving elements in the simulation
	private ArrayList<Actor> actors;
	// A list of all packing stations in the simulation
	private ArrayList<PackingStation> packingStations;
	// A list of all charging pods in the simulation
	private ArrayList<ChargingPod> chargingpPods;
	// private ArrayList<StorageShelf> shelves;
	private ArrayList<Actor> actorsToReturnHome;

	private ArrayList<Order> newOrders;

	private boolean runToEnd;
	private int xDim;
	private int yDim;
	
	
	private HashMap<Actor, PackingStation> assignedActors;
	private HashMap<PackingStation, Order> assignedOrders;
	private HashMap<PackingStation, Order> ordersToProcess;

	private ArrayList<Location> destinations;
	private ArrayList<Location> obstacles;
	private ArrayList<Order> dispatchedOrders;

	public Simulation(ArrayList<Actor> actors, ArrayList<PackingStation> packingStations,
			ArrayList<ChargingPod> chargingPods, ArrayList<Order> orders, int xDim, int yDim) {

		this.actors = actors;
		this.packingStations = packingStations;
		this.chargingpPods = chargingPods;
		this.newOrders = orders;
		this.xDim = xDim;
		this.yDim = yDim;
		this.setActive(true);
		this.setTicksDuration(0);
		this.assignedActors = new HashMap<Actor, PackingStation>();
		this.assignedOrders = new HashMap<PackingStation, Order>();
		this.ordersToProcess = new HashMap<PackingStation, Order>();
		this.setRunToEnd(false);
		this.obstacles = new ArrayList<Location>();
		this.destinations = new ArrayList<Location>();
		this.actorsToReturnHome = new ArrayList<Actor>();
		this.dispatchedOrders = new ArrayList<Order>();
	}

	/**
	 * Runs the simulation for a specified number of ticks
	 * 
	 * @param numTicksToAdvance
	 *            how long to run the simulation
	 */
	public void advance(int numTicksToAdvance) {
		int counter = 0;

		while ((counter < numTicksToAdvance || runToEnd) && isActive()) {
			assignNewOrderToStation();
			processOrders();
			moveActors();
			counter++;
			setTicksDuration(ticksDuration + 1);
		}

	}
	
	/**
	 * An accesor for the simulation's fields
	 * @return a list of all orders whicb have been dispatched
	 */
	
	public ArrayList<Order> getDispatchedOrders()
	{
		return dispatchedOrders;
	}
	
	/**
	 * An accesor for the simulation's fields
	 * @return a list of all orders which have been assigned to a packing station
	 */
	
	public ArrayList<Order> getAssignedOrders()
	{
		ArrayList<Order> assignedOrdersList = new ArrayList<Order>();
		for(PackingStation ps : assignedOrders.keySet())
		{
			Order o = assignedOrders.get(ps);
			assignedOrdersList.add(o);
		}
		
		return assignedOrdersList;
	}
	
	/**
	 * 
	 * @param se the {@link SimElement} to find the obstacles for
	 * @return a list of obstacles for the <code>SimElement</code> to avoid
	 */
	private ArrayList<Location> getObstacles(SimElement se) {
		ArrayList<Location> obstaclesToReturn = new ArrayList<Location>();

		for (Actor a : actors) {
			if (a.getClass().getSuperclass().equals(SimElement.class)) {
				SimElement se1 = (SimElement) a;
				if (se.idNum != se1.getIdNum()) {
					obstaclesToReturn.add(se1.getLocation());
				}
			}
		}
		return obstaclesToReturn;

	}
	
	/**
	 * Processes orders that have been delivered for 1 tick and dispatches them if finished processing
	 */

	private void processOrders() {
		for (PackingStation p : ordersToProcess.keySet()) {
			Order o = ordersToProcess.get(p);
			o.process();
			if (o.orderIsComplete()) {
				dispatchedOrders.add(o);
				ordersToProcess.remove(p);
			}
		}
	}
	
	/**
	 * Moves all {@link Actors} according to their path @see {@link Actor#move(ArrayList, ArrayList, int, int)}
	 */

	public void moveActors() {
		for (Actor a : actors) {

			if (a instanceof Robot) {
				Robot r1 = (Robot) a;
				for (ChargingPod cp : chargingpPods) {
					if (r1.getLocation().equals(cp.getLocation()) && r1.getIdNum() == cp.getIdNum()) {
						r1.increaseCharge();
						if (!actorsToReturnHome.contains(a)) {
							actorsToReturnHome.remove(a);
						}
					} else 
						r1.decreaseCharge();
						
					if (r1.isDead()) {
						
						setActive(false);
					
						break;
					}
				}
				if (a.getClass().getSuperclass().equals(SimElement.class)) {
					SimElement se = (SimElement) a;
					obstacles = getObstacles(se);

					if (assignedActors.containsKey(a)) {

						PackingStation p = assignedActors.get(a);
						Order o = assignedOrders.get(p);
						setVisitedShelves(a);
						for (StorageShelf ss : o.getStorageShelfs()) {

							if (!ss.isVisited()) {
								destinations.add(ss.getLocation());
							}

						}
						destinations.add(p.getLocation());

						if (checkDelivered(a)) {
							
							if(assignedOrders.isEmpty() && newOrders.isEmpty() && assignedActors.isEmpty()) {
								
								setActive(false);
							}
							// return to pod
							if(actorsToReturnHome.contains(a))
							{
								if(a instanceof Robot)
								{
									Robot r = (Robot) a;
									for(ChargingPod cp : chargingpPods)
									{
										if (cp.getIdNum() == r.getIdNum()
												&& !cp.getLocation().equals(r.getLocation())) {
											destinations.add(cp.getLocation());
										}
									}
								}
							}
						}
					}
				}
				a.move(destinations, obstacles, xDim, yDim);
				destinations.clear();
				obstacles.clear();
				if (hasCollided()) {
					setActive(false);
					break;
				}
			}
		}

	}
	
	/**
	 * Sets {@link StorageShelfs} as visited if a {@link Robot} has visited it as part of an order
	 * @param a
	 */

	private void setVisitedShelves(Actor a) {

		PackingStation ps = assignedActors.get(a);
		if (a.getClass().getSuperclass().equals(SimElement.class)) {
			SimElement se = (SimElement) a;
			Order o = assignedOrders.get(ps);
			for (StorageShelf ss : o.getStorageShelfs()) {
				if (se.getLocation().equals(ss.getLocation())) {
					ss.setVisited(true);
				}
			}
		}
	}
	
	/**
	 * Finds an {@link Actor} to take up a {@link Order}
	 * @return an {@link Actor}
	 */
	public Actor findSuitableActor() {
		for (Actor a : actors) {
			if (!assignedActors.containsKey(a) && a.isAvailable(xDim, yDim)) {
				return a;
			}
		}
		return null;
	}
	
	/**
	 * Assigns an {@link Order} to an idle {@link PackingStation}
	 */

	public void assignNewOrderToStation() {
		if (!newOrders.isEmpty()) {
			Actor a = findSuitableActor();
			for (PackingStation ps : packingStations) {
				if (!assignedOrders.containsKey(ps) && !(a == null)) {
					// assign the next order
					Order o = newOrders.get(0);
					assignedOrders.put(ps, o);
					newOrders.remove(o);
					newOrders.trimToSize();
					if(!assignedActors.containsKey(a))
					{
						assignedActors.put(a, ps);
					}
				}
			}
		}
	}
	
	/**
	 * Checks if an {@link Order} has been delivered
	 * @param a an {@link Actor} to check whether it has completed its order
	 * @return a <code>boolean</code> pertaining whether the {@link Actor} has completed its order
	 */

	private boolean checkDelivered(Actor a) {

		PackingStation p = assignedActors.get(a);
		if (a.getClass().getSuperclass().equals(SimElement.class)) {
			SimElement se = (SimElement) a;
			if (se.getLocation().equals(p.getLocation())) {
				Order o = assignedOrders.get(p);
				for (StorageShelf ss : o.getStorageShelfs()) {
					ss.setVisited(false);
				}
				ordersToProcess.put(p, o);
				assignedOrders.remove(p);
				assignedActors.remove(a);
				actorsToReturnHome.add(a);

				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks whether any two {@link Actors} have collided
	 * @return a <code>boolean</code> pertaining whether they have collided
	 */
	private boolean hasCollided() {

		ArrayList<SimElement> elements = new ArrayList<SimElement>();
		for (Actor a : actors) {
			if (a.getClass().getSuperclass().equals(SimElement.class)) {
				SimElement se = (SimElement) a;
				elements.add(se);
			}
		}
		for (SimElement s1 : elements) {
			for (SimElement s2 : elements) {
				if (s1.getLocation().equals(s2.getLocation()) && elements.indexOf(s1) != elements.indexOf(s2)) {
					return true;
				}
			}
		}

		return false;
	}
	
	/**
	 * An accesor for the simulation's fields
	 * @return the {@link Actors } in a simulation
	 */

	public ArrayList<Actor> getActors() {
		return actors;
	}

	/**
	 * Resets the simulation to a starting state
	 */
	public void reset() {
		destinations.clear();
		obstacles.clear();
		assignedActors.clear();
		// newOrders.clear();
		assignedOrders.clear();
		ordersToProcess.clear();
		for (Actor a : actors) {
			if (a instanceof Robot) {
				Robot r = (Robot) a;
				r.getPath().clear();
			}
		}
	}
	
	/**
	 * An accesor for the simulation's fields
	 * @return how long the simulation has run for
	 */

	public int getTicksDuration() {
		return ticksDuration;
	}

	public void setTicksDuration(int ticksDuration) {
		this.ticksDuration = ticksDuration;
	}
	
	/**
	 * An accesor for the simulation's fields
	 * @return whether the simulation is acive (has not stopped)
	 */

	public boolean isActive() {
		return active;
	}
	
	/**
	 * A mutator for the simulation's fields
	 * @param active the <code>boolean</code> value to set
	 */

	public void setActive(boolean active) {
		this.active = active;
	}
	
	/**
	 * An accesor for the simulation's fields
	 * @return whether the simulation is to run until the end
	 */

	public boolean isRunToEnd() {
		return runToEnd;
	}
	
	/**
	 * A mutator for the simulation's fields
	 * @param active the <code>boolean</code> value to set
	 */
	public void setRunToEnd(boolean runToEnd) {
		this.runToEnd = runToEnd;
	}
}
