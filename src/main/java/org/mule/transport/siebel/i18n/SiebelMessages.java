/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.siebel.i18n;

import org.mule.config.i18n.Message;
import org.mule.config.i18n.MessageFactory;

public class SiebelMessages extends MessageFactory
{
    private static final String BUNDLE_PATH = getBundlePath("siebel");

    private static final SiebelMessages factory = new SiebelMessages();

    public static Message failedToCreate()
    {
        return factory.createMessage(BUNDLE_PATH, 1);
    }

    public static Message messageParamServiceNotSet()
    {
        return factory.createMessage(BUNDLE_PATH, 2);
    }

    public static Message messageParamMethodNotSet()
    {
        return factory.createMessage(BUNDLE_PATH, 3);
    }

    public static Message messageServiceNotFound(String message)
    {
        return factory.createMessage(BUNDLE_PATH, 4, message);
    }
    
    public static Message siebelConnectorNotSetForTransformer()
    {
        return factory.createMessage(BUNDLE_PATH, 5);
    }

}
