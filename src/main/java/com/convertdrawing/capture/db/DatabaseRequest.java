/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.convertdrawing.capture.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author aswin.vijayakumar
 */
public class DatabaseRequest {
    
    private Connection conn;
    public static enum Specification {
        STATUS_INCOMPLETE("Incomplete"), 
        STATUS_SUBMITTED("Submitted"), 
        STATUS_COMPLETE("Completed");
        
        private String status;
        
        Specification(String status)
        {
            this.status = status;
        }
        
        public String getStatus()
        {
            return this.status;
        }
    };
    
    public DatabaseRequest(DbConnection conn)
    {
        this.conn = conn.getConnection();
    }
    
    /**
     * 
     * @param projectId
     * @return
     * @throws SQLException 
     */
    public ResultSet getRequest(int projectId) throws SQLException
    {
        ResultSet results;
        
        try {
            PreparedStatement stmt = this.conn.prepareStatement("SELECT * FROM request WHERE project_id = ?");
            stmt.setInt(1, projectId);
            results = stmt.executeQuery();
        } catch(SQLException exception) {
            throw exception;
        }
        
        return results;
    }
    
    /**
     * 
     * @param projectCode
     * @param unitCode
     * @return
     * @throws SQLException 
     */
    public ResultSet getProjectDetails(String projectCode, String unitCode) throws SQLException
    {
        ResultSet results;
        
        try {
            String sql = "SELECT * FROM project WHERE project_code = ? AND unit_code = ?";
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, projectCode);
            stmt.setString(2, unitCode);
            results = stmt.executeQuery();
        } catch(SQLException exception) {
            throw exception;
        }
        
        return results;
    }
    
    public boolean createProject(String projectCode, String unitCode) throws SQLException
    {
        boolean status = false;
        
        this.conn.setAutoCommit(false);
        try {
            this.conn.setSavepoint();
            String sql = "INSERT INTO project (project_code, unit_code, status) VALUES (?,?,?)";
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, projectCode);
            stmt.setString(2, unitCode);
            stmt.setString(3, Specification.STATUS_INCOMPLETE.getStatus());
            if(stmt.executeUpdate() == 1) {
                status = true;
            }
        } catch(SQLException exception) {
            this.conn.rollback();
            throw exception;
        } finally {
            this.conn.commit();
            this.conn.setAutoCommit(true);
        }
        
        return status;
    }
    
    public ResultSet getMetrics() throws SQLException
    {
        ResultSet results;
        
        try {
            PreparedStatement stmt = this.conn.prepareStatement("SELECT * FROM variable WHERE parameter = ?");
            stmt.setString(1, "metrics");
            results = stmt.executeQuery();
        } catch(SQLException exception) {
            throw exception;
        }
        
        return results;
    }
    
    public ResultSet getFocalPoints() throws SQLException
    {
        ResultSet results;
        
        try {
            PreparedStatement stmt = this.conn.prepareStatement("SELECT * FROM variable WHERE parameter = ?");
            stmt.setString(1, "focal_point");
            results = stmt.executeQuery();
        } catch(SQLException exception) {
            throw exception;
        }
        
        return results;
    }
    
    public ResultSet getMenu(String parameter) throws SQLException
    {
        ResultSet results;
        
        try {
            PreparedStatement stmt = this.conn.prepareStatement("SELECT * FROM variable WHERE parameter = ?");
            stmt.setString(1, parameter);
            results = stmt.executeQuery();
        } catch(SQLException exception) {
            throw exception;
        }
        
        return results;
    }
    
    public short createVariables(String parameter, String[] values) throws SQLException
    {
        short status = 0;
        
        this.conn.setAutoCommit(false);
        try {
            this.conn.setSavepoint();
            String sql = "INSERT INTO variable (parameter, value) VALUES (?,?)";
            for(String value : values) {
                PreparedStatement stmt = this.conn.prepareStatement(sql);
                stmt.setString(1, parameter);
                stmt.setString(2, value);
                status += stmt.executeUpdate();
            }
        } catch(SQLException exception) {
            this.conn.rollback();
            throw exception;
        } finally {
            this.conn.commit();
            this.conn.setAutoCommit(true);
        }
        
        return status;
    }
    
    public ResultSet getPlans() throws SQLException
    {
        ResultSet results;
        
        try {
            PreparedStatement stmt = this.conn.prepareStatement("SELECT * FROM floor");
            results = stmt.executeQuery();
        } catch(SQLException exception) {
            throw exception;
        }
        
        return results;
    }
    
    public int getRegions() throws SQLException
    {
        ResultSet results;
        int regions;
        
        try {
            PreparedStatement stmt = this.conn.prepareStatement("SELECT * FROM variable WHERE parameter = ?");
            stmt.setString(1, "region_limit");
            results = stmt.executeQuery();
            results.next();
            String value = results.getString("value");
            regions = Integer.parseInt(value);
        } catch(SQLException exception) {
            throw exception;
        }
        
        return regions;
    }
}
