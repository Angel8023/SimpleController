package sc.ustc.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import entity.orMapping.ClassMapping;
import entity.orMapping.ClassProperty;
import entity.orMapping.JDBC;
import sc.ustc.controller.SimpleController;
import util.ClassReflector;

//Configuration 负责解析 UseSC 工程的配置 or_mapping.xml
public class Configuration {
	private JDBC jdbc;
	private List<ClassMapping> classMappingList;
	private String orMappingXmlPath;

	public Configuration() {
		// TODO Auto-generated constructor stub
		jdbc = new JDBC();
		classMappingList = new ArrayList<ClassMapping>();
		orMappingXmlPath = SimpleController.class.getClassLoader().getResource("or_mapping.xml").getPath();
	}

	// 先读取url路径中的文件，读取为document对象
	public Document getDocument(String url) {
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(url);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return document;
	}

	// 根据节点和名称，获取节点内容
	private String getElementValue(Element element, String name) {
		return element.elementText(name);
	}

	// 获取到jdbc 配置属性
	public JDBC getJdbc() {
		Document document = getDocument(orMappingXmlPath);
		Element rootElement = document.getRootElement(); // 获取到根节点
		// 获取到jdbc节点
		Element elementJdbc = rootElement.element("jdbc");
		// 遍历所有的property 对jdbc对象进行赋值
		for (Iterator<?> propertyIterator = elementJdbc.elementIterator(); propertyIterator.hasNext();) {
			Element propetyElement = (Element) propertyIterator.next();
			// 为jdbc的对应属性赋值
			ClassReflector.setField(jdbc, getElementValue(propetyElement, "name"),
					getElementValue(propetyElement, "value"));
		}
		return jdbc;
	}

	// 获取到到 table 和class 的映射信息
	public List<ClassMapping> getClassMappingList() {
		Document document = getDocument(orMappingXmlPath);
		Element rootElement = document.getRootElement(); // 获取到根节点

		// 对root节点进行迭代，遍历所有的class节点
		for (Iterator<?> classIterator = rootElement.elementIterator(); classIterator.hasNext();) {
			Element elementClass = (Element) classIterator.next();
			// 如果当前遍历的是class节点
			if ("class".equals(elementClass.getName())) {
				ClassMapping classMapping = new ClassMapping();				
				classMapping.setClassName(getElementValue(elementClass, "name"));
				classMapping.setTable(getElementValue(elementClass, "table"));
				classMapping.setId(getElementValue(elementClass.element("id"), "name"));									

				for (Iterator<?> proIterator = elementClass.elementIterator(); proIterator.hasNext();) {
					Element proElement = (Element) proIterator.next();					
					
					if ("property".equals(proElement.getName())) {
						ClassProperty classProperty = new ClassProperty();
						classProperty.setName(getElementValue(proElement, "name"));
						classProperty.setColumn(getElementValue(proElement, "column"));
						classProperty.setType(getElementValue(proElement, "type"));
						classProperty.setLazy(getElementValue(proElement, "lazy"));												
						// 将该列属性放入propertyMaping中
						classMapping.getPropertyList().add(classProperty);
					}
				}
				// 将该classMapping 放入list中
				classMappingList.add(classMapping);
			}
		}		
		return classMappingList;
	}

	public String getOrMappingXmlPath() {
		return orMappingXmlPath;
	}

	public void setOrMappingXmlPath(String orMappingXmlPath) {
		this.orMappingXmlPath = orMappingXmlPath;
	}

}
