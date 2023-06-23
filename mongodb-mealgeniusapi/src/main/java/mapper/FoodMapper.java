package mapper;

import dto.FoodDTO;
import jakarta.enterprise.context.ApplicationScoped;
import entity.FoodEntity;
import org.bson.Document;
import org.bson.types.ObjectId;
import service.FoodService;



public abstract class FoodMapper {

    private final FoodService foodService = new FoodService();
    public static FoodDTO entityToDTO(FoodEntity foodEntity){
        FoodDTO foodDTO = new FoodDTO();
        foodDTO.setId(foodEntity.getId());
        foodDTO.setName(foodEntity.getName());
        return foodDTO;
    }

    public static FoodEntity DTOToEntity(FoodDTO foodDTO){
        FoodEntity foodEntity = new FoodEntity();
        if(foodDTO.getId() == null){
            foodEntity.setId(new ObjectId().toHexString());
        }else{
                foodEntity.setId(foodDTO.getId());
        }
        foodEntity.setName(foodDTO.getName());
        return foodEntity;
    }

    public static Document entityToDocument(FoodEntity foodEntity){
        Document doc = new Document();
        doc.append("_id", new ObjectId(foodEntity.getId()));
        doc.append("name", foodEntity.getName());
        return doc;
    }

    public static FoodEntity documentToEntity(Document doc) {
        FoodEntity foodEntity = new FoodEntity();
        ObjectId objectId = doc.getObjectId("_id");
        if (objectId != null) {
            foodEntity.setId(objectId.toHexString());
        } else {
            Object id = doc.get("id");
            if (id != null) {
                foodEntity.setId(id.toString());
            }
        }
        foodEntity.setName(doc.getString("name"));
        return foodEntity;
    }

}
