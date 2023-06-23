package mapper;

import dto.StepDTO;
import entity.StepEntity;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.Document;
import org.bson.types.ObjectId;

public abstract class StepMapper {

    /**
     * Convert StepEntity to StepDTO
     * @param stepEntity StepEntity
     * @return StepDTO
     */
    public static StepDTO entityToDTO(StepEntity stepEntity){
        StepDTO stepDTO = new StepDTO();
        stepDTO.setId(stepEntity.getId());
        stepDTO.setDescription(stepEntity.getDescription());
        stepDTO.setNumber(stepEntity.getNumber());
        stepDTO.setDuration(stepEntity.getDuration());
        return stepDTO;
    }

    /**
     * Convert StepDTO to StepEntity
     * @param stepDTO StepDTO
     * @return StepEntity
     */
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

    /**
     * Convert StepEntity to Document
     * @param stepEntity StepEntity
     * @return Document
     */
    public static Document entityToDocument(StepEntity stepEntity){
        Document stepDocument = new Document();
        stepDocument.append("_id", new ObjectId(stepEntity.getId()));
        stepDocument.append("description", stepEntity.getDescription());
        stepDocument.append("number", stepEntity.getNumber());
        stepDocument.append("duration", stepEntity.getDuration());
        return stepDocument;
    }


    /**
     * Convert Document to StepEntity
     * @param stepDocument Document
     * @return StepEntity
     */
    public static StepEntity documentToEntity(Document stepDocument) {
        StepEntity stepEntity = new StepEntity();
        ObjectId objectId = stepDocument.getObjectId("_id");
        if (objectId != null) {
            stepEntity.setId(objectId.toString());
        }
        stepEntity.setDescription(stepDocument.getString("description"));
        stepEntity.setNumber(stepDocument.getInteger("number"));
        stepEntity.setDuration(stepDocument.getInteger("duration"));
        return stepEntity;
    }

}
