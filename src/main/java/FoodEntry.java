/**
 * This class represents a row in the food log database, excluding the
 * entry ID. The purpose of this class is to store the data within individual
 * rows that are fetched from the food log database.
 */

public class FoodEntry {

    private String entryDate;
    private String foodName;
    private String mealType;
    private double servingQuantity;
    private String entryNotes;

    /**
     * Constructor for a FoodEntry object
     * @param entryDate         Date of food log entry
     * @param foodName          Name of food in food log
     * @param mealType          Meal type of food log entry
     * @param servingQuantity   Serving quantity of food log entry
     * @param entryNotes        Notes in food lgo entry
     */
    public FoodEntry(String entryDate, String foodName, String mealType, double servingQuantity, String entryNotes) {
        this.entryDate = entryDate;
        this.foodName = foodName;
        this.mealType = mealType;
        this.servingQuantity = servingQuantity;
        this.entryNotes = entryNotes;
    }

    /**
     * Returns the entry date of this food log entry
     * @return  A String of entry date
     */
    public String getEntryDate() {
        return entryDate;
    }

    /**
     * Returns the food name of this food log entry
     * @return  A String of the food name
     */
    public String getFoodName() {
        return foodName;
    }

    /**
     * Returns the meal type of this food log entry
     * @return  A String of the meal type
     */
    public String getMealType() {
        return mealType;
    }

    /**
     * Returns the serving quantity of this food log entry
     * @return  A double value of the serving quantity
     */
    public double getServingQuantity() {
        return servingQuantity;
    }

    /**
     * Returns any notes associated with this food log entry
     * @return  A String of this entry's notes
     */
    public String getEntryNotes() {
        return entryNotes;
    }
}
