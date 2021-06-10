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
            = new HashSet<String>(Arrays.asList("breakfast", "lunch", "dinner", "brunch", "snack"));


    public static void main(String[] args) {
        introduction();
        choseOption();
    }

    /**
     * Introduces the user to the Food Log program
     */
    public static void introduction() {
        System.out.println("Welcome to the Food Log program.");
        System.out.println("This program allows you to record and view data regarding");
        System.out.println("the food you eat throughout the day. Please review the");
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

        // Proceed forward based on user's chosen option
        System.out.println();
        if (userOption.equals("1")) {
            modifyFoodLog();
        } else if (userOption.equals("2")) {
            addFoodDetails();
        } else {
            viewData();
        }

    }

    /**
     * Creates a connection to the food log database and allows user to choose
     * whether to add, delete, or edit a food log entry
     */
    public static void modifyFoodLog() {

        // Establish connection to the food log database
        FoodLogConnection foodLogConn = new FoodLogConnection();

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
        System.out.println();
        if (userOption.equals("1")) {
            addEntry(foodLogConn);
        } else if (userOption.equals("2")) {
            deleteEntry(foodLogConn);
        } else {
            editEntry(foodLogConn);
        }
    }

    /**
     * Accepts a FoodLogConnection object and uses this connection to create
     * a FoodLogEntry object used to add an entry to the food_log
     * @param foodLogConn  Provides a method to add an entry to the food_log table
     */
    public static void addEntry(FoodLogConnection foodLogConn) {

        // Get entry details from user
        Scanner input = new Scanner(System.in);

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
        double servingQuantity = 0;  // Initialize serving quantity to zero

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

        // Create Food and FoodLogEntry objects
        Food foodEaten = new Food(foodName, mealType, servingQuantity);
        FoodLogEntry foodLogEntry = null;

        // Create entry object depending on if user entered an entry date or not
        if (entryDate.equals("")) {
            foodLogEntry = new FoodLogEntry(foodLogConn, notes, foodEaten);
        } else {
            foodLogEntry = new FoodLogEntry(foodLogConn, entryDate, notes, foodEaten);
        }

        // Add entry to database and check if insertion was successful
        boolean success = foodLogEntry.insertRow();
        if (success) {
            System.out.println("Entry successfully added.");
        } else {
            System.out.println("Entry insertion failed.");
        }

    }

    public static void deleteEntry(FoodLogConnection foodLogConn) {

    }

    public static void editEntry(FoodLogConnection foodLogConn) {

    }

    public static void addFoodDetails() {

    }

    public static void viewData() {

    }
}
