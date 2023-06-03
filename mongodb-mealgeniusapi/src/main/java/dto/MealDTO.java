package dto;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class MealDTO {
    private String id;
    private String name;
    private String description;
    private Base64 image;
    private int duration;
    private List<IngredientDTO> ingredients = new ArrayList<>();

    private List<StepDTO> steps = new ArrayList<>();

    public MealDTO(String id, String name, String description, Base64 image, int duration, List<IngredientDTO> ingredients) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.duration = duration;
        this.ingredients = ingredients;
    }

    public MealDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Base64 getImage() {
        return image;
    }

    public void setImage(Base64 image) {
        this.image = image;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<IngredientDTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientDTO> ingredients) {
        this.ingredients = ingredients;
    }
}