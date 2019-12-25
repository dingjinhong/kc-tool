package kc.tool.lang.builder;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import kc.tool.lang.model.LangData;
import kc.tool.lang.model.LangItem;

public class IOSLangBuilder implements LangBuilder {

	@Override
	public void buildLang(LangData ld, String filePath) {
		// TODO Auto-generated method stub
		

		try {
			List<String> list=new ArrayList<String>();
			for(LangItem li:ld.getLangList()) {
				String s="\""+li.getName()+"\" = "+"\""+li.getValue().trim()+"\";";
				list.add(s);
			}
			IOUtils.writeLines(list, "\n", new FileOutputStream(filePath), "utf-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
