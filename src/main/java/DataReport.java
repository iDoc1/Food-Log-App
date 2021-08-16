import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class represents a Data Report where a ResultSet is passed to the
 * constructor. The methods in this class can be used to print the given
 * ResultSet to the console, or export the ResultSet in JSON or CSV format
 * to be used by another application.
 *
 * @author iDoc1
 *
 */

public class DataReport {

    private ResultSet results;

    /**
     * Constructs a DataReport object given a ResultSet object parameter
     * @param results   A ResultSet object obtained from a database query
     */
    public DataReport(ResultSet results) {
        this.results = results;
    }

    /**
     * Prints all of the results in a table format separated by spaces to
     * keep columns aligned. Spaces are used instead of tabs to ensure
     * columns remain aligned if users have different tab sizes settings.
     */
    public void printResults() {

        // Check if results are empty
        try {
            if (!results.isBeforeFirst()) {  // isBeforeFirst returns false if ResultSet is empty
                System.out.println("No results found.");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while checking if data exists.");
            return;
        }

        // Print column headers with spaces for column alignment
        System.out.print("Entry ID" + "        ");
        System.out.print("Entry Date" + "      ");
        System.out.print("Food Name" + "                       ");
        System.out.print("Meal Type" + "       ");
        System.out.print("Serving Qty" + "     ");
        System.out.println("Entry Notes");

        // Print results and handle exception
        try {

            // Loop through results set and add the proper number of spaces
            while (results.next()) {
                Integer entryID = results.getInt("entry_id");
                System.out.print(entryID);
                insertSpaces(entryID.toString(), 16);

                String entryDate = results.getString("entry_date");
                System.out.print(entryDate);
                insertSpaces(entryDate, 16);

                String foodName = results.getString("food_name");
                System.out.print(foodName);
                insertSpaces(foodName, 32);

                String mealType = results.getString("meal_type");
                System.out.print(mealType);
                insertSpaces(mealType, 16);

                Double servingQty = results.getDouble("serving_quantity");
                System.out.print(servingQty);
                insertSpaces(servingQty.toString(), 16);

                String entryNotes = results.getString("entry_notes");
                System.out.println(entryNotes);
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while fetching data.");
        }
    }

    /**
     * Inserts the proper number of spaces necessary to keep columns aligned. Number
     * of spaces is determined using the length of the given String and the standard
     * given column width parameter. The maximum width of any column in the food log
     * database is 30 characters (except for the last column), so the maximum column
     * width given as a parameter should be limited to 32 characters.
     * @param strEntry String to insert spaces after
     * @param colWidth Desired width of column in number of characters
     */
    private void insertSpaces(String strEntry, int colWidth) {
        int numOfSpaces = colWidth - strEntry.length();

        // Print spaces
        for (int i = 1; i <= numOfSpaces; i++) {
            System.out.print(" ");
        }
    }

    /**
     * Saves this object's ResultSet in CSV format
     */
    public void saveAsCSV() {

    }

    /**
     * Saves this object's ResultSet in JSON format
     */
    public void saveAsJson() {

    }

}
