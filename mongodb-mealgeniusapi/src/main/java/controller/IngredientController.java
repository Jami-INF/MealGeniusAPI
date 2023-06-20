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

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
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

    @DELETE
    @Path("/{id}")
    public Response deleteIngredient(@PathParam("id") String id) {
        return ingredientService.deleteIngredient(id);
    }
}
