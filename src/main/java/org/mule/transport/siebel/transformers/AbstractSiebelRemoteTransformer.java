/*
 * $Id: $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.transport.siebel.transformers;

import org.mule.RequestContext;
import org.mule.api.transformer.TransformerException;
import org.mule.api.transport.Connector;
import org.mule.transformer.AbstractMessageTransformer;
import org.mule.transport.siebel.SiebelConnector;
import org.mule.transport.siebel.i18n.SiebelMessages;

/**
 * Abstract class for Siebel remote transformer 
 *
 */
public abstract class AbstractSiebelRemoteTransformer extends AbstractMessageTransformer {

	private SiebelConnector siebelConnector;

	/**
	 * Get a siebel connector
	 * @return
	 */
	public SiebelConnector getSiebelConnector() throws TransformerException {
		if (siebelConnector == null) {
			Connector connector = RequestContext.getEvent().getEndpoint().getConnector();
			if (connector instanceof SiebelConnector) {
				siebelConnector = (SiebelConnector) connector;
			} else {
				throw new TransformerException(
						SiebelMessages.siebelConnectorNotSetForTransformer(), this);
			}
		}

		return siebelConnector;
	}

	/**
	 * Specify the siebel connector
	 * @param connector
	 */
	public void setSiebelConnector(SiebelConnector siebelConnector) {
		this.siebelConnector = siebelConnector;
	} 
}
