package mapper;

import dto.StepDTO;
import entity.StepEntity;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.Document;

@ApplicationScoped
public class StepMapper {
    public static StepDTO entityToDTO(StepEntity stepEntity){
        StepDTO stepDTO = new StepDTO();
        stepDTO.setId(stepEntity.getId());
        stepDTO.setDescription(stepEntity.getDescription());
        stepDTO.setNumber(stepEntity.getNumber());
        stepDTO.setDuration(stepEntity.getDuration());
        return stepDTO;
    }

    public static StepEntity DTOToEntity(StepDTO stepDTO){
        StepEntity stepEntity = new StepEntity();
        stepEntity.setId(stepDTO.getId());
        stepEntity.setDescription(stepDTO.getDescription());
        stepEntity.setNumber(stepDTO.getNumber());
        stepEntity.setDuration(stepDTO.getDuration());
        return stepEntity;
    }

    public static Document entityToDocument(StepEntity stepEntity){
        Document stepDocument = new Document();
        stepDocument.append("id", stepEntity.getId());
        stepDocument.append("description", stepEntity.getDescription());
        stepDocument.append("number", stepEntity.getNumber());
        stepDocument.append("duration", stepEntity.getDuration());
        return stepDocument;
    }

    public StepEntity documentToEntity(Document stepDocument){
        StepEntity stepEntity = new StepEntity();
        stepEntity.setId(stepDocument.getObjectId("_id").toHexString());
        stepEntity.setDescription(stepDocument.getString("description"));
        stepEntity.setNumber(stepDocument.getInteger("number"));
        stepEntity.setDuration(stepDocument.getInteger("duration"));
        return stepEntity;
    }
}
