package controller;

import dto.IngredientDTO;
import dto.MealDTO;
import entity.IngredientEntity;
import entity.MealEntity;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import mapper.IngredientMapper;
import mapper.MealMapper;
import service.MealService;

import java.util.ArrayList;
import java.util.List;

@Path("/api/meals")
public class MealController {
    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @GET
    @Path("/")
    public List<MealDTO> getMeals() {
        List<MealDTO> mealDTOs = new ArrayList<>();
        mealDTOs.addAll(mealService.getAll());
        return mealDTOs;
    }

    @GET
    @Path("/{id}")
    public MealDTO getMeal (@PathParam("id") String id) {
        return mealService.getMealById(id);
    }

    @PUT
    @Path("/{id}")
    public Response updateMeal (@PathParam("id") String id, MealDTO meal) {
        MealEntity entity = MealMapper.DTOToEntity(meal);
        return mealService.updateMeal(entity);
    }

    @POST
    @Path("/{id}/addnewingredient")
    public Response addnewingredient(@PathParam("id") String id_meal, IngredientDTO ingredientDTO) {
        IngredientEntity ingredientEntity = IngredientMapper.DTOToEntity(ingredientDTO);
        MealEntity mealEntity = mealService.getMealEntityById(id_meal);
        mealEntity.addIngredient(ingredientEntity);
        return mealService.updateMeal(mealEntity);
        //return mealService.addNewIngredient(id_meal, ingredientEntity);
    }

    @POST
    @Path("/{id}/addingredient")
    public Response addIngredient(@PathParam("id") String id_meal, String id_ingredient) {
        return mealService.addIngredient(id_meal, id_ingredient);
    }

    @POST
    @Path("/addmeal")
    public Response addMeal(MealDTO mealDTO) {
        MealEntity mealEntity = MealMapper.DTOToEntity(mealDTO);
        System.out.println("efvtgrt"+mealEntity.getName());
        return mealService.addMeal(mealEntity);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteMeal(@PathParam("id") String id) {
        return mealService.deleteMeal(id);
    }
}
