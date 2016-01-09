package com.fancige.util;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * <p>
 * Manages the special XML file by two ways, DOM and SAX.
 * </p>
 * 
 * @author fancige
 * 
 */
public class XMLManager extends DefaultHandler
{
	private String path;
	private static final int DTD = 0;
	private static final int NO_VALIDATOT = 3;

	/**
	 * Creates a object of XMLManager, you should specify the system path of the
	 * XML file
	 * 
	 * @param path
	 *            The XML file's path.
	 */
	public XMLManager(String path)
	{
		this.path = path;
	}

	/**
	 * Reads a document from a XML file.
	 * 
	 * @return The document read, or null if any Exceptions occur.
	 */
	
	public Document readDocument()
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setIgnoringElementContentWhitespace(true);
		try
		{
			DocumentBuilder builder = factory.newDocumentBuilder();
			return builder.parse(new File(path));
		}
		catch (ParserConfigurationException | SAXException | IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * <p>
	 * Writes the <code>doc</code> which should contains a DTD into a XML file.
	 * </p>
	 * 
	 * @param doc
	 *            The document.
	 * @return true if this operation is succeed, false if not.
	 * @see #writeDocumentWithoutValidator(Document)
	 */
	public boolean writeDocumentWithDTD(Document doc)
	{
		return writeDocument(doc, DTD);
	}
	
	/**
	 * <p>
	 * Writes the <code>doc</code> into a XML file. If the <code>doc</code>
	 * contains a DTD or a Schema, it will be ignored.
	 * </p>
	 * 
	 * @param doc
	 *            The document.
	 * @return true if this operation is succeed, false if not.
	 * @see #writeDocumentWithDTD(Document)
	 */
	public boolean writeDocumentWithoutValidator(Document doc)
	{
		return writeDocument(doc, NO_VALIDATOT);
	}

	private boolean writeDocument(Document doc, int validatorType)
	{
		TransformerFactory tff = TransformerFactory.newInstance();
		try
		{
			Transformer tf = tff.newTransformer();
			tf.setOutputProperty(OutputKeys.INDENT, "yes");
			tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount",
					"4");
			if (DTD == validatorType)
				tf.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doc
						.getDoctype().getSystemId());
			tf.transform(new DOMSource(doc), new StreamResult(path));
			return true;
		}
		catch (TransformerException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	// Just be used to generate events.
	private void parse()
	{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try
		{
			SAXParser parser = factory.newSAXParser();
			parser.parse(new File(path), this);
		}
		catch (ParserConfigurationException | SAXException | IOException e)
		{
			e.printStackTrace();
		}
	}

	public String getTextContentByElementId(String id)
	{
		reset();
		elementId = id;
		startElementSwitch = GET_TEXT_BY_ELEMENT_ID;
		parse();
		return textContent;
	}

	/**
	 * Get the text content.
	 * 
	 * @param seq
	 *            A sequence of characters it contains.
	 * @return the text content. If there are more than one, the last will be
	 *         return. If no such text content, empty string will be return;
	 */
	public String getTextContentByCharSequence(String seq)
	{
		if(seq.isEmpty())
			return "";
		reset();
		charactersStitch = GET_TEXT_BY_CHAR_SEQUENCE;
		charSequence = seq;
		parse();
		return textContent;
	}

	/*
	 * DefaultHandler
	 */

	private static final int GET_TEXT_BY_ELEMENT_ID = 0;
	private static final int GET_TEXT_BY_CHAR_SEQUENCE = 1;

	// The following attributes must be initialised
	// before you use by call the reset method.

	private int startElementSwitch;
	private int charactersStitch;
	private String elementId;
	private String textContent;
	private String charSequence;

	private void reset()
	{
		startElementSwitch = -1;
		charactersStitch = -1;
		elementId = "";
		textContent = "";
		charSequence = "";
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException
	{
		switch (startElementSwitch)
		{
		case GET_TEXT_BY_ELEMENT_ID:

			for (int i = 0; i < attributes.getLength(); i++)
			{
				String name = attributes.getQName(i);
				String value = attributes.getValue(i);

				if ("id".equalsIgnoreCase(name) && elementId.equals(value))
				{
					charactersStitch = GET_TEXT_BY_ELEMENT_ID;
				}
			}
			break;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException
	{
		switch (charactersStitch)
		{
		case GET_TEXT_BY_ELEMENT_ID:

			for (int i = 0; i < length; i++)
			{
				textContent += ch[start++];
			}
			break;

		case GET_TEXT_BY_CHAR_SEQUENCE:

			String text = "";
			for (int i = 0; i < length; i++)
			{
				text += ch[start++];
			}
			if (text.contains(charSequence))
			{
				textContent = text;
			}
			break;
		}
	}
}