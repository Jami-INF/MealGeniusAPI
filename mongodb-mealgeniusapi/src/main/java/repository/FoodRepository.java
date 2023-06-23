package repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import com.oracle.svm.core.annotate.Delete;
import entity.FoodEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import mapper.FoodMapper;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class FoodRepository {

    MongoClient mongoClient;
    private static final String DATABASE_NAME = "mealgenius";

    private MongoCollection<Document> collection = getConnexion().getCollection("foods");

    /**
     * Find a food by its id
     * @param query the id of the food
     * @return the food found
     */
    public FoodEntity find(Document query) {
        Document document = collection.find(query).first();
        return document == null ? null : FoodMapper.documentToEntity(document);
    }

    /**
     * Find a food by its name
     * @param docId the name of the food
     * @param doc the new food
     * @return true if the food has been updated
     */
    public boolean update(Document docId, Document doc) {
        UpdateResult result = collection.updateOne(docId, doc);
        return result.getModifiedCount() == 1;
    }

    /**
     * Get all the foods
     * @return a list of all the foods
     */
    public List<FoodEntity> getAll(){
        List<Document> documents = collection.find().into(new ArrayList<>());
        List<FoodEntity> foodEntities = new ArrayList<>();
        for (Document document : documents) {
            foodEntities.add(FoodMapper.documentToEntity(document));
        }
        return foodEntities;
    }

    /**
     * Add a food
     * @param document the food to add
     * @return true if the food has been added
     */
    public boolean add(Document document) {
        InsertOneResult result = collection.insertOne(document);
        return result.getInsertedId() != null;
    }

    /**
     * Delete a food
     * @param document the food to delete
     * @return true if the food has been deleted
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

        MongoClient mongoClient = MongoClients.create(connectionString);
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        //création de l'index sur le champ name
        database.getCollection("foods").createIndex(new Document("name", 1));
        return database;
    }
}
