/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.logservice;

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
            return LogMessage(val);
        });
    }
    
    
    private static int getPort() {
        if(System.getenv("PORT") != null){
           return Integer.parseInt(System.getenv("PORT"));
        }
        return 4568;
    }

    private static String LogMessage(String value) {
        
        return "mensages";
    }
}
