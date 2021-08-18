/**
 * This class represents an entire row in the food log database, excluding the
 * entry ID. The purpose of this class is to store the data within individual
 * rows that are fetched from the food log database.
 */

public class FoodEntry {

    private String entryDate;
    private String foodName;
    private String mealType;
    private double servingQuantity;
    private String entryNotes;

    public FoodEntry(String entryDate, String foodName, String mealType, double servingQuantity, String entryNotes) {
        this.entryDate = entryDate;
        this.foodName = foodName;
        this.mealType = mealType;
        this.servingQuantity = servingQuantity;
        this.entryNotes = entryNotes;
    }

}
