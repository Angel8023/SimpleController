package util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import entity.ActionXml;
import entity.ControllerXml;
import entity.ResultXml; 

public class XmlParser {
	private String xmlFilePath;  	//xml 文件路径
	private ControllerXml controllerXml;    //创建controllerxml对象
	
	public XmlParser(String xmlFilePath){
		this.xmlFilePath = xmlFilePath;
		controllerXml = new ControllerXml();
	}
	
	private Document getDocument(String url) throws DocumentException {		
		 SAXReader reader = new SAXReader();
	     Document document = reader.read(url);
	     return document;
	}
	
	public String getElementValue(Element element,String name){
		return element.attribute(name).getValue();
	}	
	
	public ControllerXml getControllerXml() {
		return this.controllerXml;
	}
	
	public void setControllerXml() throws DocumentException{
		List<ActionXml> actionXmllist = new ArrayList<ActionXml>();  //创建actionxml对象
		List<ResultXml> resultList = new ArrayList<ResultXml>();   //创建resultxml 对象
						
				
	    Document document = getDocument(this.xmlFilePath);   //获取xml文件
	    Element rootElement = document.getRootElement();// 获取根节点	
	    
	  //得到controller节点
	  Element elementCtrl = rootElement.element("controller");
	  
      //分别对每个action子节点进行迭代
	  for(Iterator<?> actionIterator = elementCtrl.elementIterator();actionIterator.hasNext();){		
		ActionXml actionXml = new ActionXml();
		Element actionElement = (Element) actionIterator.next();
		
		//分别对每个result子节点进行迭代
		for(Iterator<?> resultIterator = actionElement.elementIterator();resultIterator.hasNext();){
			ResultXml resultXml = new ResultXml();			
			Element resultElement = (Element) resultIterator.next();
			resultXml.setAll(getElementValue(resultElement,"name"), 
					getElementValue(resultElement,"type"), 
					getElementValue(resultElement,"value"));
			resultList.add(resultXml);	
		}
		
		//获取当前的action
		actionXml.setResultList(resultList);  
		resultList.clear();   //每次设置完后，要将list清空，用于下一个result的数据存储
		
		actionXml.setAll(getElementValue(actionElement, "name"), 
				getElementValue(actionElement, "class"), 
				getElementValue(actionElement, "method"));
		
		//把当前的action加入list中
		actionXmllist.add(actionXml);			
	  }	 
	  //获取当前的controller
	  controllerXml.setActionList(actionXmllist);	  
	  actionXmllist.clear();  //每次设置完后，要将list清空，用于下一个action的数据存储
	}																				
}
