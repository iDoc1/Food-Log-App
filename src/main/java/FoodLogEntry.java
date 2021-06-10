import java.sql.Connection;

/**
 *  Given a database connection parameter, defines an object with methods
 *  that can be used to add, delete, and edit entries in the food_log
 *  table located by the given connection parameter.
 *
 * @author iDoc1
 *
 */

public class FoodLogEntry {

    private final String foodLogTable = "food_log";  // Name of the table to be modified

    private Connection connection;

    /**
     * Creates a FoodLogEntry object using the given FoodLogConnection
     * parameter that is connected to the food_log_database
     *
     * @param foodLogConn
     */
    public FoodLogEntry(FoodLogConnection foodLogConn) {
        this.connection = foodLogConn.getFoodLogConnection();
    }

    /**
     *
     * @param foodName          name of the food
     * @param mealType          meal type can be breakfast, lunch, dinner, brunch, or snack
     * @param servingQuantity   decimal value specifying number of servings consumed
     * @param notes             any additional notes
     * @return                  returns true if insertion was successful, false if not
     */
    public boolean insertRow(String foodName, String mealType, double servingQuantity, String notes) {


        return true;
    }

}
