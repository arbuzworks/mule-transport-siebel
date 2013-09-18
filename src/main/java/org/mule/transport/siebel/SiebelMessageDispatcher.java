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

import org.mule.DefaultMuleMessage;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.transport.AbstractMessageDispatcher;

import com.siebel.data.SiebelDataBean;
import com.siebel.data.SiebelPropertySet;
import com.siebel.data.SiebelService;

/**
 * Will dispatch Mule events to clients that are listening to this endpoint.
 * <code>SiebelMessageDispatcher</code>
 */
public class SiebelMessageDispatcher extends AbstractMessageDispatcher
{
    private SiebelConnector connector = null;
    protected volatile SiebelDataBean siebelDataBean;
    protected volatile SiebelService siebelService;

    public SiebelMessageDispatcher(OutboundEndpoint endpoint)
    {
        super(endpoint);
        connector = (SiebelConnector)endpoint.getConnector();
    }

    /**
     * Connect and login to Siebel
     * @throws Exception
     */
    @Override
    public void doConnect() throws Exception
    {
        if (siebelDataBean == null)
        {
            siebelDataBean = connector.getSiebelDataBean();
        }
        
        logger.info("************SiebelMessageDispatcher.doConnect()*****************");
    }

    @Override
    public void doDisconnect() throws Exception
    {
        logger.info("************SiebelMessageDispatcher.doDisconnect()*****************");
    }

    @Override
    public void doDispatch(MuleEvent event) throws Exception
    {
        doSend(event);
    }

    @Override
    public MuleMessage doSend(MuleEvent event) throws Exception
    {
        if (siebelService == null)
        {
            siebelService = connector.getSiebelService(siebelDataBean, getEndpoint(), event);
        }

        SiebelPropertySet input = event.getMessage().getPayload(SiebelPropertySet.class);

        SiebelPropertySet output = new SiebelPropertySet();

        String methodName = connector.getInvokedMethod(getEndpoint(),  event);

        siebelService.invokeMethod(methodName, input, output);

        return new DefaultMuleMessage(output, event.getMuleContext());
    }

    @Override
    public void doDispose()
    {

    }

}

