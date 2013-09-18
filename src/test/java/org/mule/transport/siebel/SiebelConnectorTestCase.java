/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.siebel;

import java.io.InputStream;
import java.util.Properties;

import com.siebel.data.SiebelPropertySet;
import org.mule.api.transport.Connector;
import org.mule.transport.AbstractConnectorTestCase;

public class SiebelConnectorTestCase extends AbstractConnectorTestCase
{

    @Override
    public Connector createConnector() throws Exception
    {
    	Properties properties = new Properties();
    	
        InputStream propertiesFile = this.getClass().getClassLoader().getResourceAsStream("test.properties");
        properties.load(propertiesFile);

        String name = properties.getProperty("user");
        String password = properties.getProperty("password");
        String host = properties.getProperty("host");
        String port = properties.getProperty("port");
        String path = properties.getProperty("url_path");

        
        SiebelConnector connector = new SiebelConnector(muleContext);
        connector.setName("SiebelConnector");
        connector.setUser(name);
        connector.setPassword(password);
        connector.setHost(host);
        connector.setPort(port);
        connector.setPath(path);
                
        return connector;
    }

    @Override
    public String getTestEndpointURI()
    {
        return "siebel://SADMIN:talipwd@$localhost:2321/SBA_80/ESEObjMgr_enu?service=Workflow Utilities&method=Echo";
    }

    @Override
    public Object getValidMessage() throws Exception
    {
        SiebelPropertySet message = new SiebelPropertySet();

        message.setValue("Please echo this");

        return message;
    }


    public void testProperties() throws Exception
    {
        SiebelConnector connector = (SiebelConnector)getConnector();

        String host = "localhost";
        connector.setHost(host);

        assertEquals(host, connector.getHost());

        String port = "2321";
        connector.setPort(port);

        assertEquals(port, connector.getPort());

        String path = "/SBA_80/ESEObjMgr_enu";
        connector.setPath(path);

        assertEquals(path, connector.getPath());
    }
}
