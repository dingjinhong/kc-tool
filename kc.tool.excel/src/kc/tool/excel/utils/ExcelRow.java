package kc.tool.excel.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;

public class ExcelRow {
	int maxColumnIndex=-1;
	Map<String,ExcelCell> cells=new HashMap<String,ExcelCell>();
	Row row;
	
	public Row getRow() {
		return row;
	}
	public void setRow(Row row) {
		this.row = row;
	}
	public ExcelRow() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ExcelRow(int maxColumnIndex, Map<String, ExcelCell> cells) {
		super();
		this.maxColumnIndex = maxColumnIndex;
		this.cells = cells;
	}
	public int getMaxColumnIndex() {
		return maxColumnIndex;
	}
	public void setMaxColumnIndex(int maxColumnIndex) {
		this.maxColumnIndex = maxColumnIndex;
	}
	public Map<String, ExcelCell> getCells() {
		return cells;
	}
	public void setCells(Map<String, ExcelCell> cells) {
		this.cells = cells;
	}
	
}
