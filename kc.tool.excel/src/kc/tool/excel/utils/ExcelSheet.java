package kc.tool.excel.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;

public class ExcelSheet {
	int columnNum=-1;
	String name="";
	List<ExcelRow> rows=new ArrayList<ExcelRow>();
	List<Integer> columnWidthList=new ArrayList<Integer>();
	int sheetIndex=-1;
	Sheet sheet;
	
	public int getSheetIndex() {
		return sheetIndex;
	}
	public void setSheetIndex(int sheetIndex) {
		this.sheetIndex = sheetIndex;
	}
	public Sheet getSheet() {
		return sheet;
	}
	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}
	public List<Integer> getColumnWidthList() {
		return columnWidthList;
	}
	public ExcelSheet(int columnNum, String name, List<ExcelRow> rows) {
		super();
		this.columnNum = columnNum;
		this.name = name;
		this.rows = rows;
	}
	public ExcelSheet() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getColumnNum() {
		return columnNum;
	}
	public void setColumnNum(int columnNum) {
		this.columnNum = columnNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ExcelRow> getRows() {
		return rows;
	}
	public void setRows(List<ExcelRow> rows) {
		this.rows = rows;
	}
}
