package kc.tool.lang.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.handlers.HandlerUtil;

import kc.tool.lang.dialog.SelectFileDialog;
import kc.tool.lang.view.ILangView;

public class SelectFileHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// TODO Auto-generated method stub
		IWorkbenchPage page = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage();
		IViewPart viewPart = page.findView("kc.tool.lang.view");
		if(viewPart instanceof ILangView) {
			ILangView lv = (ILangView) viewPart;
			SelectFileDialog dialog=new SelectFileDialog(Display.getDefault().getActiveShell());
			dialog.setLangFilePath(lv.getLangFilePath());
			dialog.setCurrentOS(lv.getOS());
			dialog.setTranslationFilePath(lv.getTranslationFilePath());
			int ret=dialog.open();
			if(ret==Dialog.OK) {
				lv.setOS(dialog.getCurrentOS());
				lv.setLangFilePath(dialog.getLangFilePath());
				lv.setTranslationFilePath(dialog.getTranslationFilePath());
				
			}
		}
		return null;
	}

}
