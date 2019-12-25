package kc.tool.lang.translation;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kc.tool.lang.model.LangItem;
import kc.tool.lang.model.TranslationData;

public class TranslationParser {
	public static List<TranslationData> getTranslation(String filePath) {
		FileInputStream fis=null; 
		Workbook workbook=null;
        try {
        	String extString = filePath.substring(filePath.lastIndexOf("."));
        	fis=new FileInputStream(filePath);

			if(".xls".equals(extString)){
	        	workbook = new HSSFWorkbook(fis);
	        }else if(".xlsx".equals(extString)){
	        	workbook = new XSSFWorkbook(fis);
	        }
            //��ȡ����sheet����
            Iterator<Sheet> sheetIterator = workbook.sheetIterator();
            List<TranslationData> list=new ArrayList<TranslationData>();
            while (sheetIterator.hasNext()){
                Sheet sheet = sheetIterator.next();
                TranslationData excelSheet = parseSheet(sheet);
                excelSheet.setName(sheet.getSheetName());
                list.add(excelSheet);
            }
            if(workbook!=null) {
            	workbook.close();
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally {
        	if(fis!=null) {
        		try {
					fis.close();
				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}

        	
        }
	}
    private static TranslationData parseSheet(Sheet sheet) {
        
        //��ȡ����Row����
    	List<LangItem> list=new ArrayList<LangItem>();
        Iterator<Row> iterator = sheet.iterator();

        while (iterator.hasNext()){
        	LangItem row = parseRow(iterator.next());
            list.add(row);
        }
        TranslationData es = new TranslationData();
        es.setName(sheet.getSheetName());
        es.setItemList(list);

       
        return es;
    }
    /**
     * ����ÿ��
     * @param next
     */
    private static LangItem parseRow(Row next) {
        
        //��ȡ����Cell����ȡֵ
        Iterator<Cell> cellIterator = next.cellIterator();
        LangItem li=new LangItem();
        while (cellIterator.hasNext()){
            Cell cell = cellIterator.next();
            
            cell.setCellType(CellType.STRING);
            int index=cell.getColumnIndex();
           
            if(index==0) {	
            	li.setName(cell.getStringCellValue());
            }else {
            	li.setValue(cell.getStringCellValue());
            	break;
            }
        }
        return li;
    }
 
}
