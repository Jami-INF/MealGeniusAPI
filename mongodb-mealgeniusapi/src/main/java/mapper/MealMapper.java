package mapper;

import dto.IngredientDTO;
import dto.MealDTO;
import entity.IngredientEntity;
import entity.MealEntity;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@ApplicationScoped
public class MealMapper {

    private static IngredientMapper ingredientMapper = new IngredientMapper();
    public static MealDTO entityToDTO(MealEntity mealEntity){
        MealDTO mealDTO = new MealDTO();
        mealDTO.setId(mealEntity.getId());
        mealDTO.setName(mealEntity.getName());
        mealDTO.setDescription(mealEntity.getDescription());
        mealDTO.setImage(mealEntity.getImage());
        mealDTO.setDuration(mealEntity.getDuration());
        List<IngredientDTO> ingredientDTOList = new ArrayList<>();
        for (int i = 0; i < mealEntity.getIngredients().size(); i++) {
            ingredientDTOList.add(ingredientMapper.entityToDTO(mealEntity.getIngredients().get(i)));
        }
        mealDTO.setIngredients(ingredientDTOList);
        return mealDTO;
    }

    public static MealEntity DTOToEntity(MealDTO mealDTO){
        MealEntity mealEntity = new MealEntity();
        mealEntity.setId(mealDTO.getId());
        mealEntity.setName(mealDTO.getName());
        mealEntity.setDescription(mealDTO.getDescription());
        mealEntity.setImage(mealDTO.getImage());
        mealEntity.setDuration(mealDTO.getDuration());
        List<IngredientEntity> ingredients = new ArrayList<>();
        for (int i = 0; i < mealDTO.getIngredients().size(); i++) {
            ingredients.add(ingredientMapper.DTOToEntity(mealDTO.getIngredients().get(i)));
        }
        mealEntity.setIngredients(ingredients);

        return mealEntity;
    }

    public static Document entityToDocument(MealEntity mealEntity){
        Document mealDocument = new Document();
        mealDocument.append("id", mealEntity.getId());
        mealDocument.append("name", mealEntity.getName());
        mealDocument.append("description", mealEntity.getDescription());
        mealDocument.append("image", mealEntity.getImage());
        mealDocument.append("duration", mealEntity.getDuration());
        List<Document> ingredients = new ArrayList<>();
        for (int i = 0; i < mealEntity.getIngredients().size(); i++) {
            ingredients.add(ingredientMapper.entityToDocument(mealEntity.getIngredients().get(i)));
        }
        mealDocument.append("ingredients", ingredients);
        return mealDocument;

    }

    public static MealEntity documentToEntity(Document mealDocument){
        MealEntity mealEntity = new MealEntity();
        mealEntity.setId(mealDocument.getString("id"));
        mealEntity.setName(mealDocument.getString("name"));
        mealEntity.setDescription(mealDocument.getString("description"));
        mealEntity.setImage(mealDocument.get("image", Base64.class));
        mealEntity.setDuration(mealDocument.getInteger("duration"));
        List<IngredientEntity> ingredients = new ArrayList<>();
        for (int i = 0; i < mealDocument.getList("ingredients", Document.class).size(); i++) {
            ingredients.add(ingredientMapper.documentToEntity(mealDocument.getList("ingredients", Document.class).get(i)));
        }
        mealEntity.setIngredients(ingredients);
        return mealEntity;
    }
}
