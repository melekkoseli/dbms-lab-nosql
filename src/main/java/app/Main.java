package app;

import static spark.Spark.*;
import com.google.gson.Gson;
import app.store.*;

public class Main {
    public static void main(String[] args) {
        port(8080);
        Gson gson = new Gson();

        System.out.println("Initializing stores...");
        
        try {
            RedisStore.init();
            System.out.println("Redis initialized successfully!");
        } catch (Exception e) {
            System.err.println("Redis initialization failed: " + e.getMessage());
        }

        try {
            HazelcastStore.init();
            System.out.println("Hazelcast initialized successfully!");
        } catch (Exception e) {
            System.err.println("Hazelcast initialization failed: " + e.getMessage());
        }

        try {
            MongoStore.init();
            System.out.println("MongoDB initialized successfully!");
        } catch (Exception e) {
            System.err.println("MongoDB initialization failed: " + e.getMessage());
        }

        get("/nosql-lab-rd/student_no=:id", (req, res) -> {
            res.type("application/json");
            return gson.toJson(RedisStore.get(req.params(":id")));
        });

        get("/nosql-lab-hz/student_no=:id", (req, res) -> {
            res.type("application/json");
            return gson.toJson(HazelcastStore.get(req.params(":id")));
        });

        get("/nosql-lab-mon/student_no=:id", (req, res) -> {
            res.type("application/json");
            return gson.toJson(MongoStore.get(req.params(":id")));
        });

        get("/nosql-lab-rd/student_no/:id", (req, res) -> {
            res.type("application/json");
            return gson.toJson(RedisStore.get(req.params(":id")));
        });

        get("/nosql-lab-hz/student_no/:id", (req, res) -> {
            res.type("application/json");
            return gson.toJson(HazelcastStore.get(req.params(":id")));
        });

        get("/nosql-lab-mon/student_no/:id", (req, res) -> {
            res.type("application/json");
            return gson.toJson(MongoStore.get(req.params(":id")));
        });



        System.out.println("Server started on port 8080");
        System.out.println("Endpoints:");
        System.out.println("  - http://localhost:8080/nosql-lab-rd/student_no=2025000001");
        System.out.println("  - http://localhost:8080/nosql-lab-hz/student_no=2025000001");
        System.out.println("  - http://localhost:8080/nosql-lab-mon/student_no=2025000001");
    }
}
