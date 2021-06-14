import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class takes a connection to the food log database as a parameter
 * then defines a variety of methods by which to manipulate and run
 * queries on this database. This class serves as a communicator to the
 * food log database.
 *
 * @author iDoc1
 *
 */

public class FoodLogComm {

    private Connection connection;

    /**
     * Constructs a FoodLogComm object given an existing connection to
     * the food log database
     * @param foodLogConn   An existing connection to the food log database
     */
    public FoodLogComm(FoodLogConnection foodLogConn) {
        this.connection = foodLogConn.getFoodLogConnection();
    }

    /**
     * Inserts a row in the food log database given a FoodDetails object, an entry
     * date, and entry notes
     * @param foodEaten     The FoodDetails object with food name, meal type, and serving size
     * @param entryDate     The date of the entry to be inserted
     * @param entryNotes    Notes associated with the entry to be inserted
     * @return              true if insertion successful, false otherwise
     */
    public boolean insertRowGivenDate(FoodDetails foodEaten, String entryDate, String entryNotes) {
        String sqlString = "INSERT INTO food_log_database.food_log (entry_date, food_name, meal_type" +
                ", serving_quantity, entry_notes) VALUES (?, ?, ?, ?, ?)";

        // Create PreparedStatement using given parameters
        try {
            PreparedStatement statement = this.connection.prepareStatement(sqlString);
            statement.setString(1, entryDate);
            statement.setString(2, foodEaten.getFoodName());
            statement.setString(3, foodEaten.getMealType());
            statement.setDouble(4, foodEaten.getServingQuantity());
            statement.setString(5, entryNotes);

            // Execute query and return false if insertion fails
            statement.executeUpdate();
        } catch (SQLException e) {
            return false;
        }

        // Return true since insertion succeeded
        return true;
    }

    /**
     * Inserts a row in the food log database given a FoodDetails object and entry
     * notes. Today's date is set as the entry date.
     * @param foodEaten     The FoodDetails object with food name, meal type, and serving size
     * @param entryNotes    Notes associated with the entry to be inserted
     * @return              true if insertion successful, false otherwise
     */
    public boolean insertRowCurrDate(FoodDetails foodEaten, String entryNotes) {

        // Store today's date in yyyy-MM-dd format
        Date date = new Date();
        String entryDate = new SimpleDateFormat("yyyy-MM-dd").format(date);

        // Add entry using insertRowGivenDate method and return boolean result
        return this.insertRowGivenDate(foodEaten, entryDate, entryNotes);
    }


}
