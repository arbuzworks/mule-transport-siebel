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

import org.mule.api.endpoint.EndpointURI;
import org.mule.endpoint.MuleEndpointURI;
import org.mule.tck.AbstractMuleTestCase;

public class SiebelEndpointTestCase extends AbstractMuleTestCase
{

    public void testValidEndpointURI() throws Exception
    {
        EndpointURI url = new MuleEndpointURI("siebel://localhost:2321", muleContext);
        assertEquals("siebel", url.getScheme());
        assertEquals(0, url.getParams().size());
    }
}
