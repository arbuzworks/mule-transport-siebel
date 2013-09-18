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

import org.mule.api.MuleContext;
import org.mule.api.config.MuleProperties;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.transport.MessageReceiver;
import org.mule.transport.AbstractPollingMessageReceiver;

import java.util.Properties;

/**
 * The SiebelPollingConnector allows for inbound Siebel endpoints to be configured with the business service
 * to poll for a result. If a result is received it becomes the inbound event for the component.
 */
public class SiebelPollingConnector extends SiebelConnector
{
    public static final String PROPERTY_POLLING_FREQUENCY = "pollingFrequency";

    private long pollingFrequency = AbstractPollingMessageReceiver.DEFAULT_POLL_FREQUENCY;

    public SiebelPollingConnector(MuleContext context)
    {
        super(context);
        serviceOverrides = new Properties();
        serviceOverrides.setProperty(MuleProperties.CONNECTOR_MESSAGE_RECEIVER_CLASS, PollingSiebelMessageReceiver.class.getName());
    }

    /**
     *  Create a Message receiver for Siebel connector
     * {@inheritDoc}
     */
    @Override
    protected MessageReceiver createReceiver(FlowConstruct flow, InboundEndpoint endpoint) throws Exception
    {
        String tempPolling = (String) endpoint.getProperty(PROPERTY_POLLING_FREQUENCY);
        if (tempPolling != null)
        {
            try
            {
                pollingFrequency = Long.parseLong(tempPolling);
            }
            catch (NumberFormatException e)
            {
                logger.error(PROPERTY_POLLING_FREQUENCY+" connector parameter is not a valid integer.");
            }

            if (pollingFrequency <= 0)
            {
                pollingFrequency = AbstractPollingMessageReceiver.DEFAULT_POLL_FREQUENCY;
            }
            if (logger.isDebugEnabled())
            {
                logger.debug("Set polling frequency to: " + pollingFrequency);
            }
        }

        return serviceDescriptor.createMessageReceiver(this, flow, endpoint, pollingFrequency);
    }

    public long getPollingFrequency()
    {
        return pollingFrequency;
    }

    public void setPollingFrequency(long pollingFrequency)
    {
        this.pollingFrequency = pollingFrequency;
    }
}
