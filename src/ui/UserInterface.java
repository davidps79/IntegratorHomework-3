package ui;

import java.util.Scanner;

import model.DataCenterSystem;
import model.Brand;

/**
 * UserInterface is the class that communicates with users. It works together with the class DataCenterSystem.
 */
public class UserInterface{
	/**
     * It is default message of the main menu.
     */
    private final String MENU_MESSAGE = "Select an option:";

    /**
     *It contains the options to be displayed on the menu.
     */
    private final String[] MENU_OPTIONS = {"Show list of available mini rooms", "Rent a mini room",
	"Cancel mini room rental", "Cancel all the mini rooms rentals of a company", "Show data center map",
	"Start a simulation"};

    /**
     * It contains the options to be displayed on the simulation menu.
     */
    private final String[] SIMULATION_OPTIONS = {"1- Turn on all the mini rooms", "L shutdown protocol", "Z shutdown protocol",
    "H shutdown protocol", "O shutdown protocol", "M shutdown protocol", "P shutdown protocol", "0- Return to main menu"};

    /**
     * It is the default message of the simulation menu.
     */
    private final String SIMULATION_MESSAGE = "Select a protocol:";

    /**
     * It represents a specific menu and is used to loop it until another one is selected.
     */
    private int currentMenu = 0;

    /**
     * Scanner of the class. It is used to read user input.
     */
	private Scanner sc;

    /**
     * If true, the program will stop its execution.
     */
	private boolean exit;

    /**
     * Connection with the DataCenterSystem class. The user has not direct access to it. 
     */
	private DataCenterSystem icesiDataCenter;

    /**
     * Constructor of the class. It initializes the necessary Scanner object and sets exit to false.
     */
	public UserInterface(){
		sc = new Scanner(System.in);
		exit = false;
	}

    /** 
     * Main method of the project
     * @param args String[]
     */
    public static void main(String[] args){
        UserInterface admin = new UserInterface();
        admin.initializeSystem();
    }


    /**
     * Initializes the DataCenterSystem object necessary to meet the requirements. The values of its attributes are entered by the user.
     */
    public void initializeSystem(){
		System.out.println("initializing the Data Center System");
		System.out.println("Enter the base price of the miniRooms:");
		double basePrice = sc.nextDouble();

        icesiDataCenter = new DataCenterSystem(basePrice, 8, 50);

        System.out.println("the system has been initialized:\n" + icesiDataCenter.toString());

        enableFunctions();
    }

    /**
     * Verifies that the DataCenterSystem object exists and launches a menu. The first time it will launch
     * main menu and if currentMenu changes it will launch the launch menu.
     */
	public void enableFunctions(){
        if (icesiDataCenter != null){
            do{
                switch(currentMenu){
                    case 0:
                        mainMenu();
                        break;
                    case 1:
                        simulationMenu();
                        break;

                }
            } while (!exit);    
        } else{
            System.out.println("The Data Center System has not been created yet"); // This should NEVER happen.
        }

        System.out.println("Program closed");
	}

    /**
     * Main menu of the program. It allows acces to main functionalities and the simulation menu.
     */
    public void mainMenu(){
        int selectedOption = showOptionsMenu("Main Menu", MENU_MESSAGE, MENU_OPTIONS);
        switch(selectedOption){
            case 0:
                exit = true;
                break;
            case 1:
                showAvailableMiniRoomsList();
                break;
            case 2:
                rentMiniRoom();
                break;
            case 3:
                cancelRental();
                break;
            case 4:
                cancelCompanyRental();
                break;
            case 5:
                System.out.println("X- Turned Off\tO- Turned On");
                showCenterMap();
                break;
            case 6:
                icesiDataCenter.startSimulation();
                System.out.println("Simulation started, all changes made here will not affect the actual state of the mini rooms");
                System.out.println("Current state of the mini rooms:");
                System.out.println("");
                System.out.println(icesiDataCenter.getSimulatedMap());
                System.out.println("Remember X means Turned OFF and O turned ON");
                currentMenu = 1;
                break;
        }
    }

    /**
     * Submenu that groups the functionalities related to simulations protocols. It asks the user to choose an option. When
     * called it will first make a copy of the current state of each mini rooms because it starts a simulation, therefore it does not
     * affect the actual state of them.
     */
    public void simulationMenu(){
        showProtocolMenu(SIMULATION_MESSAGE, SIMULATION_OPTIONS);
        String selectedOption = sc.nextLine();
        selectedOption = selectedOption.toUpperCase();
        switch(selectedOption){
            case "1":
                icesiDataCenter.simTurnOnAllMiniRooms();
                break;
            case "L":
                icesiDataCenter.simTurnOffL();
                break;
            case "Z":
                icesiDataCenter.simTurnOffZ();
                break;
            case "H":
                icesiDataCenter.simTurnOffH();
                break;
            case "O":
                icesiDataCenter.simTurnOffO();
                break;
            case "M":
                System.out.println("Enter the column:");
                int column = sc.nextInt();
                icesiDataCenter.simTurnOffM(column);
                break;
            case "P":
            System.out.println("Enter the corridor:");
                int corridor = sc.nextInt();
                icesiDataCenter.simTurnOffP(corridor);
                break;
            case "0":
                System.out.println("\nEnd of simulation");
                currentMenu = 0;
                break;
            default:
                System.out.println("Protocol not recognized");
                break;
        }
        System.out.println("Result:");
        System.out.println(icesiDataCenter.getSimulatedMap());
    }

	/**
     * Prints a custom menu. It asks the user to select one of its options until it receive a valid option.
     * @param title String to be displayed as the title of the menu.
     * @param message String to be displayed as a message of the menu.
     * @param options String[] to be displayed as options of the menu. It includes the number of each option automatically.
     * @return option int corresponding to the option selected by the user.
     */
    public int showOptionsMenu(String title, String message, String[] options){
        int option = -1;
        System.out.println("------" + title + "------");
        System.out.println(message);
        while (option>options.length || option <0){
            for (int i=0; i<options.length; i++){
                System.out.println("(" + (i+1) + ") " + options[i]);
            }
            System.out.println("(0) Exit");
            option = sc.nextInt();
            if (option>options.length || option <0){
                System.out.println("Invalid option, please type again:");
            }
        }
        sc.nextLine();
        return option;
    }
    
    /**
     * Works exactly as showOptionsMenu, the only difference is the look and that it does not have return / exit option.
     * @param message String to be displayed as a message of the menu.
     * @param options String[] to be displayed as options of the menu. It includes the number of each option automatically.
     * @return option int corresponding to the option selected by the user.
     */
    public int showOptions(String message, String[] options){
        int option = -1;
        System.out.println(message);
        while (option>options.length || option <0){
            for (int i=0; i<options.length; i++){
                System.out.println((i+1) + "- " + options[i]);
            }
            option = sc.nextInt();
            if (option>options.length || option <0){
                System.out.println("Invalid option, please type again:");
            }
        }
        sc.nextLine();
        return option;
    }

    /**
     * It only prints an String array as an option list. It is used specifically for the protocol menu.
     * @param message String to be displayed as a message of the menu.
     * @param options String[] to be displayed as options of the menu.
     * @return option int corresponding to the option selected by the user.
     */
    public void showProtocolMenu(String message, String[] options){
        System.out.println(message);
        for (int i=0; i<options.length; i++){
            System.out.println(options[i]);
        }
    }

    /**
     * Shows the list of the available mini rooms.
     */
	public void showAvailableMiniRoomsList(){
		System.out.println(icesiDataCenter.getAvailableMiniRoomsList());		
	}

    /**
     * Perfoms the necessary operations to rent a mini room. It will ask the user to enter the desired location
     * of the mini room. Then, if the selected one is available it will need more information and it will vary depending
     * on the type of tenant (external company or university itself). The arrays are used to send all the attributes
     * values to the controller object, the controller will use them to create the Server objects.
     */
	public void rentMiniRoom(){
        System.out.println(icesiDataCenter.getCenterMap());
        System.out.println("Available mini rooms are marked with an X (Turned Off)");
        System.out.println("");
        System.out.println("Enter the desired location for the mini room: ");
		System.out.print("Corridor: ");
        int corridor = -1;
        int column = -1;
        do{
            corridor = sc.nextInt();
            if (corridor < 1 || corridor > icesiDataCenter.getCORRIDORS()){
                System.out.print("That corridor does not exists\nType again: ");
            }
        } while (corridor < 1 || corridor > icesiDataCenter.getCORRIDORS());
        
        System.out.print("Column: ");

        do{
            column = sc.nextInt();
            if (column < 1 || column > icesiDataCenter.getCOLUMNS()){
                System.out.print("That column does not exists\nType again:");
            }
        } while (column < 1 || column > icesiDataCenter.getCOLUMNS());

        if (icesiDataCenter.checkAvailability(corridor, column)){
            double thePrice = icesiDataCenter.getMiniRoom(corridor, column).getRentalPrice();
            System.out.println("The mini room is available for $" + thePrice + " please enter the resources you need: ");
            System.out.print("Number of servers: ");
            int serverNumber = sc.nextInt();

            if (serverNumber < 4){
                System.out.println("An additional 15% will be charged for under-utilization"); 
                System.out.print(thePrice + " => ");
                icesiDataCenter.priceModifier(corridor, column, 15); 
                thePrice = icesiDataCenter.getMiniRoom(corridor, column).getRentalPrice();
                System.out.println(thePrice);
            }

            double[] sCacheMemory = new double[serverNumber];
            int[] sProcessorNumber = new int[serverNumber];
            Brand[] sProcessorBrand = new Brand[serverNumber];
            int[] sRamMemory = new int[serverNumber];
            int[] sDiskNumber = new int[serverNumber];
            double[] sDiskCapacity = new double[serverNumber];

            for (int i=0; i<serverNumber; i++){
                System.out.println("Enter the hardware requirements for the #" + (i+1) + " server:");
                System.out.print("Cache memory: ");
                sCacheMemory[i] = sc.nextDouble();
                sc.nextLine();
                System.out.print("Processor number: ");
                sProcessorNumber[i] = sc.nextInt();
                sc.nextLine();
                String[] brandOptions = {"AMD", "Intel"};
                Brand brand = Brand.AMD;
                switch (showOptions("Processor brand:", brandOptions)) {
                    case 1:
                        brand = Brand.AMD;
                        break;
                    case 2:
                        brand = Brand.INTEL;
                        break;
                }
                sProcessorBrand[i] = brand;
                System.out.print("RAM memory: ");
                sRamMemory[i] = sc.nextInt();
                sc.nextLine();
                System.out.print("Disk number: ");
                sDiskNumber[i] = sc.nextInt();
                sc.nextLine();
                System.out.print("Capacity of each disk: (in TB): ");
                double dCapacity = sc.nextDouble();
                sc.nextLine();
                sDiskCapacity[i] = dCapacity * sDiskNumber[i];
                System.out.println("");
            }

            System.out.println("Enter the information of the rental:");
            System.out.println("Date...");
            System.out.print("Day: ");
            int dateDay = sc.nextInt();
            System.out.print("Month: ");
            int dateMonth = sc.nextInt();
            System.out.print("Year: ");
            int dateYear = sc.nextInt();

            String[] icesiOptions = {"Yes", "No"};
            if (showOptions("Is this mini room for one of the university's research projects?", icesiOptions) == 1){
                System.out.println("Enter the id of the research project:");
                String projectId = sc.nextLine();
                icesiDataCenter.rentMiniRoomIcesi(corridor, column, projectId, dateDay, dateMonth, dateYear, sCacheMemory,
                sProcessorNumber, sProcessorBrand, sRamMemory, sDiskNumber, sDiskCapacity);
            } else{
                System.out.println("Enter the information of the tenant company:");
                System.out.print("Name: ");
                String companyName = sc.nextLine();
                System.out.print("NIT: ");
                String companyNit = sc.nextLine();
                icesiDataCenter.rentMiniRoom(corridor, column, companyNit, companyName, dateDay, dateMonth,
                dateYear, sCacheMemory, sProcessorNumber, sProcessorBrand, sRamMemory, sDiskNumber, sDiskCapacity);
            }
        } else{
            System.out.println("This mini room is already taken");
        }
	}

    /**
     * Perfoms the necessary operations to cancel the rental of a specific mini room. It will ask the user to enter an id. The
     * id of a mini room is given after succesfully renting it in the system (rentMiniRoom method). It will first verify the
     * selected mini room is already rented.
     */
	public void cancelRental(){
        System.out.println("Enter the id of the mini room:");
        String id = sc.nextLine();
        icesiDataCenter.cancelRental(id);
	}

    /**
     * Perfoms the necessary operations to cancel all the rentals of a specific company. It will ask the user to enter the name
     * of the company. It will first verify the entered one exists in the system.
     */
	public void cancelCompanyRental(){
        System.out.println("Enter the name of company:");
        String companyName = sc.nextLine();
        icesiDataCenter.cancelCompanyRental(companyName);
	}

    /**
     * Shows a map of the data center with the current state of the mini rooms.
     */
	public void showCenterMap(){
        System.out.println(icesiDataCenter.getCenterMap());
	}
}