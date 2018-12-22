package util;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import entity.ActionLog;
import entity.LogXml;

public class LogUtil {
	private String logXmlPath;
	private LogXml logXml;

	public LogUtil(String logXmlPath) {
		// TODO Auto-generated constructor stub
		this.logXmlPath = logXmlPath;
		logXml = LogXml.INSTANCE;
	}

	private Document getDocument(String url) throws DocumentException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(url);
		if (document == null) {
			System.out.println("文件为空");
		}
		return document;
	}

	private String getElementValue(Element element, String name) {
		return element.elementText(name);
	}

	// 将log.xml文件读取为LogXml 对象
	public LogXml readLog() throws DocumentException {
		// 创建actionLog列表
		List<ActionLog> actionLogList = new ArrayList<ActionLog>();

		Document document = getDocument(this.logXmlPath); // 获取xml文件
		Element rootElement = document.getRootElement();// 获取根节点

		// 迭代遍历每个action节点
		for (Iterator<?> actionIterator = rootElement.elementIterator(); actionIterator.hasNext();) {
			ActionLog actionLog = new ActionLog();
			Element actionElement = (Element) actionIterator.next();

			actionLog.setAll(getElementValue(actionElement, "name"), getElementValue(actionElement, "s-time"),
					getElementValue(actionElement, "e-time"), getElementValue(actionElement, "result"));

			logXml.addAction(actionLog);			
		}
		return logXml;
	}

	// 把对象写入日志文件log.xml中
	public void writeLog() {
		// 创建文档。
		Document document = DocumentHelper.createDocument();
		// 文档增加节点log节点，即根节点，一个文档只能有一个根节点，多加出错
		Element root = document.addElement("log");
		// 添加注释
		root.addComment("some actions");

		for (ActionLog actionLog : logXml.getActionList()) {
			// 在root节点下，添加action节点
			Element actionElement = root.addElement("action");
			// 在action节点下，添加其他节点
			Element nameElement = actionElement.addElement("name");
			// 设置name节点内容数据
			nameElement.setText(actionLog.getName());
			Element stimeElement = actionElement.addElement("s-time");
			// 设置stime节点内容数据
			stimeElement.setText(actionLog.getStime());
			Element etimeElement = actionElement.addElement("e-time");
			// 设置etime节点内容数据
			etimeElement.setText(actionLog.getEtime());
			Element resultElement = actionElement.addElement("result");
			// 设置result节点内容数据
			resultElement.setText(actionLog.getResult());
		}
		// 将创建的xml文件写入磁盘中
		try {
			// 创建格式化类
			OutputFormat format = OutputFormat.createPrettyPrint();
			// 设置编码格式，默认UTF-8
			format.setEncoding("UTF-8");
			// 创建输出流，此处要使用Writer，需要指定输入编码格式，使用OutputStream则不用
			FileOutputStream fos = new FileOutputStream("src/log.xml");
			// 创建xml输出流
			XMLWriter writer = new XMLWriter(fos, format);
			// 生成xml文件
			writer.write(document);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
