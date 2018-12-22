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
import entity.InterceptorXml;
import entity.Interceptorref;
import entity.ResultXml;

public class XmlParser {
	private String xmlFilePath; // xml 文件路径
	private ControllerXml controllerXml; // 创建controllerxml对象
	private List<InterceptorXml> interceptorXmlList; // 创建interceptor对象

	public XmlParser(String xmlFilePath) {
		this.xmlFilePath = xmlFilePath;
		controllerXml = new ControllerXml();
		interceptorXmlList = new ArrayList<InterceptorXml>();
	}

	private Document getDocument(String url) throws DocumentException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(url);
		return document;
	}

	public String getElementValue(Element element, String name) {
		return element.attribute(name).getValue();
	}

	public ControllerXml getControllerXml() {
		return this.controllerXml;
	}

	public List<InterceptorXml> getInterceptorXmlList() {
		return this.interceptorXmlList;
	}

	public void setControllerXml() throws DocumentException {
		List<ActionXml> actionXmllist = new ArrayList<ActionXml>(); // 创建actionxml对象
		List<ResultXml> resultList = new ArrayList<ResultXml>(); // 创建resultxml
		List<Interceptorref> interceptorrefList = new ArrayList<Interceptorref>(); // 创建interceptorrefList对象

		Document document = getDocument(this.xmlFilePath); // 获取xml文件
		Element rootElement = document.getRootElement();// 获取根节点

		// 得到interceptor节点，并将所有定义的拦截器加入列表中
		for (Iterator<?> interceptorIterator = rootElement.elementIterator(); interceptorIterator.hasNext();) {
			InterceptorXml interceptorXml = new InterceptorXml();
			Element interceptorElement = (Element) interceptorIterator.next();
			if ("interceptor".equals(interceptorElement.getName())) {
				interceptorXml.setAll(getElementValue(interceptorElement, "name"),
						getElementValue(interceptorElement, "class"), getElementValue(interceptorElement, "predo"),
						getElementValue(interceptorElement, "afterdo"));
				interceptorXmlList.add(interceptorXml); // 将定义的拦截器加入列表中
			}
		}

		// 得到controller节点
		Element elementCtrl = rootElement.element("controller");
		// 分别对每个action子节点进行迭代
		for (Iterator<?> actionIterator = elementCtrl.elementIterator(); actionIterator.hasNext();) {
			ActionXml actionXml = new ActionXml();
			Element actionElement = (Element) actionIterator.next();
			// 分别对每个result子节点进行迭代
			for (Iterator<?> resultIterator = actionElement.elementIterator(); resultIterator.hasNext();) {
				// 获取action 中配置的拦截器信息
				Interceptorref interceptorref = new Interceptorref();
				// 获取action中配置的result信息
				ResultXml resultXml = new ResultXml();

				Element resultElement = (Element) resultIterator.next();
				if ("interceptor-ref".equals(resultElement.getName())) {
					interceptorref.setName(getElementValue(resultElement, "name"));
					interceptorrefList.add(interceptorref);
				}
				if ("result".equals(resultElement.getName())) {
					resultXml.setAll(getElementValue(resultElement, "name"), getElementValue(resultElement, "type"),
							getElementValue(resultElement, "value"));
					resultList.add(resultXml);
				}

			}
			// 获取当前的action
			actionXml.setResultList(resultList);
			actionXml.setInterceptorrefList(interceptorrefList);
			interceptorrefList.clear(); //// 每次设置完后，要将list清空，用于下一个interceptor的数据存储
			resultList.clear(); // 每次设置完后，要将list清空，用于下一个result的数据存储
			actionXml.setAll(getElementValue(actionElement, "name"), getElementValue(actionElement, "class"),
					getElementValue(actionElement, "method"));
			// 把当前的action加入list中
			actionXmllist.add(actionXml);
		}
		// 获取当前的controller
		controllerXml.setActionList(actionXmllist);
		actionXmllist.clear(); // 每次设置完后，要将list清空，用于下一个action的数据存储
	}
}
