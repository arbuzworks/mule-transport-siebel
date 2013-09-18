/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.transport.siebel.config;

import org.mule.config.spring.handlers.AbstractMuleNamespaceHandler;
import org.mule.config.spring.parsers.generic.MuleOrphanDefinitionParser;
import org.mule.config.spring.parsers.specific.MessageProcessorDefinitionParser;
import org.mule.endpoint.URIBuilder;
import org.mule.transport.siebel.SiebelConnector;
import org.mule.transport.siebel.SiebelPollingConnector;
import org.mule.transport.siebel.transformers.EncodedStringToSiebelPropertySet;
import org.mule.transport.siebel.transformers.SiebelPropertySetToEncodedString;
import org.mule.transport.siebel.transformers.SiebelPropertySetToXmlString;
import org.mule.transport.siebel.transformers.SiebelPropertySetToXmlStringRemote;
import org.mule.transport.siebel.transformers.XmlStringToSiebelPropertySet;
import org.mule.transport.siebel.transformers.XmlStringToSiebelPropertySetRemote;

/**
 * Registers a Bean Definition Parser for handling <code><siebel:connector></code> and <code><siebel:polling-connector></code> elements
 * and supporting endpoint elements.
 */
public class SiebelNamespaceHandler extends AbstractMuleNamespaceHandler
{
    public void init()
    {
        registerStandardTransportEndpoints(SiebelConnector.SIEBEL, URIBuilder.PATH_ATTRIBUTES);
        
        registerConnectorDefinitionParser(SiebelPollingConnector.class);
        registerConnectorDefinitionParser(SiebelConnector.class);
        registerBeanDefinitionParser("polling-connector", new MuleOrphanDefinitionParser(SiebelPollingConnector.class, true));
        registerBeanDefinitionParser("xml-string-to-propset-transformer", new MessageProcessorDefinitionParser(XmlStringToSiebelPropertySet.class));
        registerBeanDefinitionParser("xml-string-to-propset-remote-transformer", new MessageProcessorDefinitionParser(XmlStringToSiebelPropertySetRemote.class));
        registerBeanDefinitionParser("encoded-string-to-propset-transformer", new MessageProcessorDefinitionParser(EncodedStringToSiebelPropertySet.class));
        registerBeanDefinitionParser("propset-to-xml-string-transformer", new MessageProcessorDefinitionParser(SiebelPropertySetToXmlString.class));
        registerBeanDefinitionParser("propset-to-xml-string-remote-transformer", new MessageProcessorDefinitionParser(SiebelPropertySetToXmlStringRemote.class));
        registerBeanDefinitionParser("propset-to-encoded-string-transformer", new MessageProcessorDefinitionParser(SiebelPropertySetToEncodedString.class));

    }
}
