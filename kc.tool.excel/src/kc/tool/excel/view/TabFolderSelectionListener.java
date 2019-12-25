package kc.tool.excel.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Listener;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import kc.tool.excel.utils.ExcelSheet;

public class TabFolderSelectionListener implements SelectionListener ,CTabFolder2Listener{
	List<ExcelSheet> allSheets=new ArrayList<ExcelSheet>();
	List<ExcelViewer> viewers=new ArrayList<ExcelViewer>();
	
	public List<ExcelSheet> getAllSheets() {
		return allSheets;
	}

	public void setAllSheets(List<ExcelSheet> allSheets) {
		this.allSheets = allSheets;
	}
	
	
	
	public List<ExcelViewer> getViewers() {
		return viewers;
	}

	public void setViewers(List<ExcelViewer> viewers) {
		this.viewers = viewers;
	}

	public TabFolderSelectionListener() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TabFolderSelectionListener(List<ExcelSheet> allSheets, List<ExcelViewer> viewers) {
		super();
		this.allSheets = allSheets;
		this.viewers = viewers;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		CTabItem item = (CTabItem) e.item;
		CTabFolder folder=(CTabFolder) e.widget;
		int index=folder.indexOf(item);
		for(int i=0;i<viewers.size();i++) {
			if(i==index) {
				viewers.get(i).setInput(allSheets.get(i).getRows());
			}else {
				viewers.get(i).setInput(null);
			}
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(CTabFolderEvent e) {
		// TODO Auto-generated method stub
		CTabItem item = (CTabItem) e.item;
		CTabFolder folder=(CTabFolder) e.widget;
		int index=folder.indexOf(item);
		viewers.remove(index);
		allSheets.remove(index);
	}

	@Override
	public void minimize(CTabFolderEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void maximize(CTabFolderEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restore(CTabFolderEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showList(CTabFolderEvent event) {
		// TODO Auto-generated method stub
		
	}

}
