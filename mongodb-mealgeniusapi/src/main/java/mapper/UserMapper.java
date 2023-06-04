package mapper;

import dto.UserDTO;
import jakarta.enterprise.context.ApplicationScoped;
import entity.UserEntity;

import org.bson.Document;
import org.bson.types.ObjectId;

@ApplicationScoped
public class UserMapper {

    public static UserDTO entityToDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setFirstName(userEntity.getFirstName());
        userDTO.setLastName(userEntity.getLastName());
        userDTO.setEmail(userEntity.getEmail());
        return userDTO;
    }

    public static UserEntity DTOToEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDTO.getId());
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setEmail(userDTO.getEmail());
        return userEntity;
    }

    public static Document entityToDocument(UserEntity userEntity) {
        return new Document()
            .append("_id", new ObjectId(userEntity.getId()))
            .append("firstname", userEntity.getFirstName())
            .append("lastname", userEntity.getLastName())
            .append("email", userEntity.getEmail())
            .append("password", userEntity.getPassword());
    }

    public UserEntity documentToEntity(Document doc){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(doc.getObjectId("_id").toHexString());
        userEntity.setFirstName(doc.getString("firstname"));
        userEntity.setLastName(doc.getString("lastname"));
        userEntity.setEmail(doc.getString("email"));
        userEntity.setPassword(doc.getString("password"));
        return userEntity;
    }
}