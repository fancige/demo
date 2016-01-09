package com.fancige.vote;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class QuestionManager {

private static Document doc = null;
	
	private static List<String> titles = null;
	private static List<List<String>> allOptions = null;
	private static List<String> types = null;
	private static List<List<Node>> optionCountNode = null;
	private static List<List<String>> optionCount = null;
	private static List<List<String>> optionIdList = null;
	private static List<String> questionIdList = null;
	private static String xmlReadUrl = "";
	private static String xmlWriteUrl = "";
	
	static {
		String path = QuestionManager.class.getResource("").getPath();
		xmlReadUrl = path + "/QuestionsRead.xml";
		xmlWriteUrl = path + "/QuestionsWrite.xml";
	}
	
	public static void setXmlReadUrl(String readUrl){
		xmlReadUrl = readUrl;
	}
	
	public static void setXmlWriteUrl(String writeUrl){
		xmlWriteUrl = writeUrl;
	}
	
	public static List<String> getQuestionIdList(){
		return questionIdList;
	}
	
	public static List<String> getOptionIdList(String questionId){
		
		return optionIdList.get(Integer.parseInt(questionId) - 1);
	}
	
	/**
	 * 获得指定问题的所有选项。
	 * @param id 指定问题的题号。
	 * @return 选项组成集合。
	 */
	public static List<String> getOptionsById(String id){
		
		List<String> options = allOptions.get(Integer.parseInt(id) - 1);
		return options;
	}
	
	/**
	 * 获得指定的问题的指定选项。
	 * @param questionId 指定问题的题号。
	 * @param optionId 指定选项的序号。
	 * @return 指定的选项。
	 */
	public static String getOptionById(String questionId,String optionId){
		
		List<String> options = getOptionsById(questionId);
		return options.get(Integer.parseInt(optionId) - 1);
	}

	/**
	 * 获得所有问题题目。
	 * @return 题目组成的集合。
	 */
	public static List<String> getTitles(){
		return titles;
	}
	
	/**
	 * 获得指定问题的题目。
	 * @param id 指定问题的题号。
	 * 
	 * @return 题目。
	 */
	public static String getTitleById(String id){
		
		String title = titles.get(Integer.parseInt(id) - 1); 
		return title;
	}

	/**
	 * 获得指定问题的类型。包括单选，多选。
	 * @param id 题号。
	 * @return single:单选，more:多选。
	 */
	public static String getTypeById(String id){
		
		return types.get(Integer.parseInt(id) - 1);
	}
	
	/**
	 * 获得指定题目指定选项的已选数。
	 * 
	 * @param questionId 题目序号。
	 * @param optionId 选项序号。
	 * @return 该选项的已选数。
	 */
	public static String getOptionCountById(String questionId,String optionId){
		
		List<String> question = optionCount.get(Integer.parseInt(questionId) - 1);
		String result = question.get(Integer.parseInt(optionId) - 1);
		return result;
	}
	
	/**
	 * 设置指定题目指定选项的已选数，每次调用此方法都会将已选数加一。
	 * @param questionId 题目序号。
	 * @param optionId 选项序号。
	 */
	
	public static synchronized void setOptionCountById(String questionId,String optionId){
	
		int id1 = Integer.parseInt(questionId) - 1;
		int id2 = Integer.parseInt(optionId) - 1;
		List<String> question = optionCount.get(id1);
		String result = question.get(id2);
		result = String.valueOf(Integer.parseInt(result) + 1);
		question.set(id2,result);
		
		List<Node> nodeList = optionCountNode.get(id1);
		Node node = nodeList.get(id2);
		node.setNodeValue(result);
		
		write();
	}
	
	/**
	 * 
	 * 将投票后的结果输出到新的xml文件中,调用setOptionCount方法时会调用此方法，一般不需直接调用。
	 */
	
	public static synchronized void write(){
		
		TransformerFactory tff = TransformerFactory.newInstance();
		try {
			Transformer tf = tff.newTransformer();
			Source source = new DOMSource(doc);
			Result result = new StreamResult(new File(xmlWriteUrl));
			tf.transform(source, result);
			
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	static {
		
		read();
	}
	
	/**
	 * 
	 * 第一次使用时会自动先执行此方法，从xml文件中读取数据，一般不需要手动调用。
	 */
	public static void read(){
			
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(new File(xmlReadUrl));
			Element root = doc.getDocumentElement();
			

			titles = new ArrayList<String>();
			types = new ArrayList<String>();
			allOptions = new ArrayList<List<String>>();
			optionCount = new ArrayList<List<String>>();
			optionCountNode = new ArrayList<List<Node>>();
			optionIdList = new ArrayList<List<String>>();
			questionIdList = new ArrayList<String>();
			
			// 获得question节点。
			NodeList list = root.getElementsByTagName("question");
			
			// 遍历每个question,获得相关信息。
			for(int i=0;i<list.getLength();i++){
				
				Node node = list.item(i);
				NodeList childlist = node.getChildNodes();
				NamedNodeMap map = node.getAttributes();
				
				types.add(map.getNamedItem("type").getNodeValue());
				
				List<String> options = new ArrayList<String>();
				List<String> countList = new ArrayList<String>();
				List<Node> countNodeList = new ArrayList<Node>();
				List<String> optionIds = new ArrayList<String>();
				
				// 获得每个问题的标题，选项。
				int opttionIdCount = 1;
				for(int j=0;j<childlist.getLength();j++){
					Node childnode = childlist.item(j);
					
					if("title".equals(childnode.getNodeName())){
						titles.add(childnode.getTextContent());
						
					}
					if("option".equals(childnode.getNodeName())){
						
						options.add(childnode.getTextContent());
						Node countNode = childnode.getAttributes().getNamedItem("count");
						countNodeList.add(countNode);
						countList.add(countNode.getNodeValue());
						optionIds.add(String.valueOf(opttionIdCount));
						opttionIdCount ++;
					}
				}
				allOptions.add(options);
				optionIdList.add(optionIds);
				optionCount.add(countList);
				optionCountNode.add(countNodeList);
				questionIdList.add(String.valueOf(i + 1));
				
			}
			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}

}
