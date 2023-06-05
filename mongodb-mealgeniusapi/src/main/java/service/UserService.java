package service;

import dto.UserDTO;
import entity.UserEntity;
import jakarta.ws.rs.core.Response;
import mapper.UserMapper;
import org.bson.Document;
import org.bson.types.ObjectId;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private final UserRepository userRepository;
    private UserMapper userMapper;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userMapper = new UserMapper();
    }

    public UserDTO getUserById(String id) {
        Document document = new Document("_id", new ObjectId(id));
        UserEntity entity = userRepository.find(document);

        if (entity != null)
            return UserMapper.entityToDTO(entity);

        return null;
    }

    public Response updateUser(UserEntity user) {
        Document doc = new Document("$set", UserMapper.entityToDocument(user));
        Document docId = new Document("_id", user.getId());
        Boolean result =  userRepository.update(docId, doc);

        return getResponse(result);
    }

    public List<UserDTO> getAll() {
        List<UserDTO> userDTOs = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.getAll();
        for (UserEntity userEntity : userEntities) {
            userDTOs.add(UserMapper.entityToDTO(userEntity));
        }
        return userDTOs;
    }

    public Response addUser(UserEntity user) {
        user.setId(new ObjectId().toHexString());
        Boolean result = userRepository.add(UserMapper.entityToDocument(user));
        return getResponse(result);
    }

    public Response deleteUser(String id) {
        Document document = new Document("_id", new ObjectId(id));
        Boolean result = userRepository.delete(document);
        return getResponse(result);
    }

    private Response getResponse(Boolean result) {
        Response.Status response = Boolean.TRUE.equals(result) ? Response.Status.OK : Response.Status.NOT_FOUND;
        return Response.status(response).build();
    }
}
