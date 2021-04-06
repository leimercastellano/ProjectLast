package connections;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import resources.ReadProperties;

public class ConnectionMongo implements Connection {

    MongoDatabase database;
    MongoClient mongoClient;

    /**
     * @author Roger Vinyals
     *
     * El metode es connecta a la base de dades amb MongoDB
     */
    @Override
    public void connect() {
        ReadProperties readProperties = new ReadProperties();
        String mongoDB = readProperties.getDatabase();

        try  {
            mongoClient = MongoClients.create();
            database = mongoClient.getDatabase(mongoDB);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @author Roger Vinyals
     *
     * El metode tanca la connexió amb la base de dades
     */
    @Override
    public void close() {
        mongoClient.close();
    }

    /**
     * @author Roger Vinyals
     *
     * @return retorna la base de dades, per poguer-la utilitzar als diferents metodes
     */
    public MongoDatabase getDatabase() {
        return database;
    }
}
