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
	  
	 /* for(ActionXml actionXml2 : actionXmllist){
		  System.out.println(actionXml2.getMethod());
	  }*/
	  //获取当前的controller
	  controllerXml.setActionList(actionXmllist);	  
	  actionXmllist.clear();  //每次设置完后，要将list清空，用于下一个action的数据存储
	}																	
	
	/*
	private static final String CTRL_NODE_NAME = "controller";	
	private static final String ACTION_NODE_NAME = "action";
	private static final String RESULT_NODE_NAME = "result";
	private static final String ATTR_NAME = "name";
	private static final String NONE_STR = "";	
	
	private static final String INTERCEPTOR_NODE_TYPE = "interceptor";					
	
	public Element matchInterceptor (String interceptorName) throws DocumentException {
		//得到Document对象
		Document document = getDocument(xmlFilePath);
		//获取XML根节点
		Element root = document.getRootElement();
		return matchElement(INTERCEPTOR_NODE_TYPE ,interceptorName, root);
	}
	
	public Element matchAction(String actionName) throws DocumentException {
		//得到Document对象
		Document document = getDocument(xmlFilePath);
		//获取XML根节点
		Element root = document.getRootElement();
		//得到controller节点
		Element elementCtrl = root.element(CTRL_NODE_NAME);
		
		return matchElement(ACTION_NODE_NAME ,actionName, elementCtrl);
	}
	
	public Element matchResult(String parentActionName, String resultName) throws DocumentException {
		//得到Document对象
		Document document = getDocument(xmlFilePath);
		//获取XML根节点
		Element root = document.getRootElement();
		//得到controller节点
		Element elementCtrl = root.element(CTRL_NODE_NAME);
		//得到特定的action节点
		Element elementAction = matchElement( ACTION_NODE_NAME ,parentActionName, elementCtrl);
		
		return matchElement(RESULT_NODE_NAME ,resultName, elementAction);
	}
	
	public Element matchElement (String elementType, String elementName, Element parentElement ) {
		return matchElement(elementType,ATTR_NAME,elementName,parentElement);
	}
	
	public static Element matchElement (String elementType,String attrType, String attrName, Element parentElement ) {
		Element matchedElement = null;
		//获取parentElement节点下所以直接结点列表
		List<Element> elementList = getElementNodeList(parentElement,elementType);
		//判断parentElement节点下是否有节点
		if ( elementList.size() == 0 ) {
			throw new RuntimeException(parentElement.getName()+"节点下面没有节点");
		}
		//遍历整个elementList
		for (Element element : elementList) {
			//去掉传入的elementName前后空格
			attrName = attrName.trim();
			//得到name属性对应的值
			String attrValue = getAttributeValue(element,attrType);
			//匹配
			if ( NONE_STR.equals( attrName ) ) {
				throw new RuntimeException("attrName不能为全空格");
			} else if ( attrName.equals( attrValue ) ) {
				matchedElement = element;
				break;
			}
		}
		//遍历完整个elementList,没有找到匹配elementName的element
		if (matchedElement == null) {
			throw new RuntimeException( attrName + "不能匹配到合适的" + elementType );
		}
		return matchedElement;
	}
	
	public static String getAttributeValue( Element element , String attrName) {
		Attribute attribute = element.attribute(attrName.trim());
		if(attribute == null) {
			throw new RuntimeException("元素节点"+element.getName()+"中找不到");
		}
		return attribute.getValue().trim();
	}
	
	public static String getNodeContent( Element element ) {
		return element.getTextTrim();
	}
	
	@SuppressWarnings("unchecked")
	private static List<Element> getElementNodeList(Element root, String elementType) {
		return  root.elements(elementType);
	}
		
	*/			
}
