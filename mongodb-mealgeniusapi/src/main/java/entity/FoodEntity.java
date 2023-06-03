package entity;

public class FoodEntity {
    private String id;
    private String name;

    public FoodEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public FoodEntity() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
