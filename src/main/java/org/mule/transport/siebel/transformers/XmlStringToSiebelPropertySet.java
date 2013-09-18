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

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.types.DataTypeFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.siebel.common.util.Base64;
import com.siebel.data.SiebelPropertySet;

/**
 * Transformer to convert XML string to SiebelPropertySet object
 */
public class XmlStringToSiebelPropertySet extends AbstractSiebelTransformer {

	public XmlStringToSiebelPropertySet() {
		registerSourceType(DataTypeFactory.XML_STRING);
		setReturnDataType(DataTypeFactory.create(SiebelPropertySet.class));
		setName(SiebelPropertySetToXmlString.class.getSimpleName());
	}

	/** {@inheritDoc}
	 */
	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding)
			throws TransformerException {
		try {
			String xml = (String) message.getPayload();
			ByteArrayInputStream sr = new ByteArrayInputStream(xml.getBytes("UTF-8"));

			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
			Document doc = docBuilder.parse(sr);

			return convXmlToPropertySet(doc.getDocumentElement());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new TransformerException(this, e);
		}
	}

	protected SiebelPropertySet convXmlToPropertySet(Element el) {
		SiebelPropertySet propSet = new SiebelPropertySet();

		String elName = el.getTagName();
		propSet.setType(unescapeName(elName));

		NamedNodeMap attributes = el.getAttributes();
		int n = attributes.getLength();
		for (int i = 0; i < n; i++) {
			Node attr = attributes.item(i);
			if (!"sblValueVariant".equals(attr.getNodeName()))
				propSet.setProperty(unescapeName(attr.getNodeName()), attr.getNodeValue());
		}

		NodeList nodes = el.getChildNodes();
		n = nodes.getLength();
		for (int i = 0; i < n; i++) {
			Node node = nodes.item(i);
			if (node.getNodeType() == Node.TEXT_NODE) {
				String text = node.getNodeValue().trim();
				if (!text.isEmpty()) {
					if ("CCFVT_MEMBLOCK".equals(el.getAttribute("sblValueVariant"))) {
						try {
							byte[] bs = Base64.decode(text);
							propSet.setByteValue(bs);
						} catch (Exception e) {
							logger.error(e.getMessage(), e);
						}
					} else
						propSet.setValue(text);
				}
			} else if (node.getNodeType() == Node.ELEMENT_NODE) {
				propSet.addChild(convXmlToPropertySet((Element) node));
			}
		}

		return propSet;
	}
}
