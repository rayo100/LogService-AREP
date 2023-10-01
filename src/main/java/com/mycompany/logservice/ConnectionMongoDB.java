/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.logservice;

import com.mongodb.MongoException;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import org.bson.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ConnectionMongoDB {
    //mongodb://localhost:27017
    private String url = "mongodb://localhost:27017";
    private static com.mongodb.client.MongoClient mongoClient = null;
    private static MongoDatabase mongoDatabase = null;
    private static MongoCollection<Document> mongoCollection;
    
    public void createConnection() {
        try {
            mongoClient = MongoClients.create(url);
            mongoDatabase = mongoClient.getDatabase("LogArep");
            mongoCollection = mongoDatabase.getCollection("Messages");
        } catch (MongoException ex) {
            System.out.println(ex);
        }
    }

    public void addDocumentToDB(String value){
        System.out.println("VALUE: " + value);
        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/YYYY 'at' HH:mm:ss"));
        Document document = new Document("string", value).append("date", currentDate);
        System.out.println("DOCUMENTO:" + document.toString());
        mongoCollection.insertOne(document);
    }
    
    public List<Document> getListDocuments(){
        List<Document> documents = new ArrayList<>();
        try (MongoCursor<Document> cursor = mongoCollection.find().limit(10).sort(Sorts.descending("date")).iterator()) {
            while (cursor.hasNext()) {
                documents.add(cursor.next());
            }
        }
        return documents;
    }

    public void closeConnection() {
        mongoClient.close();
    }
}
