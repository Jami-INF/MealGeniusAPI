package controller;

import dto.MealDTO;
import dto.UserDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import mapper.UserMapper;
import entity.UserEntity;
import org.bson.types.ObjectId;
import service.UserService;


import java.util.ArrayList;
import java.util.List;

@Path("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
        UserEntity userEntity = UserMapper.DTOToEntity(userDTO);
        return userService.addUser(userEntity);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") String id) {
        return userService.deleteUser(id);
    }
}
