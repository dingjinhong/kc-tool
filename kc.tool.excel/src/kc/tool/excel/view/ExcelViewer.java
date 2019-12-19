package kc.tool.excel.view;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import kc.tool.excel.utils.ExcelSheet;

public class ExcelViewer {
	TableViewer viewer;
	Composite tableComposite;
	ExcelSheet sheet;
	
	public ExcelViewer(ExcelSheet sheet) {
		super();
		this.sheet = sheet;
	}
	
	public TableViewer getViewer() {
		return viewer;
	}

	protected void createContent(Composite parent) {
		tableComposite = new Composite(parent, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(tableComposite);
		TableColumnLayout tableColumnLayout = new TableColumnLayout();
		tableComposite.setLayout(tableColumnLayout);
		viewer = new TableViewer(tableComposite,SWT.SINGLE | SWT.FULL_SELECTION|SWT.BORDER | SWT.V_SCROLL|SWT.H_SCROLL);
	    Table table=viewer.getTable();
	    table.setLinesVisible(true);
	    table.setHeaderVisible(true);
	    TableLayout layout=new TableLayout();
		table.setLayout(layout);
		viewer.setContentProvider(new DataProvider());
		viewer.setLabelProvider(new LabelProvider());
		
		//tv.setInput(blockdeflist);
		String[] props=new String[sheet.getColumnNum()+1];
		for(int i=0;i<sheet.getColumnNum()+1;i++) {
			props[i]=i+"";
			TableColumn problemColumn=new TableColumn(table, SWT.NONE);
			//problemColumn.setText(text);
			tableColumnLayout.setColumnData(problemColumn, new ColumnPixelData(sheet.getColumnWidthList().get(i)));
		}
		viewer.setColumnProperties(props);
	}
	public void setInput(Object input) {
		viewer.setInput(input);
	}
	public void refresh() {
		viewer.refresh();
	}
	public void update(Object element,String[] properties) {
		viewer.update(element, properties);
	}
	public Control getControl() {
		return tableComposite;
	}
}
