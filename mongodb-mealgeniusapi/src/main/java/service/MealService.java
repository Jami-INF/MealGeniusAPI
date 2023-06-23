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
    private final MealRepository mealRepository;
    private final FoodService foodService;

    public MealService() {
        this.mealRepository = new MealRepository();
        this.foodService = new FoodService();
    }

    /**
     * Get all meals
     * @param id the id of the user
     * @return List of MealDTO
     */
    public MealDTO getMealById(String id) {
        Document document = new Document("_id", new ObjectId(id));
        MealEntity entity = mealRepository.find(document);
        if (entity != null)
            return MealMapper.entityToDTO(entity);
        return null;
    }

    /**
     * Get a meal by name
     * @param name the name of the meal
     * @return MealDTO
     */
    public MealDTO getMealByName(String name) {
        Document document = new Document("name", name);
        MealEntity entity = mealRepository.find(document);
        if (entity != null)
            return MealMapper.entityToDTO(entity);
        return null;
    }

    /**
     * Get all meals that contains a name
     * @param name the name of the meal
     * @return List of MealDTO
     */
    public List<MealDTO> getMealsContainsName(String name) {
        List<MealDTO> mealDTOs = new ArrayList<>();
        List<MealEntity> mealEntities = mealRepository.getMealsContainsName(name);
        for (MealEntity mealEntity : mealEntities) {
            mealDTOs.add(MealMapper.entityToDTO(mealEntity));
        }
        return mealDTOs;
    }

    /**
     * Get a mealEntity by id
     * @param id the id of the meal
     * @return MealEntity
     */
    public MealEntity getMealEntityById(String id) {
        Document document = new Document("_id", new ObjectId(id));
        return mealRepository.find(document);
    }

    /**
     * Update a meal
     * @param meal the meal to update
     * @return Response
     */
    public Response updateMeal(MealEntity meal) {
        Document doc = new Document("$set", MealMapper.entityToDocument(meal));
        Document docId = new Document("_id", new ObjectId(meal.getId()));
        Boolean result =  mealRepository.update(docId, doc);

        return getResponse(result);
    }

    /**
     * Get all meals
     * @return List of MealDTO
     */
    public List<MealDTO> getAll() {
        List<MealDTO> mealDTOs = new ArrayList<>();
        List<MealEntity> mealEntities = mealRepository.getAll();
        for (MealEntity mealEntity : mealEntities) {
            mealDTOs.add(MealMapper.entityToDTO(mealEntity));
        }
        return mealDTOs;
    }

    /**
     * Get all meals available for a user
     * @param idUser the id of the user
     * @return List of MealDTO
     */
    public List<MealDTO> getAvailableMeals(String idUser) {
        List<MealDTO> mealDTOs = new ArrayList<>();
        List<MealEntity> mealEntities = mealRepository.getAvailableMeals(idUser);
        for (MealEntity mealEntity : mealEntities) {
            mealDTOs.add(MealMapper.entityToDTO(mealEntity));
        }
        return mealDTOs;
    }

    /**
     * Add a meal
     * @param meal the meal to add
     * @return Response
     */
    public Response addMeal(MealEntity meal) {
        //meal.setId(new ObjectId().toHexString());
        Boolean result = mealRepository.add(MealMapper.entityToDocument(meal));
        return getResponse(result);
    }

    /**
     * Add ingredient to a meal
     * @param idMeal the id of the meal
     * @param ingredientDTO the ingredient to add
     * @return Response
     */
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

    /**
     * Delete a meal
     * @param id the id of the meal to delete
     * @return Response
     */
    public Response deleteMeal(String id) {
        Document document = new Document("_id", new ObjectId(id));
        Boolean result = mealRepository.delete(document);
        return getResponse(result);
    }

    /**
     * Get the response
     * @param result the result of the request
     * @return Response
     */
    private Response getResponse(Boolean result) {
        Response.Status response = Boolean.TRUE.equals(result) ? Response.Status.OK : Response.Status.NOT_FOUND;
        return Response.status(response).build();
    }


}
