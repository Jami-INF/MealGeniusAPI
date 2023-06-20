package service;

import dto.MealDTO;
import dto.UserDTO;
import entity.IngredientEntity;
import entity.MealEntity;
import entity.UserEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.ws.rs.core.Response;
import mapper.IngredientMapper;
import mapper.MealMapper;
import mapper.UserMapper;
import org.bson.Document;
import org.bson.types.ObjectId;
import repository.MealRepository;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Default
public class MealService {
    private MealRepository mealRepository;

    public MealService() {
        this.mealRepository = new MealRepository();
    }

    public MealDTO getMealById(String id) {
        Document document = new Document("_id", new ObjectId(id));
        MealEntity entity = mealRepository.find(document);
        if (entity != null)
            return MealMapper.entityToDTO(entity);
        return null;
    }

    public MealEntity getMealEntityById(String id) {
        Document document = new Document("_id", new ObjectId(id));
        MealEntity entity = mealRepository.find(document);

        if (entity != null)
            return entity;

        return null;
    }

    public Response updateMeal(MealEntity meal) {
        Document doc = new Document("$set", MealMapper.entityToDocument(meal));
        Document docId = new Document("_id", meal.getId());
        Boolean result =  mealRepository.update(docId, doc);

        return getResponse(result);
    }

    public List<MealDTO> getAll() {
        List<MealDTO> mealDTOs = new ArrayList<>();
        List<MealEntity> mealEntities = mealRepository.getAll();
        for (MealEntity mealEntity : mealEntities) {
            mealDTOs.add(MealMapper.entityToDTO(mealEntity));
        }
        return mealDTOs;
    }

    public Response addMeal(MealEntity meal) {
        //meal.setId(new ObjectId().toHexString());
        Boolean result = mealRepository.add(MealMapper.entityToDocument(meal));
        return getResponse(result);
    }

//    public Response addNewIngredient(String id, IngredientEntity ingredientEntity) {
//        Document doc = new Document("$set", IngredientMapper.entityToDocument(ingredientEntity));
//        System.out.println("efferf"+doc);
//        Document docId = new Document("_id", new ObjectId(id));
//        Boolean result = mealRepository.update(docId, doc);
//        return getResponse(result);
//    }

    public Response addIngredient(String id, String id_ingredient) {
        Document doc = new Document("$push", new Document("ingredients", new Document("_id", new ObjectId(id_ingredient))));
        Document docId = new Document("_id", new ObjectId(id));
        Boolean result = mealRepository.update(docId, doc);
        return getResponse(result);
    }

    public Response deleteMeal(String id) {
        Document document = new Document("_id", new ObjectId(id));
        Boolean result = mealRepository.delete(document);
        return getResponse(result);
    }

    private Response getResponse(Boolean result) {
        Response.Status response = Boolean.TRUE.equals(result) ? Response.Status.OK : Response.Status.NOT_FOUND;
        return Response.status(response).build();
    }
}
