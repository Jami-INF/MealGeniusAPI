package controller;

import dto.FoodDTO;
import entity.FoodEntity;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mapper.FoodMapper;
import org.bson.types.ObjectId;
import repository.FoodRepository;
import service.FoodService;

import java.util.ArrayList;
import java.util.List;

@Path("/api/foods")
public class FoodController {
    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GET
    @Path("/")
    public List<FoodDTO> getFood() {
        List<FoodDTO> foodDTOs = new ArrayList<>();
        foodDTOs.addAll(foodService.getAll());
        return foodDTOs;
    }

    @GET
    @Path("/{id}")
    public FoodDTO getFood (@PathParam("id") String id) {
        return foodService.getFoodById(id);
    }

    @PUT
    @Path("/{id}")
    public Response updateFood (@PathParam("id") String id, FoodDTO food) {
        FoodEntity entity = FoodMapper.DTOToEntity(food);
        return foodService.updateFood(entity);
    }

    @POST
    @Path("/addfood")
    public Response addFood(FoodDTO foodDTO) {
        FoodEntity foodEntity = FoodMapper.DTOToEntity(foodDTO);
        return foodService.addFood(foodEntity);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteFood(@PathParam("id") String id) {
        return foodService.deleteFood(id);
    }
}
