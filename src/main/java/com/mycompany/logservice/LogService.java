/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.logservice;

import com.google.gson.Gson;
import java.util.List;
import org.bson.Document;

import static spark.Spark.*;

/**
 *
 * @author cesar.vasquez-m
 */
public class LogService {

    public static void main(String[] args) {
        System.out.println("Log Service Server");
        port(getPort());
        get("/logservice", (req, pesp) -> {
            String val = req.queryParams("value");
            Gson gson = new Gson();
            return gson.toJson(LogMessage(val));
        });
    }
    
    
    private static int getPort() {
        if(System.getenv("PORT") != null){
           return Integer.parseInt(System.getenv("PORT"));
        }
        return 4568;
    }

    private static List<Document> LogMessage(String value) {
        ConnectionMongoDB mongoConnection = new ConnectionMongoDB();
        mongoConnection.createConnection();
        mongoConnection.addDocumentToDB(value);
        List<Document> listDocuments = mongoConnection.getListDocuments();
        mongoConnection.closeConnection();
        return listDocuments;
    }
}
