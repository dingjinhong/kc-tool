package kc.tool.excel.utils;

import org.apache.poi.ss.usermodel.Cell;

import kc.tool.excel.constants.ExcelCellType;

public class ExcelCell {
	ExcelCellType type;
	String value;
	Cell cell;
	public Cell getCell() {
		return cell;
	}
	public void setCell(Cell cell) {
		this.cell = cell;
	}
	public ExcelCellType getType() {
		return type;
	}
	public void setType(ExcelCellType type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public ExcelCell(ExcelCellType type, String value) {
		super();
		this.type = type;
		this.value = value;
	}
	public ExcelCell() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
