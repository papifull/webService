package ws;
//
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

// pour coder des documents en json pour MongoDB
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.types.ObjectId;

// bd mongo
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;

import domaine.User;
//
import java.util.List;

import static com.mongodb.client.model.Projections.include;


@WebService(name="UserService")
/**
 *
 */
public class UserService {
    //variable
    private final MongoCollection<User> userCollection;
    //construteur
    public UserService(MongoDatabase database) {
        this.userCollection = database.getCollection("users", User.class);
    }
    // getUsers permet de recuperer user dans en utilisant la methode find
    // les users trouvent sont mis dans une liste
    // et cette liste est retourne

    /**
     *
     * @return list de user trouve
     */
    @WebMethod(operationName = "getUsers")
    public List<User> getUsers() {
        List<User> users = new ArrayList<User>();
        FindIterable<User> result = this.userCollection.find();
        result.forEach(users::add);
        return  users;
    }

    /**
     *
     * @param idUser id ud user
     * @return retour  la liste de users
     */
    @WebMethod(operationName = "getUser")
    public User getUser(@WebParam(name="idUser") String idUser) {
        FindIterable<User> result = this.userCollection
                .find(new Document("_id", new ObjectId(idUser)));
        return result.first();
    }

    /**
     *
     * @param payload
     * @return insert le new user dans la collection
     */
    @WebMethod(operationName = "postUser")
    public BsonValue postUser(@WebParam(name="payload")User payload) {
        return this.userCollection
                .insertOne(payload).getInsertedId();
    }

    /**
     * modifier un utilisateur
     * @param key
     * @param value
     * @param idUser
     * @return
     */
    @WebMethod(operationName = "updateUser")
    public BsonValue updateUser(
            @WebParam(name="key")String key,
            @WebParam(name="value")String value,
            @WebParam(name="idUser")String idUser
    ) {
        return this.userCollection.updateOne(
                new Document("_id", new ObjectId(idUser)),
                Updates.set(key, value)
        ).getUpsertedId();
    }

    @WebMethod(operationName = "deleteUser")
    public long deleteUser(@WebParam(name="idUser")String idUser) {
        return this.userCollection.deleteOne(new Document("_id", new ObjectId(idUser)))
                .getDeletedCount();
    }

    @WebMethod(operationName = "login")
    public String login(@WebParam(name="user")User user) {
        String logged = "logged";
        return logged;
    }

}