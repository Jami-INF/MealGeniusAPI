package service;

import dto.FoodDTO;
import entity.FoodEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.ws.rs.core.Response;
import mapper.FoodMapper;
import mapper.UserMapper;
import org.bson.Document;
import org.bson.types.ObjectId;
import repository.FoodRepository;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Default
public class FoodService {
    private FoodRepository foodRepository;


    public FoodService() {
        this.foodRepository = new FoodRepository();
    }

    /**
     * Get food by id
     * @param id food id
     * @return  food
     */
    public FoodDTO getFoodById(String id) {
        Document document = new Document("_id", new ObjectId(id));
        FoodEntity entity = foodRepository.find(document);

        if (entity != null)
            return FoodMapper.entityToDTO(entity);

        return null;
    }

    /**
     * Get food by id
     * @param id food id
     * @return food
     */
    public FoodEntity getFoodEntityById(String id) {
        Document document = new Document("_id", new ObjectId(id));
        FoodEntity entity = foodRepository.find(document);
        return entity;
    }

    /**
     * Update food
     * @param food food
     * @return response
     */
    public Response updateFood(FoodEntity food) {
        Document doc = new Document("$set", FoodMapper.entityToDocument(food));
        Document docId = new Document("_id", new ObjectId(food.getId()));
        Boolean result =  foodRepository.update(docId, doc);

        return getResponse(result);
    }

    /**
     * Get all foods
     * @return list of foods
     */
    public List<FoodDTO> getAll(){
        List<FoodDTO> foodDTOs = new ArrayList<>();
        List<FoodEntity> foodEntities = foodRepository.getAll();
        for (FoodEntity foodEntity : foodEntities) {
            foodDTOs.add(FoodMapper.entityToDTO(foodEntity));
        }
        return foodDTOs;
    }

    /**
     * Add food
     * @param food food
     * @return response
     */
    public Response addFood(FoodEntity food) {
        //food.setId(new ObjectId().toHexString());
        if(food.getId() == null)
            food.setId(new ObjectId().toHexString());
        Boolean result = foodRepository.add(FoodMapper.entityToDocument(food));
        return getResponse(result);
    }

    /**
     * Delete food
     * @param id food id
     * @return response
     */
    public Response deleteFood(String id) {
        Document document = new Document("_id", id);
        Boolean result = foodRepository.delete(document);
        return getResponse(result);
    }

    /**
     * Get response
     * @param result result
     * @return response
     */
    private Response getResponse(Boolean result) {
        Response.Status response = Boolean.TRUE.equals(result) ? Response.Status.OK : Response.Status.NOT_FOUND;
        return Response.status(response).build();
    }
}
