package repository;

import com.mongodb.client.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import entity.MealEntity;
import mapper.MealMapper;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MealRepository {
    MongoClient mongoClient;
    private static final String DATABASE_NAME = "mealgenius";

    private MongoCollection<Document> collection = getConnexion().getCollection("meals");

    /**
     * Find a meal by its id
     * @param query the id of the meal
     * @return the meal found
     */
    public MealEntity find(Document query) {
        Document document = collection.find(query).first();
        return document == null ? null : MealMapper.documentToEntity(document);
    }

    /**
     * Update a meal
     * @param docId the id of the meal
     * @param doc the new meal
     * @return true if the meal has been updated
     */
    public boolean update(Document docId, Document doc) {
        collection.updateOne(docId, doc);
        return true;
    }

    /**
     * Get all the meals
     * @return a list of all the meals
     */
    public List<MealEntity> getAll() {
        List<Document> documents = collection.find().into(new ArrayList<>());
        List<MealEntity> mealEntities = new ArrayList<>();
        for (Document document : documents) {
            mealEntities.add(MealMapper.documentToEntity(document));
        }
        return mealEntities;
    }

    /**
     * Get all the meals that contains the name
     * @param name the name of the meal
     * @return a list of all the meals that contains the name
     */
    public List<MealEntity> getMealsContainsName(String name) {
        Document query = new Document("name", new Document("$regex", name).append("$options", "i"));
        List<Document> documents = collection.find(query).into(new ArrayList<>());
        List<MealEntity> mealEntities = new ArrayList<>();
        for (Document document : documents) {
            mealEntities.add(MealMapper.documentToEntity(document));
        }
        return mealEntities;
    }

    /**
     * Get all the meals that contains the name
     * @param idUser the id of the user
     * @return a list of all the meals that contains the name
     */
    public List<MealEntity> getAvailableMeals(String idUser) {
        return getAll();
    }

    /**
     * Add a meal
     * @param document the meal to add
     * @return  true if the meal has been added
     */
    public boolean add(Document document) {
        collection.insertOne(document);
        return true;
    }

    /**
     * Delete a meal
     * @param document the meal to delete
     * @return true if the meal has been deleted
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
