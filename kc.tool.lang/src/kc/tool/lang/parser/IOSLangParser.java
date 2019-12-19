package kc.tool.lang.parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;

import kc.tool.lang.model.LangData;
import kc.tool.lang.model.LangDataImpl;
import kc.tool.lang.model.LangItem;

public class IOSLangParser implements LangParser {

	@Override
	public LangData parseLang(String filePath) {
		// TODO Auto-generated method stub
		LangData ld=null;
		try {
			List<String> strings = IOUtils.readLines(new FileInputStream(filePath), "UTF-8");
			ld=new LangDataImpl();
			for(String s:strings) {
				if(s.indexOf("=")!=-1) {
					LangItem li=new LangItem();
					li.setName(s.split("=")[0].trim().replace("\"", "").replace(";", ""));
					li.setValue(s.split("=")[1].trim().replace("\"", "").replace(";", ""));
					ld.getLangList().add(li);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ld;
	}

}
