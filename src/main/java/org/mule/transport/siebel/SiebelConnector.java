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
import com.siebel.data.SiebelException;
import com.siebel.data.SiebelPropertySet;
import com.siebel.data.SiebelService;
import org.mule.api.MessagingException;
import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.transport.PropertyScope;
import org.mule.transport.AbstractConnector;
import org.mule.transport.siebel.config.SiebelProperties;
import org.mule.transport.siebel.i18n.SiebelMessages;
import org.mule.util.StringUtils;

/**
 * A Connector for Siebel
 */
public class SiebelConnector extends AbstractConnector
{
    /* This constant defines the main transport protocol identifier */
    public static final String SIEBEL = "siebel";

    public static final int DEFAULT_SIEBEL_PORT = 2321;

    //connection properties
    private String host;
    private String port = String.valueOf(DEFAULT_SIEBEL_PORT);
    private String path;
    private String user;
    private String password;

    SiebelDataBean siebelDataBean;
    private SiebelService transformerXmlConverterService;
    
    public SiebelConnector(MuleContext context)
    {
        super(context);
    }
       
    @Override
    public void doInitialise() throws InitialisationException
    {
        logger.info("************SiebelConnector.doInitialise()*****************");
    }

    /**
     * Creates SiebelDataBean and log in to Siebel
     * @throws InitialisationException if was unable to connect or login
     */
    @Override
    public void doConnect() throws InitialisationException
    {
		String url = getProtocol() + "://" + host + ":" + port + path;

		siebelDataBean = new SiebelDataBean();
		try {
			siebelDataBean.login(url, user, password);
		} catch (SiebelException e) {
			throw new InitialisationException(SiebelMessages.failedToCreate(), e, this);
		}
		
        logger.info("************SiebelConnector.doConnect()*****************");
    }

    /**
     * LogOff from Siebel
     * @throws Exception
     */
    @Override
    public void doDisconnect() throws Exception
    {
    	if (siebelDataBean != null)
            siebelDataBean.logoff();
    	
        logger.info("************SiebelConnector.doDisconnect()*****************");
    }

    @Override
    public void doStart() throws MuleException
    {
        logger.info("************SiebelConnector.doStart()*****************");
    }

    @Override
    public void doStop() throws MuleException
    {
    }

    @Override
    public void doDispose()
    {
    }

	/**
	 * Get a siebel protocol
	 */
    public String getProtocol()
    {
        return SIEBEL;
    }

	/**
	 * Get a siebel host
	 * @return Returns Siebel host
	 */
    public String getHost() {
        return host;
    }

	/**
	 * Specify the siebel host
	 * @param host Siebel host name or IP address
	 */
    public void setHost(String host) {
        this.host = host;
    }

	/**
	 * Get a siebel port
	 * @return Returns Siebel port
	 */
    public String getPort() {
        return port;
    }

	/**
	 * Specify the siebel port
	 * @param portString Siebel port
	 */
    public void setPort(String portString) {
        try
        {
            int port = Integer.parseInt(portString);
            if (port < 1)
            {
                logger.warn("Negative value set for port in Siebel connector: " + getName() + ". Using default port: " + SiebelConnector.DEFAULT_SIEBEL_PORT);
                port = SiebelConnector.DEFAULT_SIEBEL_PORT;
            }
            this.port = String.valueOf(port);
        } catch (Exception e)
        {
            logger.error("Invalid value set for port in Siebel connector: " + getName() + ". Using default port: " + SiebelConnector.DEFAULT_SIEBEL_PORT + ".\n" + e.getMessage(), e);
        }
    }

	/**
	 * Get a siebel path
	 * @return Returns Siebel path
	 */
    public String getPath() {
        return path;
    }

	/**
	 * Specify the siebel path
	 * @param path Siebel path
	 */
    public void setPath(String path) {
        this.path = path;
    }

	/**
	 * Get a Siebel user
	 * @return Return Siebel user
	 */
    public String getUser() {
        return user;
    }

	/**
	 * Specify the Siebel user
	 * @param user Siebel user
	 */
    public void setUser(String user) {
        this.user = user;
    }

	/**
	 * Get a siebel password
	 * @return Returns Siemel password
	 */
    public String getPassword() {
        return password;
    }

	/**
	 * Specify the siebel password
	 * @param password Siebel password
	 */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for Siebel DataBean
     * @return Returns Siebel DataBean
     */
	public SiebelDataBean getSiebelDataBean() {
		return siebelDataBean;
	}

    /**
     * Get Siebel service
     * Service name is being retrieved from endpoint properties (authority).
     * If not found and event is not null, search for service name in message properties
     * Endpoint configuraition is experced in form siebel://service_name/method_name?parameters
     * @param dataBean Sievel DataBean. Must be already connected to Siebel.
     * @param endpoint Endpoint
     * @param event Mule Event, nullable
     * @return Siebel Service handle
     * @throws MuleException If service is not configured or cannot be found.
     */
    public SiebelService getSiebelService(SiebelDataBean dataBean, ImmutableEndpoint endpoint, MuleEvent event)
            throws MuleException
    {
        String serviceName = endpoint.getEndpointURI().getAuthority();

        if (serviceName == null && event != null)
        {
            serviceName = (String) event.getMessage().removeProperty(SiebelProperties.SIEBEL_SERVICE_PROPERTY, PropertyScope.INVOCATION);

            if (serviceName == null)
            {
                throw new MessagingException(SiebelMessages.messageParamServiceNotSet(), event);
            }
        }

        try
        {
            return dataBean.getService(serviceName);
        } catch (SiebelException se)
        {
            throw new MessagingException(SiebelMessages.messageServiceNotFound(serviceName), event);
        }
    }

    /**
     * Search for Siebel method to invoke in Endpoint configuration and in message proeprties
     * @param endpoint Siebel Endpoint
     * @param event Mule Event, nullable
     * @return Method name to invoke
     * @throws MuleException if method is not found
     */
    public String getInvokedMethod(ImmutableEndpoint endpoint, MuleEvent event) throws MuleException
    {
        String methodName = endpoint.getEndpointURI().getPath();

        if (StringUtils.isNotBlank(methodName))
        {
            if (methodName.startsWith("/"))
                methodName = methodName.substring(1);
        }
        else
        {
            if (event!=null)
            {
                methodName = (String)event.getMessage().removeProperty(SiebelProperties.SIEBEL_METHOD_PROPERTY, PropertyScope.INVOCATION);
            }
        }

        if (StringUtils.isBlank(methodName))
        {
            throw new MessagingException(SiebelMessages.messageParamMethodNotSet(), event);
        }
        return methodName;
    }

    /**
     * Siebel service for remote XML to SiebelPropertySet transformer
     * @return Transformer service
     * @throws MuleException
     * @throws SiebelException
     */
	private SiebelService getTransformerXmlConverterService() throws MuleException, SiebelException {
		if (transformerXmlConverterService == null) {
			transformerXmlConverterService = siebelDataBean.getService("XML Converter");
		}

		return transformerXmlConverterService;
	}

    /**
     * Invoke method for remote XML to SiebelPropertySet transformation
     * @param methodName Method name
     * @param propSetIn Input property set
     * @param propSetOut Output property set
     * @return true if method invocation was successfull
     * @throws MuleException
     * @throws SiebelException
     */
	public boolean invokeTransformerMethod(String methodName, SiebelPropertySet propSetIn,
			SiebelPropertySet propSetOut) throws MuleException, SiebelException {

		SiebelService service = getTransformerXmlConverterService();
		return service.invokeMethod(methodName, propSetIn, propSetOut);
	}

}
