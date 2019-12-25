package kc.tool.lang.view;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class LangViewer {
	private TableViewer viewer;
	private Composite tableCom;
	public void createContent(Composite parent) {
		tableCom=new Composite(parent,SWT.BORDER);
		TableColumnLayout tableColumnLayout = new TableColumnLayout();
		tableCom.setLayout(tableColumnLayout);
		viewer=new TableViewer(tableCom,SWT.SINGLE | SWT.FULL_SELECTION| SWT.V_SCROLL|SWT.H_SCROLL);
		Table table=viewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		TableLayout layout=new TableLayout();
		table.setLayout(layout);
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setLabelProvider(new LabelProvider());
		TableColumn nameColumn=new TableColumn(table, SWT.NONE);
		nameColumn.setText("名称");
		tableColumnLayout.setColumnData(nameColumn, new ColumnWeightData(50, 150, true));
		TableColumn valueColumn=new TableColumn(table, SWT.NONE);
		valueColumn.setText("值");
		tableColumnLayout.setColumnData(valueColumn, new ColumnWeightData(50, 150, true));
	}
	public Object getInput() {
		if(viewer!=null) {
			return viewer.getInput();
		}
		return null;
	}
	public void setInput(Object input) {
		if(viewer!=null) {
			viewer.setInput(input);
		}
	}
	public Control getControl() {
		return tableCom;
	}
}
