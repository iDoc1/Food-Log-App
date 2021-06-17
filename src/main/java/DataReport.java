import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class represents a Data Report where a ResultSet is passed to the
 * constructor and is used to display and manipulate date as defined by
 * a variety of methods.
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
    public void printAllResults() {

        // Print column headers with spaces for column alignment
        System.out.print("Entry ID" + "                        ");
        System.out.print("Entry Date" + "                      ");
        System.out.print("Food Name" + "                       ");
        System.out.print("Meal Type" + "                       ");
        System.out.print("Serving Quantity" + "                ");
        System.out.println("Entry Notes");

        // Print results and handle exception
        try {

            // Loop through results set and add the proper number of spaces
            while (results.next()) {
                Integer entryID = results.getInt("entry_id");
                System.out.print(entryID);
                insertSpaces(entryID.toString());

                String entryDate = results.getString("entry_date");
                System.out.print(entryDate);
                insertSpaces(entryDate);

                String foodName = results.getString("food_name");
                System.out.print(foodName);
                insertSpaces(foodName);

                String mealType = results.getString("meal_type");
                System.out.print(mealType);
                insertSpaces(mealType);

                Double servingQty = results.getDouble("serving_quantity");
                System.out.print(servingQty);
                insertSpaces(servingQty.toString());

                String entryNotes = results.getString("entry_notes");
                System.out.println(entryNotes);
            }
        } catch (SQLException e) {
            System.out.println("Results not found.");
        }
    }

    /**
     * Inserts the proper number of spaces necessary to keep columns aligned. Number
     * of spaces is determined using the length of the given String and the standard
     * column width for displaying each entry, which is defined as 32 characters for
     * this program.
     * @param strEntry String to insert spaces after
     */
    private void insertSpaces(String strEntry) {
        int numOfSpaces = 32 - strEntry.length();

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
