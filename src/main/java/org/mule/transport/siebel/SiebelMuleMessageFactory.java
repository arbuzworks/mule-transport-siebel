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

import com.siebel.data.SiebelPropertySet;
import org.mule.api.MuleContext;
import org.mule.transport.AbstractMuleMessageFactory;

/**
 * <code>SiebelMuleMessageFactory</code>
 */
public class SiebelMuleMessageFactory extends AbstractMuleMessageFactory
{

    public SiebelMuleMessageFactory(MuleContext context)
    {
        super(context);
    }
    
    @Override
    protected Class<?>[] getSupportedTransportMessageTypes()
    {   
        return new Class[]{SiebelPropertySet.class};
    }

    @Override
    protected Object extractPayload(Object transportMessage, String encoding) throws Exception
    {
        return transportMessage;
    }

}
