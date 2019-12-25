package kc.tool.lang.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import kc.tool.lang.constants.OSType;

public class SelectFileDialog extends Dialog {
	private OSType currentOS=OSType.ANDROID;
	private String[] filterExtensions;
	
	private String langFilePath="";
	
	private String translationFilePath="";
	public  SelectFileDialog(Shell parentShell) {
		super(parentShell);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void configureShell(Shell newShell) {
		// TODO Auto-generated method stub
		newShell.setText("Select File"); 
		super.configureShell(newShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		// TODO Auto-generated method stub
		Composite composite= (Composite) super.createDialogArea(parent);
		Group group=new Group(composite,SWT.NONE);
		group.setText("Select OS");
		group.setLayout(new GridLayout());
		
	
		OSType[] oses=new OSType[] {OSType.ANDROID,OSType.IOS,OSType.WEB};
		for(OSType os:oses) {
			Button btn=new Button(group,SWT.RADIO);
			btn.setText(os.getName());
			
			btn.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent e) {
					// TODO Auto-generated method stub
					Button btn=(Button) e.widget;
					if(btn.getSelection()) {
						currentOS=os;
						if(currentOS==OSType.ANDROID) {
							filterExtensions=new String[] {"*.xml"};
						}else if(currentOS==OSType.IOS) {
							filterExtensions=new String[] {"*.strings"};
						}else {
							filterExtensions=new String[] {"*.json"};
						}
					}
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
			if(currentOS==os) {
				btn.setSelection(true);
				if(currentOS==OSType.ANDROID) {
					filterExtensions=new String[] {"*.xml"};
				}else if(currentOS==OSType.IOS) {
					filterExtensions=new String[] {"*.strings"};
				}else {
					filterExtensions=new String[] {"*.json"};
				}
				
			}
		}
		Composite selectFileCom=new Composite(composite,SWT.NONE);
		selectFileCom.setLayout(new GridLayout(3,false));
		Label langLabel=new Label(selectFileCom,SWT.NONE);
		langLabel.setText("语言文件：");
		Text langText =new Text(selectFileCom,SWT.BORDER);
		GridData gd=new GridData();
		gd.widthHint=250;
		langText.setLayoutData(gd);
		Button langBrowser=new Button(selectFileCom,SWT.PUSH);
		langBrowser.setText("浏览");
		langText.setText(langFilePath);
		
		langBrowser.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				FileDialog fd=new FileDialog(Display.getCurrent().getActiveShell(),SWT.OPEN);
				fd.setFilterExtensions(filterExtensions);
				String path=fd.open();
				if(path!=null) {
					langFilePath=path;
					langText.setText(langFilePath);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		Label translationLabel=new Label(selectFileCom,SWT.NONE);
		translationLabel.setText("翻译文件：");
		Text translationText =new Text(selectFileCom,SWT.BORDER);
		translationText.setText(translationFilePath);
		gd=new GridData();
		gd.widthHint=250;
		translationText.setLayoutData(gd);
		Button translationBrowser=new Button(selectFileCom,SWT.PUSH);
		translationBrowser.setText("浏览");
		translationBrowser.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				FileDialog fd=new FileDialog(Display.getCurrent().getActiveShell(),SWT.OPEN);
				fd.setFilterExtensions(new String[] {"*.xls;*.xlsx"});
				String path=fd.open();
				if(path!=null) {
					translationFilePath=path;
					translationText.setText(translationFilePath);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		return composite;
	}

	

	public OSType getCurrentOS() {
		return currentOS;
	}

	public void setCurrentOS(OSType currentOS) {
		this.currentOS = currentOS;
	}

	public String getLangFilePath() {
		return langFilePath;
	}

	public void setLangFilePath(String langFilePath) {
		this.langFilePath = langFilePath;
	}

	public String getTranslationFilePath() {
		return translationFilePath;
	}

	public void setTranslationFilePath(String translationFilePath) {
		this.translationFilePath = translationFilePath;
	}
	
	
}
