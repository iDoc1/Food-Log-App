import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Given a database connection parameter and a series, defines an object with methods
 * that can be used to add, delete, and edit entries in the food_log
 * table located by the given connection parameter.
 *
 * @author iDoc1
 *
 */

public class FoodLogEntry {

    private final String foodLogTable = "food_log";  // Name of the table to be modified

    private Connection connection;  // Existing connection to food_log_database
    private String entryDate;  // Desired date of entry
    private String entryNotes;  // Notes associated with an entry
    private Food foodEaten;  // Food object to add to food log

    /**
     * Creates a FoodLogEntry object using all of the available private members
     *
     * @param foodLogConn
     */
    public FoodLogEntry(FoodLogConnection foodLogConn, String entryDate, String notes, Food foodEaten) {
        this.connection = foodLogConn.getFoodLogConnection();
        this.entryDate = entryDate;
        this.entryNotes = notes;
        this.foodEaten = foodEaten;
    }

    /**
     *
     */
    //public FoodLogEntry

    /**
     * Uses this object's private fields to insert a row into the food_log table
     *
     * @return                  returns true if insertion was successful, false if not
     */
    public boolean insertRow() throws SQLException {
        String sqlString = "INSERT INTO food_log_database.food_log (entry_date, food_name, meal_type" +
                ", serving_quantity, entry_notes) VALUES (?, ?, ?, ?, ?)";

        // Create PreparedStatement using given parameters
        PreparedStatement statement = this.connection.prepareStatement(sqlString);


        return true;
    }

}
