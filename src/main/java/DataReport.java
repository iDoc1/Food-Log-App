import java.util.HashMap;

/**
 * This class represents a report that contains information about meals that
 * occurred over a certain time span, as determined by the calling class. This
 * class is used mainly by the ReportBuilder class to store and return
 * information regarding past meals.
 *
 * @author iDoc1
 *
 */

public class DataReport {
    private double totalCalories;
    private int mealCount;
    private HashMap<String, Integer> mealTypeCount;  // Count of each type of meal eaten
    private HashMap<String, Double> mealCategoryCount;  // Count of each meal cetegory eaten

    /**
     * Construct a YesterdayReport object and initialize data members
     */
    public DataReport() {
        this.totalCalories = 0;
        this.mealCount = 0;
        this.mealTypeCount = new HashMap<>();

        // Initialize meal type counts to zero
        this.mealTypeCount.put("breakfast", 0);
        this.mealTypeCount.put("brunch", 0);
        this.mealTypeCount.put("lunch", 0);
        this.mealTypeCount.put("dinner", 0);
        this.mealTypeCount.put("snack", 0);

        this.mealCategoryCount = new HashMap<>();

        // Initialize meal category counts to zero
        this.mealCategoryCount.put("grain", 0.0);
        this.mealCategoryCount.put("fruit", 0.0);
        this.mealCategoryCount.put("vegetable", 0.0);
        this.mealCategoryCount.put("dairy", 0.0);
        this.mealCategoryCount.put("protein", 0.0);
        this.mealCategoryCount.put("other", 0.0);
    }

    /**
     * Adds the given number of calories to the current total calories amount
     * @param caloriesPerServing    Number of calories per serving of a specific meal
     * @param servingQuantity       Quantity of servings eaten of specific meal
     */
    public void addTotalCalories(int caloriesPerServing, double servingQuantity) {
        this.totalCalories += (caloriesPerServing * servingQuantity);
    }

    /**
     * Calculates and returns calories per meal eaten yesterday
     * @return  Calories per meal eaten yesterday
     */
    public double getCaloriesPerMeal() {
        if (this.totalCalories > 0) {
            return this.totalCalories / this.mealCount;
        } else {
            return 0;
        }
    }

    /**
     * Increments the meal type count by one based on the given meal type
     * @param mealType  Meal type (breakfast, brunch, lunch, dinner, or snack)
     */
    public void incrementMealType(String mealType) {
        int currCount = this.mealTypeCount.get(mealType);
        this.mealTypeCount.put(mealType, currCount + 1);  // Increment count
        this.mealCount += 1;
    }

    /**
     * Increments the count of the given meal by one
     * @param mealCategory  Meal category (grain, vegetable, fruit, protein, dairy, other)
     */
    public void incrementMealCategory(String mealCategory, double servingQuantity) {
        double currentCount = this.mealCategoryCount.get(mealCategory);
        this.mealCategoryCount.put(mealCategory, currentCount + servingQuantity);
    }

    /**
     * @param totalCalories Total calories in eaten yesterday
     */
    public void setTotalCalories(int totalCalories) {
        this.totalCalories = totalCalories;
    }

    /**
     * @param mealCount Number of meals eaten yesterday
     */
    public void setMealCount(int mealCount) {
        this.mealCount = mealCount;
    }

    /**
     * @return  Total calories eaten yesterday
     */
    public double getTotalCalories() {
        return totalCalories;
    }

    /**
     * @return  Current meal count eaten yesterday
     */
    public int getMealCount() {
        return mealCount;
    }

    /**
     * @return Counts of breakfast, brunch, lunch, dinner, and snacks eaten yesterday
     */
    public HashMap<String, Integer> getMealTypeCount() {
        return mealTypeCount;
    }

    /**
     * @return Counts of category of meals eaten yesterday
     */
    public HashMap<String, Double> getMealCategoryCount() {
        return mealCategoryCount;
    }
}
