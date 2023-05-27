package repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import model.User;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Default
public class UserRepository {

    @Inject
    MongoClient mongoClient;

    private MongoCollection<Document> collection;

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
}
