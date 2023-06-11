package repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
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

    private MealMapper mealMapper = new MealMapper();

    private MongoCollection<Document> collection = getConnexion().getCollection("meals");

    public MealEntity find(Document query) {
        Document document = collection.find(query).first();
        return document == null ? null : mealMapper.documentToEntity(document);
    }

    public boolean update(Document docId, Document doc) {
        UpdateResult result = collection.updateOne(docId, doc);
        return result.getModifiedCount() == 1;
    }

    public List<MealEntity> getAll() {
        List<Document> documents = collection.find().into(new ArrayList<>());
        List<MealEntity> mealEntities = new ArrayList<>();
        for (Document document : documents) {
            mealEntities.add(mealMapper.documentToEntity(document));
        }
        return mealEntities;
    }

    public boolean add(Document document) {
        InsertOneResult result = collection.insertOne(document);
        return result.getInsertedId() != null;
    }

    public boolean delete(Document document) {
        DeleteResult result = collection.deleteOne(document);
        return result.getDeletedCount() == 1;
    }

    public MongoDatabase getConnexion(){
//        String connectionString = "mongodb+srv://jamidev:uz2paZc5Dsii0FVY@mealgeniusapi.j6cu3vg.mongodb.net/?retryWrites=true&w=majority";
        String connectionString = "mongodb://localhost:27017";

        MongoClient mongoClient = MongoClients.create(connectionString);
        MongoDatabase database = mongoClient.getDatabase("mealgenius");
        MongoCollection<Document> collection = database.getCollection("users");
        return database;
    }
}
