import java.util.HashMap;

public class YesterdayReport {
    private double totalCalories;
    private int mealCount;
    private HashMap<String, Integer> mealTypeCount;  // Count of each type of meal eaten

    public YesterdayReport() {
        this.totalCalories = 0;
        this.mealCount = 0;
        this.mealTypeCount = new HashMap<String, Integer>();

        // Initialize food category counts to zero
        this.mealTypeCount.put("grain", 0);
        this.mealTypeCount.put("vegetable", 0);
        this.mealTypeCount.put("fruit", 0);
        this.mealTypeCount.put("dairy", 0);
        this.mealTypeCount.put("protein", 0);
        this.mealTypeCount.put("other", 0);
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
        return (double) this.totalCalories / this.mealCount;
    }

    /**
     * Increases the given meal type count to 1 if that meal type
     * is currently zero. Otherwise, does nothing.
     * @param mealType  Breakfast, brunch, lunch, dinner, or snack
     */
    public void incrementMealType(String mealType) {
        if (this.mealTypeCount.get(mealType) == 0) {
            this.mealTypeCount.put(mealType, 1);
            this.mealCount = 1;
        }
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
}
