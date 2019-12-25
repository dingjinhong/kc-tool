package kc.tool.excel.utils.test;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExcelParse {
	

	    public List<List<Map<String,String>>> parseExcel(File file) {
	        try {
	            
	            //��ȡ��������ļ�����
	        	String filePath=file.getName();
	        	String extString = filePath.substring(filePath.lastIndexOf("."));
	        	Workbook workbook=null;
		        if(".xls".equals(extString)){
		        	workbook = new HSSFWorkbook(new FileInputStream(file));
		        }else if(".xlsx".equals(extString)){
		        	workbook = new XSSFWorkbook(new FileInputStream(file));
		        }
	            
	            //��ȡ����sheet����
	            Iterator<Sheet> sheetIterator = workbook.sheetIterator();
	            List<List<Map<String,String>>> list=new ArrayList<List<Map<String,String>>>();
	            while (sheetIterator.hasNext()){
	                Sheet sheet = sheetIterator.next();
	                list.add(parseSheet(sheet));
	            }
	            return list;
	        } catch (IOException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	    /**
	     * ����������
	     * @param sheet
	     */
	    private List<Map<String,String>> parseSheet(Sheet sheet) {
	        
	        //��ȡ����Row����
	    	List<Map<String,String>> list=new ArrayList<Map<String,String>>();
	        Iterator<Row> iterator = sheet.iterator();
	        while (iterator.hasNext()){
	            list.add(parseRow(iterator.next()));
	        }
	        return list;
	    }
	    /**
	     * ����ÿ��
	     * @param next
	     */
	    private Map<String,String> parseRow(Row next) {
	        
	        //��ȡ����Cell����ȡֵ
	    	System.out.println(next.getPhysicalNumberOfCells());
	        Iterator<Cell> cellIterator = next.cellIterator();
	        Map<String,String> map=new LinkedHashMap<String,String>();
	        int index=0;
	        while (cellIterator.hasNext()){
	        	index++;
	            Cell cell = cellIterator.next();
	            cell.setCellType(CellType.STRING);
	            //System.out.println("cell.value = " + cell.getStringCellValue());
	            map.put(index+"",cell.getStringCellValue());
	        }
	        return map;
	    }
	
}
