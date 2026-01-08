package app.store;

import com.mongodb.client.*;
import org.bson.Document;
import app.model.Student;
import com.google.gson.Gson;

public class MongoStore {
    static MongoClient client;
    static MongoCollection<Document> collection;
    static Gson gson = new Gson();

    public static void init() {
        client = MongoClients.create("mongodb://localhost:27017");
        collection = client.getDatabase("nosqllab").getCollection("ogrenciler");
        
        // Eski kayıtları temizle
        collection.drop();
        
        System.out.println("Populating MongoDB with 10,000 records...");
        for (int i = 0; i < 10000; i++) {
            String id = "2025" + String.format("%06d", i);
            Student s = new Student(id, "Ad Soyad " + i, "Bilgisayar");
            collection.insertOne(Document.parse(gson.toJson(s)));
            
            if ((i + 1) % 1000 == 0) {
                System.out.println("MongoDB: " + (i + 1) + " records inserted");
            }
        }
        System.out.println("MongoDB population complete!");
    }

    public static Student get(String id) {
        Document doc = collection.find(new Document("ogrenciNo", id)).first();
        return doc != null ? gson.fromJson(doc.toJson(), Student.class) : null;
    }
}
