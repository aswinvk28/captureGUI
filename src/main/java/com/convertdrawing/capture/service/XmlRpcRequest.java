/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.convertdrawing.capture.service;

import com.convertdrawing.capture.service.definition.Application;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.ArrayList;
import org.apache.xmlrpc.XmlRpcException;

/**
 *
 * @author aswin.vijayakumar
 */
public class XmlRpcRequest extends AbstractXmlRpcRequest {
    
    public XmlRpcRequest() throws FileNotFoundException, IOException
    {
        super();
    }
    
    public String ObtainSession(boolean refresh, String projectCode, String unitCode, Instant time, String sessionParam) throws Exception
    {
        ArrayList params = new ArrayList();
        String sessionId;
        
        params.add(refresh);
        params.add(projectCode);
        params.add(unitCode);
        if(time != null) {
            params.add(time.getEpochSecond());
        } else {
            params.add(-1);
        }
        if(sessionParam != null) {
            params.add(sessionParam);
        } else {
            params.add("");
        }
        
        try {
            sessionId = (String) rpClient.execute("obtainSession", params);
        } catch(XmlRpcException exception) {
            throw exception;
        }
        return sessionId;
    }
    
    public boolean ExpireSession(String sessionId) throws XmlRpcException
    {
        ArrayList params = new ArrayList();
        boolean result;
        
        params.add(sessionId);
        
        try {
            result = (boolean) rpClient.execute("expireSession", params);
        } catch(XmlRpcException exception) {
            throw exception;
        }
        return result;
    }
    
    public int CreateProjectShortCode(String projectCode, String unitCode, String password) throws XmlRpcException
    {
        ArrayList params = new ArrayList();
        int result;
        
        params.add(projectCode);
        params.add(unitCode);
        params.add(password);
        
        try {
            result = (int) rpClient.execute("createProjectShortCode", params);
        } catch(XmlRpcException exception) {
            throw exception;
        }
        return result;
    }
    
    public boolean VerifyProjectShortCode(String projectCode, String unitCode, String password, String sessionId) throws XmlRpcException
    {
        ArrayList params = new ArrayList();
        boolean result;
        
        params.add(projectCode);
        params.add(unitCode);
        params.add(password);
        params.add(sessionId);
        
        try {
            result = (boolean) rpClient.execute("verifyProjectShortCode", params);
        } catch(XmlRpcException exception) {
            throw exception;
        }
        return result;
    }
    
    public String[] ListFocalPoints(String sessionId, Application application) throws XmlRpcException
    {
        HashMap<String, String> map = new HashMap<String, String>();
        if (application != null) {
            map.put("App", application.App);
            map.put("Length", application.Length);
        }
        int length = 100;
        ArrayList params = new ArrayList();
        params.add(sessionId);
        if (application != null) {
            params.add(map);
            if(application.Length == "*") {
                length = 100;
            } else {
                length = Integer.parseInt(application.Length);
            }
        }
        String[] resultList = new String[length];
        
        Object[] result = (Object[]) rpClient.execute("listFocalPoints", params);
        for(int i=0; i<result.length; i++) {
            resultList[i] = (String) result[i];
        }
        
        return resultList;
    }
    
    public Object ListMetrics(String sessionId, String filter) throws XmlRpcException
    {
        ArrayList params = new ArrayList();
        params.add(sessionId);
        params.add(filter);
        
        Object[] resultList = new String[50];
        if(filter != "all") {
            Object[] result = (Object[]) rpClient.execute("listMetrics", params);
            for(int i=0; i<result.length; i++) {
                resultList[i] = (String) result[i];
            }
            return resultList;
        } else {
            HashMap<String, String[]> result = (HashMap<String, String[]>) rpClient.execute("listMetrics", params);
            return result;
        }
    }
    
}
