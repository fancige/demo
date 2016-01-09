package com.fancige.util;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class DocumentManager
{
	public static Document readDocument(String path)
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

	public static void writeDocument(Document doc, String path)
	{
		TransformerFactory tff = TransformerFactory.newInstance();
		try
		{
			Transformer tf = tff.newTransformer();
			tf.setOutputProperty(OutputKeys.INDENT, "yes");
			tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount",
					"4");
			if (null != doc.getDoctype().getSystemId())
				tf.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doc
						.getDoctype().getSystemId());
			tf.transform(new DOMSource(doc), new StreamResult(path));
		}
		catch (TransformerException e)
		{
			e.printStackTrace();
		}
	}
}