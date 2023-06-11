package controller;

import dto.MealDTO;
import entity.MealEntity;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import mapper.MealMapper;
import service.MealService;

import java.util.ArrayList;
import java.util.List;


public class MealController {
    private final MealService mealService;
    private MealMapper mealMapper;

    public MealController(MealService mealService) {
        this.mealService = mealService;
        this.mealMapper = new MealMapper();
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
    @Path("/addmeal")
    public Response addMeal(MealDTO mealDTO) {
        MealEntity mealEntity = MealMapper.DTOToEntity(mealDTO);
        return mealService.addMeal(mealEntity);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteMeal(@PathParam("id") String id) {
        return mealService.deleteMeal(id);
    }
}
