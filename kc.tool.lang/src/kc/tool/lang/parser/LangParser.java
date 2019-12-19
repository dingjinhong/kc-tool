package kc.tool.lang.parser;

import kc.tool.lang.model.LangData;

public interface LangParser {
	LangData parseLang(String filePath); 
}
