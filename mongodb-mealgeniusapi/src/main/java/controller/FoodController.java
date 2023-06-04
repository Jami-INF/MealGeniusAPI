package controller;

import dto.FoodDTO;
import entity.FoodEntity;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mapper.FoodMapper;
import repository.FoodRepository;
import java.util.ArrayList;
import java.util.List;

@Path("/api/foods")
public class FoodController {
    private final FoodRepository foodRepository;
    private FoodMapper foodMapper;

    public FoodController(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
        this.foodMapper = new FoodMapper();
    }

    @GET
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<FoodEntity> getFood() {
        List<FoodEntity> foodEntities = new ArrayList<FoodEntity>();
        foodEntities.addAll(foodRepository.listAll());
        return foodEntities;
    }

    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public FoodDTO getFood (@PathParam("id") String id) {
        return FoodMapper.foodToFoodDTO(foodRepository.findById(id));
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateFood (@PathParam("id") String id, FoodDTO food) {
        foodRepository.update(FoodMapper.foodDTOToFood(food));
    }

    @POST
    @Path("/addfood")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addFood(FoodDTO foodDTO) {
        foodDTO.setId(new ObjectId().toHexString());
        FoodEntity foodEntity = FoodMapper.foodDTOToFood(foodDTO);
        foodRepository.addFood(foodEntity);
        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteFood(@PathParam("id") String id) {
        FoodEntity foodEntity = foodRepository.findById(id);
        foodRepository.delete(foodEntity);
    }

    @POST
    @Path("/initdb")
    public void initDB() {
        foodRepository.initDB();
    }

}
