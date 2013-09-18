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

import org.mule.api.transformer.Transformer;

public class SiebelPropertySetToEncodedStringTestCase extends AbstractSiebelPropertySetToStringTestCase {

	@Override
	public Object getResultData() {
		return "@0*0*2*2*11*PropertySet3*4*Body11*Attribute 123*Calendar and Activities8*number#11*N2*0*10*Date range3*0*8*EndDates19*2011-07-14 12:11:1110*StartDates19*2011-07-14 11:11:110*0*10*Attachment2*4*VEVTVA==";
	}

	@Override
	public Transformer getTransformer() throws Exception {
		SiebelPropertySetToEncodedString trans = new SiebelPropertySetToEncodedString();
		initialiseObject(trans);
		return trans;
	}

	@Override
	public Transformer getRoundTripTransformer() throws Exception {
		EncodedStringToSiebelPropertySet trans = new EncodedStringToSiebelPropertySet();
		initialiseObject(trans);
		return trans;
	}
}
