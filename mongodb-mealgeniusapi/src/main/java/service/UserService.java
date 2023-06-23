package service;

import dto.FoodDTO;
import dto.MealDTO;
import dto.UserDTO;
import entity.FoodEntity;
import entity.UserEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.bson.Document;
import org.bson.types.ObjectId;
import mapper.UserMapper;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static mapper.FoodMapper.DTOToEntity;


@ApplicationScoped
@Default
public class UserService {

    private UserRepository userRepository;
    private final FoodService foodService;

    public UserService() {
        this.userRepository = new UserRepository();
        this.foodService = new FoodService();

    }

    /**
     * Get user by id
     * @param id
     * @return
     */
    public UserDTO getUserById(String id) {
        Document document = new Document("_id", new ObjectId(id));
        UserEntity entity = userRepository.find(document);
        if (entity != null)
            return UserMapper.entityToDTO(entity);
        return null;
    }

    /**
     * Get user by id
     * @param id the id of the user
     * @return UserEntity
     */
    public UserEntity getUserEntityById(String id) {
        Document document = new Document("_id", new ObjectId(id));
        UserEntity entity = userRepository.find(document);
        if (entity != null)
            return entity;
        return null;
    }

    /**
     * Update user
     * @param user the user to update
     * @return Response
     */
    public Response updateUser(UserEntity user) {
        Document doc = new Document("$set", UserMapper.entityToDocument(user));
        Document docId = new Document("_id", new ObjectId(user.getId()));
        Boolean result =  userRepository.update(docId, doc);

        return getResponse(result);
    }


    /**
     * Get all users
     * @return list of users
     */
    public List<UserDTO> getAll() {
        List<UserDTO> userDTOs = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.getAll();
        for (UserEntity userEntity : userEntities) {
            userDTOs.add(UserMapper.entityToDTO(userEntity));
        }
        return userDTOs;
    }

    /**
     * Add user
     * @param user the user to add
     * @return Response
     */
    public Response addUser(UserEntity user) {
        //user.setId(new ObjectId().toHexString());
        Boolean result = userRepository.add(UserMapper.entityToDocument(user));
        return getResponse(result);
    }

    /**
     * Delete user
     * @param id the id of the user to delete
     * @return Response
     */
    public Response deleteUser(String id) {
        Document document = new Document("_id", new ObjectId(id));
        Boolean result = userRepository.delete(document);
        return getResponse(result);
    }

    /**
     * Get response
     * @param result the result of the operation
     * @return Response
     */
    private Response getResponse(Boolean result) {
        Response.Status response = Boolean.TRUE.equals(result) ? Response.Status.OK : Response.Status.NOT_FOUND;
        return Response.status(response).build();
    }

    /**
     * Add meal to user
     * @param idUser the id of the user
     * @param foodDTO the food to add
     * @return Response
     */
    public Response addFoodToUser(String idUser, FoodDTO foodDTO) {
        if(foodDTO.getId() == null){// if id is null, add the new food
            foodDTO.setId(new ObjectId().toHexString());
            foodService.addFood(DTOToEntity(foodDTO));
        }else if(foodService.getFoodById(foodDTO.getId()) == null){// if id is not null, check if the food exists
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        FoodEntity foodEntity = DTOToEntity(foodDTO);
        UserEntity user = getUserEntityById(idUser);
        user.addFood(foodEntity);
        return updateUser(user);
    }

    /**
     * Add meal to user
     * @param idUser the id of the user
     * @param email the email of the user
     * @param password the password of the user
     * @return Response
     */
    public Response login(String idUser, String email, String password) {
        UserEntity user = getUserEntityById(idUser);
        if(user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if(user.getEmail().equals(email) && user.getPassword().equals(password)){
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
