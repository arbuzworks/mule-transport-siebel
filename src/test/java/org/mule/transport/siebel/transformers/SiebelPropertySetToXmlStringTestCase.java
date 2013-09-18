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

public class SiebelPropertySetToXmlStringTestCase extends AbstractSiebelPropertySetToStringTestCase {

	@Override
	public Object getResultData() {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><?Siebel-Property-Set EscapeNames=\"true\"?><PropertySet Attribute_spc1=\"Calendar and Activities\" number_pnd1=\"N\">Body<Date_spcrange EndDates=\"2011-07-14 12:11:11\" StartDates=\"2011-07-14 11:11:11\"/><Attachment sblValueVariant=\"CCFVT_MEMBLOCK\">VEVTVA==</Attachment></PropertySet>";
	}
	
	@Override
	public Transformer getTransformer() throws Exception {
		SiebelPropertySetToXmlString trans = new SiebelPropertySetToXmlString();
		initialiseObject(trans);
		return trans;
	}

	@Override
	public Transformer getRoundTripTransformer() throws Exception {
		XmlStringToSiebelPropertySet trans = new XmlStringToSiebelPropertySet();
		initialiseObject(trans);
		return trans;
	}
}
