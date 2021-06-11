import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Given a database connection parameter and a series, defines an object with methods
 * that can be used to add, delete, and edit entries in the food_log
 * table located by the given connection parameter.
 *
 * @author iDoc1
 *
 */

public class FoodLogEntry {

    private Connection connection;  // Existing connection to food_log_database
    private String entryDate;  // Desired date of entry
    private String entryNotes;  // Notes associated with an entry
    private Food foodEaten;  // Food object to add to food log

    /**
     * Creates a FoodLogEntry object using all of the available private data members
     * @param foodLogConn   Current connection to food_log_database
     * @param entryDate     Date of the desired food log entry
     * @param notes         Any entry notes the user would like to add
     * @param foodEaten     Food object containing food name, meal type and serving quantity
     */
    public FoodLogEntry(FoodLogConnection foodLogConn, String entryDate, String notes, Food foodEaten) {
        this.connection = foodLogConn.getFoodLogConnection();
        this.entryDate = entryDate;
        this.entryNotes = notes;
        this.foodEaten = foodEaten;
    }

    /**
     * Creates a FoodLogEntry object with today's date as the entryDate data member. Overloads
     * the original constructor.
     * @param foodLogConn   Current connection to food_log_database
     * @param notes         Any entry notes the user would like to add
     * @param foodEaten     Food object containing food name, meal type and serving quantity
     */
    public FoodLogEntry(FoodLogConnection foodLogConn, String notes, Food foodEaten) {
        this(foodLogConn, "", notes, foodEaten);

        // Set entryDate data member to today's date in yyyy-MM-dd format
        Date date = new Date();
        this.entryDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    /**
     * Uses this object's private fields to insert a row into the food_log table
     * @return  Returns true if insertion was successful, false if not
     */
    public boolean insertRow() {
        String sqlString = "INSERT INTO food_log_database.food_log (entry_date, food_name, meal_type" +
                ", serving_quantity, entry_notes) VALUES (?, ?, ?, ?, ?)";

        // Create PreparedStatement using given parameters
        try {
            PreparedStatement statement = this.connection.prepareStatement(sqlString);
            statement.setString(1, this.entryDate);
            statement.setString(2, this.foodEaten.getFoodName());
            statement.setString(3, this.foodEaten.getMealType());
            statement.setDouble(4, this.foodEaten.getServingQuantity());
            statement.setString(5, this.entryNotes);

        // Execute query and return false if insertion fails
            statement.executeUpdate();
        } catch (SQLException e) {
            return false;
        }

        // Return true since insertion succeeded
        return true;
    }

}
