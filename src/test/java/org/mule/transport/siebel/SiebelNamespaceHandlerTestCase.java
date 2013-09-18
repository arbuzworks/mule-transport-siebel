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

import org.mule.tck.FunctionalTestCase;

/**
 * Tastcases for Namespace Handler
 */
public class SiebelNamespaceHandlerTestCase extends AbstractSiebelFunctionalTestCase
{
    @Override
    protected String getConfigResources()
    {
        return "siebel-namespace-config.xml";
    }

    public void testSiebelConfig() throws Exception
    {
        SiebelConnector c = (SiebelConnector) muleContext.getRegistry().lookupConnector("siebelConnector");
        assertNotNull(c);
        assertTrue(c.isConnected());
        assertTrue(c.isStarted());
        assertEquals(c.getHost(), host);
        assertEquals(c.getPort(), port);
        assertEquals(c.getUser(), name);
        assertEquals(c.getPassword(), password);
        assertEquals(c.getPath(), path);
    }
}
