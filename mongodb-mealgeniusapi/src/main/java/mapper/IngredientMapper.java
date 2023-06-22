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

        FoodDTO foodDTO = ingredientDTO.getFood();
        // Vérifie si l'id du food est null
        if (foodDTO.getId() == null) {
            // Si elle est null, crée le food dans la base de données
            FoodEntity foodEntity = FoodMapper.DTOToEntity(foodDTO);
            foodService.addFood(foodEntity);
            ingredientEntity.setIdFood(foodEntity.getId());
        } else {
            // Sinon, récupère le food dans la base de données
            FoodEntity foodEntity = foodService.getFoodEntityById(foodDTO.getId());
            if (foodEntity != null) {
                ingredientEntity.setIdFood(foodEntity.getId());
            } else {
                // Si le food n'existe pas, crée le food dans la base de données
                foodEntity = FoodMapper.DTOToEntity(foodDTO);
                foodService.addFood(foodEntity);
                ingredientEntity.setIdFood(foodEntity.getId());
            }
        }
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
        String foodId = ingredientDocument.get("id_food").toString();
        ingredientEntity.setIdFood(foodService.getFoodEntityById(foodId).getId());

        ingredientEntity.setQuantity(ingredientDocument.getDouble("quantity"));
        ingredientEntity.setUnit(ingredientDocument.getString("unit"));
        return ingredientEntity;
    }
}
