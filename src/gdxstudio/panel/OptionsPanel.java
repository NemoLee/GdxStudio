package gdxstudio.panel;
import gdxstudio.Content;
import gdxstudio.Editor;
import gdxstudio.Frame;
import gdxstudio.Icon;
import gdxstudio.Style;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import web.laf.lite.layout.HorizontalFlowLayout;
import web.laf.lite.layout.ToolbarLayout;
import web.laf.lite.layout.VerticalFlowLayout;
import web.laf.lite.popup.AlignPanel;
import web.laf.lite.utils.SpringUtils;
import web.laf.lite.utils.UIUtils;
import web.laf.lite.widget.Register;
import web.laf.lite.widget.WebButtonGroup;
import web.laf.lite.widget.WebSwitch;

public class OptionsPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	JPanel row1 = new JPanel(new HorizontalFlowLayout());
	
	JPanel content1 = new JPanel(new VerticalFlowLayout());
	JPanel content2 = new JPanel(new VerticalFlowLayout());
	
	JPanel conten34Border = new JPanel(new VerticalFlowLayout());
	JPanel content34 = new JPanel(new HorizontalFlowLayout());
	JPanel content3 = new JPanel(new VerticalFlowLayout());
	JPanel content4 = new JPanel(new VerticalFlowLayout());
	
	JPanel conten5Border = new JPanel(new VerticalFlowLayout());
	JPanel content5 = new JPanel(new SpringLayout());

	public OptionsPanel(){
		super(new VerticalFlowLayout(0, 10));
		UIUtils.setUndecorated(this, true);
		createHeader(content1, "ToolBar");
		createHeader(content2, "SearchBar");
		createHeader(conten34Border, "Editor");
		createHeader(conten5Border, "Texture Packer");
        WebSwitch left = new WebSwitch(); 
        WebSwitch right = new WebSwitch();
        WebSwitch status = new WebSwitch();
        menuItem(content1, left, "Show Left SideBar");
        menuItem(content1, right, "Show Right SideBar");
        menuItem(content1, status, "Show StatusBar");
        row1.add(content1);
        /*
         *  Rewrite this with first launch
         */
        //if(!left.load()) Frame.toggleLeftSideBar();
        //if(!right.load()) Frame.toggleRightSideBar();
        //if(!status.load()) Frame.toggleStatusBar();
        left.addActionListener(new ActionListener(){
     		@Override
     		public void actionPerformed(ActionEvent arg0) {
     			Frame.toggleLeftSideBar();
     		}
        });
        right.addActionListener(new ActionListener(){
     		@Override
     		public void actionPerformed(ActionEvent arg0) {
     			Frame.toggleRightSideBar();
     		}
        });
        status.addActionListener(new ActionListener(){
     		@Override
     		public void actionPerformed(ActionEvent arg0) {
     			Frame.toggleStatusBar();
     		}
        });
        
        // Search Stuff
        final WebSwitch cs = new WebSwitch(); 
        final WebSwitch ww = new WebSwitch();
        final WebSwitch re = new WebSwitch();
        menuItem(content2, cs, "Case Sensitive");
        menuItem(content2,ww, "Whole Word");
        menuItem(content2,re, "Regular Expression");
        row1.add(content2);
        
        cs.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Editor.context.setMatchCase(cs.isSelected());
			}
        });
        
        ww.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Editor.context.setWholeWord(ww.isSelected());
			}
        });
        
        re.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Editor.context.setRegularExpression(re.isSelected());
			}
        });
  
        // Editor Stuff
        final WebSwitch showLineNumbers = new WebSwitch();
        final WebSwitch showMargin = new WebSwitch();
        final WebSwitch codeFold = new WebSwitch();
        final WebSwitch showEol = new WebSwitch();
        final WebSwitch paintTabLines = new WebSwitch();
        final WebSwitch showWhitespaces = new WebSwitch();
        //final JSlider tabSlider = new JSlider(0, 0, 0l
        menuItem(content3,showLineNumbers, "Show Line Numbers");
        menuItem(content3,showMargin, "Show Margin");
        menuItem(content3,showEol, "Show Eol");
        content34.add(content3);
      
        
        menuItem(content4,paintTabLines, "Show Tab");
        menuItem(content4,codeFold, "Code Folding");
        menuItem(content4,showWhitespaces, "Show WhiteSpace");
        content34.add(content4);
        add(row1);
        conten34Border.add(content34);
        add(conten34Border);
        
    	createRow(content5, "Encoding Format", new JComboBox<String>(new String[]{"RGBA8888", "RGBA4444", "RGB888", "RGB565", 
    			"Alpha", "LuminanceAlpha", "Intensity"}));
    	createRow(content5, "Min Filter",  new JComboBox<String>(new String[]{"Nearest", "Linear", "MipMap", "MipMapNearestNearest",
    			"MipMapNearestLinear", "MipMapLinearNearest", "MipMapLinearLinear"}));
    	createRow(content5, "Output Format", new JComboBox<String>(new String[]{"Nearest", "Linear", "MipMap", "MipMapNearestNearest", "MipMapNearestLinear", "MipMapLinearNearest", "MipMapLinearLinear"}));
    	createRow(content5, "Mag Filter", new JComboBox<String>(new String[]{"png", "jpg"}));
    	
    	JComboBox<String> wcb1 = new JComboBox<String>();
    	for(int i= 0; i <= 10; i++)
    			wcb1.addItem(""+(2<<i));
    	createRow(content5, "Min Page Width", wcb1);
    	createRow(content5, "PaddingX", new JComboBox<String>());
    	JComboBox<String> wcb2 = new JComboBox<String>();
    	for(int i= 0; i <= 10; i++)
    			wcb2.addItem(""+(2<<i));
    	createRow(content5, "Min Page Height", wcb2);
    	createRow(content5, "PaddingY", new JComboBox<String>());
    	
    	JComboBox<String> wcb3 = new JComboBox<String>();
    	for(int i= 0; i <= 10; i++)
    			wcb3.addItem(""+(2<<i));
    	createRow(content5, "Max Page Width", wcb3);
    	createRow(content5, "ClampX", new JComboBox<String>());
    	JComboBox<String> wcb4 = new JComboBox<String>();
    	for(int i= 0; i <= 10; i++)
    			wcb4.addItem(""+(2<<i));
    	createRow(content5, "Max Page Height", wcb4);
    	createRow(content5, "ClampY", new JComboBox<String>());
    	
    	SpringUtils.makeCompactGrid(content5, 6, 4, 10, 0, 10, 5);
    	conten5Border.add(content5);
    	add(conten5Border);
        initStyle();
       // add(menuItem(tabSlider, "Tab Size"));
      //setMarginLineColor(Color.black);
        //setMarkOccurrences(true);
        //setPaintMarkOccurrencesBorder(true);
        //setPaintMatchedBracketPair(true);
        
    	Content.editorScroll.setLineNumbersEnabled(showLineNumbers.isSelected());
    	Content.editorScroll.setIconRowHeaderEnabled(showMargin.isSelected());
        Content.editor.setCodeFoldingEnabled(codeFold.isSelected());
        Content.editor.setPaintTabLines(paintTabLines.isSelected());
    	Content.editor.setWhitespaceVisible(showWhitespaces.isSelected());
    	Content.editor.setEOLMarkersVisible(showEol.isSelected());
    	
    	
    	showLineNumbers.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Content.editorScroll.setLineNumbersEnabled(showLineNumbers.isSelected());
			}
        });
    	
    	showMargin.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Content.editorScroll.setIconRowHeaderEnabled(showMargin.isSelected());
			}
        });
        
        codeFold.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Content.editor.setCodeFoldingEnabled(codeFold.isSelected());
			}
        });
        showEol.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Content.editor.setEOLMarkersVisible(showEol.isSelected());
			}
        });
        paintTabLines.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Content.editor.setPaintTabLines(paintTabLines.isSelected());
			}
        });
        showWhitespaces.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Content.editor.setWhitespaceVisible(showWhitespaces.isSelected());
			}
        }); 
	}
	
	void createRow(JPanel content, String title, Component b){
    	JLabel label = new JLabel(title);
		label.setFont(label.getFont().deriveFont(Font.BOLD));
		content.add(label);
		if(b instanceof WebSwitch)
			content.add(new AlignPanel(b, AlignPanel.LEFT, AlignPanel.VERTICAL));
		else
			content.add(b);
    }
	
	void menuItem(JPanel content, final WebSwitch sw, String text){
		final JLabel label = new JLabel("   "+text);
		label.setFont(label.getFont().deriveFont(Font.BOLD));
		JPanel pan = new JPanel(new ToolbarLayout());
		pan.setOpaque(false);
		pan.add(label, ToolbarLayout.START);
		pan.add(sw, ToolbarLayout.END);
		content.add(pan);
	}
	
	void createHeader(JPanel content, String title){
		content.add(new Style.TitleLabel(title));
		content.setBorder(BorderFactory.createLineBorder(Style.border));
	}
	
	void createHeader(JPanel content, String title, String icon){
		content.add(new Style.TitleLabel(title, icon));
		content.setBorder(BorderFactory.createLineBorder(Style.border));
	}
	
	void initStyle(){
		JPanel vert = new JPanel(new VerticalFlowLayout());
		createHeader(vert, "Styles");
        ThemeButton[] themeButtons = {
        		createThemeItem("IntelliJ IDEA", "idea"),
        		createThemeItem("Dark", "dark"),
        		createThemeItem("Visual Studio", "vs"),
        		createThemeItem("Eclipse", "eclipse")
        };
        WebButtonGroup themeGroup = new WebButtonGroup(WebButtonGroup.HORIZONTAL, true, themeButtons);
        themeGroup.setButtonsDrawFocus(false);
        themeGroup.setButtonsDrawFocus(false);
        vert.add(themeGroup);
        add(vert);
	}
	
	ThemeButton createThemeItem(String title, String iconname){
		final ThemeButton themeButton = new ThemeButton(title, iconname);
		if(Register.getTheme().equals(iconname)) 
			themeButton.setSelected(true);
		themeButton .addActionListener (new ActionListener (){
            public void actionPerformed (ActionEvent e){
            	Register.setTheme(themeButton.iconame);
            	Content.theme = Icon.loadTheme(themeButton.iconame);
            	Content.theme.apply(Content.editor);
           }
        });
		return themeButton;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		setVisible(false);
	}
}


final class ThemeButton extends JToggleButton{
	private static final long serialVersionUID = 1L;
	String iconame;
	ThemeButton(String text, String ic){
		super(text, Icon.icon(ic));
		iconame = ic;
		setVerticalTextPosition(SwingConstants.BOTTOM);
        setHorizontalTextPosition(SwingConstants.CENTER);
        setFocusable(false);
	}
}