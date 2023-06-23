package entity;

import java.util.ArrayList;
import java.util.List;

public class UserEntity {
    public String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<FoodEntity> foods = new ArrayList<>();

    public UserEntity(String id, String firstName, String lastName, String email, String password, List<FoodEntity> foods) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.foods = foods;
    }

    public UserEntity() {
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<FoodEntity> getFoods() {
        return foods;
    }

    public void setFoods(List<FoodEntity> foods) {
        this.foods = foods;
    }

    public void addFood(FoodEntity food) {
        this.foods.add(food);
    }
}
