package kc.tool.lang.builder;

import kc.tool.lang.constants.OSType;


public class LangBuilderFactory {
	public static LangBuilderFactory instance=new LangBuilderFactory();
	private LangBuilderFactory(){
		
	}
	public LangBuilder getLangBuilder(OSType t) {
		if(t==OSType.ANDROID) {
			return new AndroidLangBuilder();
		}
		if(t==OSType.IOS) {
			return new IOSLangBuilder();
		}
		return null;
	}
}
