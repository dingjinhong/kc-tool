package kc.tool.lang.parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import kc.tool.lang.model.LangData;
import kc.tool.lang.model.LangDataImpl;
import kc.tool.lang.model.LangItem;

public class AndroidLangParser implements LangParser {

	@Override
	public LangData parseLang(String langFile) {
		// TODO Auto-generated method stub
		LangData ld=null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        // 获取此类的实例之后，将可以从各种输入源解析XML
		
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			InputSource is=new InputSource(new InputStreamReader(new FileInputStream(langFile), "UTF-8"));
	        // builder.parse(this.getClass().getResourceAsStream("/" + fileName));
	        // Document接口表示整个HTML或XML文档，从概念上讲，它是文档树的根，并提供对文档数据的基本访问
			Document document =builder.parse(is);
			ld=new LangDataImpl();
			NodeList nodeList = document.getElementsByTagName("string");
			for(int i=0;i<nodeList.getLength();i++) {
				Element e=(Element) nodeList.item(i);
				LangItem li=new LangItem();
				li.setName(e.getAttribute("name"));
				li.setValue(e.getTextContent());
				ld.getLangList().add(li);
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ld;
	}

}
