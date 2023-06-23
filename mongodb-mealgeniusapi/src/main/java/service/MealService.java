package service;

import dto.FoodDTO;
import dto.IngredientDTO;
import dto.MealDTO;
import dto.UserDTO;
import entity.FoodEntity;
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
import repository.FoodRepository;
import repository.MealRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static mapper.FoodMapper.DTOToEntity;

@ApplicationScoped
@Default
public class MealService {
    private MealRepository mealRepository;
    private FoodService foodService;

    public MealService() {
        this.mealRepository = new MealRepository();
        this.foodService = new FoodService();
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
        Document docId = new Document("_id", new ObjectId(meal.getId()));
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

    public List<MealDTO> getAvailableMeals(String idUser) {
        List<MealDTO> mealDTOs = new ArrayList<>();
        List<MealEntity> mealEntities = mealRepository.getAvailableMeals(idUser);
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

    public Response addIngredient(String idMeal, IngredientDTO ingredientDTO) {
        FoodDTO foodDTO = ingredientDTO.getFood();
        if(foodDTO.getId() == null){
            foodDTO.setId(new ObjectId().toHexString());
            foodService.addFood(DTOToEntity(foodDTO));
            ingredientDTO.setFood(foodDTO);
        }else if(foodService.getFoodEntityById(foodDTO.getId()) == null){
            foodService.addFood(DTOToEntity(foodDTO));
        }
        IngredientEntity ingredientEntity = IngredientMapper.DTOToEntity(ingredientDTO);
        MealEntity mealEntity = getMealEntityById(idMeal);
        mealEntity.addIngredient(ingredientEntity);
        return updateMeal(mealEntity);


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
