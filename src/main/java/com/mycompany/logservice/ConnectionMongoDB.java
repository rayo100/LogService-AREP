/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.logservice;

import com.mongodb.MongoException;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConnectionMongoDB {
    //
    private String url = "200.118.63.83:27017";
    private MongoClient mongoClient = null;
    private MongoDatabase mongoDatabase = null;
    private MongoCollection<Document> mongoCollection;

    public void createConnection() {
        try {
            mongoClient = new MongoClient(url);
            mongoDatabase = mongoClient.getDatabase("arep");
            mongoCollection = mongoDatabase.getCollection("messages");
        } catch (MongoException ex) {
            System.out.println(ex);
        }
    }

    public List<String> getDocumentsColecction() {
        ArrayList<String> data = new ArrayList<>();
        for (Document d: mongoCollection.find()) {
            System.out.println(d.toJson());
            data.add(d.toJson());
        }
        return data.subList(Math.max(data.size() - 10, 0), data.size());
    }

    public void addDocument(String text) {
        Document myDocument = new Document();
        myDocument.put("text", text);
        mongoCollection.insertOne(myDocument);
    }

    public void closeConnection() {
        this.mongoClient.close();
    }
}
