package controller;

import dto.UserDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mapper.UserMapper;
import entity.UserEntity;
import org.bson.types.ObjectId;
import repository.UserRepository;


import java.util.ArrayList;
import java.util.List;

@Path("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private UserMapper userMapper;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userMapper = new UserMapper();
    }

    @GET
    @Path("/")
    public List<UserEntity> getUsers() {
        List<UserEntity> userEntities = new ArrayList<UserEntity>();
        userEntities.addAll(userRepository.listAll());
        return userEntities;
    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserDTO getUser (@PathParam("id") String id) {
        return UserMapper.userToUserDTO(userRepository.findById(id));
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void updateUser (@PathParam("id") String id, UserDTO user) {
        userRepository.update(UserMapper.userDTOToUser(user));
    }

    @POST
    @Path("/adduser")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(UserDTO userDTO) {
        userDTO.setId(new ObjectId().toHexString());
        UserEntity userEntity = UserMapper.userDTOToUser(userDTO);
        userRepository.addUser(userEntity);
        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("/{id}")
    public void deleteUser(@PathParam("id") String id) {
        UserEntity userEntity = userRepository.findById(id);
        userRepository.delete(userEntity);
    }

    @POST
    @Path("/initdb")
    public void initDB() {
        userRepository.initDB();
    }

}
