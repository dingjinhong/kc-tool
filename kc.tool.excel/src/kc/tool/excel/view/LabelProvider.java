package kc.tool.excel.view;

import java.util.Map;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import kc.tool.excel.constants.ExcelCellType;
import kc.tool.excel.utils.ExcelCell;
import kc.tool.excel.utils.ExcelRow;

//public class LabelProvider extends org.eclipse.jface.viewers.LabelProvider implements ITableLabelProvider {
//
//	@Override
//	public Image getColumnImage(Object element, int columnIndex) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getColumnText(Object element, int columnIndex) {
//		// TODO Auto-generated method stub
//		return ((ExcelRow) element).getCells().get(columnIndex+"");
//	}
//
//}
public class LabelProvider extends StyledCellLabelProvider{

	@Override
	public void update(ViewerCell cell) {
		// TODO Auto-generated method stub
		ExcelRow er=(ExcelRow) cell.getElement();
		ExcelCell ec = er.getCells().get(cell.getColumnIndex()+"");
		if(ec!=null) {
			if(ec.getType()==ExcelCellType.LINK) {
				StyleRange range = new StyleRange(0,ec.getValue().length(), Display.getDefault().getSystemColor(SWT.COLOR_LINK_FOREGROUND), null);
			 	range.underline=true;
			 	range.underlineColor=Display.getDefault().getSystemColor(SWT.COLOR_LINK_FOREGROUND);
			 	range.underlineStyle=SWT.UNDERLINE_SQUIGGLE;
			 	cell.setStyleRanges(new StyleRange[] {range});
			}
			cell.setText(ec.getValue());
			
		}
		super.update(cell);
	}
	
}
