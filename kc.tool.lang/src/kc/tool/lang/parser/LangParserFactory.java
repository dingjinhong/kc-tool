package kc.tool.lang.parser;

import kc.tool.lang.constants.OSType;

public class LangParserFactory {
	public static LangParserFactory instance=new LangParserFactory();
	private LangParserFactory(){
		
	}
	public LangParser getLangParser(OSType t) {
		if(t==OSType.ANDROID) {
			return new AndroidLangParser();
		}
		if(t==OSType.IOS) {
			return new IOSLangParser();
		}
		return null;
	}
}
