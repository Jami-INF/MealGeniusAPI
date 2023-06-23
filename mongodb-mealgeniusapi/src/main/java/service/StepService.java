package service;


import dto.StepDTO;
import entity.StepEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.ws.rs.core.Response;
import mapper.StepMapper;
import org.bson.Document;
import org.bson.types.ObjectId;
import repository.StepRepository;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Default
public class StepService {

    private StepRepository stepRepository;

    public StepService() {
        this.stepRepository = new StepRepository();
    }

    /**
     * Get step by id
     * @param id step id
     * @return step
     */
    public StepDTO getStepById(String id) {
        Document document = new Document("_id", new ObjectId(id));
        StepEntity entity = stepRepository.find(document);

        if (entity != null)
            return StepMapper.entityToDTO(entity);

        return null;
    }

    /**
     * Get step by id
     * @param step step id
     * @return step
     */
    public Response updateStep(StepEntity step) {
        Document doc = new Document("$set", StepMapper.entityToDocument(step));
        Document docId = new Document("_id", new ObjectId(step.getId()));
        Boolean result =  stepRepository.update(docId, doc);

        return getResponse(result);
    }

    /**
     * Get all steps
     * @return all steps
     */
    public List<StepDTO> getAll(){
        List<StepDTO> stepDTOs = new ArrayList<>();
        List<StepEntity> stepEntities = stepRepository.getAll();
        for (StepEntity stepEntity : stepEntities) {
            stepDTOs.add(StepMapper.entityToDTO(stepEntity));
        }
        return stepDTOs;
    }

    /**
     * Add step
     * @param step step
     * @return response
     */
    public Response addStep(StepEntity step) {
        //step.setId(new ObjectId().toHexString());
        Boolean result = stepRepository.add(StepMapper.entityToDocument(step));
        return getResponse(result);
    }

    /**
     * Delete step
     * @param id step id
     * @return response
     */
    public Response deleteStep(String id) {
        Document document = new Document("_id", id);
        Boolean result = stepRepository.delete(document);
        return getResponse(result);
    }

    /**
     * Get response
     * @param result result
     * @return response
     */
    private Response getResponse(Boolean result) {
        Response.Status response = Boolean.TRUE.equals(result) ? Response.Status.OK : Response.Status.NOT_FOUND;
        return Response.status(response).build();
    }
}
