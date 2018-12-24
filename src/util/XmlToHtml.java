package util;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.dom4j.Document;
import org.dom4j.io.DocumentResult;
import org.dom4j.io.DocumentSource;
import org.dom4j.io.HTMLWriter;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;

import sc.ustc.controller.SimpleController;

public class XmlToHtml {
	// 通过xml文件生成html文件，并以字符串的形式返回
	// 传入的是一个xml文件
	public static String getHtmlByXml(File file) {
		// TODO Auto-generated method stub
		// 得到xsl文件路径
		String path = SimpleController.class.getClassLoader().getResource("../../").getPath() + "xslFile/translator.xsl";
		File xslFile = new File(path);
		Document xmlDoc = null; // xml document
		Document htmlDoc = null; // html document
		try {
			xmlDoc = new SAXReader().read(file);
			// 按照xsl文件中定义的格式，将document转换为Html document
			htmlDoc = getHtmlDocumentByXsl(xmlDoc, xslFile);
		} catch (Exception e) {
			if (xmlDoc == null)
				throw new RuntimeException(file.toString() + "不存在");
		}
		//System.out.println(getHtmlString(htmlDoc));
		String htmlString  = getHtmlString(htmlDoc);
		System.out.println(htmlString);
		// 将html文件写成字符串形式
		return htmlString;
	}

	// 将html文件转为字符串形式
	public static String getHtmlString(Document doc) {
		// 将HTML写成字符串流
		StringWriter strWriter = new StringWriter();
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding(doc.getXMLEncoding());
		format.setXHTML(true);
		// 按照format的格式，将html文件写入strWriter字符流
		HTMLWriter htmlWriter = new HTMLWriter(strWriter, format);
		format.setExpandEmptyElements(false);
		try {
			htmlWriter.write(doc);
			htmlWriter.flush();
		} catch (IOException e) {
		}
		return strWriter.toString();
	}

	// 将document按照xsl格式转换成HTML document
	public static Document getHtmlDocumentByXsl(Document document, File file) throws Exception {
		// 使用JAXP加载xstl
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(new StreamSource(file));
		DocumentSource source = new DocumentSource(document);
		DocumentResult result = new DocumentResult();
		transformer.transform(source, result);
		// 返回转换过的文档
		Document transformedDoc = result.getDocument();
		return transformedDoc;
	}
}
