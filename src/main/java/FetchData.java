import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class accepts a FoodLogConnection object as a parameter then provides
 * multiple methods that can be used to run queries on the food log
 * database and get the resulting ResultSet object.
 *
 * @author iDoc1
 *
 */

public class FetchData {

    private Connection connection;

    /**
     * Creates a FetchData object using an existing connection to the food
     * log database
     * @param foodLogConn   Current connection to food_log_database
     */
    public FetchData(FoodLogConnection foodLogConn) {
        this.connection = foodLogConn.getFoodLogConnection();
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
            PreparedStatement statement = this.connection.prepareStatement(sqlQuery);
            statement.setInt(1, rowID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                System.out.print(resultSet.getInt("entry_id") + "\t");
                System.out.print(resultSet.getString("entry_date") + "\t");
                System.out.print(resultSet.getString("food_name") + "\t");
                System.out.println(resultSet.getString("meal_type") + "\t");
            }

            return resultSet;

        } catch (SQLException e) {
            System.out.println("Error.");
            return null;  // Return null if an exception is thrown
        }

    }

    /**
     * Returns a ResultSet of all the entries in the food log that occur
     * on a given date
     * @param date  The date that the user wants to find food log entries for
     * @return      The ResultSet object for all entries occurring on given date
     */
    //public ResultSet fetchDataFromDate(String date) {

    //}

    /**
     * Returns a ResultSet of all entries in the food log that occur
     * between a start date (inclusive) and and end date (inclusive)
     * @param startDate The start of the date range to search data for (inclusive)
     * @param endDate   The end of the date range to search data for (inclusive)
     * @return          The ResultSet object for all entries in given date range
     */
    //public ResultSet fetchDataFromFoodDateRange(String startDate, String endDate) {

    //}

}
