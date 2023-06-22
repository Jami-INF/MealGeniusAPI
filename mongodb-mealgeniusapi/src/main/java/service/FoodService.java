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

    public FoodDTO getFoodById(String id) {
        Document document = new Document("_id", new ObjectId(id));
        FoodEntity entity = foodRepository.find(document);

        if (entity != null)
            return FoodMapper.entityToDTO(entity);

        return null;
    }

    public FoodEntity getFoodEntityById(String id) {
        Document document = new Document("_id", new ObjectId(id));
        FoodEntity entity = foodRepository.find(document);
        return entity;
    }

    public Response updateFood(FoodEntity food) {
        Document doc = new Document("$set", FoodMapper.entityToDocument(food));
        Document docId = new Document("_id", food.getId());
        Boolean result =  foodRepository.update(docId, doc);

        return getResponse(result);
    }

    public List<FoodDTO> getAll(){
        List<FoodDTO> foodDTOs = new ArrayList<>();
        List<FoodEntity> foodEntities = foodRepository.getAll();
        for (FoodEntity foodEntity : foodEntities) {
            foodDTOs.add(FoodMapper.entityToDTO(foodEntity));
        }
        return foodDTOs;
    }

    public Response addFood(FoodEntity food) {
        //food.setId(new ObjectId().toHexString());
        Boolean result = foodRepository.add(FoodMapper.entityToDocument(food));
        return getResponse(result);
    }

    public Response deleteFood(String id) {
        Document document = new Document("_id", id);
        Boolean result = foodRepository.delete(document);
        return getResponse(result);
    }

    private Response getResponse(Boolean result) {
        Response.Status response = Boolean.TRUE.equals(result) ? Response.Status.OK : Response.Status.NOT_FOUND;
        return Response.status(response).build();
    }
}
