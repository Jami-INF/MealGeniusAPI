package service;

import dto.IngredientDTO;
import entity.IngredientEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.ws.rs.core.Response;
import mapper.IngredientMapper;
import org.bson.Document;
import org.bson.types.ObjectId;
import repository.IngredientRepository;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Default
public class IngredientService {

    private IngredientRepository ingredientRepository;


    public IngredientService() {
        this.ingredientRepository = new IngredientRepository();
    }

    public IngredientDTO getIngredientById(String id) {
        Document document = new Document("_id", new ObjectId(id));
        IngredientEntity entity = ingredientRepository.find(document);
        if (entity != null)
            return IngredientMapper.entityToDTO(entity);
        return null;
    }

    public Response updateIngredient(IngredientEntity ingredient) {
        Document doc = new Document("$set", IngredientMapper.entityToDocument(ingredient));
        Document docId = new Document("_id", ingredient.getId());
        Boolean result =  ingredientRepository.update(docId, doc);

        return getResponse(result);
    }

    public List<IngredientDTO> getAll(){
        List<IngredientDTO> ingredientDTOs = new ArrayList<>();
        List<IngredientEntity> ingredientEntities = ingredientRepository.getAll();
        for (IngredientEntity ingredientEntity : ingredientEntities) {
            ingredientDTOs.add(IngredientMapper.entityToDTO(ingredientEntity));
        }
        return ingredientDTOs;
    }

    public Response addIngredient(IngredientEntity ingredient) {
        //ingredient.setId(new ObjectId().toHexString());
        Boolean result = ingredientRepository.add(IngredientMapper.entityToDocument(ingredient));
        return getResponse(result);
    }

    public Response deleteIngredient(String id) {
        Document document = new Document("_id", id);
        Boolean result = ingredientRepository.delete(document);
        return getResponse(result);
    }

    private Response getResponse(Boolean result) {
        Response.Status response = Boolean.TRUE.equals(result) ? Response.Status.OK : Response.Status.NOT_FOUND;
        return Response.status(response).build();
    }


}
