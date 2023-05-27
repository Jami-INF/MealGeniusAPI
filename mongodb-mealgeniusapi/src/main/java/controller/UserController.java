package controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.User;
import org.bson.types.ObjectId;
import repository.UserRepository;


import java.util.List;

@Path("/api/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GET
    public List<User> getUsers() {
        return userRepository.listAll();
    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser (@PathParam("id") String id) {
        return userRepository.findById(id);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void updateUser (@PathParam("id") String id, User user) {
        user.setId(new ObjectId(id));
        userRepository.update(user);
    }
    @POST
    public Response addUser(User user) {
        userRepository.persist(user);
        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("/{id}")
    public void deleteUser(@PathParam("id") String id) {
        User user = userRepository.findById(id);
        userRepository.delete(user);
    }
}
