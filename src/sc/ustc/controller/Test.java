package sc.ustc.controller;

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
	public static void main(String[] args) throws DocumentException{
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

		String xmlPath = "src/controller.xml";
		XmlParser xmlParser = new XmlParser(xmlPath);
		xmlParser.setControllerXml();
		ControllerXml controllerXml = xmlParser.getControllerXml();
		
		//���xml�ļ��е����ݲ���
		for(ActionXml actionXml : controllerXml.getActionList()){
			for(ResultXml resultXml : actionXml.getResultList()){
				System.out.println(resultXml.getName());
			}
		}
		
					
		/*
		SAXReader reader = new SAXReader();
		Document document = reader.read(xmlPath);

		Element rootElement = document.getRootElement();// ��ȡ���ڵ�
		System.out.println(rootElement.getName().toString());		
		*/
		// �����������Ӹ��ڵ㿪ʼ������xml���ṹ���н���
		/*
		for (Iterator<?> iterator = rootElement.elementIterator(); iterator.hasNext();) {
			// �ֱ��ÿ���ӽڵ���е���
			Element element = (Element) iterator.next();
			System.out.println(element.getName());

			List<Element> list2 = element.elements();
			for (Element e : list2) {
				System.out.println(e.getText());
			}

			for (Iterator<?> iterator2 = element.elementIterator(); iterator2.hasNext();) {
				Element element2 = (Element) iterator2.next();
				System.out.print(element2.getName() + ":\t");

				// ��ȡĳ���ڵ��ÿ������ֵ
				String nameValue = element2.attribute("name").getValue();// ��ȡԪ�ص�login���Զ���
				System.out.println(nameValue);

				String classValue = element2.attribute("class").getValue();// ��ȡԪ�ص�class���Զ���
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