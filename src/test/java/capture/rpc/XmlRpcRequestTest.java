/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capture.rpc;

import com.convertdrawing.capture.service.XmlRpcRequest;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import junit.framework.Assert;
import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.Test;

/**
 *
 * @author aswin.vijayakumar
 */
public class XmlRpcRequestTest extends TestCase
{

    private XmlRpcRequest rpcRequest;
    private String projectCode;
    private String unitCode;
    private String password;
    
    private String[][] dataProvider = {{"project2", "unit2", "password"}};
    
    public XmlRpcRequestTest(String name)
    {
        super(name);
    }

    protected void setUp() throws IOException
    {
        rpcRequest = new XmlRpcRequest();
        projectCode = "xxx123";
        unitCode = "uuu_123";
        password = "123";
    }
    
    public void testObtainSession() throws Exception
    {
        try {
            String sessionId = rpcRequest.ObtainSession(true, projectCode, unitCode, null, null);
            Assert.assertNotNull("The session id is not valid", sessionId);
            System.out.println(sessionId);
        } catch(Exception e) {
            throw e;
        }
    }
    
    public void testCreateProject() throws Exception
    {
        try {
            String projectCode = dataProvider[0][0];
            String unitCode = dataProvider[0][1];
            String password = dataProvider[0][2];
            int result = rpcRequest.CreateProjectShortCode(projectCode, unitCode, password);
            Assert.assertEquals("There is an error in project creation", 1, result);
            System.out.println(result);
        } catch(Exception exception) {
            throw exception;
        }
    }
    
    public void testVerifyProject() throws Exception
    {
        try {
            String sessionId = rpcRequest.ObtainSession(true, projectCode, unitCode, null, null);
            boolean result = rpcRequest.VerifyProjectShortCode(projectCode, unitCode, password, sessionId);
            Assert.assertTrue("The session id verification is not successful", result);
        } catch(Exception exception) {
            throw exception;
        }
    }
    
    public void testExpireSession() throws Exception
    {
        try {
            String sessionId = rpcRequest.ObtainSession(true, projectCode, unitCode, null, null);
            boolean result = rpcRequest.ExpireSession(sessionId);
            Assert.assertTrue("The session has not been expired", result);
        } catch(Exception exception) {
            throw exception;
        }
    }
    
    public void testListMetrics() throws Exception
    {
        try {
            String sessionId = rpcRequest.ObtainSession(true, projectCode, unitCode, null, null);
            Object result = rpcRequest.ListMetrics(sessionId, "all");
            HashMap<String, String[]> map = (HashMap<String, String[]>) result;
            Assert.assertTrue("The list metrics for filter 'all' is invalid", map.size() > 0);
            Object result2 = rpcRequest.ListMetrics(sessionId, "circulation");
            Object[] array = (Object[]) result2;
            Assert.assertTrue("The list metrics for filter 'all' is invalid", array.length > 0);
        } catch(Exception exception) {
            throw exception;
        }
    }
    
    public void testListFocalPoints() throws Exception
    {
        try {
            String sessionId = rpcRequest.ObtainSession(true, projectCode, unitCode, null, null);
            String[] result = rpcRequest.ListFocalPoints(sessionId, null);
            Assert.assertTrue("The list metrics for filter 'all' is invalid", result.length > 0);
        } catch(Exception exception) {
            throw exception;
        }
    }
}
