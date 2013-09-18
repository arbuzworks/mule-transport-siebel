/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.siebel.transformers;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.types.DataTypeFactory;
import org.mule.transport.siebel.SiebelConnector;

import com.siebel.data.SiebelPropertySet;

/**
 * Transformer to convert SiebelPropertySet object to XML string using remote siebel XML converter
 */
public class SiebelPropertySetToXmlStringRemote extends AbstractSiebelRemoteTransformer {
	
	public SiebelPropertySetToXmlStringRemote() {
		registerSourceType(DataTypeFactory.create(SiebelPropertySet.class));
		setReturnDataType(DataTypeFactory.XML_STRING);
		setName(SiebelPropertySetToXmlStringRemote.class.getSimpleName());
	}

	/** {@inheritDoc}
	 */
	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding)
			throws TransformerException {
		SiebelPropertySet propSetOut = new SiebelPropertySet();
		String result;
		try {
			SiebelPropertySet propSetIn = (SiebelPropertySet) message.getPayload();			
			SiebelConnector connector = getSiebelConnector();			
			connector.invokeTransformerMethod("PropSetToXML", propSetIn, propSetOut);
			result = new String(propSetOut.getByteValue(), "UTF-8");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new TransformerException(this, e);
		}
		return result;
	}
	
}
