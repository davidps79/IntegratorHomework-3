/**
 * Array with actual states of the mini rooms. It is modified by the methods
 * related to main menu functionalities (UserInterface class).
 */
package model;

/**
 * It has most of the intern logic of the program. But it does not communicate with the user directly, its methods are called by the UserInterface class.
 */
public class DataCenterSystem{
	/**
	 * Number of corridors (rows) of the data center.
	 */
	private int CORRIDORS;

	/**
	 * Number of columns (subdivisions of each corridor) of the data center.
	 */
	private int COLUMNS;

	/** 
	 * Base price of a mini room. It is also used to calculate discounts and recharges.
	 */
	private double BASE_PRICE;

	/**
	 * Array with simulated states of the mini rooms. It is modified by the methods
	 * related to the simulation menu functionalities (UserInterface class).
	 */
	private char[][] simulatedMiniRoomsStates;

	/**
	 * Matrix with the MiniRoom objects.
	 */
	private MiniRoom[][] miniRooms;

	/**
	 * Constructor of the class. It initializes all the mini rooms as available and turned off and
	 * assigns the prices according to the position of each one of them (discounts and recharges
	 * rules stablished by the university).
	 * @param basePrice double.
	 * @param corridors int.
	 * @param columns int.
	 */
	public DataCenterSystem(double basePrice, int corridors, int columns){
		this.BASE_PRICE = basePrice;
		this.CORRIDORS = corridors;
		this.COLUMNS = columns;
		this.miniRooms = new MiniRoom[CORRIDORS][COLUMNS];
		for (int i=0; i<CORRIDORS; i++){
			for (int j=0; j<COLUMNS; j++){
				String id = (i*COLUMNS + (j+1)) + "";
				boolean nextToWindow = false;
				double rentalPrice = basePrice;

				if (j==0 || j==(COLUMNS-1) || i==0 || i==(CORRIDORS-1)){
					nextToWindow = true;
					rentalPrice -= basePrice * 0.1;
				}

				if (i==6 && (rentalPrice==basePrice)){
					rentalPrice -= basePrice * 0.15;
				}

				if (i>=1 && i<=5 && (rentalPrice==basePrice)){
					rentalPrice += basePrice * 0.25;
				}

				miniRooms[i][j] = new MiniRoom(id, nextToWindow, rentalPrice, i+1, j+1);
			}
		}
	}

	/**
	 * Returns a list with the information of the available mini rooms of the data center. The list
	 * is the result of the toString method of each available mini room and a final line with the
	 * count of them.
	 * @return list String which represents the list
	 */
	public String getAvailableMiniRoomsList(){
		String list = "";
		int count = 0;
		for (int i=0; i<CORRIDORS; i++){
			for (int j=0; j<COLUMNS; j++){
				MiniRoom currentMiniRoom = miniRooms[i][j];
				if (currentMiniRoom.getState().equals(State.AVAILABLE)){
					list += currentMiniRoom.toString() + "\n\n";
					count++;
				}
			}
		}
		list += "Total available mini rooms: " + count;
		return list;
	}

	/**
	 * Checks if a specific mini room is avaible to be rented. It uses the corridor and the column and
	 * adjusts it  to the 0-base numbering instead of using the id. It is not necessary to go through
	 * the matrix because when choosing the mini room the user specifies its "coordinates".
	 * @param corridor int. 
	 * @param column int.
	 * @return isAvailable boolean, true only if the room is available. 
	 */
	public boolean checkAvailability(int corridor, int column){
		boolean isAvailable = false;
		if (miniRooms[corridor-1][column-1].getState().equals(State.AVAILABLE)){
			isAvailable = true;
		}
		return isAvailable;
	}

	/**
	 * Modifies the state of a MiniRoom object. It will set the state to RENTED and the
	 * power to ON and initialize the company and rentalDate object-attributes. Some
	 * parameters are used to create the objects associated to a MiniRoom
	 * such as a Date and a Rack with its Server collection. There is no verification here,
	 * it asumes the selected mini room is available because the UserInterface
	 * class calls checkAvailability method before calling this one. 
	 * @param row int.
	 * @param column int.
	 * @param companyNit String.
	 * @param companyName String.
	 * @param dateDay int.
	 * @param dateMonth int.
	 * @param dateYear int.
	 * @param sCacheMemory double.
	 * @param sProcessorNumber int.
	 * @param sProcessorBrand Brand.
	 * @param sRamMemory int.
	 * @param sDiskNumber int.
	 * @param sDiskCapacity double.
	 */
	public void rentMiniRoom(int row, int column, String companyNit, String companyName, int dateDay,
	int dateMonth, int dateYear, double[] sCacheMemory, int[] sProcessorNumber, Brand[] sProcessorBrand, int[] sRamMemory, int[] sDiskNumber, double[] sDiskCapacity){
		Company company = new Company(companyNit, companyName);
		Date rentalDate = new Date(dateDay, dateMonth, dateYear);
		int serverNumber = sCacheMemory.length;
		Server[] preServers = new Server[serverNumber];
		for (int i=0; i<serverNumber; i++){
			preServers[i] = new Server(sCacheMemory[i], sProcessorNumber[i], sRamMemory[i], sDiskNumber[i], sDiskCapacity[i]);
		}
		MiniRoom selectedMiniRoom = miniRooms[row-1][column-1];
		selectedMiniRoom.rent(company, rentalDate, preServers);
		System.out.println("Your mini room has been registered succesfully. Its ID is: " + selectedMiniRoom.getId());
	}

	/**
	 * It works exactly as rentMiniRoom, but it is used in the particular case of the
	 * university. It do not need companyNit and companyName because the associated 
	 * company is an instance of Icesi class which already has the name and nit of Icesi.
	 * However, it needs a projectId parameter (extra attribute of Icesi class).
	 * @param row int.
	 * @param column int.
	 * @param projectId String.
	 * @param dateDay int.
	 * @param dateMonth int.
	 * @param dateYear int.
	 * @param sCacheMemory double.
	 * @param sProcessorNumber int.
	 * @param sProcessorBrand Brand.
	 * @param sRamMemory int.
	 * @param sDiskNumber int.
	 * @param sDiskCapacity double.
	 */
	public void rentMiniRoomIcesi(int row, int column, String projectId, int dateDay, int dateMonth, int dateYear,
	double[] sCacheMemory, int[] sProcessorNumber, Brand[] sProcessorBrand, int[] sRamMemory, int[] sDiskNumber, double[] sDiskCapacity){
		Company company = new Icesi(projectId);
		Date rentalDate = new Date(dateDay, dateMonth, dateYear);
		int serverNumber = sCacheMemory.length;
		Server[] preServers = new Server[serverNumber];
		for (int i=0; i<serverNumber; i++){
			preServers[i] = new Server(sCacheMemory[i], sProcessorNumber[i], sRamMemory[i], sDiskNumber[i], sDiskCapacity[i]);
		}
		MiniRoom selectedMiniRoom = miniRooms[row-1][column-1];
		selectedMiniRoom.rent(company, rentalDate, preServers);
		System.out.println("Your mini room has been registered succesfully. Its ID is: " + selectedMiniRoom.getId());
	}

	/**
	 * Modifies the state of a MiniRoom object. It will set the state to AVAILABLE and the
	 * power to OFF and set the company and rentalDate object-attributes to null again. It
	 * uses the id of a mini room and searches it inside the matrix with all mini rooms. 
	 * @param miniRoomId String id of the mini room.
	 */
	public void cancelRental(String miniRoomId){
		boolean flag = false;
		for (int i=0; i<CORRIDORS && !flag; i++){
			for (int j=0; j<COLUMNS && !flag; j++){
				MiniRoom currentMiniRoom = miniRooms[i][j];
				if (currentMiniRoom.getId().equals(miniRoomId)){
					if (currentMiniRoom.getState().equals(State.AVAILABLE)){
						System.out.println("This mini room is not rented");
					} else{
						System.out.println(currentMiniRoom.getProcessingCapacity());
						currentMiniRoom.cancelRental();
						System.out.println("The mini room is available now, all related data was deleted");
					}
					flag = true;
				}
			}
		}

		if (!flag){
			System.out.println("There is no MiniRoom registered with that id");
		}
	}

	/**
	 * Modifies the state of at least one MiniRoom object. It makes the same modification as
	 * cancelRental but it will go through the entire matrix looking for mini rooms rented by
	 * the specified company instead of using a single id. It has a flag boolean to determine
	 * if the company has at least one rented mini room. If flag is true all the rented mini rooms
	 * of the company will be cancelled.
	 * @param companyName String name of the company.
	 */
	public void cancelCompanyRental(String companyName){
		boolean flag = false;
		for (int i=0; i<CORRIDORS; i++){
			for (int j=0; j<COLUMNS; j++){
				MiniRoom currentMiniRoom = miniRooms[i][j];
				if (currentMiniRoom.getCompany() != null){
					String currentCompany = currentMiniRoom.getCompany().getName();
					if (currentCompany.equals(companyName)){
						flag = true;
						System.out.println(currentMiniRoom.getProcessingCapacity() + "\n");
						currentMiniRoom.cancelRental();
						System.out.println("All the mini rooms of " + companyName + " are available now, all related data was deleted");
					}
				}
			}
		}

		if (!flag){
			System.out.println("the entered company does not have any rented mini room");
		}
	}

	/**
	 * Returns a String simulating a map of the data center and the state of each mini room.
	 * It is a "shortcut" that combines getCurrentMiniRoomStates and getStringGrid with the 
	 * actual state of the data center.
	 * @return map String with a "map" of the data center.
	 */
	public String getCenterMap(){
		char[][] states = getCurrentMiniRoomStates();
		String map = getStringGrid(states);
		return map;		
	}

	/**
	 * Goes through the entire miniRooms matrix and gets the state of each position. It uses
	 * "O" to represent "turned on" and "X" to represent "turned off". It will store the actual
	 * states, it means, the values reflected on the system.
	 * @return actualStates char[][] with the symbols representing states of the mini rooms.
	 */
	public char[][] getCurrentMiniRoomStates(){
		char[][] actualStates = new char[CORRIDORS][COLUMNS];
		char icon = 'X';
		for (int i=0; i<CORRIDORS; i++){
			for (int j=0; j<COLUMNS; j++){
				icon = 'X';
				MiniRoom currentMiniRoom = miniRooms[i][j];
				if (currentMiniRoom.getPower().equals(Power.ON)){
					icon = 'O';
				}
				actualStates[i][j] = icon;
			}
		}

		return actualStates;
	}

	/**
	 * Similar to getCenterMap, but it returns a simulated map, it is, a String only for
	 * visualization. It will not affect the state of the MiniRoom objects. It uses a stored
	 * variable instead of going through the matrix: the actual map can be obtained in real time,
	 * but simulated map has to be stored because it will be modified and the modifications 
	 * consider the previous simulated states.
	 * @return String map with the simulated states.
	 */
	public String getSimulatedMap(){
		return getStringGrid(simulatedMiniRoomsStates);
	}

	/**
	 * Returns a String with a "Map format". It uses a matrix of character and only organizes
	 * them with some spaces and line breaks.
	 * @param states char[][] elements used in the map.
	 * @return map String with the data of states but organized in a more human
	 * readable way.
	 */
	public String getStringGrid(char[][] states){
		String map = "";
		for (int i=0; i<CORRIDORS; i++){
			for (int j=0; j<COLUMNS; j++){
				map += states[i][j] + " ";
			}
			map += "\n";
		}
		return map;
	}

	/**
	 * Obtains the actual current states of the mini rooms and copies to the
	 * simulatedMiniRoomsStates matrix. It is called when a simulation is started.
	 * Its purpose is updating the simulated map because each simulations starts
	 * as a mirror of the actual map.
	 */
	public void startSimulation(){
		simulatedMiniRoomsStates = getCurrentMiniRoomStates();
	}

// These methods only changes the values of the simulated map

	/**
	 * Turns on all the mini rooms. It only modifies the simulated map state.
	 * It is one of the simulation protocols.
	 */
	public void simTurnOnAllMiniRooms(){
		for (int i=0; i<CORRIDORS; i++){
			for (int j=0; j<COLUMNS; j++){
				simulatedMiniRoomsStates[i][j] = 'O';
			}
		}
	}

	/**
	 * Turns off all the first corridor and all the first column.
	 * It only modifies the simulated map state. It is one of the
	 * simulation protocols.
	 */
	public void simTurnOffL(){
		for (int i=0; i<CORRIDORS; i++){
			simulatedMiniRoomsStates[i][0] = 'X';
		}

		for (int i=1; i<COLUMNS; i++){
			simulatedMiniRoomsStates[0][i] = 'X';
		}
	}

	/**
	 * Turns off all the first and last corridor and all the mini rooms
	 * of the inverse diagonal (Like an inverted Z shape). It only modifies
	 * the simulated map state. It is one of the
	 * simulation protocols.
	 */
	public void simTurnOffZ(){
		for (int i=0; i<COLUMNS; i++){
			simulatedMiniRoomsStates[0][i] = 'X';
			simulatedMiniRoomsStates[CORRIDORS-1][i] = 'X';
		}

		for (int i=CORRIDORS-1, j=COLUMNS-1; i>-1 && j>-1; i--, j--){
			simulatedMiniRoomsStates[i][j] = 'X';
		}
	}

	/**
	 * Turns off all the odd corridors. It only modifies
	 * the simulated map state. It is one of the
	 * simulation protocols.
	*/
	public void simTurnOffH(){
		for (int i=0; i<CORRIDORS; i++){
			if ((i+1)%2 != 0){
				for (int j=0; j<COLUMNS; j++){
					simulatedMiniRoomsStates[i][j] = 'X';
				}
			}
		}
	}

	/**
	 * Turns off all the mini rooms adjacent to the windows (borders
	 * of the data center). It only modifies the simulated map state.
	 * It is one of the simulation protocols.
	*/
	public void simTurnOffO(){
		for (int i=0; i<CORRIDORS; i++){
			for (int j=0; j<COLUMNS; j++){
				if (j==0 || j==(COLUMNS-1) || i==0 || i==(CORRIDORS-1)){
					simulatedMiniRoomsStates[i][j] = 'X';
				}
			}
		}
	}

	/**
	 * Turns off all the mini rooms of a specified column.
	 * It only modifies the simulated map state.
	 * It is one of the simulation protocols.
	 * @param column number of the column.
	 */
	public void simTurnOffM(int column){
		for (int i=0; i<CORRIDORS; i++){
			simulatedMiniRoomsStates[i][column-1] = 'X';
		}
	}

	/**
	 * Turns off all the mini rooms of a specified corridor.
	 * It only modifies the simulated map state.
	 * It is one of the simulation protocols.
	 * @param corridor number of the corridor.
	 */
	public void simTurnOffP(int corridor){
		for (int j=0; j<COLUMNS; j++){
			simulatedMiniRoomsStates[corridor-1][j] = 'X';
		}
	}

	/**
	 * Modifies the rentalPrice of a MiniRoom object. It applies a discount or recharge to
	 * a specific position. The specific operation depends on the sign of the percentage
	 * parameter. 
	 * @param corridor int.
	 * @param column int.
	 * @param percentage double Discount or recharge percentage calculated over the base price.
	 */
	public void priceModifier(int corridor, int column, double percentage){
		double currentPrice = miniRooms[corridor-1][column-1].getRentalPrice();
		double newPrice = currentPrice + ((currentPrice*percentage)/100);
		miniRooms[corridor-1][column-1].setRentalPrice(newPrice);
	}

	/**
	 * Getter for MiniRooms. It returns a MiniRoom based on its
	 * locations (corridor, column).
	 * @param corridor int
	 * @param column int
	 * @return MiniRoom
	 */
	public MiniRoom getMiniRoom(int corridor, int column){
		return miniRooms[corridor-1][column-1];
	}

	/**
	 * ToString method. It includes corridors, columns and basePrice
	 * attributes.
	 * @return s String.
	 */
	public String toString(){
		String s = "";
		s += "Corridors: " + CORRIDORS;
		s += "\nColumns: " + COLUMNS;
		s += "\nBase price of miniRooms: " + BASE_PRICE;
		return s; 
	}

// Getters. No setters (finals).

	public double getBASE_PRICE() {
		return this.BASE_PRICE;
	}

	public int getCORRIDORS() {
		return this.CORRIDORS;
	}

	public int getCOLUMNS() {
		return this.COLUMNS;
	}
}