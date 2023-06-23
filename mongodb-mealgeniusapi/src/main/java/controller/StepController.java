package controller;

import dto.StepDTO;
import entity.StepEntity;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import mapper.StepMapper;
import service.StepService;

import java.util.ArrayList;
import java.util.List;

public class StepController {
    private final StepService stepService;

    public StepController(StepService stepService) {
        this.stepService = stepService;
    }

    /**
     * Get all steps
     * @return List of StepDTO
     */
    @GET
    @Path("/")
    public List<StepDTO> getSteps() {
        List<StepDTO> stepDTOs = new ArrayList<>();
        stepDTOs.addAll(stepService.getAll());
        return stepDTOs;
    }


    /**
     * Get step by id
     * @param id id of step
     * @return StepDTO
     */
    @GET
    @Path("/{id}")
    public StepDTO getStep (@PathParam("id") String id) {
        return stepService.getStepById(id);
    }

    /**
     * Update step
     * @param id id of step
     * @param step StepDTO
     * @return Response
     */
    @PUT
    @Path("/{id}")
    public Response updateStep (@PathParam("id") String id, StepDTO step) {
        StepEntity entity = StepMapper.DTOToEntity(step);
        return stepService.updateStep(entity);
    }

    /**
     * Add step
     * @param stepDTO StepDTO
     * @return Response
     */
    @POST
    @Path("/addstep")
    public Response addStep(StepDTO stepDTO) {
        StepEntity stepEntity = StepMapper.DTOToEntity(stepDTO);
        return stepService.addStep(stepEntity);
    }

    /**
     * Delete step
     * @param id id of step
     * @return Response
     */
    @DELETE
    @Path("/{id}")
    public Response deleteStep(@PathParam("id") String id) {
        return stepService.deleteStep(id);
    }
}
