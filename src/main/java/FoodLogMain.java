import java.sql.ResultSet;
import java.util.*;

/**
 * Provides a console interface by which the user can enter information
 * about food that they ate on specific days for specific meals. The
 * user can add, delete and modify entries, display food log data, and
 * enter food-specific details such as calories per serving.
 *
 * @author iDoc1
 *
 */
public class FoodLogMain {

    // Types of meals recognized by this program stored as a Set
    public static final Set<String> mealTypes
            = new HashSet<>(Arrays.asList("breakfast", "lunch", "dinner", "brunch", "snack"));


    public static void main(String[] args) {
        introduction();
        choseOption();
    }

    /**
     * Introduces the user to the Food Log program
     */
    public static void introduction() {
        System.out.println("Welcome to the Food Log App.");
        System.out.println("This program allows you to record and view data regarding");
        System.out.println("the meals you have eaten during the day. Please review the");
        System.out.println("following options to get started.");
        System.out.println();
    }

    /**
     * Presents user with options to modify the food log, add food details, or view data
     */
    public static void choseOption() {
        System.out.println("Choose an option:");
        System.out.println("1: Add, delete, or modify food log entries");
        System.out.println("2: Add food details");
        System.out.println("3: View food log data");

        // Validate user input
        System.out.print("Enter option: ");
        Scanner input = new Scanner(System.in);
        String userOption = input.next();

        while (!userOption.equals("1") && !userOption.equals("2") && !userOption.equals("3")) {
            System.out.print("Invalid choice. Please enter a valid option: ");
            userOption = input.next();
        }

        // Establish connection to the food log database to be passed as an argument
        System.out.println("\nConnecting to database...");
        FoodLogConnection foodLogConn = new FoodLogConnection();
        FoodLogComm foodLogComm = new FoodLogComm(foodLogConn);

        // Proceed forward based on user's chosen option
        System.out.println();
        if (userOption.equals("1")) {
            modifyFoodLog(foodLogComm);
        } else if (userOption.equals("2")) {
            addFoodDetails();
        } else {
            viewData(foodLogComm);
        }
    }

    /**
     * Creates a connection to the food log database and allows user to choose
     * whether to add, delete, or edit a food log entry
     * @param foodLogComm   Object used to modify and query the food log database
     */
    public static void modifyFoodLog(FoodLogComm foodLogComm) {

        // Present options to user for how to proceed
        System.out.println("Please choose from the following options to modify the food log:");
        System.out.println("1: Add one or more entries");
        System.out.println("2: Delete one or more entries");
        System.out.println("3: Edit a specific entry");

        // Validate user input
        System.out.print("Enter option: ");
        Scanner input = new Scanner(System.in);
        String userOption = input.next();

        while (!userOption.equals("1") && !userOption.equals("2") && !userOption.equals("3")) {
            System.out.print("Invalid choice. Please enter a valid option: ");
            userOption = input.next();
        }

        // Proceed forward based on user input and pass the database connection
        if (userOption.equals("1")) {
            addEntry(foodLogComm);
        } else if (userOption.equals("2")) {
            deleteEntry(foodLogComm);
        } else {
            editEntry(foodLogComm);
        }
    }

    /**
     * Calls the addEntryHelper method to add an entry then checks if user would
     * like to add another entry to the food log
     * @param foodLogComm   Object used to modify and query the food log database
     */
    public static void addEntry(FoodLogComm foodLogComm) {

        // Add an entry to the table
        addEntryHelper(foodLogComm);

        // Check if user wants to add another entry
        boolean addAnother = true;
        while (addAnother) {

            // Ask if user wants to add another entry
            System.out.println();
            System.out.println("Would you like to add another entry?");
            System.out.print("Type 'y' if yes, any other key if no: ");

            Scanner input = new Scanner(System.in);
            String answer = input.nextLine();

            // Add another entry if user answers 'y'
            if (answer.equalsIgnoreCase("y")) {
                addEntryHelper(foodLogComm);
            } else {
                addAnother = false;
            }
        }
    }

    /**
     * Accepts a FoodLogConnection object and uses this connection to create
     * Food and FoodLogEntry objects to add an entry to the food log
     * @param foodLogComm  Object used to modify and query the food log database
     */
    private static void addEntryHelper(FoodLogComm foodLogComm) {

        // Get entry details from user
        Scanner input = new Scanner(System.in);

        System.out.println();
        System.out.println("Please enter the following details to add an entry.");
        System.out.println("Leave date blank if entry is for today's date.");
        System.out.print("Entry date (yyyy-MM-dd): ");
        String entryDate = input.nextLine();

        System.out.print("Food name: ");
        String foodName = input.nextLine().toLowerCase();

        // Validate that meal type is breakfast, lunch, dinner, brunch, or snack
        System.out.print("Meal type: ");
        String mealType = input.nextLine().toLowerCase();

        while (!mealTypes.contains(mealType)) {
            System.out.println("Meal type must be breakfast, lunch, dinner, brunch, or snack.");
            System.out.print("Re-renter meal type: ");
            mealType = input.nextLine().toLowerCase();
        }

        // Get serving quantity and check that value entered is valid
        System.out.print("Serving quantity: ");
        double servingQuantity;  // Initialize serving quantity

        // Prompt user for valid number until no exception thrown
        while (true) {
            try {
                String servingInput = input.nextLine();
                servingQuantity = Double.parseDouble(servingInput);  // String to double conversion
                break;  // break if no exception thrown
            } catch (NumberFormatException e) {
                System.out.println("Serving quantity must be a number.");
                System.out.print("Re-enter serving quantity: ");
            }
        }

        System.out.print("Entry notes: ");
        String notes = input.nextLine();

        // Create FoodDetails and FoodLogComm objects
        FoodDetails foodEaten = new FoodDetails(foodName, mealType, servingQuantity);

        // Create entry object depending on if user entered an entry date or not
        boolean success;
        if (entryDate.equals("")) {
            success = foodLogComm.insertRowCurrDate(foodEaten, notes);
        } else {
            success = foodLogComm.insertRowGivenDate(foodEaten, entryDate, notes);
        }

        // Add entry to database and check if insertion was successful
        if (success) {
            System.out.println("\nEntry successfully added.");
        } else {
            System.out.println("\nError: Entry not added.");
        }
    }

    public static void deleteEntry(FoodLogComm foodLogComm) {

    }

    public static void editEntry(FoodLogComm foodLogComm) {

    }

    public static void addFoodDetails() {

    }

    /**
     * Asks user for details regarding what data they want to view, then fetches
     * the data from the food log database using the FetchData class. A
     * DataReport object is then created and used to print the results.
     * @param foodLogComm   Object used to modify and query the food log database
     */
    public static void viewData(FoodLogComm foodLogComm) {
        //DataReport report = new DataReport(foodLogComm.fetchDataFromDateRange("2021-06-09", "2021-06-16"));
        //report.printResults();

        // Present options to user regarding what data to view
        System.out.println("Please choose from the following options to view data");
        System.out.println("1: View entries on a given date");
        System.out.println("2: View entries within a given date range");
        System.out.println("3: View entries containing a specific food");
        System.out.println("4: Food log report for the last week");
        System.out.println("5: Food log report for the last day");

        // Receive user input
        Scanner input = new Scanner(System.in);
        System.out.print("Enter option: ");

        // Ensure input is an integer
        int userOption = 0;
        try {
            userOption = Integer.parseInt(input.next());
        } catch (NumberFormatException e) {
            System.out.println("Input must be numerical.");
        }

        // Validate user input
        while (userOption < 1 || userOption > 5) {
            System.out.print("Option not valid. Please enter a valid option: ");

            // Ensure input is an integer
            try {
                userOption = Integer.parseInt(input.next());
            } catch (NumberFormatException e) {
                System.out.println("Input must be numerical.");
            }
        }
        System.out.println();

        // Print report given an single date
        if (userOption == 1) {

            // Get date from user
            System.out.print("Enter a date (yyyy-MM-dd): ");
            String userDate = input.next();

            // Ensure given date is valid
            ResultSet resultSet = foodLogComm.fetchDataFromDate(userDate);
            while (resultSet == null) {
                System.out.print("Date is invalid. Please enter a valid date in yyyy-MM-dd format: ");
                userDate = input.next();

                resultSet = foodLogComm.fetchDataFromDate(userDate);
            }

            // Create DataReport and print results
            DataReport report = new DataReport(resultSet);
            System.out.println();
            report.printResults();

        // Print report given a date range
        } else if (userOption == 2) {

            // Get date range from user
            System.out.print("Enter start date (yyyy-MM-dd): ");
            String startDate = input.next();
            System.out.print("Enter end date (yyyy-MM-dd): ");
            String endDate = input.next();

            // Ensure given dates are valid
            ResultSet resultSet = foodLogComm.fetchDataFromDateRange(startDate, endDate);
            while (resultSet == null) {
                System.out.println("One or both dates are invalid. Please enter valid dates.");
                System.out.print("Enter start date (yyyy-MM-dd): ");
                startDate = input.next();
                System.out.print("Enter end date (yyyy-MM-dd): ");
                endDate = input.next();

                resultSet = foodLogComm.fetchDataFromDateRange(startDate, endDate);
            }

            // Create DataReport and print results
            DataReport report = new DataReport(resultSet);
            System.out.println();
            report.printResults();
        } else if (userOption == 3) {

            // Get food name from user
            System.out.print("Enter food name: ");
            String foodName = input.next();

            // Create a DataReport and print results
            DataReport report = new DataReport(foodLogComm.fetchDataFromFood(foodName));
            System.out.println();
            report.printResults();
        }

        // Ask if user would like to export the viewed data

    }
}
