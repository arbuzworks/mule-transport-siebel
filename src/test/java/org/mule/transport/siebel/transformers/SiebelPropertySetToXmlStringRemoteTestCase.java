/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.siebel.transformers;

import java.io.InputStream;
import java.util.Properties;

import org.mule.api.transformer.Transformer;
import org.mule.transport.siebel.SiebelConnector;

public class SiebelPropertySetToXmlStringRemoteTestCase extends AbstractSiebelPropertySetToStringTestCase 
{
    
	private SiebelConnector siebelConnector;
	
    protected void doSetUp() throws Exception
    {
    	super.doSetUp();
    }
    
    @Override
    protected void doTearDown() throws Exception
    {
    	if (siebelConnector != null) {
	    	siebelConnector.dispose();
	    	siebelConnector = null;
    	}
    	super.doTearDown();
    }

    @Override
    public Object getResultData()
    {
    	return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><?Siebel-Property-Set EscapeNames=\"true\"?><PropertySet Attribute_spc1=\"Calendar and Activities\" number_pnd1=\"N\">Body<Date_spcrange StartDates=\"2011-07-14 11:11:11\" EndDates=\"2011-07-14 12:11:11\"></Date_spcrange><Attachment sblValueVariant=\"CCFVT_MEMBLOCK\">VEVTVA==</Attachment></PropertySet>";
    }
    
    @Override
    public Transformer getTransformer() throws Exception
    {
    	SiebelPropertySetToXmlStringRemote trans = new SiebelPropertySetToXmlStringRemote();
    	trans.setSiebelConnector(getSiebelConnector());
        initialiseObject(trans);
        return trans;
    }

    @Override
    public Transformer getRoundTripTransformer() throws Exception
    {
    	XmlStringToSiebelPropertySetRemote trans = new XmlStringToSiebelPropertySetRemote();
    	trans.setSiebelConnector(getSiebelConnector());
    	initialiseObject(trans);
    	return trans;
    }
    
    private SiebelConnector getSiebelConnector() throws Exception
    {
    	if (siebelConnector == null)
    	{
	    	Properties properties = new Properties();
	
	        InputStream propertiesFile = this.getClass().getClassLoader().getResourceAsStream("test.properties");
	        properties.load(propertiesFile);
	
	        String name = properties.getProperty("user");
	        String password = properties.getProperty("password");
	        String host = properties.getProperty("host");
	        String port = properties.getProperty("port");
	        String path = properties.getProperty("url_path");
	
	        
	        siebelConnector = new SiebelConnector(muleContext);
	        siebelConnector.setName("SiebelConnector");
	        siebelConnector.setUser(name);
	        siebelConnector.setPassword(password);
	        siebelConnector.setHost(host);
	        siebelConnector.setPort(port);
	        siebelConnector.setPath(path);
	        
	        siebelConnector.initialise();
	        siebelConnector.connect();
    	}
    	
        return siebelConnector;
    }
}
