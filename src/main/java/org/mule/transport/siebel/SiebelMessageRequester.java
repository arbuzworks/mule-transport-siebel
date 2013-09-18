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

import org.mule.api.MuleMessage;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.transport.AbstractMessageRequester;

/**
 * <code>SiebelMessageRequester</code>
 */
public class SiebelMessageRequester extends AbstractMessageRequester
{

    public SiebelMessageRequester(InboundEndpoint endpoint)
    {
        super(endpoint);
    }

    protected MuleMessage doRequest(long timeout) throws Exception
    {
        return null;
    }

    public void doConnect() throws Exception
    {
    }

    public void doDisconnect() throws Exception
    {
    }

    public void doDispose()
    {
    }

}

