package sc.ustc.controller;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.DocumentException;

import entity.ActionXml;
import entity.ControllerXml;
import entity.ResultXml;
import util.XmlParser;

public class Test {
	public static void main(String[] args) throws DocumentException, ClassNotFoundException{	
		/*
		String actionName = "login";
				
		String xmlPath = "src/controller.xml";
		XmlParser xmlParser = new XmlParser(xmlPath);
		xmlParser.setControllerXml();
		ControllerXml controllerXml = xmlParser.getControllerXml();
		
		
		boolean isFound = false;
		//输出xml文件中的数据测试
		for(ActionXml actionXml : controllerXml.getActionList()){
			if(actionName.equals(actionXml.getName())){
				isFound = true;		
				//通过java 反射机制，通过类名获取到java 类
				System.out.println(Class.forName(actionXml.getclassLocation()));
				System.out.println(actionXml.getMethod());
				
				Class<?> theAction = null;
				try{
					//通过java 反射机制，通过类名获取到java 类
					theAction = Class.forName(actionXml.getclassLocation());
					//通过java 反射机制，通过类名和方法名获取到对应方法并执行
					Method method=theAction.getMethod(actionXml.getMethod());
					//执行对应方法
					String resultName = (String) method.invoke(theAction.newInstance());
					//System.out.println(resultName);
					for(ResultXml resultXml : actionXml.getResultList()){
						//System.out.println(resultXml.getName());
						if(resultName!=null && resultName.equals(resultXml.getName())){
							System.out.println(resultXml.getType()+" : "+resultXml.getValue());
						}
					}					
				}catch(Exception e){
					e.printStackTrace();					
				} 
								
									
			}		
		}
		if(!isFound) System.out.println("不可识别的 action 请求");
		*/
		
		/*
		SAXReader reader = new SAXReader();
		Document document = reader.read(xmlPath);

		Element rootElement = document.getRootElement();// 获取根节点
		System.out.println(rootElement.getName().toString());		
		*/
		// 创建迭代器从根节点开始对整个xml树结构进行解析
		/*
		for (Iterator<?> iterator = rootElement.elementIterator(); iterator.hasNext();) {
			// 分别对每个子节点进行迭代
			Element element = (Element) iterator.next();
			System.out.println(element.getName());

			List<Element> list2 = element.elements();
			for (Element e : list2) {
				System.out.println(e.getText());
			}

			for (Iterator<?> iterator2 = element.elementIterator(); iterator2.hasNext();) {
				Element element2 = (Element) iterator2.next();
				System.out.print(element2.getName() + ":\t");

				// 获取某个节点的每个属性值
				String nameValue = element2.attribute("name").getValue();// 获取元素的login属性对象
				System.out.println(nameValue);

				String classValue = element2.attribute("class").getValue();// 获取元素的class属性对象
				System.out.println(classValue);

				for (Iterator<?> iterator3 = element2.elementIterator(); iterator3.hasNext();) {
					Element element3 = (Element) iterator3.next();
					System.out.println(element3.getName());

					String nameValue1 = element3.attribute("name").getValue();
					System.out.println(nameValue1);

					String typeValue = element3.attribute("type").getValue();
					System.out.println(typeValue);

					String pageValue = element3.attribute("value").getValue();
					System.out.println(pageValue);
				}
			}
		}
		*/
	}		
}
