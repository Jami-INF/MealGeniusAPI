package mapper;

import dto.FoodDTO;
import dto.IngredientDTO;
import entity.FoodEntity;
import entity.IngredientEntity;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.Document;
import repository.FoodRepository;
import service.FoodService;

import java.awt.desktop.SystemEventListener;

public abstract class IngredientMapper {

    private static FoodService foodService = new FoodService();
    public static IngredientDTO entityToDTO(IngredientEntity ingredientEntity) {
        IngredientDTO ingredientDTO = new dto.IngredientDTO();
        ingredientDTO.setId(ingredientEntity.getId());
        ingredientDTO.setFood(FoodMapper.entityToDTO(foodService.getFoodEntityById(ingredientEntity.getFood())));
        ingredientDTO.setQuantity(ingredientEntity.getQuantity());
        ingredientDTO.setUnit(ingredientEntity.getUnit());
        return ingredientDTO;
    }

    public static IngredientEntity DTOToEntity(IngredientDTO ingredientDTO) {
        IngredientEntity ingredientEntity = new entity.IngredientEntity();
        if (ingredientDTO.getId() == null) {
            ingredientEntity.setId(new org.bson.types.ObjectId().toHexString());
        }else{
            ingredientEntity.setId(ingredientDTO.getId());
        }
        FoodDTO foodDTO = ingredientDTO.getFood();
        //vérifie si l'id est null
        if (foodDTO.getId() == null) {
            //si elle est nul, crée le food dans la base de donnée
            FoodEntity foodEntity = FoodMapper.DTOToEntity(foodDTO);
            foodService.addFood(foodEntity);
            ingredientEntity.setFood(foodEntity.getId());
        }else{
            //sinon, récupère le food dans la base de donnée
            FoodEntity foodEntity = foodService.getFoodEntityById(foodDTO.getId());
            ingredientEntity.setFood(foodEntity.getId());
        }
        ingredientEntity.setQuantity(ingredientDTO.getQuantity());
        ingredientEntity.setUnit(ingredientDTO.getUnit());
        return ingredientEntity;
    }

    public static Document entityToDocument(IngredientEntity ingredientEntity) {
        Document ingredientDocument = new Document();
        ingredientDocument.append("_id", ingredientEntity.getId());
        ingredientDocument.append("id_food", ingredientEntity.getFood());
        ingredientDocument.append("quantity", ingredientEntity.getQuantity());
        ingredientDocument.append("unit", ingredientEntity.getUnit());
        return ingredientDocument;
    }
    public static IngredientEntity documentToEntity(Document ingredientDocument) {
        IngredientEntity ingredientEntity = new IngredientEntity();
        Object id = ingredientDocument.get("_id");
        if (id != null) {
            ingredientEntity.setId(id.toString());
        }
        //FoodEntity foodEntity = FoodMapper.documentToEntity(ingredientDocument.get("food", Document.class));
        String foodId = ingredientDocument.get("id_food").toString();
        ingredientEntity.setFood(foodService.getFoodEntityById(foodId).getId());

        ingredientEntity.setQuantity(ingredientDocument.getDouble("quantity"));
        ingredientEntity.setUnit(ingredientDocument.getString("unit"));
        return ingredientEntity;
    }
}
