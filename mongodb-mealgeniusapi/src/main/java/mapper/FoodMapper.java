package mapper;

import dto.FoodDTO;
import jakarta.enterprise.context.ApplicationScoped;
import entity.FoodEntity;
import org.bson.Document;
import org.bson.types.ObjectId;
import service.FoodService;



public abstract class FoodMapper {

    private final FoodService foodService = new FoodService();

    /**
     * Map FoodEntity to FoodDTO
     * @param foodEntity FoodEntity
     * @return FoodDTO
     */
    public static FoodDTO entityToDTO(FoodEntity foodEntity){
        FoodDTO foodDTO = new FoodDTO();
        foodDTO.setId(foodEntity.getId());
        foodDTO.setName(foodEntity.getName());
        return foodDTO;
    }

    /**
     * Map FoodDTO to FoodEntity
     * @param foodDTO FoodDTO
     * @return FoodEntity
     */
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

    /**
     * Map FoodEntity to Document
     * @param foodEntity FoodEntity
     * @return Document
     */
    public static Document entityToDocument(FoodEntity foodEntity){
        Document doc = new Document();
        doc.append("_id", new ObjectId(foodEntity.getId()));
        doc.append("name", foodEntity.getName());
        return doc;
    }

    /**
     * Map Document to FoodEntity
     * @param doc   Document
     * @return FoodEntity
     */
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
