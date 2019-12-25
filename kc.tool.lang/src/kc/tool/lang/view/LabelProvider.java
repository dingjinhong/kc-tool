package kc.tool.lang.view;

import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;

import kc.tool.lang.model.LangItem;



public class LabelProvider extends StyledCellLabelProvider{

	@Override
	public void update(ViewerCell cell) {
		// TODO Auto-generated method stub
		LangItem li=(LangItem) cell.getElement();
		
		if(li!=null) {
			if(cell.getColumnIndex()==0) {
				cell.setText(li.getName());
			}else {
				cell.setText(li.getValue());
			}
			
		}
		super.update(cell);
	}
	
}