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

    private MongoCollection<Document> collection = getConnexion().getCollection("meals");

    public MealEntity find(Document query) {
        System.out.println("query : "+query);
        Document document = collection.find(query).first();
        System.out.println("document : "+document);
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

    public List<MealEntity> getAvailableMeals(String idUser) {
        // Recherche de l'utilisateur par son id pour obtenir la liste des ingrédients qu'il possède
        Document userQuery = new Document("_id", idUser);
        Document userDocument = getConnexion().getCollection("users").find(userQuery).first();

        // Récupération de la liste des ingrédients de l'utilisateur
        List<String> userIngredients = userDocument.get("ingredients", List.class);

        // Construction de la requête pour les meals possédant le plus d'ingrédients en commun avec l'utilisateur
        Document query = new Document("$lookup",
                new Document("from", "meals")
                        .append("localField", "ingredients.idFood")
                        .append("foreignField", "ingredients.idFood")
                        .append("as", "commonIngredients"))
                .append("$project",
                        new Document("_id", 1)
                                .append("name", 1)
                                .append("commonIngredientsCount",
                                        new Document("$size", "$commonIngredients")))
                .append("$match",
                        new Document("commonIngredientsCount",
                                new Document("$gte", userIngredients.size())))
                .append("$sort", new Document("commonIngredientsCount", -1))
                .append("$limit", 10);

        List<Document> documents = collection.aggregate(List.of(query)).into(new ArrayList<>());
        List<MealEntity> mealEntities = new ArrayList<>();
        for (Document document : documents) {
            mealEntities.add(MealMapper.documentToEntity(document));
        }
        return mealEntities;
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
