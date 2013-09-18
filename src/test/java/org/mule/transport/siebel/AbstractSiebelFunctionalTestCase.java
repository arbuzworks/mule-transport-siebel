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

import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;

import java.io.InputStream;
import java.util.Properties;

public abstract class AbstractSiebelFunctionalTestCase extends FunctionalTestCase
{

    protected String name;
    protected String password;

    protected String host;
    protected String port;
    protected String path;

    protected String service;
    protected String method;

    protected MuleClient client;

    protected Properties properties;

    @Override
    protected void doSetUp() throws Exception
    {
        super.doSetUp();
        client = new MuleClient(muleContext);
        properties = new Properties();

        InputStream propertiesFile = this.getClass().getClassLoader().getResourceAsStream("test.properties");
        properties.load(propertiesFile);

        name = properties.getProperty("user");
        password = properties.getProperty("password");
        host = properties.getProperty("host");
        port = properties.getProperty("port");

        path = properties.getProperty("url_path");

        service = properties.getProperty("service");
        method = properties.getProperty("method");
    }


}
