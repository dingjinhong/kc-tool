package kc.tool.lang.view;

import kc.tool.lang.constants.OSType;

public interface ILangView {
	void setLangFilePath(String p);
	void setTranslationFilePath(String p);
	void setOS(OSType p);
	String getLangFilePath();
	String getTranslationFilePath();
	OSType getOS();
	
	
//	void setLangData(LangData ld);
//	void setTranslationDataList(List<TranslationData> tds);
}
