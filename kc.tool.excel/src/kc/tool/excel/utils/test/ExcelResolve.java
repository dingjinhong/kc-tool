package kc.tool.excel.utils.test;
//package kc.djh.excel.utils.test;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.poi.hssf.usermodel.HSSFDateUtil;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
//import org.apache.poi.poifs.filesystem.POIFSFileSystem;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
//
//public class ExcelResolve {
//	public final String XLSX = ".xlsx";
//    public final String XLS=".xls";
// 
//    /**
//     * ��ȡExcel�ļ���.xls��.xlsx��֧�֣�
//     * @param file
//     * @return  ����excle���Json����
//     * @throws IOException
//     * @throws FileNotFoundException
//     * @throws InvalidFormatException
//     */
//    public JsonArray readExcel(File file) throws Exception{
//        int res = checkFile(file);
// 
//        if (res == 0) {
//            System.out.println("File not found");
//        }else if (res == 1) {
//            return readXLSX(file);
//        }else if (res == 2) {
//            return readXLS(file);
//        }
//        JsonArray array = new JsonArray();
//        return array;
//    }
// 
//    /**
//     * �ж�File�ļ�������
//     * @param file ������ļ�
//     * @return 0-�ļ�Ϊ�գ�1-XLSX�ļ���2-XLS�ļ���3-�����ļ�
//     */
//    public int checkFile(File file){
//        if (file==null) {
//            return 0;
//        }
//        String flieName = file.getName();
//        if (flieName.endsWith(XLSX)) {
//            return 1;
//        }
//        if (flieName.endsWith(XLS)) {
//            return 2;
//        }
//        return 3;
//    }
// 
//    /**
//     * ��ȡXLSX�ļ�
//     * @param file
//     * @return
//     * @throws IOException
//     * @throws InvalidFormatException
//     */
//    public JsonArray readXLSX(File file) throws InvalidFormatException, IOException{
//        Workbook book = new XSSFWorkbook(file);
//        Sheet sheet = book.getSheetAt(0);
//        return read(sheet, book);
//    }
// 
//    /**
//     * ��ȡXLS�ļ�
//     * @param file
//     * @return
//     * @throws IOException
//     * @throws FileNotFoundException
//     */
//    public JsonArray readXLS(File file) throws FileNotFoundException, IOException{
//        POIFSFileSystem poifsFileSystem = new POIFSFileSystem(new FileInputStream(file));
//        Workbook book = new HSSFWorkbook(poifsFileSystem);
//        Sheet sheet = book.getSheetAt(0);
//        return read(sheet, book);
//    }
// 
//    /**
//     * ��������
//     * @param sheet ���sheet����
//     * @param book �������ر�
//     * @return
//     * @throws IOException
//     */
//    public JsonArray read(Sheet sheet,Workbook book) throws IOException{
//        int rowStart = sheet.getFirstRowNum();	// �����±�
//        int rowEnd = sheet.getLastRowNum();	// β���±�
//        // ���������β����ͬ������ֻ��һ�У�ֱ�ӷ��ؿ�����
//        if (rowStart == rowEnd) {
//            book.close();
//            return new JsonArray();
//        }
//        // ��ȡ��һ��JSON�����
//        Row firstRow = sheet.getRow(rowStart);
//        int cellStart = firstRow.getFirstCellNum();
//        int cellEnd = firstRow.getLastCellNum();
//        Map<Integer, String> keyMap = new HashMap<Integer, String>();
//        for (int j = cellStart; j < cellEnd; j++) {
//            keyMap.put(j,getValue(firstRow.getCell(j), rowStart, j, book, true));
//        }
//        // ��ȡÿ��JSON�����ֵ
//        JsonArray array = new JsonArray();
//        for(int i = rowStart+1; i <= rowEnd ; i++) {
//            Row eachRow = sheet.getRow(i);
//            JsonObject obj = new JsonObject();
//            StringBuffer sb = new StringBuffer();
//            for (int k = cellStart; k < cellEnd; k++) {
//                if (eachRow != null) {
//                    String val = getValue(eachRow.getCell(k), i, k, book, false);
//                    sb.append(val);		// ����������ӵ����棬�����жϸ����Ƿ�Ϊ��
//                    obj.addProperty(keyMap.get(k),val);
//                  
//                }
//            }
//            if (sb.toString().length() > 0) {
//        
//                array.add(obj);
//            }
//        }
//        book.close();
//        return array;
//    }
// 
//    /**
//     * ��ȡÿ����Ԫ�������
//     * @param cell ��Ԫ�����
//     * @param rowNum �ڼ���
//     * @param index ���еڼ���
//     * @param book ��Ҫ���ڹر���
//     * @param isKey �Ƿ�Ϊ����true-�ǣ�false-���ǡ� �������Json����ֵΪ��ʱ�����������Json����ֵΪ�ղ�����
//     * @return
//     * @throws IOException
//     */
//    public String getValue(Cell cell,int rowNum,int index,Workbook book,boolean isKey) throws IOException{
// 
//        // �հ׻��
//        if (cell == null || cell.getCellType()==Cell.CELL_TYPE_BLANK ) {
//            if (isKey) {
//                book.close();
//                throw new NullPointerException(String.format("the key on row %s index %s is null ", ++rowNum,++index));
//            }else{
//                return "";
//            }
//        }
// 
//        // 0. ���� ����
//        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
//            if (HSSFDateUtil.isCellDateFormatted(cell)) {
//                Date date = cell.getDateCellValue();
//                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                return df.format(date);
//            }
//            String val = cell.getNumericCellValue()+"";
//            val = val.toUpperCase();
//            if (val.contains("E")) {
//                val = val.split("E")[0].replace(".", "");
//            }
//            return val;
//        }
// 
//        // 1. String����
//        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
//            String val = cell.getStringCellValue();
//            if (val == null || val.trim().length()==0) {
//                if (book != null) {
//                    book.close();
//                }
//                return "";
//            }
//            return val.trim();
//        }
// 
//        // 2. ��ʽ CELL_TYPE_FORMULA
//        if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
//            return cell.getStringCellValue();
//        }
// 
//        // 4. ����ֵ CELL_TYPE_BOOLEAN
//        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
//            return cell.getBooleanCellValue()+"";
//        }
// 
//        // 5.	���� CELL_TYPE_ERROR
//        return "";
//    }
// 
//
//}
