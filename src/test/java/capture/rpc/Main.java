/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capture.rpc;

import junit.framework.TestCase;

/**
 *
 * @author aswin.vijayakumar
 */
public class Main {
    
    public static void main(String[] args)
    {
        TestCase test = new XmlRpcRequestTest("testObtainSession");
        
        test.run();
    }
    
}
