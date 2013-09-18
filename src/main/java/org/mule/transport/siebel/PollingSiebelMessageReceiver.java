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

import com.siebel.data.SiebelDataBean;
import com.siebel.data.SiebelPropertySet;
import com.siebel.data.SiebelService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.lifecycle.CreateException;
import org.mule.api.transport.Connector;
import org.mule.transport.AbstractPollingMessageReceiver;
import java.util.Properties;

public class PollingSiebelMessageReceiver extends AbstractPollingMessageReceiver
{

    //private static Log logger  = LogFactory.getLog(PollingSiebelMessageReceiver.class);
    protected volatile SiebelDataBean siebelDataBean;
    protected SiebelPropertySet params;
    private SiebelService siebelService;

    /**
     * Creates and configures Siebel polling message receiver
     * @param connector Siebel connector
     * @param flowConstruct Flow Construct
     * @param endpoint Siebel Endpoint expected in format siebel://service_name/method_name?parameters
     * @param frequency polling frequency in milliseconds
     * @throws CreateException in case of initialisation error
     */
    public PollingSiebelMessageReceiver(Connector connector, FlowConstruct flowConstruct,
                              InboundEndpoint endpoint, long  frequency)
            throws CreateException
    {
        super(connector, flowConstruct, endpoint);
        logger.debug("Initializing");

        setFrequency(frequency);
        logger.debug("Polling frequency set to "+frequency);


        //grab call parameters from endpoint
        params = new SiebelPropertySet();
        Properties endPointParams = getEndpoint().getEndpointURI().getParams();
        for (String paramName: endPointParams.stringPropertyNames())
        {
            logger.debug("Setting property ["+ paramName+ "] to ["+ endPointParams.getProperty(paramName)+"]");
            params.setProperty(paramName, endPointParams.getProperty(paramName));
        }

    }

    /**
     * Establish connection to Siebel
     * @throws Exception if connection cannot be established or login error has occurred
     */
    @Override
    protected void doConnect() throws Exception
    {
        if (siebelDataBean == null)
        {
            SiebelPollingConnector connector = (SiebelPollingConnector)getConnector();
            siebelService =  connector.getSiebelService(connector.getSiebelDataBean(),getEndpoint(), null);
        }
        logger.info("************PollingSiebelMessageReceiver.doConnect()*****************");
    }

    /**
     * Log off from Siebel
     * @throws Exception
     */
    @Override
    protected void doDisconnect() throws Exception
    {
        if (siebelDataBean != null)
        {
            siebelDataBean.logoff();
            siebelDataBean = null;
        }
        logger.info("************PollingSiebelMessageReceiver.doDisconnect()*****************");
    }

    @Override
    public void poll() throws Exception
    {
        SiebelPropertySet output = new SiebelPropertySet();
        siebelService.invokeMethod(((SiebelPollingConnector)getConnector()).getInvokedMethod(getEndpoint(), null), params, output);
        MuleMessage message = new DefaultMuleMessage(output, getConnector().getMuleContext());
        routeMessage( message );
    }

}
