package mapper;

import dto.FoodDTO;
import dto.UserDTO;
import entity.FoodEntity;
import jakarta.enterprise.context.ApplicationScoped;
import entity.UserEntity;

import org.bson.Document;
import org.bson.types.ObjectId;
import service.FoodService;

import java.util.ArrayList;
import java.util.List;

public abstract class UserMapper {

    private static FoodService foodService = new FoodService();

    public static UserDTO entityToDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setFirstName(userEntity.getFirstName());
        userDTO.setLastName(userEntity.getLastName());
        userDTO.setEmail(userEntity.getEmail());
        List<FoodDTO> foodDTOList = new ArrayList<>();
        userEntity.getFoods().forEach(foodEntity -> {
            //        ingredientDTO.setFood(FoodMapper.entityToDTO(foodService.getFoodEntityById(ingredientEntity.getIdFood())));
            foodDTOList.add(FoodMapper.entityToDTO(foodService.getFoodEntityById(foodEntity.getId())));
        });
        userDTO.setFoods(foodDTOList);
        return userDTO;
    }

    public static UserEntity DTOToEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        if (userDTO.getId() == null) {
            userEntity.setId(new ObjectId().toHexString());
        }else{
            userEntity.setId(userDTO.getId());
        }
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setEmail(userDTO.getEmail());
        List<FoodEntity> foodEntityList = new ArrayList<>();
        userDTO.getFoods().forEach(foodDTO -> foodEntityList.add(FoodMapper.DTOToEntity(foodDTO)));
        userEntity.setFoods(foodEntityList);
        return userEntity;
    }

    public static Document entityToDocument(UserEntity userEntity) {
        Document userDocument = new Document();
        userDocument.append("_id", new ObjectId(userEntity.getId()));
        userDocument.append("firstname", userEntity.getFirstName());
        userDocument.append("lastname", userEntity.getLastName());
        userDocument.append("email", userEntity.getEmail());
        userDocument.append("password", userEntity.getPassword());
        List<Document> foodDocuments = new ArrayList<>();
        userEntity.getFoods().forEach(foodEntity ->{
            Document foodDocument = new Document();
            foodDocument.append("id_food", foodEntity.getId());
            foodDocuments.add(foodDocument);
        });
        userDocument.append("foods", foodDocuments);
        return userDocument;
    }

    public static UserEntity documentToEntity(Document doc) {
        UserEntity userEntity = new UserEntity();
        ObjectId objectId = doc.getObjectId("_id");
        if (objectId != null) {
            userEntity.setId(objectId.toHexString());
        } else {
            userEntity.setId(doc.get("id").toString());
        }
        userEntity.setFirstName(doc.getString("firstname"));
        userEntity.setLastName(doc.getString("lastname"));
        userEntity.setEmail(doc.getString("email"));
        userEntity.setPassword(doc.getString("password"));
        List<FoodEntity> foodEntities = new ArrayList<>();
        List<Document> foodDocuments = doc.getList("foods", Document.class);
        if(foodDocuments != null) {
            foodDocuments.forEach(foodDocument -> {
                String idIngredient = foodDocument.get("id_food").toString();
                FoodEntity foodEntity = foodService.getFoodEntityById(idIngredient);
                foodEntities.add(foodEntity);
            });
        }
        userEntity.setFoods(foodEntities);
        return userEntity;
    }
}