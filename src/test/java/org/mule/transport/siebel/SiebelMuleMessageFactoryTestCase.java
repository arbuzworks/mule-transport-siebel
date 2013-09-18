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
import org.mule.api.transport.MuleMessageFactory;
import org.mule.transport.AbstractMuleMessageFactoryTestCase;

import java.util.ArrayList;

public class SiebelMuleMessageFactoryTestCase  extends AbstractMuleMessageFactoryTestCase
{
    /* For general guidelines on writing transports see
       http://www.mulesoft.org/documentation/display/MULE3USER/Creating+Transports */

    @Override
    protected MuleMessageFactory doCreateMuleMessageFactory()
    {
        return new SiebelMuleMessageFactory(muleContext);
    }

    @Override
    protected Object getValidTransportMessage() throws Exception
    {
        return new SiebelPropertySet();
    }

    @Override
    protected Object getUnsupportedTransportMessage()
    {
        return new ArrayList();
    }
}
