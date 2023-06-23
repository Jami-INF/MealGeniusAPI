package mapper;

import dto.FoodDTO;
import dto.IngredientDTO;
import entity.FoodEntity;
import entity.IngredientEntity;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.Document;
import org.bson.types.ObjectId;
import repository.FoodRepository;
import service.FoodService;

import java.awt.desktop.SystemEventListener;

public abstract class IngredientMapper {

    private static FoodService foodService = new FoodService();
    public static IngredientDTO entityToDTO(IngredientEntity ingredientEntity) {
        IngredientDTO ingredientDTO = new dto.IngredientDTO();
        ingredientDTO.setId(ingredientEntity.getId());

        ingredientDTO.setFood(FoodMapper.entityToDTO(foodService.getFoodEntityById(ingredientEntity.getIdFood())));
        ingredientDTO.setQuantity(ingredientEntity.getQuantity());
        ingredientDTO.setUnit(ingredientEntity.getUnit());
        return ingredientDTO;
    }

    public static IngredientEntity DTOToEntity(IngredientDTO ingredientDTO) {
        IngredientEntity ingredientEntity = new IngredientEntity();
        if (ingredientDTO.getId() == null) {
            ingredientEntity.setId(new ObjectId().toHexString());
        } else {
            ingredientEntity.setId(ingredientDTO.getId());
        }
        ingredientEntity.setIdFood(FoodMapper.DTOToEntity(ingredientDTO.getFood()).getId());
        ingredientEntity.setQuantity(ingredientDTO.getQuantity());
        ingredientEntity.setUnit(ingredientDTO.getUnit());
        return ingredientEntity;
    }


    public static Document entityToDocument(IngredientEntity ingredientEntity) {
        Document ingredientDocument = new Document();
        ingredientDocument.append("_id", new ObjectId(ingredientEntity.getId()));
        ingredientDocument.append("id_food", ingredientEntity.getIdFood());
        ingredientDocument.append("quantity", ingredientEntity.getQuantity());
        ingredientDocument.append("unit", ingredientEntity.getUnit());
        return ingredientDocument;
    }
    public static IngredientEntity documentToEntity(Document ingredientDocument) {
        IngredientEntity ingredientEntity = new IngredientEntity();
        ObjectId id = ingredientDocument.getObjectId("_id");
        if (id != null) {
            ingredientEntity.setId(id.toHexString());
        }
        //FoodEntity foodEntity = FoodMapper.documentToEntity(ingredientDocument.get("food", Document.class));
        ingredientEntity.setIdFood(ingredientDocument.get("id_food").toString());
        ingredientEntity.setQuantity(ingredientDocument.getDouble("quantity"));
        ingredientEntity.setUnit(ingredientDocument.getString("unit"));
        return ingredientEntity;
    }
}
