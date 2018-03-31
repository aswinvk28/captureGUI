/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.convertdrawing.capture.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 *
 * @author aswin.vijayakumar
 */
public class DbConnection {
    
    private Connection database;
    
    DbConnection(Properties defaultProperties)
    {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            this.database = DriverManager
                    .getConnection(
                            "jdbc:derby://localhost:1527/" + defaultProperties.getProperty("dbname"), 
                            defaultProperties.getProperty("user"), 
                            defaultProperties.getProperty("pass"));
            this.database.prepareStatement("SET SCHEMA APP").execute();
        } catch(Exception exception) {
            exception.printStackTrace();
        }
    }
    
    public Connection getConnection()
    {
        return this.database;
    }
}
