package mapper;

import dto.StepDTO;
import entity.StepEntity;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.Document;
import org.bson.types.ObjectId;

public abstract class StepMapper {
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
        if(stepDTO.getId() == null){
            stepEntity.setId(new ObjectId().toHexString());
        }else{
            stepEntity.setId(stepDTO.getId());
        }
        stepEntity.setDescription(stepDTO.getDescription());
        stepEntity.setNumber(stepDTO.getNumber());
        stepEntity.setDuration(stepDTO.getDuration());
        return stepEntity;
    }

    public static Document entityToDocument(StepEntity stepEntity){
        Document stepDocument = new Document();
        stepDocument.append("_id", stepEntity.getId());
        stepDocument.append("description", stepEntity.getDescription());
        stepDocument.append("number", stepEntity.getNumber());
        stepDocument.append("duration", stepEntity.getDuration());
        return stepDocument;
    }

    public static StepEntity documentToEntity(Document stepDocument) {
        StepEntity stepEntity = new StepEntity();
        Object objectId = stepDocument.get("_id");
        if (objectId != null) {
            stepEntity.setId(objectId.toString());
        } else {
            Object id = stepDocument.get("id");
            if (id != null) {
                stepEntity.setId(id.toString());
            }
        }
        stepEntity.setDescription(stepDocument.getString("description"));
        stepEntity.setNumber(stepDocument.getInteger("number"));
        stepEntity.setDuration(stepDocument.getInteger("duration"));
        return stepEntity;
    }

}
