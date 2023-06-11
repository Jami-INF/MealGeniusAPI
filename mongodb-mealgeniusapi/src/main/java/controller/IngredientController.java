package controller;

import dto.IngredientDTO;
import entity.IngredientEntity;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import mapper.IngredientMapper;
import service.IngredientService;

import java.util.ArrayList;
import java.util.List;

@Path("/api/ingredients")
public class IngredientController {
    private final IngredientService ingredientService;
    private IngredientMapper ingredientMapper;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
        this.ingredientMapper = new IngredientMapper();
    }

    @GET
    @Path("/")
    public List<IngredientDTO> getIngredients() {
        List<IngredientDTO> ingredientDTOs = new ArrayList<>();
        ingredientDTOs.addAll(ingredientService.getAll());
        return ingredientDTOs;
    }

    @GET
    @Path("/{id}")
    public IngredientDTO getIngredient (@PathParam("id") String id) {
        return ingredientService.getIngredientById(id);
    }

    @PUT
    @Path("/{id}")
    public Response updateIngredient (@PathParam("id") String id, IngredientDTO ingredient) {
        IngredientEntity entity = IngredientMapper.DTOToEntity(ingredient);
        return ingredientService.updateIngredient(entity);
    }

    @POST
    @Path("/addingredient")
    public Response addIngredient(IngredientDTO ingredientDTO) {
        IngredientEntity ingredientEntity = IngredientMapper.DTOToEntity(ingredientDTO);
        return ingredientService.addIngredient(ingredientEntity);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteIngredient(@PathParam("id") String id) {
        return ingredientService.deleteIngredient(id);
    }
}
