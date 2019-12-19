package kc.tool.excel.utils.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class Test1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExcelParse ep=new ExcelParse();
		List<List<Map<String, String>>> excelList = ep.parseExcel(new File("excel/test.xlsx"));
		System.out.println(excelList.size());
		for(List<Map<String,String>> list:excelList) {
			for(Map<String,String> map:list ) {
				
				Iterator<Entry<String, String>> it = map.entrySet().iterator();
				while(it.hasNext()) {
					Entry<String, String> entry = it.next();
					System.out.println(entry.getKey()+" : "+entry.getValue());
				}
			}
		}

	}

}
