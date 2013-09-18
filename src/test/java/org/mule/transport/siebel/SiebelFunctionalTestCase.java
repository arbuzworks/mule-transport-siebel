/*
 * $Id: $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.transport.siebel;

import com.siebel.data.SiebelPropertySet;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.transport.siebel.transformers.SiebelPropertySetToEncodedStringTestCase;
import org.mule.transport.siebel.transformers.SiebelPropertySetToXmlStringRemoteTestCase;
import org.mule.transport.siebel.transformers.SiebelPropertySetToXmlStringTestCase;

public class SiebelFunctionalTestCase extends AbstractSiebelFunctionalTestCase
{

    public SiebelFunctionalTestCase()
    {
        super();
        this.setDisposeManagerPerSuite(true);
    }

    @Override
    protected void doSetUp() throws Exception
    {
        super.doSetUp();
    }

    protected String getConfigResources()
    {
        return "siebel-functional-test.xml";
    }

    public void testEcho() throws Exception
    {
        SiebelPropertySet payload = new SiebelPropertySet();
        payload.setValue("Please echo this");

        MuleMessage reply = client.send("vm://echo", new DefaultMuleMessage(payload, muleContext));

        assertTrue(((SiebelPropertySet)reply.getPayload()).getValue().equalsIgnoreCase(payload.getValue()));
    }

    public void testEncodedString() throws Exception
    {
    	SiebelPropertySetToEncodedStringTestCase tc = new SiebelPropertySetToEncodedStringTestCase();
    	String str = (String)tc.getResultData();
    	
        MuleMessage reply = client.send("vm://encodedString", new DefaultMuleMessage(str, muleContext));

        SiebelPropertySet propSet = (SiebelPropertySet)reply.getPayload();
		assertTrue(str.equals(propSet.encodeAsString()));
    }
    
    public void testLocalXml() throws Exception
    {
    	SiebelPropertySetToXmlStringTestCase tc = new SiebelPropertySetToXmlStringTestCase();
    	String str = (String)tc.getResultData();
    	
        MuleMessage reply = client.send("vm://localXml", new DefaultMuleMessage(str, muleContext));

        SiebelPropertySet propSet = (SiebelPropertySet)reply.getPayload();
		assertTrue(tc.compareRoundtripResults(tc.getTestData(), propSet));
    }
    
    public void testRemoteXml() throws Exception
    {
    	SiebelPropertySetToXmlStringRemoteTestCase tc = new SiebelPropertySetToXmlStringRemoteTestCase();
    	String str = (String)tc.getResultData();
    	
        MuleMessage reply = client.send("vm://remoteXml", new DefaultMuleMessage(str, muleContext));

        SiebelPropertySet propSet = (SiebelPropertySet)reply.getPayload();
		assertTrue(tc.compareRoundtripResults(tc.getTestData(), propSet));
    }
}
