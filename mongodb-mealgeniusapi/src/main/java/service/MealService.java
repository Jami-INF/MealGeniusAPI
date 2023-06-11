package service;

import dto.MealDTO;
import entity.MealEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.ws.rs.core.Response;
import mapper.MealMapper;
import org.bson.Document;
import org.bson.types.ObjectId;
import repository.MealRepository;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Default
public class MealService {
    private MealRepository mealRepository;
    private MealMapper mealMapper;

    public MealService() {
        this.mealRepository = new MealRepository();
        this.mealMapper = new MealMapper();
    }

    public MealDTO getMealById(String id) {
        Document document = new Document("_id", new ObjectId(id));
        MealEntity entity = mealRepository.find(document);

        if (entity != null)
            return MealMapper.entityToDTO(entity);

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
        meal.setId(new ObjectId().toHexString());
        Boolean result = mealRepository.add(MealMapper.entityToDocument(meal));
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
