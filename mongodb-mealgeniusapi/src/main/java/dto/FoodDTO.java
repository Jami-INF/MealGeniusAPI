package dto;

public class FoodDTO {
    private String id;
    private String name;

    public FoodDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public FoodDTO() {
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
