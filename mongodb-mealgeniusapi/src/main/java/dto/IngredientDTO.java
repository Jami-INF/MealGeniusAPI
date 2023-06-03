package dto;

public class IngredientDTO {
    private String id;
    private FoodDTO food;

    private String unit;

    private double quantity;

    public IngredientDTO(String id, FoodDTO food, String unit, double quantity) {
        this.id = id;
        this.food = food;
        this.unit = unit;
        this.quantity = quantity;
    }

    public IngredientDTO() {
    }

    public String getId() {
        return id;
    }

    public FoodDTO getFood() {
        return food;
    }

    public String getUnit() {
        return unit;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFood(FoodDTO food) {
        this.food = food;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }


}
