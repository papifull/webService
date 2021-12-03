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
public class AccesBD {
    /**
     * constructeur connexion avec notre bd Mongo db
     * */
    public MongoDatabase database;
    public AccesBD() {
        ConnectionString connectionString = new ConnectionString(this.getConfig());
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        this.database = mongoClient.getDatabase("Blog").withCodecRegistry(pojoCodecRegistry);
    }

    /**
     * ./
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
    /**
     *
     * @return
     */
    public String getConfig() {
        String mongodb_url = "";
        try {
            String configPath = "src/application.properties"; //TODO: pass env in as parameter
            System.out.println(configPath);
            final File configFile = new File(configPath);
            FileInputStream input = new FileInputStream(configFile);
            Properties prop = new Properties();
            prop.load(input);
            mongodb_url = prop.getProperty("MONGODB_URL");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return  mongodb_url;
    }

}

