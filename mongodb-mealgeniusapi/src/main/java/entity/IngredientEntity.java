package entity;

public class IngredientEntity {
    private String id;
    private String food;

    private String unit;

    private double quantity;

    public IngredientEntity(String id, String food, String unit, double quantity) {
        this.id = id;
        this.food = food;
        this.unit = unit;
        this.quantity = quantity;
    }

    public IngredientEntity() {
    }

    public String getId() {
        return id;
    }

    public String getFood() {
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

    public void setFood(String food) {
        this.food = food;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }


}
