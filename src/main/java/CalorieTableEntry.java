/**
 * This class represents a row in the food log calorie table that contains
 * data regarding the calories per serving and food category of a specific
 * food. This class is used to store data this is queried from a specific
 * row in the food log calorie table.
 *
 * @author iDoc1
 *
 */
public class CalorieTableEntry {
    private int caloriesPerServing;
    private String foodCategory;

    /**
     * Constructs a CalorieTableEntry object given calories and category
     * @param caloriesPerServing    kCal per serving of food
     * @param foodCategory          Category of food (fruit, vegetable, grain, dairy, protein, other)
     */
    public CalorieTableEntry(int caloriesPerServing, String foodCategory) {
        this.caloriesPerServing = caloriesPerServing;
        this.foodCategory = foodCategory;
    }

    /**
     * @return  Calories per serving of food in calorie table
     */
    public int getCaloriesPerServing() {
        return caloriesPerServing;
    }

    /**
     * @return Category of food in calorie table
     */
    public String getFoodCategory() {
        return this.foodCategory;
    }
}
