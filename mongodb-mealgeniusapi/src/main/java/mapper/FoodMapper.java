package mapper;

import dto.FoodDTO;
import jakarta.enterprise.context.ApplicationScoped;
import entity.FoodEntity;
import org.bson.Document;
import org.bson.types.ObjectId;


@ApplicationScoped
public class FoodMapper {

    public static FoodDTO entityToDTO(FoodEntity foodEntity){
        FoodDTO foodDTO = new FoodDTO();
        foodDTO.setId(foodEntity.getId());
        foodDTO.setName(foodEntity.getName());
        return foodDTO;
    }

    public static FoodEntity DTOToEntity(FoodDTO foodDTO){
        FoodEntity foodEntity = new FoodEntity();
        foodEntity.setId(foodDTO.getId());
        foodEntity.setName(foodDTO.getName());
        return foodEntity;
    }

    public static Document entityToDocument(FoodEntity foodEntity){
        Document doc = new Document()
                .append("_id", new ObjectId(foodEntity.getId()))
                .append("name", foodEntity.getName());
        return doc;
    }

    public static FoodEntity documentToEntity(Document doc){
        FoodEntity foodEntity = new FoodEntity();
        foodEntity.setId(doc.getObjectId("_id").toHexString());
        foodEntity.setName(doc.getString("name"));
        return foodEntity;
    }
}
