package kc.tool.excel.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.HandlerEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import kc.tool.excel.view.ExcelView;
import kc.tool.excel.view.IExcelView;

public class ImportExcelHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// TODO Auto-generated method stub
		FileDialog dialog=new FileDialog(Display.getCurrent().getActiveShell(),SWT.OPEN);
		dialog.setFilterExtensions(new String[] {"*.xls;*.xlsx"});
		String path=dialog.open();
		if(path!=null) {
			IWorkbenchPage page = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage();
			try {
				IWorkbenchPart part= page.showView(ExcelView.ID, null, IWorkbenchPage.VIEW_ACTIVATE);
				if(part instanceof IExcelView) {
					((IExcelView)part).setExcelFile(path);
				}
			} catch (PartInitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

}
