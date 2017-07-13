package buaa.act.baseService;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by luckcul on 17-6-19.
 */

/**
 * mongoDB client 内部实现了连接池，只需要自己唯一实例化。 单例模式
 * */

public class MongoConnection {
    private static final MongoClient mongoClient = new MongoClient("192.168.7.113", 30000);
//    private MongoDatabase mongoDatabase = init();


    public static MongoClient getMongoClient(){
        return mongoClient;
    }


}


//public class MongoConnection {
//    private MongoClient mongoClient = null;
//    private MongoDatabase mongoDatabase = null;
//    public MongoConnection(String dataBase){
//        this.mongoClient = new MongoClient("192.168.7.113", 30000);
//        this.mongoDatabase = mongoClient.getDatabase(dataBase);
//    }
//
//    public void setMongoClient(MongoClient mongoClient) {
//        this.mongoClient = mongoClient;
//    }
//
//    public void setMongoDatabase(MongoDatabase mongoDatabase) {
//        this.mongoDatabase = mongoDatabase;
//    }
//
//    public MongoClient getMongoClient() {
//        return mongoClient;
//    }
//
//    public MongoDatabase getMongoDatabase() {
//        return mongoDatabase;
//    }
//
//    public MongoCollection<Document> getCollection(String collection){
//        return mongoDatabase.getCollection(collection);
//    }
//    public void close(){
//        if(mongoClient != null) {
//            mongoClient.close();
//        }
//    }
//
////    public static void main(String args[]) {
////        MongoConnection connection = new MongoConnection("stackoverflow");
////        MongoCollection<Document> collection = connection.getCollection("User");
////        Document document = collection.find(eq("userID", 1)).first();
////        System.out.println(document.toJson());
////        connection.close();
////    }
//}
