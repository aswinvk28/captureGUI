/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.convertdrawing.capture.db;

import java.io.FileReader;
import java.util.Properties;

/**
 *
 * @author aswin.vijayakumar
 */
public class DatabaseRequestFactory {
    
    static DatabaseRequest request;
    
    public static DatabaseRequest createDatabaseRequest() throws Exception
    {
        if(request == null) {
            try {
                Properties defaultProperties = new Properties();
                defaultProperties.load(new FileReader("db/derby.properties"));
                DbConnection connection = new DbConnection(defaultProperties);
                request = new DatabaseRequest(connection);
            } catch(Exception exception) {
                throw exception;
            }
        }
        return request;
    }
    
}
