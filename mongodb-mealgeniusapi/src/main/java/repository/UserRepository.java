package repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import mapper.UserMapper;
import entity.UserEntity;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    MongoClient mongoClient;
    private static final String DATABASE_NAME = "mealgenius";

    private MongoCollection<Document> collection = getConnexion().getCollection("users");

    /**
     * Find a user by its id
     * @param query the id of the user
     * @return the user found
     */
    public UserEntity find(Document query) {
        System.out.println("query : "+query);
        Document document = collection.find(query).first();
        System.out.println("document : "+document);
        return document == null ? null : UserMapper.documentToEntity(document);
    }

    /**
     * Update a user
     * @param docId the id of the user
     * @param doc the new user
     * @return true if the user has been updated
     */
    public boolean update(Document docId, Document doc) {
        UpdateResult result = collection.updateOne(docId, doc);
        return result.getModifiedCount() == 1;
    }

    /**
     * Get all the users
     * @return a list of all the users
     */
    public List<UserEntity> getAll() {
        List<Document> documents = collection.find().into(new ArrayList<>());
        List<UserEntity> userEntities = new ArrayList<>();
        for (Document document : documents) {
            userEntities.add(UserMapper.documentToEntity(document));
        }
        return userEntities;
    }

    /**
     * Add a user
     * @param document the user to add
     * @return true if the user has been added
     */
    public boolean add(Document document) {
        InsertOneResult result = collection.insertOne(document);
        return result.getInsertedId() != null;
    }

    /**
     * Delete a user
     * @param document the user to delete
     * @return true if the user has been deleted
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
        return database;
    }
}