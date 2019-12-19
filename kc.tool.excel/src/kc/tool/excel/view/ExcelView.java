package kc.tool.excel.view;

import com.google.gson.Gson;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Listener;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabFolderListener;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.part.ViewPart;

import kc.tool.excel.utils.ExcelManager;
import kc.tool.excel.utils.ExcelParser;
import kc.tool.excel.utils.ExcelRow;
import kc.tool.excel.utils.ExcelSheet;



public class ExcelView extends ViewPart implements IExcelView{
	public static final String ID = "kc.tool.excel.view";

	@Inject IWorkbench workbench;
	
	private CTabFolder tabFolder;
	
	private Composite composite;
	
	private Button search_all;
	
	private Button download_image;
	
	private List<ExcelSheet> list;
	
	private ExcelManager excelManager;
	
	private TabFolderSelectionListener selectionListener;
	
	private boolean shouldSave=false;
	
	
	private ProgressMonitorDialog progressDialog=new ProgressMonitorDialog(Display.getCurrent().getActiveShell()) {

		@Override
		public boolean close() {
			// TODO Auto-generated method stub
			boolean ret=super.close();
			if(ret&&shouldSave) {
				excelManager.save();
			}
			return ret;
			
		}
		
	};
	
	
	
	


	@Override
	public void createPartControl(Composite parent) {
		composite=new Composite(parent,SWT.NONE);
		composite.setLayout(new GridLayout());
		Composite actionBar=new Composite(composite,SWT.NONE);
		actionBar.setLayout(new GridLayout(2,false));
		search_all=new Button(actionBar,SWT.PUSH);
		search_all.setText("全部浏览");
		search_all.setEnabled(false);
		
		download_image=new Button(actionBar,SWT.PUSH);
		download_image.setText("下载图片");
		download_image.setEnabled(false);
		
		tabFolder=new CTabFolder(composite, SWT.RESIZE | SWT.BORDER|SWT.CLOSE);
		
		tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		
		search_all.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				shouldSave=true;
				ExcelSheet es = list.get(tabFolder.getSelectionIndex());
				ExcelViewer ev = (ExcelViewer)tabFolder.getItem(tabFolder.getSelectionIndex()).getData();
				try {
					progressDialog.run(true, true, new BrowseProgress(es.getRows(),excelManager,ev));
				} catch (InvocationTargetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		download_image.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				DirectoryDialog dialog=new DirectoryDialog(Display.getCurrent().getActiveShell());
				String dir=dialog.open();
				if(dir!=null) {
					shouldSave=false;
					ExcelSheet es = list.get(tabFolder.getSelectionIndex());
					try {
						progressDialog.run(true, true, new ImageDownloadProgress(es.getRows(),dir));
					} catch (InvocationTargetException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		

	}


	@Override
	public void setFocus() {
		//composite.getChildren()[0].setFocus();
	}
	
	private void showExcel(String excelFile) {
		excelManager=new ExcelManager(excelFile);
		list = excelManager.parseExcel();
		
		if(list!=null&&list.size()!=0) {
			if(selectionListener!=null) {
				tabFolder.removeSelectionListener(selectionListener);
				tabFolder.removeCTabFolder2Listener(selectionListener);
			}
			for(CTabItem item:tabFolder.getItems()) {
				item.dispose();
			}
			
			
			
			List<ExcelViewer> viewers=new ArrayList<ExcelViewer>();
			int index=0;
			
			for(ExcelSheet sheet:list) {
				if(index==0) {
					List<String> strs=new ArrayList<String>();
					//String[] strs=new String[sheet.getRows().size()];
					Map<String,Integer> sameMap=new HashMap<String,Integer>();
					System.out.println(sheet.getRows().size()-1);
					for(ExcelRow row:sheet.getRows()) {
						String val=row.getCells().get("0").getValue();
						if(!strs.contains(val)) {
							strs.add(val);
						}else {
							if(sameMap.containsKey(val)) {
								sameMap.put(val, sameMap.get(val)+1);
							}else {
								sameMap.put(val, 1);
							}
						}
						
					}
					System.out.println(strs.size());
					Gson gson=new Gson();
					Iterator<Entry<String, Integer>> it = sameMap.entrySet().iterator();
					while(it.hasNext()) {
						Entry<String, Integer> entry = it.next();
						System.out.println("ID:"+entry.getKey()+"多出现了"+entry.getValue()+"次");
					}
					System.out.println(gson.toJson(strs));
				}
				CTabItem item=new CTabItem(tabFolder, SWT.NONE);
				item.setText(sheet.getName());
				ExcelViewer ev=new ExcelViewer(sheet);
				ev.createContent(tabFolder);
				item.setData(ev);
//				Composite tableComposite = new Composite(tabFolder, SWT.NONE);
//				GridDataFactory.fillDefaults().grab(true, true).applyTo(tableComposite);
//				TableColumnLayout tableColumnLayout = new TableColumnLayout();
//				tableComposite.setLayout(tableColumnLayout);
//				TableViewer viewer = new TableViewer(tableComposite,SWT.SINGLE | SWT.FULL_SELECTION|SWT.BORDER | SWT.V_SCROLL|SWT.H_SCROLL);
//			    Table table=viewer.getTable();
//			    table.setLinesVisible(true);
//			    table.setHeaderVisible(true);
//			    TableLayout layout=new TableLayout();
//				table.setLayout(layout);
//				viewer.setContentProvider(new DataProvider());
//				viewer.setLabelProvider(new LabelProvider());
//				//tv.setInput(blockdeflist);
//				for(int i=0;i<sheet.getColumnNum()+1;i++) {
//					TableColumn problemColumn=new TableColumn(table, SWT.NONE);
//					//problemColumn.setText(text);
//					tableColumnLayout.setColumnData(problemColumn, new ColumnPixelData(sheet.getColumnWidthList().get(i)));
//				}
				item.setControl(ev.getControl());
				viewers.add(ev);
				index++;
			}
			tabFolder.setSelection(0);
			viewers.get(0).setInput(list.get(0).getRows());
			selectionListener=new TabFolderSelectionListener(list, viewers);
			tabFolder.addSelectionListener(selectionListener);
			tabFolder.addCTabFolder2Listener(selectionListener);


		}
		if(tabFolder.getItemCount()==0) {
			search_all.setEnabled(false);
			download_image.setEnabled(false);
		}else {
			search_all.setEnabled(true);
			download_image.setEnabled(true);
		}
		
	}

	@Override
	public void setExcelFile(String file) {
		// TODO Auto-generated method stub
		this.showExcel(file);
		
	}
}