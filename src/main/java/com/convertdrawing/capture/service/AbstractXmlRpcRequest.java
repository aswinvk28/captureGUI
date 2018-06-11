/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.convertdrawing.capture.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

/**
 *
 * @author aswin.vijayakumar
 */
abstract public class AbstractXmlRpcRequest
{
    protected XmlRpcClient rpClient;
    
    public AbstractXmlRpcRequest() throws FileNotFoundException, IOException
    {
        Properties defaultProperties = new Properties();
        defaultProperties.load(new FileReader("config/xmlrpc.properties"));
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(
                new URL("http://" + 
                        defaultProperties.getProperty("host") + ":" + 
                        defaultProperties.getProperty("port") + "/XmlRpcService")
        );
        rpClient = new XmlRpcClient();
        rpClient.setConfig(config);
    }
}
