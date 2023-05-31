package repository;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Default
public class UserRepository {

    private static final String DATABASE_NAME = "mealgenius";
    @Inject
    MongoClient mongoClient;

    private MongoCollection<Document> collection = getConnexion().getCollection("users");;

    public void getCollection(){
        mongoClient.getDatabase("test").getCollection("users");
    }

    public void persist(User user) {
        Document doc = new Document()
                .append("firstname", user.getFirstName())
                .append("lastname", user.getLastName())
                .append("email", user.getEmail())
                .append("password", user.getPassword());
        collection.insertOne(doc);
        user.setId(doc.getObjectId("_id"));
    }

    public User findById(String id) {
        Document query = new Document("_id", new ObjectId(id));
        Document userDoc = collection.find(query).first();
        if (userDoc != null) {
            return documentToUser(userDoc);
        }
        return null;
    }

    public User findByUsername(String username) {
        Document query = new Document("username", username);
        Document userDoc = collection.find(query).first();
        if (userDoc != null) {
            return documentToUser(userDoc);
        }
        return null;
    }

    public void delete(User user) {
        Document query = new Document("_id", user.getId());
        collection.deleteOne(query);
    }

    public void update(User user) {
        Document query = new Document("_id", user.getId());
        Document doc = new Document()
                .append("firstname", user.getFirstName())
                .append("lastname", user.getLastName())
                .append("email", user.getEmail())
                .append("password", user.getPassword());
        collection.updateOne(query, new Document("$set", doc));
    }

    public List<User> listAll() {
        List<Document> documents = collection.find().into(new ArrayList<>());
        List<User> users = new ArrayList<>();
        for (Document document : documents) {
            users.add(documentToUser(document));
        }
        return users;
    }

    private User documentToUser(Document document) {
        User user = new User(
                document.getObjectId("_id"),
                document.getString("firstname"),
                document.getString("lastname"),
                document.getString("email"),
                document.getString("password")
        );
        return user;
    }

    public void initDB(){
        //ajoute un utilisateur à la collection mealgenius
        Document userDocument = new Document()
                .append("firstname", "John")
                .append("lastname", "Doe")
                .append("email", "John@gmail.com")
                .append("password", "password");
        collection.insertOne(userDocument);

    }


//    public void initDB() {
//        // Créer une instance du client MongoDB
//        MongoClientSettings settings = MongoClientSettings.builder()
//                .applyConnectionString(new ConnectionString("mongodb://localhost:27017"))
//                .build();
//        try (MongoClient client = MongoClients.create(settings)) {
//            // Accéder à la base de données
//            MongoDatabase database = client.getDatabase(DATABASE_NAME);
//
//            // Vérifier si la collection "users" existe déjà
//            if (!collectionExists(database, "users")) {
//                // Créer la collection "users"
//                database.createCollection("users");
//
//                // Ajouter un utilisateur au document "mealgenius"
//                MongoCollection<Document> usersCollection = database.getCollection("users");
//                Document userDocument = new Document()
//                        .append("firstName", "John")
//                        .append("lastName", "Doe")
//                        .append("email", "johndoe@gmail.com")
//                        .append("password", "password");
//                usersCollection.insertOne(userDocument);
//
//            }
//        }
//    }

    public MongoDatabase getConnexion(){
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("mealgenius");
        MongoCollection<Document> collection = database.getCollection("users");
        return database;
    }


//private boolean collectionExists(MongoDatabase database, String collectionName) {
//        for (String name : database.listCollectionNames()) {
//            if (name.equals(collectionName)) {
//                return true;
//            }
//        }
//        return false;
//    }
}
