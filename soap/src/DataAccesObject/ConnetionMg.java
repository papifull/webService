package DataAccesObject;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
public class ConnetionMg {
    /**
     * constructeur connexion avec notre bd Mongo db
     */
    public ConnetionMg() {
        ConnectionString connectionString = new ConnectionString(this.getConfig());
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        this.database = mongoClient.getDatabase("Blog").withCodecRegistry(pojoCodecRegistry);
    }

    /**
     * insert un user dans la base dedonnnees
     * @param collection
     * @param json
     */
    public void insertDocument(String collection,String json) {
        Document payload = Document.parse(json);
        this.database
                .getCollection(collection)
                .insertOne(payload);
    }

    public FindIterable<Document> findDocument(String collection) {
        return this.database.getCollection(collection).find();
    }

    /**
     *
     * @param collection
     * @param json
     * @return
     */
    public FindIterable<Document> findDocument(String collection, String json) {
        return this.database.getCollection(collection).find(Document.parse(json));
    }

    /**
     * suppression d'un user
     * @param collection
     * @param id
     */
    public void deleteDocument(String collection, String id) {
        this.database.getCollection(collection)
                .deleteOne(new Document("_id", new ObjectId(id)));
    }
}
