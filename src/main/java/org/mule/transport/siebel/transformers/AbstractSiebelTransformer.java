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

import org.mule.transformer.AbstractMessageTransformer;
import org.mule.util.StringUtils;

/**
 * Abstract class for Siebel transformer 
 *
 */
public abstract class AbstractSiebelTransformer extends AbstractMessageTransformer {

	private static final String[][] BASIC_ARRAY = { { " ", "spc" }, // Space
			{ "_", "und" }, // Underscore
			{ "\"", "dqt" }, // Double Quote
			{ "'", "sqt" }, // Single Quote
			{ ":", "cln" }, // Colon
			{ ";", "scn" }, // Semicolon
			{ "(", "lpr" }, // Left Parenthesis
			{ ")", "rpr" }, // Right Parenthesis
			{ "&", "amp" }, // Ampersand
			{ ",", "cma" }, // Comma
			{ "#", "pnd" }, // Pound symbol
			{ "/", "slh" }, // (Forward) slash
			{ "?", "qst" }, // Question Mark
			{ "<", "lst" }, // Less Than
			{ ">", "grt" } // Greater Than
	};

	private static final char[] ILLIGAL = { '!', '$', '%', '*', '+', '=', '@', '[', '\\', ']', '^',
			'`', '{', '|', '}', '~' };

	protected static final String PROPERTY_SET = "PropertySet";

	protected String escapeName(String str) {
		if (StringUtils.isEmpty(str))
			return str;

		int len = str.length();
		StringBuilder result = new StringBuilder((int) (len * 1.1));
		for (int i = 0; i < len; i++) {
			char c = str.charAt(i);
			if (i == 0) {
				if (('0' <= c && c <= '9') || c == '.' || c == '-') {
					result.append('_').append(c);
					continue;
				}
			}

			String entityName = entityName(c);
			if (entityName == null) {
				if (c > 0x7F || isIlligalChar(c)) {
					result.append('_').append(Integer.toString(c, 10));
				} else {
					result.append(c);
				}
			} else {
				result.append('_').append(entityName);
			}
		}
		return result.toString();
	}

	protected String unescapeName(String str) {
		if (StringUtils.isEmpty(str))
			return str;

		int firstUnd = str.indexOf('_');
		if (firstUnd < 0)
			return str;

		for (String[] a : BASIC_ARRAY) {
			str = str.replaceAll('_' + a[1], a[0]);
		}

		firstUnd = str.indexOf('_');
		if (firstUnd < 0)
			return str;

		int len = str.length();
		StringBuilder result = new StringBuilder((int) (len * 1.1));
		result.append(str.substring(0, firstUnd));

		for (int i = firstUnd; i < len; i++) {
			char c = str.charAt(i);
			if (c == '_') {
				if (i == 0)
					continue;

				if (i + 4 < len) {
					String code = str.substring(i, i + 4);
					if (StringUtils.isNumeric(code)) {
						char ch = (char) Integer.parseInt(code, 10);
						result.append(ch);
						i += 4;
						continue;
					}
				}
			}
			result.append(c);
		}
		return result.toString();

	}

	private String entityName(char c) {
		for (String[] a : BASIC_ARRAY) {
			if (a[0].charAt(0) == c)
				return a[1];
		}

		return null;
	}

	private boolean isIlligalChar(char ch) {
		for (char c : ILLIGAL) {
			if (c == ch)
				return true;
		}
		return false;
	}
}
