package repository;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import entity.IngredientEntity;
import mapper.IngredientMapper;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class IngredientRepository {

    MongoClient mongoClient;
    private static final String DATABASE_NAME = "mealgenius";

    private IngredientMapper ingredientMapper = new IngredientMapper();

    private MongoCollection<Document> collection = getConnexion().getCollection("ingredients");

    public IngredientEntity find(Document query) {
        Document document = collection.find(query).first();
        return document == null ? null : ingredientMapper.documentToEntity(document);
    }

    public boolean update(Document docId, Document doc) {
        UpdateResult result = collection.updateOne(docId, doc);
        return result.getModifiedCount() == 1;
    }

    public List<IngredientEntity> getAll() {
        List<Document> documents = collection.find().into(new ArrayList<>());
        List<IngredientEntity> ingredientEntities = new ArrayList<>();
        for (Document document : documents) {
            ingredientEntities.add(ingredientMapper.documentToEntity(document));
        }
        return ingredientEntities;
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
        String connectionString = "mongodb+srv://jamidev:uz2paZc5Dsii0FVY@mealgeniusapi.j6cu3vg.mongodb.net/?retryWrites=true&w=majority";
//        String connectionString = "mongodb://localhost:27017";

        com.mongodb.client.MongoClient mongoClient = MongoClients.create(connectionString);
        MongoDatabase database = mongoClient.getDatabase("mealgenius");
        MongoCollection<Document> collection = database.getCollection("users");
        return database;
    }
}
