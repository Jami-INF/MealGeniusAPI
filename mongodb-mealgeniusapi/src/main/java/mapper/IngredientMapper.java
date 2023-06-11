package mapper;

import dto.IngredientDTO;
import entity.IngredientEntity;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.Document;

@ApplicationScoped
public class IngredientMapper {

    private FoodMapper foodMapper = new FoodMapper();
    public static IngredientDTO entityToDTO(entity.IngredientEntity ingredientEntity) {
        IngredientDTO ingredientDTO = new dto.IngredientDTO();
        ingredientDTO.setId(ingredientEntity.getId());
        ingredientDTO.setFood(FoodMapper.entityToDTO(ingredientEntity.getFood()));
        ingredientDTO.setQuantity(ingredientEntity.getQuantity());
        ingredientDTO.setUnit(ingredientEntity.getUnit());
        return ingredientDTO;
    }

    public static IngredientEntity DTOToEntity(dto.IngredientDTO ingredientDTO) {
        IngredientEntity ingredientEntity = new entity.IngredientEntity();
        ingredientEntity.setId(ingredientDTO.getId());
        ingredientEntity.setFood(FoodMapper.DTOToEntity(ingredientDTO.getFood()));
        ingredientEntity.setQuantity(ingredientDTO.getQuantity());
        ingredientEntity.setUnit(ingredientDTO.getUnit());
        return ingredientEntity;
    }

    public static Document entityToDocument(entity.IngredientEntity ingredientEntity) {
        Document ingredientDocument = new Document();
        ingredientDocument.append("id", ingredientEntity.getId());
        ingredientDocument.append("food", FoodMapper.entityToDocument(ingredientEntity.getFood()));
        ingredientDocument.append("quantity", ingredientEntity.getQuantity());
        ingredientDocument.append("unit", ingredientEntity.getUnit());
        return ingredientDocument;
    }

    public static entity.IngredientEntity documentToEntity(Document ingredientDocument) {
        entity.IngredientEntity ingredientEntity = new entity.IngredientEntity();
        ingredientEntity.setId(ingredientDocument.getObjectId("_id").toHexString());
        ingredientEntity.setFood(FoodMapper.documentToEntity((Document) ingredientDocument.get("food")));
        ingredientEntity.setQuantity(ingredientDocument.getDouble("quantity"));
        ingredientEntity.setUnit(ingredientDocument.getString("unit"));
        return ingredientEntity;
    }
}
