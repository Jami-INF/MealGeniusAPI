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
    @PUT
    @Path("/{id}")
    public Response updateMeal (@PathParam("id") String id, MealDTO meal) {
        MealEntity entity = MealMapper.DTOToEntity(meal);
        return mealService.updateMeal(entity);
    }

    @POST
    @Path("/{id}/addingredient")
    public Response addingredient(@PathParam("id") String id_meal, IngredientDTO ingredientDTO) {

        return mealService.addIngredient(id_meal, ingredientDTO);

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
