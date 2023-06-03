package repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import entity.FoodEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import mapper.FoodMapper;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Default
public class FoodRepository {

    @Inject
    MongoClient mongoClient;
    private static final String DATABASE_NAME = "mealgenius";

    private FoodMapper foodMapper = new FoodMapper();

    private MongoCollection<Document> collection = getConnexion().getCollection("foods");

    public void getCollection(){
        mongoClient.getDatabase("mealgenius").getCollection("foods");
    }

    public void addFood(FoodEntity foodEntity) {
        Document foodDoc = FoodMapper.foodToDocument(foodEntity);
        collection.insertOne(foodDoc);
    }

    public FoodEntity findById(String id) {
        Document query = new Document("_id", new ObjectId(id));
        Document foodDoc = collection.find(query).first();
        if (foodDoc != null) {
            return foodMapper.documentToFood(foodDoc);
        }
        return null;
    }

    public void delete(FoodEntity foodEntity) {
        Document query = new Document("_id", foodEntity.getId());
        collection.deleteOne(query);
    }

    public void update(FoodEntity foodEntity) {
        Document query = new Document("_id", foodEntity.getId());
       collection.updateOne(query, new Document("$set", FoodMapper.foodToDocument(foodEntity)));
    }

    public List<FoodEntity> listAll() {
        List<Document> documents = collection.find().into(new ArrayList<>());
        List<FoodRepository> foods = new ArrayList<>();
        for (Document document : documents) {
            foods.add(foodMapper.documentToFood(document));
        }
        return foods;
    }

    public void initDB() {
        Document foodDoc = new Document()
                .append("name", "Pomme");
        collection.insertOne(foodDoc);
    }

    public MongoDatabase getConnexion(){
        String connectionString = "mongodb+srv://jamidev:uz2paZc5Dsii0FVY@mealgeniusapi.j6cu3vg.mongodb.net/?retryWrites=true&w=majority";
//        String connectionString = "mongodb://localhost:27017";

        MongoClient mongoClient = MongoClients.create(connectionString);
        MongoDatabase database = mongoClient.getDatabase("mealgenius");
        MongoCollection<Document> collection = database.getCollection("users");
        return database;
    }
}
