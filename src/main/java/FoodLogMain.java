import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Provides a command line interface by which the user can enter information
 * about food that they ate on specific days for specific meals. The user can
 * add, delete and edit entries, display food log data, and enter food-specific
 * details such as calories per serving and food category.
 *
 * @author iDoc1
 *
 */
public class FoodLogMain {

    // Types of meals recognized by this app
    public static final Set<String> mealTypes
            = new HashSet<>(Arrays.asList("breakfast", "lunch", "dinner", "brunch", "snack"));

    // Types of food categories recognized by this app
    public static final Set<String> foodCategories
            = new HashSet<>(Arrays.asList("fruit", "vegetable", "grain", "dairy", "protein", "other"));


    public static void main(String[] args) {

        // Introduce app
        introduction();

        // Establish connection to the food log database to be passed as an argument
        System.out.println("Connecting to database...");
        FoodLogConnection foodLogConn = new FoodLogConnection();
        FoodLogComm foodLogComm = new FoodLogComm(foodLogConn);

        // Begin app session
        boolean continueSession = true;
        while (continueSession) {
            choseOption(foodLogComm);

            // Ask if user would like to continue using app
            continueSession = returnToMenu(foodLogComm);
        }

        System.out.println("\nThanks for using! Good bye.");
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
     * @param foodLogComm   Object used to modify and query the food log database
     */
    public static void choseOption(FoodLogComm foodLogComm) {
        System.out.println();
        System.out.println("Choose an option:");
        System.out.println("1: Add, delete, or modify food log entries");
        System.out.println("2: Add or delete food details");
        System.out.println("3: View food log data");

        // Validate user input
        System.out.print("Enter option: ");
        Scanner input = new Scanner(System.in);
        String userOption = input.next();

        while (!userOption.equals("1") && !userOption.equals("2") && !userOption.equals("3")) {
            System.out.print("Invalid choice. Please enter a valid option: ");
            userOption = input.next();
        }

        // Proceed forward based on user's chosen option
        System.out.println();
        if (userOption.equals("1")) {
            modifyFoodLog(foodLogComm);
        } else if (userOption.equals("2")) {
            modifyFoodDetails(foodLogComm);
        } else {
            viewData(foodLogComm);
        }
    }

    /**
     * Ask user if they would like to return to the main menu or exit the app
     * @param foodLogComm   Object used to modify and query the food log database
     */
    public static boolean returnToMenu(FoodLogComm foodLogComm) {
        System.out.println();
        System.out.println("Would you like to return to the main menu?");
        System.out.print("Type 'y' if yes, or any other key to exit the app: ");

        // Get user answer and route accordingly
        Scanner input = new Scanner(System.in);
        String answer = input.nextLine();

        if (answer.equalsIgnoreCase("y")) {
            return true;
        }

        return false;
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
        System.out.println("2: Delete old entries");
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
            System.out.print("Type 'y' if yes, or any other key if no: ");

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

                // Ensure quantity given is greater than zero
                while (servingQuantity <= 0) {
                    System.out.println("Serving quantity must be greater than zero.");
                    System.out.print("Re-enter serving quantity: ");
                    servingInput = input.nextLine();
                    servingQuantity = Double.parseDouble(servingInput);
                }

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

        // Insert record depending on if user entered a date or not
        boolean success;
        if (entryDate.equals("")) {
            success = foodLogComm.insertRowCurrDate(foodEaten, notes);
        } else {
            success = foodLogComm.insertRowGivenDate(foodEaten, entryDate, notes);
        }

        // Check if insertion was successful
        if (success) {
            System.out.println("\nEntry successfully added.");
        } else {
            System.out.println("\nError: Entry not added.");
        }
    }

    /**
     * Asks user how far back they would like to delete entries by, then deletes all
     * entries in the food log database older than the chosen option
     * @param foodLogComm   Object used to modify and query the food log database
     */
    public static void deleteEntry(FoodLogComm foodLogComm) {
        System.out.println();
        System.out.println("Choose an option to delete entries older than:");
        System.out.println("1: One week");
        System.out.println("2: One month");
        System.out.println("3: 6 months");
        System.out.println("4: One year");

        // Get option from user
        Scanner input = new Scanner(System.in);
        System.out.print("Enter option: ");
        String userOption = input.next();

        // Ensure option given is valid
        while (!userOption.equals("1") && !userOption.equals("2")
                && !userOption.equals("3") && !userOption.equals("4")) {
            System.out.print("Invalid choice. Please enter a valid option: ");
            userOption = input.next();
        }

        // Set the number of days older than to delete
        int deleteDays;
        String deleteInterval;
        if (userOption.equals("1")) {
            deleteDays = 7;
            deleteInterval = "1 week";
        } else if (userOption.equals("2")) {
            deleteDays = 30;  // Roughly 1 month
            deleteInterval = "1 month";
        } else if (userOption.equals("3")) {
            deleteDays = 182;  // Roughly 6 months
            deleteInterval = "6 months";
        } else {
            deleteDays = 365;
            deleteInterval = "1 year";
        }

        // Delete entries older than deleteDays
        boolean success = foodLogComm.deleteOldEntries(deleteDays);

        // Check if deletion was successful
        if (success) {
            System.out.println("\nAll entries older than " + deleteInterval + " have been deleted.");
        } else {
            System.out.println("\nError: records not deleted.");
        }
    }

    /**
     * Allows user to give a range of dates between which an entry they want to edit
     * resides. Then, the user can specify the ID of the entry to edit, and the new
     * values for each field they want to edit.
     * @param foodLogComm   Object used to modify and query the food log database
     */
    public static void editEntry(FoodLogComm foodLogComm) {
        System.out.println();
        System.out.println("Please provide a date range in which the entry you would like to edit resides.");

        // Get date range from user
        Scanner input = new Scanner(System.in);
        System.out.println();
        System.out.print("Start date (yyyy-MM-dd): ");
        String startDate = input.nextLine();
        System.out.print("End date (yyyy-MM-dd): ");
        String endDate = input.nextLine();

        // Ensure given dates are valid
        ResultSet resultSet = foodLogComm.fetchDataFromDateRange(startDate, endDate);
        while (resultSet == null) {
            System.out.println("One or both dates are invalid. Please enter valid dates.");
            System.out.print("Enter start date (yyyy-MM-dd): ");
            startDate = input.nextLine();
            System.out.print("Enter end date (yyyy-MM-dd): ");
            endDate = input.nextLine();

            resultSet = foodLogComm.fetchDataFromDateRange(startDate, endDate);
        }

        // Print all rows within given date range and store results in Map
        System.out.println();
        DataReport report = new DataReport(resultSet);
        report.printResults();

        HashMap<Integer, FoodEntry> resultMap = report.getResultsMap();  // Store results in a Map

        // Ask user to input entry ID of record to edit
        System.out.println();
        System.out.print("Enter the Entry ID of the record you would like to edit: ");

        // Validate that given entry ID is an integer
        int entryID;
        while (true) {
            try {
                String stringEntryID = input.nextLine();
                entryID = Integer.parseInt(stringEntryID);  // String to int conversion

                // Ensure that given entry ID is in the results that were queried
                while (!resultMap.containsKey(entryID)) {
                    System.out.println("Entry ID not in above results.");
                    System.out.print("Enter a valid Entry ID: ");
                    stringEntryID = input.nextLine();
                    entryID = Integer.parseInt(stringEntryID);
                }

                break;  // break if no exception thrown
            } catch (NumberFormatException e) {
                System.out.println("Entry ID must be an integer.");
                System.out.print("Re-enter Entry ID: ");
            }
        }

        // Display row of entry ID that user wants to modify
        DataReport entryRow = new DataReport(foodLogComm.fetchDataFromID(entryID));

        // Get new input for database fields that user would like to edit
        System.out.println();
        System.out.println("Enter new info for Entry ID# " + entryID + ".");
        System.out.println("Leave the line blank if you do not want to change the field.");

        // Get new date, food name, and meal type from user
        System.out.print("Entry Date: ");
        String entryDate = input.nextLine();
        System.out.print("Food Name: ");
        String foodName = input.nextLine();
        System.out.print("Meal Type: ");
        String mealType = input.nextLine().toLowerCase();

        // Ensure meal type is valid
        while (!mealTypes.contains(mealType) && !mealType.equals("")) {
            System.out.println("Meal type must be breakfast, lunch, dinner, brunch, or snack.");
            System.out.print("Re-enter meal type: ");
            mealType = input.nextLine().toLowerCase();
        }

        // Get new serving quantity from user
        System.out.print("Serving Quantity: ");
        double servingQuantity = 0.0;  // Initialize serving quantity

        // Ensure new serving quantity is valid
        while (true) {
            try {
                String servingInput = input.nextLine();

                // Convert to double only if user did not input a blank line
                if (!servingInput.equals("")) {
                    servingQuantity = Double.parseDouble(servingInput);  // String to double conversion
                }

                break;  // break if no exception thrown
            } catch (NumberFormatException e) {
                System.out.println("Serving quantity must be a number.");
                System.out.print("Re-enter serving quantity: ");
            }
        }

        // Get new entry notes from user
        System.out.print("Entry Notes: ");
        String entryNotes = input.nextLine();

        // Check which fields user left blank, then populate those with existing data in food log
        if (entryDate.equals("")) {
            entryDate = resultMap.get(entryID).getEntryDate();
        }

        if (foodName.equals("")) {
            foodName = resultMap.get(entryID).getFoodName();
        }

        if (mealType.equals("")) {
            mealType = resultMap.get(entryID).getMealType();
        }

        if (servingQuantity == 0) {
            servingQuantity = resultMap.get(entryID).getServingQuantity();
        }

        if (entryNotes.equals("")) {
            entryNotes = resultMap.get(entryID).getEntryNotes();
        }

        // Update food log database with new values
        boolean success = foodLogComm.editEntry(entryID, entryDate, foodName, mealType, servingQuantity, entryNotes);
        if (success) {
            System.out.println("\nEntry ID# " + entryID + " successfully updated.");
        } else {
            System.out.println("\nError: entry not updated.");
        }
    }

    /**
     * Asks user whether they want to add or delete calorie information for
     * a specific food
     * @param foodLogComm   Object used to modify and query the food log database
     */
    public static void modifyFoodDetails(FoodLogComm foodLogComm) {
        System.out.println("Choose an option to either add or delete food calorie details.");
        System.out.println("1: Add food calorie information");
        System.out.println("2: Delete food calorie information");
        System.out.print("Enter option: ");

        Scanner input = new Scanner(System.in);
        String userOption = input.nextLine();

        // Verify option chosen is valid
        while (!userOption.equals("1") && !userOption.equals("2")) {
           System.out.print("Invalid choice. Please enter option 1 or 2: ");
           userOption = input.nextLine();
        }

        // Move forward based on given option
        if (userOption.equals("1")) {

            boolean addAnother = true;

            // Allow user to add multiple entries until they choose to stop
            while (addAnother) {
                addFoodDetails(foodLogComm);

                System.out.println();
                System.out.println("Would you like to add another food details entry?");
                System.out.print("Type 'y' if yes, or any other key if no: ");

                // Update loop condition if user does not choose 'y'
                String answer = input.nextLine();
                if (!answer.equalsIgnoreCase("y")) {
                    addAnother = false;
                }
            }

        // Delete the entry from the food details table specified by user
        } else {
            deleteFoodDetails(foodLogComm);
        }
    }

    /**
     * Asks user for calories per serving and food category details regarding a
     * specific food. This data is stored in a table in teh food log database and
     * is used while running certain data reports.
     * @param foodLogComm   Object used to modify and query the food log database
     */
    public static void addFoodDetails(FoodLogComm foodLogComm) {
        System.out.println();
        System.out.println("Please enter the below information about a specific food.");
        System.out.println("This information is used when you run a data report so");
        System.out.println("that you can view data regarding your past meals.");

        // Get user input regarding food details
        Scanner input = new Scanner(System.in);
        System.out.print("Food name: ");
        String foodName = input.nextLine();

        // Ensure user does not leave food name blank
        while (foodName.equals("")) {
            System.out.print("Food name cannot be blank. Please enter food name: ");
            foodName = input.nextLine();
        }

        // Ensure calories input is an integer
        System.out.print("kCal per serving: ");
        int calories;

        while (true) {
            try {
                String caloriesInput = input.nextLine();
                calories = Integer.parseInt(caloriesInput);  // String to int conversion
                break;  // break if no exception thrown
            } catch (NumberFormatException e) {
                System.out.println("kCal/serving must be an integer.");
                System.out.print("Re-enter kCal/serving: ");
            }
        }

        // Ensure given food category is valid
        System.out.print("Closest food category (grain, fruit, vegetable, dairy, protein, other): ");
        String foodCategory = input.nextLine().toLowerCase();

        while (!foodCategories.contains(foodCategory)) {
            System.out.println("Food category must be grain, fruit, vegetable, dairy, protein, or other.");
            System.out.print("Please re-enter food category: ");
            foodCategory = input.nextLine();
        }

        // Insert the values into the database
        boolean success = foodLogComm.insertFoodDetails(foodName, calories, foodCategory);
        if (success) {
            System.out.println("\nFood details successfully added.");
        } else {
            System.out.println("\nError: food details not added. Ensure food is not already in food log.");
        }
    }

    /**
     * Asks user which food they want to delete calorie information for then
     * deletes that entry from the food log calorie table
     * @param foodLogComm   Object used to modify and query the food log database
     */
    public static void deleteFoodDetails(FoodLogComm foodLogComm) {
        System.out.println();
        System.out.print("Enter the name of the food that you want to delete: ");

        Scanner input = new Scanner(System.in);
        String food_name = input.nextLine();

        // Delete entry that matches given food name
        boolean success = foodLogComm.deleteFoodDetails(food_name);

        // Inform user whether or not deletion was successful
        if (success) {
            System.out.println("\nDeletion successful.");
        } else {
            System.out.println("\nError: entry not deleted. Food name does not exist.");
            System.out.println("Food must already exist in food log calories table to be deleted.");
        }
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
        System.out.println("4: Food log report for yesterday");
        System.out.println("5: Food log report for the last week");

        // Receive user input
        Scanner input = new Scanner(System.in);
        System.out.print("Enter option: ");

        // Ensure input is an integer
        int userOption = 0;
        try {
            userOption = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Input must be numerical.");
        }

        // Validate user input
        while (userOption < 1 || userOption > 5) {
            System.out.print("Option not valid. Please enter a valid option: ");

            // Ensure input is an integer
            try {
                userOption = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Input must be numerical.");
            }
        }
        System.out.println();

        // Print report given an single date
        if (userOption == 1) {

            // Get date from user
            System.out.print("Enter a date (yyyy-MM-dd): ");
            String userDate = input.nextLine();

            // Ensure given date is valid
            ResultSet resultSet = foodLogComm.fetchDataFromDate(userDate);
            while (resultSet == null) {
                System.out.print("Date is invalid. Please enter a valid date in yyyy-MM-dd format: ");
                userDate = input.nextLine();

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
            String startDate = input.nextLine();
            System.out.print("Enter end date (yyyy-MM-dd): ");
            String endDate = input.nextLine();

            // Ensure given dates are valid
            ResultSet resultSet = foodLogComm.fetchDataFromDateRange(startDate, endDate);
            while (resultSet == null) {
                System.out.println("One or both dates are invalid. Please enter valid dates.");
                System.out.print("Enter start date (yyyy-MM-dd): ");
                startDate = input.nextLine();
                System.out.print("Enter end date (yyyy-MM-dd): ");
                endDate = input.nextLine();

                resultSet = foodLogComm.fetchDataFromDateRange(startDate, endDate);
            }

            // Create DataReport and print results
            DataReport report = new DataReport(resultSet);
            System.out.println();
            report.printResults();
        } else if (userOption == 3) {

            // Get food name from user
            System.out.print("Enter food name: ");
            String foodName = input.nextLine();

            // Create a DataReport and print results
            DataReport report = new DataReport(foodLogComm.fetchDataFromFood(foodName));
            System.out.println();
            report.printResults();
        }

        // Ask if user would like to export the viewed data

    }

    public static void viewYesterdayReport(FoodLogComm foodLogComm) {

    }
}
