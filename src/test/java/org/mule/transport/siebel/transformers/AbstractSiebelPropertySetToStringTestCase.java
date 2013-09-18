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

import java.io.UnsupportedEncodingException;

import org.mule.transformer.AbstractTransformerTestCase;

import com.siebel.data.SiebelPropertySet;

public abstract class AbstractSiebelPropertySetToStringTestCase extends AbstractTransformerTestCase {

	@Override
	public Object getTestData() {
		SiebelPropertySet propSet = new SiebelPropertySet();
		propSet.setType("PropertySet");
		propSet.setProperty("Attribute 1", "Calendar and Activities");
		propSet.setProperty("number#1", "N");

		SiebelPropertySet ps1 = new SiebelPropertySet();
		ps1.setType("Date range");
		ps1.setProperty("StartDates", "2011-07-14 11:11:11");
		ps1.setProperty("EndDates", "2011-07-14 12:11:11");
		propSet.addChild(ps1);

		SiebelPropertySet ps2 = new SiebelPropertySet();
		ps2.setType("Attachment");
		
		try {
			ps2.setByteValue("TEST".getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		propSet.addChild(ps2);

		propSet.setValue("Body");
		return propSet;
	}

    public boolean compareRoundtripResults(Object expected, Object result)
    {
        SiebelPropertySet s1 = (SiebelPropertySet)expected;
        SiebelPropertySet s2 = (SiebelPropertySet)result;   
        
        String str1 = s1.encodeAsString();
        String str2 = s2.encodeAsString();

        logger.debug("compareRoundtripResults: expected = " + str1);
        logger.debug("compareRoundtripResults: result = " + str2);
        
        // work around for http://www.mulesoft.org/jira/browse/MULE-5675 
        assertEquals(str1, str2);
        
		return compareResults(str1, str2);
    }

}
