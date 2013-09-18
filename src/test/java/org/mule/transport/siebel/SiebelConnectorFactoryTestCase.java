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

import org.mule.api.endpoint.InboundEndpoint;
import org.mule.tck.AbstractMuleTestCase;

public class SiebelConnectorFactoryTestCase extends AbstractMuleTestCase
{

    public void testCreateFromFactory() throws Exception
    {
        InboundEndpoint endpoint = muleContext.getEndpointFactory().getInboundEndpoint(getEndpointURI());
        assertNotNull(endpoint);
        assertNotNull(endpoint.getConnector());
        assertTrue(endpoint.getConnector() instanceof SiebelConnector);

        assertEquals(getEndpointURI(), endpoint.getEndpointURI().getUri().toString());
    }

    public String getEndpointURI()
    {
        return "siebel://SADMIN:talipwd@localhost:2321/SBA_80/ESEObjMgr_enu?service=Workflow%20Utilities&method=Echo";
    }
}