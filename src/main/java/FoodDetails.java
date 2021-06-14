/**
 * This class represents an individual food with a food name, meal type,
 * and serving quantity.
 *
 * @author iDoc1
 */

public class FoodDetails {

    private String foodName;
    private String mealType;
    private double servingQuantity;

    /**
     * Constructs a FoodDetails object with food name, meal type and serving quantity
     * @param foodName          Name of the food
     * @param mealType          Meal type can be breakfast, lunch, dinner, brunch, or snack
     * @param servingQuantity   Decimal value for number of servings of this food eaten
     */
    public FoodDetails(String foodName, String mealType, double servingQuantity) {
        this.foodName = foodName;
        this.mealType = mealType;
        this.servingQuantity = servingQuantity;
    }

    /**
     * Returns the food name for this FoodDetails object
     * @return  A String representing the name of the food
     */
    public String getFoodName() {
        return this.foodName;
    }

    /**
     * Returns the meal type associated with this food
     * @return  The meal type associated with this object
     */
    public String getMealType() {
        return this.mealType;
    }

    /**
     * Returns the serving quantity associated with this food and meal
     * @return  The serving quantity value
     */
    public double getServingQuantity() {
        return this.servingQuantity;
    }


}
