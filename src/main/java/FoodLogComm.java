import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * This class takes a connection to the food log database as a parameter
 * then defines a variety of methods by which to manipulate and run
 * queries on this database. This class serves as a communicator to the
 * food log database to perform CRUD operations.
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
            statement.setString(2, foodEaten.getFoodName().toLowerCase());
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

    /**
     * Returns a ResultSet of a single row given the row ID value
     * @param rowID The ID of a specific row within the food log database
     * @return      The ResultSet object for a single row
     */
    public ResultSet fetchDataFromID(int rowID) {
        String sqlQuery = "SELECT * FROM food_log_database.food_log a WHERE a.entry_id = ?";

        // Execute query and handle exception
        try {
            PreparedStatement statement = this.connection.prepareStatement(sqlQuery
                    , ResultSet.TYPE_SCROLL_SENSITIVE
                    , ResultSet.CONCUR_READ_ONLY);
            statement.setInt(1, rowID);
            return statement.executeQuery();  // Return ResultSet object
        } catch (SQLException e) {
            return null;  // Return null if an exception is thrown
        }
    }

    /**
     * Returns a ResultSet of all the entries in the food log that occur
     * on a given date, or null if date String is invalid
     * @param date  The date that the user wants to find food log entries for
     * @return      The ResultSet object for all entries occurring on given date or
     *              null if the given date String throws an exception
     */
    public ResultSet fetchDataFromDate(String date) {
        String sqlQuery = "SELECT * FROM food_log_database.food_log a " +
                "WHERE a.entry_date = ? ORDER BY a.entry_date";

        // Execute query and handle exception
        try {

            // Ensure ResultSet will be scrollable
            PreparedStatement statement = this.connection.prepareStatement(sqlQuery
                    , ResultSet.TYPE_SCROLL_SENSITIVE
                    , ResultSet.CONCUR_READ_ONLY);
            statement.setString(1, date);
            return statement.executeQuery();  // Return ResultSet object
        } catch (SQLException e) {
            return null;  // Return null if an exception is thrown
        }
    }

    /**
     * Returns a ResultSet of all entries in the food log that occur
     * between a start date (inclusive) and and end date (inclusive)
     * @param startDate The start of the date range to search data for (inclusive)
     * @param endDate   The end of the date range to search data for (inclusive)
     * @return          The ResultSet object for all entries in given date range, or
     *                  null if an error is thrown
     */
    public ResultSet fetchDataFromDateRange(String startDate, String endDate) {
        String sqlQuery = "SELECT * FROM food_log_database.food_log a " +
                "WHERE a.entry_date >= ? AND a.entry_date <= ? ORDER BY a.entry_date";

        // Execute query and handle exception
        try {

            // Ensure ResultSet will be scrollable
            PreparedStatement statement = this.connection.prepareStatement(sqlQuery
                    , ResultSet.TYPE_SCROLL_SENSITIVE
                    , ResultSet.CONCUR_READ_ONLY);
            statement.setString(1, startDate);
            statement.setString(2, endDate);
            return statement.executeQuery();  // Return ResultSet object
        } catch (SQLException e) {
            return null;  // Return null if an exception is thrown
        }
    }

    /**
     * Returns a ResultSet of all entries in the food log that have a
     * given food name
     * @param foodName  Name of the food to get ResultSet for
     * @return          The ResultSet object for all entries that have the given
     *                  food name, or null if an error is thrown
     */
    public ResultSet fetchDataFromFood(String foodName) {
        String sqlQuery = "SELECT * FROM food_log_database.food_log a " +
                "WHERE a.food_name LIKE ? ORDER BY a.entry_date";

        // Execute query and handle exception
        try {
            PreparedStatement statement = this.connection.prepareStatement(sqlQuery
                    , ResultSet.TYPE_SCROLL_SENSITIVE
                    , ResultSet.CONCUR_READ_ONLY);
            statement.setString(1, "%" + foodName + "%");
            return statement.executeQuery();  // Return ResultSet object
        } catch (SQLException e) {
            return null;  // Return null if an exception is thrown
        }
    }

    /**
     * Returns a ResultSet with all entries with a date of yesterday
     * @return  A ResultSet of yesterday's entries
     */
    public ResultSet fetchYesterdayData() {
        String sqlQuery = "SELECT * FROM food_log_database.food_log a " +
                "WHERE a.entry_date = CURDATE() - INTERVAL 1 DAY";

        try {
            PreparedStatement statement = this.connection.prepareStatement(sqlQuery
                    , ResultSet.TYPE_SCROLL_SENSITIVE
                    , ResultSet.CONCUR_READ_ONLY);
            return statement.executeQuery();
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Deletes all entries in the food log that are older than the given number
     * of days parameter
     * @param deleteDays    Number of days older than to delete
     * @return              true if deletion successful, false otherwise
     */
    public boolean deleteOldEntries(int deleteDays) {
        String sqlString = "DELETE FROM food_log_database.food_log a " +
                "WHERE a.entry_date < CURDATE() - INTERVAL ? DAY";

        try {
            PreparedStatement statement = this.connection.prepareStatement(sqlString);
            statement.setInt(1, deleteDays);
            statement.executeUpdate();
        } catch (SQLException e) {
            return false;
        }

        return true;
    }

    /**
     * Updates a row with the given entry ID with the new values given
     * @param entryID           entry ID of row
     * @param entryDate         date of food entry
     * @param foodName          name of food entry
     * @param mealType          meal type of food entry
     * @param servingQuantity   double value of serving quantity
     * @param entryNotes        entry notes
     * @return                  true if update is successful, false otherwise
     */
    public boolean editEntry(int entryID, String entryDate, String foodName
            , String mealType, double servingQuantity, String entryNotes) {
        String sqlString = "UPDATE food_log_database.food_log " +
                "SET entry_date = ?, food_name = ?, meal_type = ?, serving_quantity = ?, " +
                "entry_notes = ? WHERE entry_id = ?";

        try {
            PreparedStatement statement = this.connection.prepareStatement(sqlString);
            statement.setString(1, entryDate);
            statement.setString(2, foodName.toLowerCase());
            statement.setString(3, mealType);
            statement.setDouble(4, servingQuantity);
            statement.setString(5, entryNotes);
            statement.setInt(6, entryID);
            statement.executeUpdate();
        } catch (SQLException e) {
            return false;
        }

        return true;
    }

    /**
     * Inserts an entry into the calorie table. Duplicate foods are not allowed in
     * this table as defined by the created table's primary key.
     * @param foodName      Name of the food
     * @param calories      Calories per serving in the food
     * @param foodCategory  Category of the food
     * @return              true if insertion successful, false otherwise
     */
    public boolean insertFoodDetails(String foodName, int calories, String foodCategory) {
        String sqlString = "INSERT INTO food_log_database.calorie_table" +
                "(food_name, calories_per_serving, food_category) " +
                "VALUES(?, ?, ?)";

        // Insert values into table and handle exceptions
        try {
            PreparedStatement statement = this.connection.prepareStatement(sqlString);
            statement.setString(1, foodName);
            statement.setInt(2, calories);
            statement.setString(3, foodCategory);
            statement.executeUpdate();
        } catch (SQLException e) {
            return false;
        }

        return true;
    }

    /**
     * Deletes the entry from the calorie table that matches the given food name
     * @param foodName  name of the food
     * @return          true if deletion successful, false otherwise
     */
    public boolean deleteFoodDetails(String foodName) {
        String sqlString = "DELETE FROM food_log_database.calorie_table a " +
                "WHERE a.food_name = ?";

        try {
            PreparedStatement statement = this.connection.prepareStatement(sqlString);
            statement.setString(1, foodName.toLowerCase());
            statement.executeUpdate();
        } catch (SQLException e) {
            return false;
        }

        return true;
    }

    /**
     * Returns a scrollable ResultSet with all data in the food log calorie table
     * @return  A ResultSet of all food calorie data
     */
    public ResultSet fetchCalorieData() {
        String sqlQuery = "SELECT * FROM food_log_database.calorie_table";

        try {
            PreparedStatement statement = this.connection.prepareStatement(sqlQuery
                    , ResultSet.TYPE_SCROLL_SENSITIVE
                    , ResultSet.CONCUR_READ_ONLY);
            return statement.executeQuery();
        } catch (SQLException e) {
            return null;
        }
    }

}
