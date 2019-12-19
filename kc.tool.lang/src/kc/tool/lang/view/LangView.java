package kc.tool.lang.view;

import java.util.List;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.part.ViewPart;

import kc.tool.lang.builder.LangBuilder;
import kc.tool.lang.builder.LangBuilderFactory;
import kc.tool.lang.constants.OSType;
import kc.tool.lang.model.LangData;
import kc.tool.lang.model.TranslationData;
import kc.tool.lang.parser.LangParser;
import kc.tool.lang.parser.LangParserFactory;
import kc.tool.lang.translation.TranslationParser;


public class LangView extends ViewPart implements ILangView{
	public static final String ID = "kc.tool.lang.view";
	private CTabFolder translationCTabFolder;
	private LangData langData;
	private LangViewer langViewer;
	private List<TranslationData> translationDataList;
	
	private String langFile="";
	private String translationFile="";
	private OSType os=OSType.ANDROID;
	public LangView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		Composite composite=new Composite(parent,SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);
		composite.setLayout(new GridLayout(1,false));
		Button convertBtn=new Button(composite,SWT.PUSH);
		convertBtn.setText("转换");
		SashForm sf=new SashForm(composite,SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(sf);
		langViewer =new LangViewer();
		langViewer.createContent(sf);
		translationCTabFolder =new CTabFolder(sf, SWT.RESIZE | SWT.BORDER);
		convertBtn.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
			
				LangData ld = ConvertUtils.convertLang(langData, translationDataList.get(translationCTabFolder.getSelectionIndex()));
				LangBuilder lb = LangBuilderFactory.instance.getLangBuilder(os);
				FileDialog fd=new FileDialog(Display.getDefault().getActiveShell(),SWT.SAVE);
				String[] filterExtensions=null;
				if(os==OSType.ANDROID) {
					filterExtensions=new String[] {"*.xml"};
				}else if(os==OSType.IOS) {
					filterExtensions=new String[] {"*.strings"};
				}else {
					filterExtensions=new String[] {"*.json"};
				}
				fd.setFilterExtensions(filterExtensions);
				String path=fd.open();
				if(path!=null) {
					lb.buildLang(ld,path);
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
		// TODO Auto-generated method stub

	}

	public void setLangData(LangData ld) {
		// TODO Auto-generated method stub
		this.langData=ld;
		langViewer.setInput(ld.getLangList());
	}


	public void setTranslationDataList(List<TranslationData> tds) {
		// TODO Auto-generated method stub
		this.translationDataList=tds;
		if(tds!=null&&tds.size()!=0) {
			for(CTabItem item:translationCTabFolder.getItems()) {
				item.dispose();
			}
			for(TranslationData td:tds) {
				CTabItem item=new CTabItem(translationCTabFolder, SWT.NONE);
				item.setText(td.getName());
				LangViewer lv = new LangViewer();
				lv.createContent(translationCTabFolder);
				lv.setInput(td.getItemList());
				item.setControl(lv.getControl());
			}
			translationCTabFolder.setSelection(0);
			
		}
		
	}

	@Override
	public void setLangFilePath(String p) {
		// TODO Auto-generated method stub
		this.langFile=p;
		LangParser lp = LangParserFactory.instance.getLangParser(this.os);
		LangData l = lp.parseLang(p);
		this.setLangData(l);
	}

	@Override
	public void setTranslationFilePath(String p) {
		// TODO Auto-generated method stub
		this.translationFile=p;
		List<TranslationData> list = TranslationParser.getTranslation(p);
		this.setTranslationDataList(list);
	}

	@Override
	public void setOS(OSType p) {
		// TODO Auto-generated method stub
		this.os=p;
	}

	@Override
	public String getLangFilePath() {
		// TODO Auto-generated method stub
		return this.langFile;
	}

	@Override
	public String getTranslationFilePath() {
		// TODO Auto-generated method stub
		return this.translationFile;
	}

	@Override
	public OSType getOS() {
		// TODO Auto-generated method stub
		return this.os;
	}



}
