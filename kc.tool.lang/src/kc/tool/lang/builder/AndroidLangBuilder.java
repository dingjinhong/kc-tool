package kc.tool.lang.builder;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import kc.tool.lang.model.LangData;
import kc.tool.lang.model.LangItem;

public class AndroidLangBuilder implements LangBuilder{

	@Override
	public void buildLang(LangData ld,String Path) {
		// TODO Auto-generated method stub
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // 获取此类的实例之后，将可以从各种输入源解析XML
		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			Element res = document.createElement("resources");
			document.appendChild(res);
			for(LangItem li:ld.getLangList()) {
				Element str = document.createElement("string");
				str.setAttribute("name", li.getName());
				str.setTextContent(li.getValue().trim());
				res.appendChild(str);
			}
			TransformerFactory tff = TransformerFactory.newInstance();
			// 创建 Transformer对象
			Transformer tf = tff.newTransformer();
			
			// 输出内容是否使用换行
			tf.setOutputProperty(OutputKeys.INDENT, "yes");
			tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			// 创建xml文件并写入内容
			tf.transform(new DOMSource(document), new StreamResult(new File(Path)));

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
		

}
