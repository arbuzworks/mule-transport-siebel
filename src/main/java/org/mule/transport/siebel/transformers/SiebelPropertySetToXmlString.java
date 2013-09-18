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

import java.io.StringWriter;
import java.util.Enumeration;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.types.DataTypeFactory;
import org.mule.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ProcessingInstruction;

import com.siebel.common.util.Base64;
import com.siebel.data.SiebelPropertySet;

/**
 * Transformer to convert SiebelPropertySet object to XML string
 */
public class SiebelPropertySetToXmlString extends AbstractSiebelTransformer {

	public SiebelPropertySetToXmlString() {
		registerSourceType(DataTypeFactory.create(SiebelPropertySet.class));
		setReturnDataType(DataTypeFactory.XML_STRING);
		setName(SiebelPropertySetToXmlString.class.getSimpleName());
	}

	/** {@inheritDoc}
	 */
	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding)
			throws TransformerException {
		try {
			SiebelPropertySet propSet = (SiebelPropertySet) message.getPayload();

			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			ProcessingInstruction pi = doc.createProcessingInstruction("Siebel-Property-Set", "EscapeNames=\"true\"");
			doc.appendChild(pi);

			Element root = convPropertySetToXml(doc, propSet);
			doc.appendChild(root);
			
            TransformerFactory transfac = TransformerFactory.newInstance();
            Transformer trans = transfac.newTransformer();
            trans.setOutputProperty(OutputKeys.INDENT, "yes");
            
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            DOMSource source = new DOMSource(doc);
            trans.transform(source, result);
            
			return sw.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new TransformerException(this, e);
		}
	}

	protected Element convPropertySetToXml(Document doc, SiebelPropertySet propSet) {
		String elementName = StringUtils.isEmpty(propSet.getType()) ? PROPERTY_SET : propSet
				.getType();

		Element el = doc.createElement(escapeName(elementName));
		Enumeration<?> propertyNames = propSet.getPropertyNames();
		while (propertyNames.hasMoreElements()) {
		      String propName = (String) propertyNames.nextElement();
		      el.setAttribute(escapeName(propName), propSet.getProperty(propName));
		}
		
		if (propSet.getByteValue() != null && propSet.getByteValue().length > 0)
		{
			el.appendChild(doc.createTextNode(Base64.encode(propSet.getByteValue())));
			el.setAttribute("sblValueVariant", "CCFVT_MEMBLOCK");
		}
		else if (StringUtils.isNotEmpty(propSet.getValue()))
		{
			el.appendChild(doc.createTextNode(propSet.getValue()));
		}
		
		int n = propSet.getChildCount();
		for (int i = 0; i < n; i++)
		{
			Element childEl = convPropertySetToXml(doc, propSet.getChild(i));
			el.appendChild(childEl);
		}
		
		return el;
	}
}
