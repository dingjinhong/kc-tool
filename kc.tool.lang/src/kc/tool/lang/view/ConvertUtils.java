package kc.tool.lang.view;

import java.util.HashMap;

import kc.tool.lang.model.LangData;
import kc.tool.lang.model.LangDataImpl;
import kc.tool.lang.model.LangItem;
import kc.tool.lang.model.TranslationData;

public class ConvertUtils {
	public static LangData convertLang(LangData ld,TranslationData td){
		HashMap<String, String> map = new HashMap<String,String>();
		for(LangItem li:td.getItemList()) {
			map.put(li.getName(), li.getValue());
		}
		LangData newLd=new LangDataImpl();
		for(LangItem li:ld.getLangList()) {
			LangItem newLi = new LangItem();
			newLi.setName(li.getName());
			String key=li.getValue();
			String val=map.get(key);
			if(val==null) {
				val=li.getValue();
			}
			newLi.setValue(val);
			newLd.getLangList().add(newLi);
			
		}
		return newLd;
		
	}
	
}
