package com.fancige.util;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XMLHandle {

	public static final int MESSAGE = 1;
	public static final int UPDATE_USER_LIST = 2;
	
	public static Document constructDocument(){
		
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("messages");
		root.addElement("type");
		root.addElement("user");
		root.addElement("content");
		return doc;
	}
	
	public static void setType(Document doc,int type){
		
		doc.getRootElement().element("type").setText(Integer.toString(type));
	}
	
	public static void setContent(Document doc,String content){
		
		doc.getRootElement().element("content").setText(content);
	}
	
	public static void setUserName(Document doc ,String userName){
		
		doc.getRootElement().element("user").setText(userName);
	}
	
	public static int getType(Document doc){
		
		String str = doc.getRootElement().element("type").getText();
		return Integer.parseInt(str);
	}
	
	public static String getUserName(Document doc){
		
		return doc.getRootElement().element("user").getText();
	}
	
	public static String getContent(Document doc){
		
		return doc.getRootElement().element("content").getText();
	}
	
}
