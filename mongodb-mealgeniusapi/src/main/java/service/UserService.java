package service;

import dto.UserDTO;
import entity.UserEntity;
import mapper.UserMapper;
import org.bson.Document;
import org.bson.types.ObjectId;
import repository.UserRepository;

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
}
