package kc.tool.excel.constants;

public enum ExcelCellType {
	STRING("string", 1), LINK("link", 2),;
	private String name;
	private int index;
	private ExcelCellType(String name,int index){
		this.name=name;
		this.index=index;
	}
	public String getName() {
		
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
}
