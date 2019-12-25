package kc.tool.excel.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kc.tool.excel.constants.ExcelCellType;

public class ExcelManager {
	String filePath;
	Workbook workbook;
	 
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Workbook getWorkbook() {
		return workbook;
	}
	
	public ExcelManager(String file) {
		super();
		this.filePath = file;
	}
	public  List<ExcelSheet> parseExcel() {
		closeWorkbook();
		FileInputStream fis=null; 
	        try {
	            File f=new File(filePath);
	            //��ȡ��������ļ�����
	        	String filePath=f.getName();
	        	String extString = filePath.substring(filePath.lastIndexOf("."));
	        	fis=new FileInputStream(f);
		        if(".xls".equals(extString)){
		        	workbook = new HSSFWorkbook(fis);
		        }else if(".xlsx".equals(extString)){
		        	workbook = new XSSFWorkbook(fis);
		        }
	            //��ȡ����sheet����
	            Iterator<Sheet> sheetIterator = workbook.sheetIterator();
	            List<ExcelSheet> list=new ArrayList<ExcelSheet>();
	            while (sheetIterator.hasNext()){
	                Sheet sheet = sheetIterator.next();
	                ExcelSheet excelSheet = parseSheet(sheet);
	                excelSheet.setName(sheet.getSheetName());
	                list.add(excelSheet);
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
//	        	if(workbook!=null) {
//	        		try {
//	        			workbook.close();
//						
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//	        	}
	        	//save();
	        	
	        }
	    }
	    /**
	     * ����������
	     * @param sheet
	     */
	    private ExcelSheet parseSheet(Sheet sheet) {
	        
	        //��ȡ����Row����
	    	List<ExcelRow> list=new ArrayList<ExcelRow>();
	        Iterator<Row> iterator = sheet.iterator();
	        int columnNum=-1;
	        while (iterator.hasNext()){
	            ExcelRow row = parseRow(iterator.next());
	            if(columnNum<row.getMaxColumnIndex()) {
	            	columnNum=row.getMaxColumnIndex();
	            }
	            list.add(row);
	        }
	        ExcelSheet es = new ExcelSheet(columnNum, "", list);
	        es.setSheet(sheet);
	        for(int i=0;i<columnNum+1;i++) {
	        	es.getColumnWidthList().add(Math.round(sheet.getColumnWidthInPixels(i)));
	        }
	       
	        return es;
	    }
	    /**
	     * ����ÿ��
	     * @param next
	     */
	    private ExcelRow parseRow(Row next) {
	        
	        //��ȡ����Cell����ȡֵ
	        Iterator<Cell> cellIterator = next.cellIterator();
	        Map<String,ExcelCell> map=new LinkedHashMap<String,ExcelCell>();
	     
	        int columnIndex=-1;
	        while (cellIterator.hasNext()){

	            Cell cell = cellIterator.next();
	            
	            cell.setCellType(CellType.STRING);
	            int index=cell.getColumnIndex();
	            if(index>columnIndex) {
	            	columnIndex=index;
	            }
	            ExcelCell excelCell = new ExcelCell();
	            excelCell.setCell(cell);
	            if(cell.getHyperlink()!=null) {
	            	excelCell.setType(ExcelCellType.LINK);
	            	excelCell.setValue(cell.getHyperlink().getAddress());
	            }else {
	            	excelCell.setType(ExcelCellType.STRING);
	            	excelCell.setValue(cell.getStringCellValue());
	            }
	            
	            //System.out.println("cell.value = " + cell.getStringCellValue());
	            map.put(index+"",excelCell);
	        }
	        ExcelRow excelRow=new ExcelRow(columnIndex, map);
	        excelRow.setRow(next);
	        return excelRow;
	    }
	    public void changeViewTimes(ExcelRow excelRow,ExcelCell excelCell,int index,String value) {
	    	Row row=excelRow.getRow();
	    	ExcelCell nextExcelCell = excelRow.getCells().get(index+"");
        	if(nextExcelCell==null) {
        		nextExcelCell = new ExcelCell(ExcelCellType.STRING, value); 
        		Cell nextCell = row.createCell(index);
        		nextCell.setCellValue(value);
        		nextExcelCell.setCell(nextCell);
        		excelRow.getCells().put(index+"",nextExcelCell);
        	}else {
        		nextExcelCell.setValue(value);
        		nextExcelCell.getCell().setCellValue(value);
        	}
	    }
	    public void save() {
	    	if(workbook!=null) {
				try {
					FileOutputStream out= new FileOutputStream(filePath);
					workbook.write(out);
					out.flush();
					out.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    }
	    public void closeWorkbook() {
        	if(workbook!=null) {
	    		try {
	    			workbook.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
	    }
}
