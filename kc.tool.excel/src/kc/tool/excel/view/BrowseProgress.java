package kc.tool.excel.view;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.xmlbeans.impl.common.ReaderInputStream;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;

import kc.tool.excel.constants.ExcelCellType;
import kc.tool.excel.utils.ExcelCell;
import kc.tool.excel.utils.ExcelManager;
import kc.tool.excel.utils.ExcelRow;

public class BrowseProgress implements IRunnableWithProgress{
	List<ExcelRow> rows=new ArrayList<ExcelRow>();
	ExcelManager excelManager;
	ExcelViewer viewer;
	
	public ExcelManager getExcelManager() {
		return excelManager;
	}

	public void setExcelManager(ExcelManager excelManager) {
		this.excelManager = excelManager;
	}

	public List<ExcelRow> getRows() {
		return rows;
	}

	public void setRows(List<ExcelRow> rows) {
		this.rows = rows;
	}

	public BrowseProgress() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BrowseProgress(List<ExcelRow> rows,ExcelManager excelManager,ExcelViewer ev) {
		super();
		this.rows = rows;
		this.excelManager=excelManager;
		this.viewer=ev;
	}

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		// TODO Auto-generated method stub
		monitor.beginTask("begin",rows.size());
		//System.setProperty("webdriver.firefox.driver",getPluginPath()+"/python/os/win/geckodriver.exe");
		for(int i=0;i<rows.size()&&!monitor.isCanceled();i++){
			monitor.setTaskName("第"+(i+1)+"行");
			ExcelRow row = rows.get(i);
			for(String j:row.getCells().keySet()) {
				ExcelCell cell = row.getCells().get(j);
				if(cell!=null) {
					if(cell.getType()==ExcelCellType.LINK) {
						String url=cell.getValue();
						monitor.subTask(url);
						String exe = getPluginPath()+"/python/python37/python";
					    String command = getPluginPath()+"/python/reptile.py";
					    String[] cmdArr = new String[] {exe,command,url,getPluginPath()};
					   
						try {
							 Process process = Runtime.getRuntime().exec(cmdArr);
						        InputStream is = process.getInputStream();
						        InputStreamReader reader=new InputStreamReader(is, "gbk");
						        BufferedReader ris=new BufferedReader(reader);
						        String str=ris.readLine();
						        process.waitFor();
						        System.out.println(str);
						        if(!StringUtils.isBlank(str)) {
						        	str=str.replaceAll("[^0-9]", "");
						        	//System.out.println(str);
						        	int index=Integer.parseInt(j)+1;
						        	excelManager.changeViewTimes(row, cell, index, str);
						        	Display.getDefault().syncExec(new Runnable() {
										
										@Override
										public void run() {
											// TODO Auto-generated method stub
											viewer.update(row, new String[] {index+""});
										}
									});
						        	
						        }
//							 process.waitFor();
//							             BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));     
//							             String line = null;
//							             while ((line = in.readLine()) != null) {
//							                 System.out.println(line);
//							             }
//							             in.close();
							
							
//							 InputStreamReader ir = new InputStreamReader(process.getInputStream());
//					         LineNumberReader input = new LineNumberReader(ir);
//					         input.
//					         String str = input.readLine();
//					         System.out.println(str);
//					         input.close();
//					         ir.close();
							 
//							 byte[] bytes = new byte[process.getInputStream().available()];
//							 dis.read(bytes);
//							 System.out.println(new String(bytes));
							 monitor.worked(1);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							monitor.worked(1);	
						}
					    
					}
				}
			}
			
		}
		monitor.done();
	}
	private String getPluginPath(){
		 String path;
	       try {
	    	   
	             path= Platform.asLocalURL(Platform.getBundle("kc.tool.excel").getEntry("")).getPath();
	          //   path=path.substring(path.indexOf("/")+1,path.length());
	             path=new File(path).getAbsolutePath();
	       } catch (IOException e) {
	             path="";
	        
	       }
	       return path;
	}


}
