package repository;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import entity.StepEntity;
import mapper.StepMapper;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class StepRepository {

    MongoClient mongoClient;
    private static final String DATABASE_NAME = "mealgenius";

    private MongoCollection<Document> collection = getConnexion().getCollection("steps");

    /**
     * Find a step by its id
     * @param query the id of the step
     * @return the step found
     */
    public StepEntity find(Document query) {
        Document document = collection.find(query).first();
        return document == null ? null : StepMapper.documentToEntity(document);
    }

    /**
     * Update a step
     * @param docId the id of the step
     * @param doc the new step
     * @return true if the step has been updated
     */
    public boolean update(Document docId, Document doc) {
        UpdateResult result = collection.updateOne(docId, doc);
        return result.getModifiedCount() == 1;
    }

    /**
     * Get all the steps
     * @return a list of all the steps
     */
    public List<StepEntity> getAll() {
        List<Document> documents = collection.find().into(new ArrayList<>());
        List<StepEntity> stepEntities = new ArrayList<>();
        for (Document document : documents) {
            stepEntities.add(StepMapper.documentToEntity(document));
        }
        return stepEntities;
    }

    /**
     * Add a step
     * @param document the step to add
     * @return true if the step has been added
     */
    public boolean add(Document document) {
        InsertOneResult result = collection.insertOne(document);
        return result.getInsertedId() != null;
    }

    /**
     * Delete a step
     * @param document the step to delete
     * @return true if the step has been deleted
     */
    public boolean delete(Document document) {
        DeleteResult result = collection.deleteOne(document);
        return result.getDeletedCount() == 1;
    }

    /**
     * Get the connexion to the database
     * @return the connexion to the database
     */
    public MongoDatabase getConnexion(){
        String connectionString = "mongodb+srv://jamidev:uz2paZc5Dsii0FVY@mealgeniusapi.j6cu3vg.mongodb.net/?retryWrites=true&w=majority";
//        String connectionString = "mongodb://localhost:27017";

        com.mongodb.client.MongoClient mongoClient = MongoClients.create(connectionString);
        MongoDatabase database = mongoClient.getDatabase( DATABASE_NAME );
        return database;
    }
}
