package kc.tool.lang.parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import kc.tool.lang.model.LangData;

public class WebLangParser implements LangParser {

	@Override
	public LangData parseLang(String filePath) {
		// TODO Auto-generated method stub
		try {
			JsonParser parser = new JsonParser();
			InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath),"UTF-8");
			JsonElement jsonElement = parser.parse(isr);
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
