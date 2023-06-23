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

    public MealEntity find(Document query) {
        Document document = collection.find(query).first();
        return document == null ? null : MealMapper.documentToEntity(document);
    }

    public boolean update(Document docId, Document doc) {
        collection.updateOne(docId, doc);
        return true;
    }

    public List<MealEntity> getAll() {
        List<Document> documents = collection.find().into(new ArrayList<>());
        List<MealEntity> mealEntities = new ArrayList<>();
        for (Document document : documents) {
            mealEntities.add(MealMapper.documentToEntity(document));
        }
        return mealEntities;
    }

    public List<MealEntity> getMealsContainsName(String name) {
        Document query = new Document("name", new Document("$regex", name).append("$options", "i"));
        List<Document> documents = collection.find(query).into(new ArrayList<>());
        List<MealEntity> mealEntities = new ArrayList<>();
        for (Document document : documents) {
            mealEntities.add(MealMapper.documentToEntity(document));
        }
        return mealEntities;
    }

    public List<MealEntity> getAvailableMeals(String idUser) {
        return getAll();
    }

    public boolean add(Document document) {
        collection.insertOne(document);
        return true;
    }

    public boolean delete(Document document) {
        DeleteResult result = collection.deleteOne(document);
        return result.getDeletedCount() == 1;
    }

    public MongoDatabase getConnexion(){
        String connectionString = "mongodb+srv://jamidev:uz2paZc5Dsii0FVY@mealgeniusapi.j6cu3vg.mongodb.net/?retryWrites=true&w=majority";
//        String connectionString = "mongodb://localhost:27017";

        MongoClient mongoClient = MongoClients.create(connectionString);
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        return database;
    }


}
