package entity;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class MealEntity {
    private String id;
    private String name;
    private String description;
    private Base64 image;
    private int duration;
    private List<IngredientEntity> ingredients = new ArrayList<>();

    private List<StepEntity> steps = new ArrayList<>();

    public MealEntity(String id, String name, String description, Base64 image, int duration, List<IngredientEntity> ingredients, List<StepEntity> steps) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.duration = duration;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public MealEntity() {
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

    public List<IngredientEntity> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientEntity> ingredients) {
        this.ingredients = ingredients;
    }

    public List<StepEntity> getSteps() {
        return steps;
    }

    public void setSteps(List<StepEntity> steps) {
        this.steps = steps;
    }
}