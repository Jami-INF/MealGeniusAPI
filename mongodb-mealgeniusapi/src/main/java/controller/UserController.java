package controller;

import dto.UserDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mapper.UserMapper;
import model.User;
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
    public List<User> getUsers() {
        List<User> users = new ArrayList<User>();
        users.addAll(userRepository.listAll());
        return users;
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
        user.setId(new ObjectId(id));
        userRepository.update(UserMapper.userDTOToUser(user));
    }
    @POST
    public Response addUser(UserDTO userDTO) {
        User user = UserMapper.userDTOToUser(userDTO);
        userRepository.persist(user);
        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("/{id}")
    public void deleteUser(@PathParam("id") String id) {
        User user = userRepository.findById(id);
        userRepository.delete(user);
    }

    @POST
    @Path("/initdb")
    public void initDB() {
        userRepository.initDB();
    }

}
