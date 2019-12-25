package kc.tool.lang.model;

import java.util.ArrayList;
import java.util.List;

public class TranslationData {
	String name;
	List<LangItem> itemList=new ArrayList<LangItem>();
	public TranslationData() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public TranslationData(String name, List<LangItem> itemList) {
		super();
		this.name = name;
		this.itemList = itemList;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public List<LangItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<LangItem> itemList) {
		this.itemList = itemList;
	}
	
	
}
