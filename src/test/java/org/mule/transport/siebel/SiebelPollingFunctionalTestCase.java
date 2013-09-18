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
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;

public class SiebelPollingFunctionalTestCase
    extends AbstractSiebelFunctionalTestCase
{
    public SiebelPollingFunctionalTestCase()
    {
        super();
        this.setDisposeManagerPerSuite(true);
    }

    protected String getConfigResources()
    {
        return "siebel-polling-functional-test.xml";
    }


    public void testPolling() throws Exception
    {
        MuleClient client = new MuleClient(muleContext);
        MuleMessage result = client.request("vm://toclient", 5000);
        assertNotNull(result);
        SiebelPropertySet properties = result.getPayload(SiebelPropertySet.class);
        assertEquals("Please", properties.getProperty("param0"));
        assertEquals("echo", properties.getProperty("param1"));
        assertEquals("me", properties.getProperty("param2"));
    }

    public void testEncodedString() throws Exception
    {
        MuleClient client = new MuleClient(muleContext);
        MuleMessage result = client.request("vm://encodedString", 5000);
        assertNotNull(result);
        Object o = result.getPayload();
        assertNotNull(o);
        assertTrue(o instanceof String);
    }
    
    public void testLocalXml() throws Exception
    {
        MuleClient client = new MuleClient(muleContext);
        MuleMessage result = client.request("vm://localXml", 5000);
        assertNotNull(result);
        Object o = result.getPayload();
        assertNotNull(o);
        assertTrue(o instanceof String);
    }

    public void testRemoteXml() throws Exception
    {
        MuleClient client = new MuleClient(muleContext);
        MuleMessage result = client.request("vm://remoteXml", 5000);
        assertNotNull(result);
        Object o = result.getPayload();
        assertNotNull(o);
        assertTrue(o instanceof String);
    }

}
