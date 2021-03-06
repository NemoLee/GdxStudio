package gdxstudio;

import gdxstudio.panel.ActorPanel;
import gdxstudio.panel.BaseList;
import gdxstudio.panel.BaseTable;
import gdxstudio.panel.ConsolePanel;
import gdxstudio.panel.DashPanel;
import gdxstudio.panel.EffectPanel;
import gdxstudio.panel.EventPanel;
import gdxstudio.panel.ProjectPanel;
import gdxstudio.panel.ProjectSettingsPanel;
import gdxstudio.panel.PropertyPanel;
import gdxstudio.panel.SceneEffectPanel;
import gdxstudio.panel.ScenePanel;
import gdxstudio.panel.StudioPanel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import scene2d.Asset;
import scene2d.Scene;
import web.laf.lite.layout.VerticalFlowLayout;
import web.laf.lite.utils.UIUtils;

final public class Frame extends JFrame implements WindowListener{
	private static final long serialVersionUID = 1L;
	private static Frame frame;
	private static ToolBar toolBar;
	private static JPanel rightSideBar;
	private static JPanel leftSideBar;
	private static StatusBar statusBar;
	public static Content content;
	
	public static ProjectPanel projectPanel;
	public static ProjectSettingsPanel projectSettingsPanel;
	public static ScenePanel scenePanel;
	public static SceneEffectPanel sceneEffectPanel;
	public static ActorPanel actorPanel;
	
	public static DashPanel dashPanel;
	public static PropertyPanel propertyPanel;
	public static EffectPanel effectPanel;
	public static EventPanel eventPanel;
	
	public static boolean projectEnabled = true;
	

    private Frame() {
       setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
       addWindowListener(this);
       //styleProject();
    }
    
    public static Frame getInstance(){
    	if(frame == null)
    		frame = new Frame();
    	return frame;
    }

    public void initToolBar(){
    	toolBar = new ToolBar();
    	add(toolBar, BorderLayout.NORTH);
    }
    
    public void initSideBar(){
   	 	rightSideBar = new JPanel(new VerticalFlowLayout(0, 10));
   	 	UIUtils.setUndecorated(rightSideBar, true);
   	 	dashPanel = new DashPanel();
   	 	propertyPanel = new PropertyPanel();
   	 	effectPanel = new EffectPanel();
   	 	eventPanel = new EventPanel();
   	 	rightSideBar.add(dashPanel);
   	 	rightSideBar.add(propertyPanel);
   	 	//rightSideBar.add(new TablePanel("TT"));
   	 	rightSideBar.add(effectPanel);
   	 	rightSideBar.add(eventPanel);
   	 	
   	 	leftSideBar = new JPanel(new VerticalFlowLayout(0, 10));
   	 	UIUtils.setUndecorated(leftSideBar, true);
   	 	projectPanel = new ProjectPanel();
   	 	projectSettingsPanel = new ProjectSettingsPanel();
   	 	projectSettingsPanel.setVisible(false);
   	 	scenePanel = new ScenePanel();
   	 	sceneEffectPanel = new SceneEffectPanel();
   	 	actorPanel = new ActorPanel();
   	 	leftSideBar.add(projectPanel);
   	 	leftSideBar.add(projectSettingsPanel);
   	 	leftSideBar.add(scenePanel);
   	 	leftSideBar.add(sceneEffectPanel);
   	 	leftSideBar.add(actorPanel);
   	 	
   	 	add(rightSideBar, BorderLayout.EAST);
   	 	add(leftSideBar, BorderLayout.WEST);
   }
    
   public void initStatusBar(){
   	 	statusBar = new StatusBar();
   	 	add(statusBar, BorderLayout.SOUTH);
   }
    
    public void initContent(){
    	content = new Content();
    	add(content, BorderLayout.CENTER);
    }
    
    public static void toggleToolBar(){
    	toolBar.setVisible(!toolBar.isVisible());
    }
    
    public static void toggleStatusBar(){
    	statusBar.setVisible(!statusBar.isVisible());
    }
    
    public static void toggleLeftSideBar(){
    	leftSideBar.setVisible(!leftSideBar.isVisible());
    	if(Content.studioPanel != null)
    		Content.studioPanel.revalidateScreenPosition();
    }
    
    public static void toggleRightSideBar(){
    	rightSideBar.setVisible(!rightSideBar.isVisible());
    	if(Content.studioPanel != null)
    		Content.studioPanel.revalidateScreenPosition();
    }
    
    private static void updateProject(){
    	Asset.loadAsynchronous = false;
		Asset.setBasePath(Content.getProject());
		SceneEditor.reloadAssets = true; // to load assets of new project
		StudioPanel.updateAssets();
		Content.toggleView(2);
    	sceneEffectPanel.clear();
    	scenePanel.clear();
    	projectSettingsPanel.clear();
    	propertyPanel.clear();
    	eventPanel.clear();
    	
    	//sceneEffectPanel.update();
		projectPanel.update();
		projectSettingsPanel.update();
		eventPanel.update();
		//Content.studioPanel.createStudioCanvas();
		ConsolePanel.updateCompiler();
    }
    
    public static void setEnabledProject(){
		if(!projectEnabled){
			enableProject();
			updateProject();
		}
	}
	
	public static void setDisabledProject(){
		if(projectEnabled)
			if(!Content.projectExists())
				Frame.disableProject();
	}
    
	private static void enableProject(){
		//enablePanel(toolBar);
    	enablePanel(content);
    	enablePanel(rightSideBar);
    	enablePanel(leftSideBar);
    	enablePanel(statusBar);
    	for(JButton btn : Style.viewGroup)
    		btn.setEnabled(true);
    	projectEnabled = true;
    }
    
    private static void disableProject(){
    	//disablePanel(toolBar);
    	disablePanel(content);
    	disablePanel(rightSideBar);
    	disablePanel(leftSideBar);
    	disablePanel(statusBar);
    	for(JButton btn : Style.viewGroup)
    		btn.setEnabled(false);
		projectEnabled = false;
    }
    
    public static void styleProject(){
    	stylePanel(toolBar);
    	stylePanel(content);
    	stylePanel(rightSideBar);
    	stylePanel(leftSideBar);
    	stylePanel(statusBar);
    	for(JButton btn : Style.viewGroup){
    		btn.setForeground(Style.font);
    		btn.setBackground(Style.topColor);
    	}
    	toolBar.searchBar.setBackground(Style.listBg);
    }
    
    private static void stylePanel(JPanel panel){
    	panel.setForeground(Style.font);
    	panel.setBackground(Style.botColor);
    	for(Component c: panel.getComponents()){
    		c.setForeground(Style.font);
    		c.setBackground(Style.botColor);
    		if(c instanceof BaseList){
    			((BaseList)c).list.setBackground(Style.listBg);
    			((BaseList)c).list.setForeground(Style.font);
    			((BaseList)c).list.setSelectionBackground(Style.listSelect);
    			((BaseList)c).list.setSelectionForeground(Style.headerFg);
    		}
    		else if(c instanceof BaseTable){
    			((BaseTable)c).table.setBackground(Style.listBg);
    			((BaseTable)c).table.setForeground(Style.font);
    			((BaseTable)c).table.setSelectionBackground(Style.listSelect);
    			((BaseTable)c).table.setSelectionForeground(Style.headerFg);
    		}
    		else if(c instanceof Style.TitleLabel)
    			((Style.TitleLabel)c).setForeground(Style.headerFg);
    		else if(c instanceof Style.TitleButton)
    			((Style.TitleButton)c).setForeground(Style.headerFg);
    		else if(c instanceof JPanel)
        		enablePanel((JPanel)c);
    	}
    }
    
    private static void enablePanel(JPanel panel){
    	panel.setForeground(Style.font);
    	panel.setBackground(Style.topColor);
    	for(Component c: panel.getComponents()){
    		c.setEnabled(true);
    		c.setForeground(Style.font);
    		c.setBackground(Style.topColor);
    		if(c instanceof JPanel)
    			enablePanel((JPanel)c);
    		else if(c instanceof JList)
    			((JList<?>)c).setEnabled(true);
    		else if(c instanceof JLabel)
    			((JLabel)c).setEnabled(true);
    		else if(c instanceof Style.TitleLabel)
    			((Style.TitleLabel)c).setForeground(Style.headerFg);
    		else if(c instanceof Style.TitleButton)
    			((Style.TitleButton)c).setForeground(Style.headerFg);
    	}
    }
    
    private static void disablePanel(JPanel panel){
    	for(Component c: panel.getComponents()){
    		c.setEnabled(false);
    		if(c instanceof JPanel)
    			disablePanel((JPanel)c);
    		else if(c instanceof JList)
    			((JList<?>)c).setEnabled(false);
    		else if(c instanceof JLabel)
    			((JLabel)c).setEnabled(false);
    	}
    }
    
	@Override
	public void windowActivated(WindowEvent arg0) {
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		Content.editor.save();
		Scene.getCurrentScene().save(scenePanel.getSelectedValue());
		if(Content.studioPanel.canvas != null)
			Scene.getRoot().clear();
		//Stage.exit();
		//Content.studioPanel.destroyCanvas();
		dispose();
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
	}
}