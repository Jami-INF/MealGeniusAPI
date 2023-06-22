package entity;

public class IngredientEntity {
    private String id;
    private String idFood;

    private String unit;

    private double quantity;

    public IngredientEntity(String id, String idFood, String unit, double quantity) {
        this.id = id;
        this.idFood = idFood;
        this.unit = unit;
        this.quantity = quantity;
    }

    public IngredientEntity() {
    }

    public String getId() {
        return id;
    }

    public String getIdFood() {
        return idFood;
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

    public void setIdFood(String idFood) {
        this.idFood = idFood;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }


}
