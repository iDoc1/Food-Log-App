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
     * Prints all of the results in a table format
     */
    public void printAllResults() {

        // Print column headers
        System.out.print("Entry ID" + "\t\t");
        System.out.print("Entry Date" + "\t\t");
        System.out.print("Food Name" + "\t\t");
        System.out.print("Meal Type" + "\t\t");
        System.out.print("Serving Quantity" + "\t\t");
        System.out.println("Entry Notes" + "\t\t");

        // Print results and handle exception
        try {

            // Loop through results set and add the proper number of tabs for column spacing
            while (results.next()) {
                System.out.print(results.getInt("entry_id") + "\t\t");
                System.out.print(results.getString("entry_date") + "\t\t");
                System.out.print(results.getString("food_name") + "\t\t");
                System.out.print(results.getString("meal_type") + "\t\t");
                System.out.print(results.getString("serving_quantity") + "\t\t");
                System.out.println(results.getString("entry_notes") + "\t\t");
            }
        } catch (SQLException e) {
            System.out.println("Results not found.");
        }

    }

    /**
     * Inserts the proper number of tabs necessary to keep columns aligned. Number
     * of tabs is determined using the length of the given String and the maximum
     * number of characters allowed amoung the first 5 columsn (50 characters)
     * @param strLength length of the String to insert tabs after
     */
    private void insertTabs(int strLength) {

    }

}
