import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents a Report Builder that uses a ResultSet passed to the
 * constructor. The methods in this class can be used to print the given
 * ResultSet to the console, pass the results as a Map, or compile and
 * return the results in a DataReport object. The main function of this class
 * is to take a ResultSet and build a usable report out of it.
 *
 * @author iDoc1
 *
 */

public class ReportBuilder {

    private ResultSet results;

    /**
     * Constructs a DataReport object given a ResultSet object parameter
     * @param results   A ResultSet object obtained from a database query
     */
    public ReportBuilder(ResultSet results) {
        this.results = results;
    }

    /**
     * Returns a HashMap where the keys are the entry IDs of each entry in this
     * object's ResultSet field, and the values are FoodTableEntry objects containing
     * the data stored in each column corresponding to the entry ID.
     * @return  A HashMap<K,V> where K is the entry ID, and V is a FoodTableEntry object
     */
    public HashMap<Integer, FoodTableEntry> getResultsMap() {
        HashMap<Integer, FoodTableEntry> resultMap = new HashMap<>();

        // Populate HashMap with Integer-FoodTableEntry pairs
        try {

            // Ensure cursor is at front of ResultSet
            this.results.beforeFirst();

            // Iterate over results and add each row to the Map
            while (results.next()) {
                int entryID = results.getInt("entry_id");
                String entryDate = results.getString("entry_date");
                String foodName = results.getString("food_name");
                String mealType = results.getString("meal_type");
                double servingQty = results.getDouble("serving_quantity");
                String entryNotes = results.getString("entry_notes");

                // Create FoodTableEntry object
                FoodTableEntry foodEntry = new FoodTableEntry(entryDate, foodName, mealType, servingQty, entryNotes);

                // Store row in HashMap
                resultMap.put(entryID, foodEntry);
            }
        } catch(SQLException e) {
            return null;
        }

        return resultMap;
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

            // Ensure cursor is a front of ResultSet
            this.results.beforeFirst();

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
     * of spaces is determined using the length of the given String and the given
     * column width parameter. The maximum width of any column in the food log mySQL
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
     * Creates a DataReport object to store total calories, meal count, and meal
     * type count for all food eaten in this object's ResultSet
     * @param foodDetailsData   A ResultSet containing all food calorie data
     * @return                  An object containing total calories, meal count, and meal
     *                          type count for all food eaten in this object's ResultSet
     */
    public DataReport getDataReport(ResultSet foodDetailsData) {

        // Initialize DataReport object
        DataReport dataReport = new DataReport();

        // Pull all given food calories details from ResultSet into a Map
        HashMap<String, CalorieTableEntry> calorieMap = this.getCalorieMap(foodDetailsData);

        try {
            results.beforeFirst();

            // Create Map to store previous meals by date to get accurate meal counts
            HashMap<String, ArrayList<String>> mealsByDate = new HashMap<>();

            // Iterate through data in ResultSet
            while (results.next()) {
                String entryDate = results.getString("entry_date");
                String foodName = results.getString("food_name");
                String mealType = results.getString("meal_type");
                double servingQuantity = results.getDouble("serving_quantity");

                // Check if calorie map contains the food name before storing calories
                int foodCalories = 0;
                if (calorieMap.containsKey(foodName)) {
                    foodCalories = calorieMap.get(foodName).getCaloriesPerServing();

                    // Only increment food category count if food is found in calorie map
                    dataReport.incrementMealCategory(calorieMap.get(foodName).getFoodCategory(), servingQuantity);
                }

                // Update total calorie count
                dataReport.addTotalCalories(foodCalories, servingQuantity);

                // Check if date is already in Map
                if (!mealsByDate.containsKey(entryDate)) {
                    mealsByDate.put(entryDate, new ArrayList<>());
                }

                /*  Only increment meal type count if this row's meal has not already occurred
                    on this row's date. Snacks are the only exception to this rule, and they
                    are counted as many times as they occur. All other meals are only incremented
                    once per day. This only allows user to have one breakfast, brunch, lunch, or
                    dinner. If user has two dinners, those will only be counted as one meal for
                    that day.
                 */
                if (!mealsByDate.get(entryDate).contains(mealType)  || mealType.equals("snack")) {
                    dataReport.incrementMealType(mealType);  // Increment meal type count
                    mealsByDate.get(entryDate).add(mealType);  // Add meal type to array where entryDate is the key
                }
            }
        } catch (SQLException e) {
            return null;
        }

        return dataReport;
    }

    /**
     * Takes a ResultSet with all food log calories data then returns a HashMap
     * containing all data in the ResultSet where the food name is the key
     * @param foodDetailsData   A ResultSet object containing all food log calorie data
     * @return                  A HashMap with all calorie data in given ResultSet
     */
    public HashMap<String, CalorieTableEntry> getCalorieMap(ResultSet foodDetailsData) {
        HashMap<String, CalorieTableEntry> calorieMap = new HashMap<>();

        try {

            // Iterate through food details ResultSet and add data to Map
            while (foodDetailsData.next()) {
                String foodName = foodDetailsData.getString("food_name");
                int caloriesPerServing = foodDetailsData.getInt("calories_per_serving");
                String foodCategory = foodDetailsData.getString("food_category");

                // Create CalorieTableEntry object and add to HashMap
                CalorieTableEntry calorieDetails = new CalorieTableEntry(caloriesPerServing, foodCategory);
                calorieMap.put(foodName, calorieDetails);
            }

        } catch (SQLException e) {
            return null;
        }

        return calorieMap;
    }
}
