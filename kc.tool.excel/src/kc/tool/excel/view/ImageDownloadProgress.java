package kc.tool.excel.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;

import kc.tool.excel.constants.ExcelCellType;
import kc.tool.excel.utils.ExcelCell;
import kc.tool.excel.utils.ExcelRow;

public class ImageDownloadProgress implements IRunnableWithProgress{
	List<ExcelRow> rows=new ArrayList<ExcelRow>();
	String dirPath;
	public String getDirPath() {
		return dirPath;
	}

	public void setDirPath(String dirPath) {
		this.dirPath = dirPath;
	}

	public List<ExcelRow> getRows() {
		return rows;
	}

	public void setRows(List<ExcelRow> rows) {
		this.rows = rows;
	}
	
	public ImageDownloadProgress() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ImageDownloadProgress(List<ExcelRow> rows, String dirPath) {
		super();
		this.rows = rows;
		this.dirPath = dirPath;
	}

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		// TODO Auto-generated method stub
		monitor.beginTask("begin",rows.size());
		//System.setProperty("webdriver.firefox.driver",getPluginPath()+"/python/os/win/geckodriver.exe");
		for(int i=0;i<rows.size()&&!monitor.isCanceled();i++){
			monitor.setTaskName("第"+(i+1)+"行");
			ExcelRow row = rows.get(i);
			int index=0;
			for(String j:row.getCells().keySet()) {
				ExcelCell cell = row.getCells().get(j);
				
				
				if(cell!=null) {
					String iconName=row.getCells().get("0").getValue();
					if(cell.getType()==ExcelCellType.LINK) {
						String url=cell.getValue();
						//System.out.println(url);
						
						downloadImage(url, iconName, monitor,index);
						index++;
					}else{
						String s=cell.getValue();
						if(s.startsWith("http")) {
							downloadImage(s, iconName, monitor,index);
							index++;
						}
					}
				}
			}
			
		}
		monitor.done();
	}
	private void downloadImage(String url,String iconName,IProgressMonitor monitor,int index) {
		if(index!=0) {
			iconName+=("_"+index);
		}
		monitor.subTask(url);
		String exe = getPluginPath()+"/python/python37/python";
	    String command = getPluginPath()+"/python/image.py";
	    String[] cmdArr = new String[] {exe,command,url,dirPath,iconName};
	   
		try {
			 Process process = Runtime.getRuntime().exec(cmdArr);
			 InputStream is = process.getInputStream();
		        InputStreamReader reader=new InputStreamReader(is, "gbk");
		        BufferedReader ris=new BufferedReader(reader);
		        String str=ris.readLine();
		        System.out.println(str);
			     process.waitFor();
	
				 monitor.worked(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			monitor.worked(1);	
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			monitor.worked(1);	
			e.printStackTrace();
		}
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
