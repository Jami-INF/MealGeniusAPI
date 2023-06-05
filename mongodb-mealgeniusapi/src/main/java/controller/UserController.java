package controller;

import dto.UserDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mapper.UserMapper;
import entity.UserEntity;
import org.bson.types.ObjectId;
import repository.UserRepository;
import service.UserService;


import java.util.ArrayList;
import java.util.List;

@Path("/api/users")
public class UserController {

    private final UserService userService;
    private UserMapper userMapper;

    public UserController(UserService userService) {
        this.userService = userService;
        this.userMapper = new UserMapper();
    }

    @GET
    @Path("/")
    public List<UserDTO> getUsers() {
        List<UserDTO> userDTOs = new ArrayList<>();
        userDTOs.addAll(userService.getAll());
        return userDTOs;
    }

    @GET
    @Path("/{id}")
    public UserDTO getUser (@PathParam("id") String id) {
        return userService.getUserById(id);
    }

    @PUT
    @Path("/{id}")
    public Response updateUser (@PathParam("id") String id, UserDTO user) {
        UserEntity entity = UserMapper.DTOToEntity(user);
        return userService.updateUser(entity);
    }

    @POST
    @Path("/adduser")
    public Response addUser(UserDTO userDTO) {
        userDTO.setId(new ObjectId().toHexString());
        UserEntity userEntity = UserMapper.DTOToEntity(userDTO);
        return userService.addUser(userEntity);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") String id) {
        return userService.deleteUser(id);
    }


/*    @POST
    @Path("/adduser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(UserDTO userDTO) {
        userDTO.setId(new ObjectId().toHexString());
        UserEntity userEntity = UserMapper.userDTOToUser(userDTO);
        userRepository.addUser(userEntity);
        return Response.status(Response.Status.CREATED).build();
    }*/

 /*   @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteUser(@PathParam("id") String id) {
        UserEntity userEntity = userRepository.findById(id);
        userRepository.delete(userEntity);
    }*/

}
