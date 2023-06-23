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

    /**
     * Get all meals
     * @return List of MealDTO
     */
    @GET
    @Path("/")
    public List<MealDTO> getMeals() {
        List<MealDTO> mealDTOs = new ArrayList<>();
        mealDTOs.addAll(mealService.getAll());
        return mealDTOs;
    }

    /**
     * Get a meal by id
     * @param id
     * @return
     */
    @GET
    @Path("/{id}")
    public MealDTO getMeal (@PathParam("id") String id) {
        return mealService.getMealById(id);
    }

    /**
     * Get a meal by name
     * @param name
     * @return
     */
    @GET
    @Path("/get/{name}")
    public MealDTO getMealByName (@PathParam("name") String name) {
        return mealService.getMealByName(name);
    }

    /**
     * Get all meals that contains a name
     * @param name
     * @return
     */
    @GET
    @Path("/search/meals/{name}")
    public List<MealDTO> getMealsContainsName (@PathParam("name") String name) {
        List<MealDTO> mealDTOs = new ArrayList<>();
        mealDTOs.addAll(mealService.getMealsContainsName(name));
        return mealDTOs;
    }

    /**
     * Get all meals that are available for a user
     * @param idUser
     * @return List of MealDTO
     */
    @GET
    @Path("/users/{id}")
    public List<MealDTO> getAvailableMeals (@PathParam("id") String idUser) {
        List<MealDTO> mealDTOs = new ArrayList<>();
        mealDTOs.addAll(mealService.getAvailableMeals(idUser));
        return mealDTOs;
    }

    /**
     * Get all meals that are not available for a user
     * @param id
     * @param meal
     * @return
     */
    @PUT
    @Path("/{id}")
    public Response updateMeal (@PathParam("id") String id, MealDTO meal) {
        MealEntity entity = MealMapper.DTOToEntity(meal);
        return mealService.updateMeal(entity);
    }

    /**
     * Add an ingredient to a meal
     * @param id_meal
     * @param ingredientDTO
     * @return
     */
    @POST
    @Path("/{id}/addingredient")
    public Response addingredient(@PathParam("id") String id_meal, IngredientDTO ingredientDTO) {

        return mealService.addIngredient(id_meal, ingredientDTO);

    }

    /**
     * Add a meal
     * @param mealDTO
     * @return
     */
    @POST
    @Path("/addmeal")
    public Response addMeal(MealDTO mealDTO) {
        MealEntity mealEntity = MealMapper.DTOToEntity(mealDTO);
        return mealService.addMeal(mealEntity);
    }

    /**
     * Delete a meal
     * @param id
     * @return
     */
    @DELETE
    @Path("/{id}")
    public Response deleteMeal(@PathParam("id") String id) {
        return mealService.deleteMeal(id);
    }
}
