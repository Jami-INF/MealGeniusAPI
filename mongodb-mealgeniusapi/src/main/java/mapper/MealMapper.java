package mapper;

import dto.IngredientDTO;
import dto.MealDTO;
import dto.StepDTO;
import entity.IngredientEntity;
import entity.MealEntity;
import entity.StepEntity;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public abstract class MealMapper {

    public static MealDTO entityToDTO(MealEntity mealEntity){
        MealDTO mealDTO = new MealDTO();
        mealDTO.setId(mealEntity.getId());
        mealDTO.setName(mealEntity.getName());
        mealDTO.setDescription(mealEntity.getDescription());
        mealDTO.setImage(mealEntity.getImage());
        mealDTO.setDuration(mealEntity.getDuration());
        List<IngredientDTO> ingredientDTOList = new ArrayList<>();
        mealEntity.getIngredients().forEach(ingredientEntity -> {
            ingredientDTOList.add(IngredientMapper.entityToDTO(ingredientEntity));
        });
        mealDTO.setIngredients(ingredientDTOList);
        List<StepDTO> stepEntityDTOList = new ArrayList<>();
        mealEntity.getSteps().forEach(stepEntity -> {
            stepEntityDTOList.add(StepMapper.entityToDTO(stepEntity));
        });
        mealDTO.setSteps(stepEntityDTOList);

        return mealDTO;
    }

    public static MealEntity DTOToEntity(MealDTO mealDTO){
        MealEntity mealEntity = new MealEntity();
        if(mealDTO.getId() == null){
            mealEntity.setId(new ObjectId().toHexString());
        }else{
            mealEntity.setId(mealDTO.getId());
        }
        mealEntity.setName(mealDTO.getName());
        mealEntity.setDescription(mealDTO.getDescription());
        mealEntity.setImage(mealDTO.getImage());
        mealEntity.setDuration(mealDTO.getDuration());
        List<IngredientEntity> ingredients = new ArrayList<>();
        mealDTO.getIngredients().forEach(ingredientDTO -> {
            if(ingredientDTO.getId() == null){
                ingredientDTO.setId(new ObjectId().toHexString());
            }else{
                ingredientDTO.setId(ingredientDTO.getId());
            }
            ingredients.add(IngredientMapper.DTOToEntity(ingredientDTO));
        });

        mealEntity.setIngredients(ingredients);
        List<StepEntity> steps = new ArrayList<>();
        mealDTO.getSteps().forEach(stepDTO -> {
            if(stepDTO.getId() == null){
                stepDTO.setId(new ObjectId().toHexString());
            }else{
                stepDTO.setId(stepDTO.getId());
            }
            steps.add(StepMapper.DTOToEntity(stepDTO));
        });
        mealEntity.setSteps(steps);

        return mealEntity;
    }

    public static Document entityToDocument(MealEntity mealEntity){
        Document mealDocument = new Document();
        mealDocument.append("_id", mealEntity.getId());
        mealDocument.append("name", mealEntity.getName());
        mealDocument.append("description", mealEntity.getDescription());
        mealDocument.append("image", mealEntity.getImage());
        mealDocument.append("duration", mealEntity.getDuration());
        List<Document> ingredients = new ArrayList<>();
        for (int i = 0; i < mealEntity.getIngredients().size(); i++) {
            ingredients.add(IngredientMapper.entityToDocument(mealEntity.getIngredients().get(i)));
        }
        mealDocument.append("ingredients", ingredients);
        List<Document> steps = new ArrayList<>();
        for (int i = 0; i < mealEntity.getSteps().size(); i++) {
            steps.add(StepMapper.entityToDocument(mealEntity.getSteps().get(i)));
        }
        mealDocument.append("steps", steps);
        return mealDocument;
    }

    public static MealEntity documentToEntity(Document mealDocument) {
        MealEntity mealEntity = new MealEntity();
        Object id = mealDocument.get("_id");
        if (id != null) {
            mealEntity.setId(id.toString());
        }
        mealEntity.setName(mealDocument.getString("name"));
        mealEntity.setDescription(mealDocument.getString("description"));
        mealEntity.setImage(mealDocument.getString("image"));
        mealEntity.setDuration(mealDocument.getInteger("duration"));
        List<IngredientEntity> ingredients = new ArrayList<>();
        List<Document> ingredientDocuments = mealDocument.getList("ingredients", Document.class);
        if (ingredientDocuments != null) {
            for (Document ingredientDocument : ingredientDocuments) {
                ingredients.add(IngredientMapper.documentToEntity(ingredientDocument));
            }
        }
        mealEntity.setIngredients(ingredients);
        List<StepEntity> steps = new ArrayList<>();
        List<Document> stepDocuments = mealDocument.getList("steps", Document.class);
        if (stepDocuments != null) {
            for (Document stepDocument : stepDocuments) {
                steps.add(StepMapper.documentToEntity(stepDocument));
            }
        }
        mealEntity.setSteps(steps);
        return mealEntity;
    }

}
