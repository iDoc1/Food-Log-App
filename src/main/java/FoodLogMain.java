import java.util.Scanner;

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

        // Create Scanner object to receive user input
        Scanner input = new Scanner(System.in);

        // Validate user input
        System.out.print("Enter option: ");
        String userOption = input.next();

        while (!userOption.equals("1") && !userOption.equals("2") && !userOption.equals("3")) {
            System.out.print("Invalid choice. Please enter a valid option: ");
            userOption = input.next();
        }

        // Continue forward based on user's chosen option
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
        FoodLogConnection foodLogConn = new FoodLogConnection();
    }

    public static void addFoodDetails() {

    }

    public static void viewData() {

    }
}
