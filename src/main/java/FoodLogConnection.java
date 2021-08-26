import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Creates a Connection object specifically to the food_log_database
 * and provides a get method that returns the current connection that
 * can be used to connect to and modify the database
 *
 * @author iDoc1
 *
 */
public class FoodLogConnection {

    private final String databaseUrl = "jdbc:mysql://localhost:3306/food_log_database";
    private final String username = "foodLog";
    private final String password = "admin";

    private Connection connection;

    /**
     * Constructs a FoodLogConnection object
     */
    public FoodLogConnection() {
        try {
            this.connection = DriverManager.getConnection(databaseUrl, username, password);
        } catch (SQLException e) {
            throw new IllegalStateException("Database connection failed.");
        }
    }

    /**
     * Returns the current connection to the food log database
     * @return  A Connection object to food_log_database
     */
    public Connection getFoodLogConnection() {
        return this.connection;
    }

    /**
     * Closes the database connection
     */
    public void closeConn() throws SQLException {
        this.connection.close();
    }
}
