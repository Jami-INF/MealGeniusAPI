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

    @Inject
    MongoClient mongoClient;
    private static final String DATABASE_NAME = "mealgenius";

    private UserMapper userMapper = new UserMapper();

    private MongoCollection<Document> collection = getConnexion().getCollection("users");;

/*    public void getCollection(){
        mongoClient.getDatabase("mealgenius").getCollection("users");
    }

    public void addUser(UserEntity userEntity) {
        Document userDoc = UserMapper.userToDocument(userEntity);
        collection.insertOne(userDoc);
    }*/

    public UserEntity find(Document query) {
        Document document = collection.find(query).first();
        return document == null ? null : userMapper.documentToEntity(document);
    }

    public boolean update(Document docId, Document doc) {
        UpdateResult result = collection.updateOne(docId, doc);
        return result.getModifiedCount() == 1;
    }

    public List<UserEntity> getAll() {
        List<Document> documents = collection.find().into(new ArrayList<>());
        List<UserEntity> userEntities = new ArrayList<>();
        for (Document document : documents) {
            userEntities.add(userMapper.documentToEntity(document));
        }
        return userEntities;
    }

    public boolean add(Document document) {
        InsertOneResult result = collection.insertOne(document);
        return result.getInsertedId() != null;
    }

    public boolean delete(Document document) {
        DeleteResult result = collection.deleteOne(document);
        return result.getDeletedCount() == 1;
    }

/*    public void delete(UserEntity userEntity) {
        Document query = new Document("_id", userEntity.getId());
        collection.deleteOne(query);
    }*/

/*    public void update(UserEntity userEntity) {
        Document query = new Document("_id", userEntity.getId());
        collection.updateOne(query, new Document("$set", UserMapper.userToDocument(userEntity)));
    }*/

/*    public List<UserEntity> listAll() {
        List<Document> documents = collection.find().into(new ArrayList<>());
        List<UserEntity> userEntities = new ArrayList<>();
        for (Document document : documents) {
            userEntities.add(userMapper.documentToEntity(document));
        }
        return userEntities;
    }*/

/*    public void initDB(){
        //ajoute un utilisateur à la collection mealgenius
        Document userDocument = new Document()
                .append("firstname", "John")
                .append("lastname", "Doe")
                .append("email", "John@gmail.com")
                .append("password", "password");
        collection.insertOne(userDocument);

    }*/

    public MongoDatabase getConnexion(){
        String connectionString = "mongodb+srv://jamidev:uz2paZc5Dsii0FVY@mealgeniusapi.j6cu3vg.mongodb.net/?retryWrites=true&w=majority";
//        String connectionString = "mongodb://localhost:27017";

        MongoClient mongoClient = MongoClients.create(connectionString);
        MongoDatabase database = mongoClient.getDatabase("mealgenius");
        MongoCollection<Document> collection = database.getCollection("users");
        return database;
    }
}
