package gdxstudio.panel;
import gdxstudio.Frame;
import gdxstudio.SceneEditor;

import java.awt.Component;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;

import scene2d.Asset;
import scene2d.ImageJson;
import scene2d.Scene;
import scene2d.Serializer;
import scene2d.Sprite;
import web.laf.lite.layout.ToolbarLayout;
import web.laf.lite.utils.UIUtils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

public class PropertyPanel extends BaseTable {
	private static final long serialVersionUID = 1L;
	
	JComboBox<String> fontcombo = createComboBox();
	JComboBox<String> texcombo = createComboBox();
	
	static int rowCount = 0;
	static int noOfRows = 16;
	
	public PropertyPanel(){
		super("Properties", null, new PropertyRenderer());
		editors.add(createTextFieldEditor()); //Name
		editors.add(createIntegerSpinner("X"));
		editors.add(createIntegerSpinner("Y"));
		editors.add(createIntegerSpinner("Width"));
		editors.add(createIntegerSpinner("Height"));
		editors.add(createIntegerSpinner("OriginX"));
		editors.add(createIntegerSpinner("OriginY"));
		editors.add(createIntegerSpinner("Rotation"));
		editors.add(createIntegerSpinner("Z-Index"));
		editors.add(new ColorEditor());
		editors.add(createTouchableEditor()); //Touchable
		editors.add(createBooleanEditor()); //Visible
		editors.add(createTextFieldEditor());
		editors.add(createTextFieldEditor());
		editors.add(createTextFieldEditor());
		editors.add(createTextFieldEditor());
	}
	
	@Override
	public void clear(String... names){
		super.clear("Name", "X", "Y", "Width", "Height",  "OriginX", "OriginY", "Rotation", "Z-Index", "Color", "Touchable", "Visible", "", "", "", "");
	}
	
	@Override
	public void update(String... names){
		rowCount = 0;
		super.update("Name", ""+SceneEditor.selectedActor.getName(), "X", ""+SceneEditor.selectedActor.getX(), 
				"Y", ""+SceneEditor.selectedActor.getY(), "Width", ""+SceneEditor.selectedActor.getWidth(),
				"Height", ""+SceneEditor.selectedActor.getHeight(), "OriginX",""+SceneEditor.selectedActor.getOriginX(),
				"OriginY",""+SceneEditor.selectedActor.getOriginY(),"Rotation",""+SceneEditor.selectedActor.getRotation(),
				"Z-Index", ""+SceneEditor.selectedActor.getZIndex(),
				"Color", ""+SceneEditor.selectedActor.getColor().toString(),
				"Touchable", ""+SceneEditor.selectedActor.getTouchable().toString(),
				"Visible", ""+SceneEditor.selectedActor.isVisible()
		);
		editors.set(12, createTextFieldEditor());
		editors.set(13, createTextFieldEditor());
		editors.set(14, createTextFieldEditor());
		editors.set(15, createTextFieldEditor());
		
		/* From here it varies for each type */
		if(SceneEditor.selectedActor instanceof ImageJson){
			addRow("Texture", ""+((ImageJson)SceneEditor.selectedActor).getTexName()); // row 7
			texcombo.removeAllItems();
			for(String tex: Asset.texMap.keys())
				texcombo.addItem(tex);
			editors.set(12, new DefaultCellEditor(texcombo));
		}
		
		/* FONTS */
		if(SceneEditor.selectedActor instanceof Label){
			addRow("Font", ""+Asset.fontMap.getKey(((Label)SceneEditor.selectedActor).getStyle().font, false)); // row 7
			addRow("Text", ""+((Label)SceneEditor.selectedActor).getText());
			fontcombo.removeAllItems();
			for(String font: Asset.fontMap.keys())
				fontcombo.addItem(font);
			editors.set(12, new DefaultCellEditor(fontcombo));
		}
		
		if(SceneEditor.selectedActor instanceof TextButton){
			addRow("Text", ""+((TextButton)SceneEditor.selectedActor).getText());
		}

		if(SceneEditor.selectedActor instanceof TextField){
			addRow("Text", ""+((TextField)SceneEditor.selectedActor).getText());
			addRow("MessageText", ""+((TextField)SceneEditor.selectedActor).getMessageText());
			//addRow("Password", ""+((TextFieldJson)Sink.selectedActor).getPasswordMode()});
		}
		if(SceneEditor.selectedActor instanceof CheckBox)
			addRow("Text", ""+((CheckBox)SceneEditor.selectedActor).getText());

		if(SceneEditor.selectedActor instanceof Dialog){
			editors.set(12, createBooleanEditor());
			editors.set(13, createBooleanEditor());
			editors.set(14, createBooleanEditor());
			addRow("Modal", ""+((Dialog)SceneEditor.selectedActor).isModal()); //7
			addRow("Moveble", ""+((Dialog)SceneEditor.selectedActor).isMovable()); //8
			addRow("Resizable", ""+((Dialog)SceneEditor.selectedActor).isResizable()); //9
			addRow("Text", ""+((Dialog)SceneEditor.selectedActor).getTitle());
		}
		/* FONTS */

		if(SceneEditor.selectedActor instanceof Touchpad){
			addRow("Deadzone", ""+Serializer.TouchpadSerializer.deadZoneRadius);
		}

		if(SceneEditor.selectedActor instanceof Slider){
			Slider slider = ((Slider)SceneEditor.selectedActor);
			addRow("Min", ""+slider.getMinValue());
			addRow("Max", ""+slider.getMaxValue());
			addRow("Step", ""+slider.getStepSize());
			addRow("Value", ""+slider.getValue());
		}
		if(SceneEditor.selectedActor instanceof Sprite){
			Sprite sprite = ((Sprite)SceneEditor.selectedActor);
			editors.set(12, createBooleanEditor());
			editors.set(13, createBooleanEditor());
			addRow("Active", ""+sprite.isAnimationActive);
			addRow("Looping", ""+sprite.isAnimationLooping);
			addRow("Duration", ""+sprite.getDuration());
			addRow("Textures", ""+sprite.toString());
		}
		int value = getRowCount();
		for(int i=0;i<noOfRows-value;i++)	
			addRow("", "");
	}
	
	
	@Override
	public void setProperty(String key, String value){
		if(key.isEmpty() || value.isEmpty() || SceneEditor.selectedActor == null)
			return ;
		switch(key){
			case "Name":
				for(Actor actor: Scene.getCurrentScene().getChildren())
					if(actor != null)
						if(actor.getName() != null)
							if(actor.getName().equals(value)){
								// Cant rename set back to old name
								updateProperty("Name", SceneEditor.selectedActor.getName(), 0);
								return;
							}
				Frame.actorPanel.renameActor(SceneEditor.selectedActor.getName(), value);
				SceneEditor.selectedActor.setName(value);
				break;
			case "X": SceneEditor.selectedActor.setX(Float.parseFloat(value));break;
			case "Y": SceneEditor.selectedActor.setY(Float.parseFloat(value));break;
			case "Width": SceneEditor.selectedActor.setWidth(Float.parseFloat(value));break;
			case "Height": SceneEditor.selectedActor.setHeight(Float.parseFloat(value));break;
			case "OriginX": SceneEditor.selectedActor.setOriginX(Float.parseFloat(value));break;
			case "OriginY": SceneEditor.selectedActor.setOriginY(Float.parseFloat(value));break;
			case "Rotation": SceneEditor.selectedActor.setRotation(Float.parseFloat(value));break;
			case "Z-Index": 
				int z = Integer.parseInt(value);
				if(z>0 && z<Scene.getCurrentScene().getChildren().size){
					SceneEditor.selectedActor.setZIndex(z);
				}
				//updateProperty("Z-Index", ""+SceneEditor.selectedActor.getZIndex(), 0);
				break;
			case "Color":
				if(value.length() == 8 && value.matches("[0-9A-Fa-f]+"))
					SceneEditor.selectedActor.setColor(Color.valueOf(value));
			break;
			case "Touchable":SceneEditor.selectedActor.setTouchable(Touchable.valueOf(value));break;
			case "Visible":SceneEditor.selectedActor.setVisible(Boolean.parseBoolean(value));break;
			
				
				
			case "Text": 
				if(SceneEditor.selectedActor instanceof Label){
					((Label)SceneEditor.selectedActor).setText(value);
					((Label)SceneEditor.selectedActor).pack();
				}
				if(SceneEditor.selectedActor instanceof TextButton){
					((TextButton)SceneEditor.selectedActor).setText(value);
					((TextButton)SceneEditor.selectedActor).pack();
				}
				if(SceneEditor.selectedActor instanceof TextField)
					((TextField)SceneEditor.selectedActor).setText(value);
				if(SceneEditor.selectedActor instanceof CheckBox){
					((CheckBox)SceneEditor.selectedActor).setText(value);
					((CheckBox)SceneEditor.selectedActor).pack();
				}
				if(SceneEditor.selectedActor instanceof Dialog){
					((Dialog)SceneEditor.selectedActor).setTitle(value);
					((Dialog)SceneEditor.selectedActor).pack();
				}
				break;
				
			// TextField Related Properties
			case "MessageText": ((TextField)SceneEditor.selectedActor).setMessageText(value);break;
			case "Password": ((TextField)SceneEditor.selectedActor).setPasswordMode(Boolean.parseBoolean(value));break;
			
				
			//Slider Related Properties
			case "Value":((Slider)SceneEditor.selectedActor).setValue(Float.parseFloat(value));break;
			case "Step":((Slider)SceneEditor.selectedActor).setStepSize(Float.parseFloat(value));break;
			
			//Dialog Related Properties
			case "Modal": 
				((Dialog)SceneEditor.selectedActor).setModal(Boolean.parseBoolean(value));
				break;
			case "Movable":
				((Dialog)SceneEditor.selectedActor).setMovable(Boolean.parseBoolean(value));
				break;
			case "Resizable":
				((Dialog)SceneEditor.selectedActor).setResizable(Boolean.parseBoolean(value));
				break;
			
			//Touchpad Related Properties
			case "Deadzone":Serializer.TouchpadSerializer.deadZoneRadius = Float.parseFloat(value);
			
			case "Texture": 
				ImageJson img = (ImageJson)SceneEditor.selectedActor;
				ImageJson newimg = new ImageJson(value);
				newimg.setName(img.getName());
				newimg.setX(img.getX());
				newimg.setY(img.getY());
				newimg.setWidth(img.getWidth());
				newimg.setHeight(img.getHeight());
				if(img.getZIndex() < 0)
					newimg.setZIndex(1);
				else
					newimg.setZIndex(img.getZIndex());
				newimg.setColor(img.getColor());
				Scene.getCurrentScene().removeActor(img);
				Scene.getCurrentScene().addActor(newimg);
				newimg.pack();
				SceneEditor.selectedActor = newimg;
				break;
			case "Font": 
				Label label = (Label)SceneEditor.selectedActor;
				Label.LabelStyle ls = new Label.LabelStyle();
				ls.font = Asset.font(value);
				label.setStyle(ls);
				label.pack();
				break;
				
			case "Textures":
				((Sprite)SceneEditor.selectedActor).setTextures(value.split(","));
				break;
				
			case "Duration":
				((Sprite)SceneEditor.selectedActor).setDuration(Float.parseFloat((String)value));
				break;
			
			case "Active":
				((Sprite)SceneEditor.selectedActor).isAnimationActive= Boolean.parseBoolean(value);
				break;
				
			case "Looping":
				((Sprite)SceneEditor.selectedActor).isAnimationLooping = Boolean.parseBoolean(value);
				break;
		}
		Scene.isDirty = true;
		Scene.getCurrentScene().outline(SceneEditor.selectedActor);
		Frame.dashPanel.update();
	}
	
	@Override
	public void updateProperty(String key, String value, int row){
		if(SceneEditor.selectedActor != null){
			switch(key){
				case "Name": super.updateProperty(key, value, 0);break;
				case "X": super.updateProperty(key, value, 1);break;
				case "Y": super.updateProperty(key, value, 2);break;
				case "Width": super.updateProperty(key, value, 3);break;
				case "Height": super.updateProperty(key, value, 4);break;
				case "Z-Index": super.updateProperty(key, value, 8);break;
			}
		}
	}
}

class PropertyRenderer extends BaseRenderer {
	private static final long serialVersionUID = 1L;

	@Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    	/*if(value instanceof Boolean) { // Boolean
    	    checkBox.setSelected(((Boolean) value).booleanValue());
    	    checkBox.repaint();
    	    checkBox.setHorizontalAlignment(JLabel.CENTER);
    	    return checkBox;
    	}*/
		//try {
	       // Boolean.parseBoolean(value.toString());
	   // } catch ( java.text.ParseException e ) {
	  //  	e.printStackTrace();
	  //  }
        setBorder(noFocusBorder);
        if(column == 0)
        	return new HeaderLabel(value.toString());
        else {
        	if(row >= 1 && row <=8){
        		if(!value.toString().isEmpty())
        			spinnerInteger.setValue((int)Float.parseFloat(value.toString()));
        		else
        			spinnerInteger.setValue(new Integer(0));
        		return spinnerInteger;
            }
        	if(row == 9){
        		label.setOpaque(true);
        		label.setBorder(BorderFactory.createLineBorder(java.awt.Color.gray));
        		String colorString = (String) value;
        		if(colorString.length() == 8 && colorString.matches("[0-9A-Fa-f]+")){
        			com.badlogic.gdx.graphics.Color cl = com.badlogic.gdx.graphics.Color.valueOf(colorString);
        			label.setBackground(new java.awt.Color(cl.r, cl.g, cl.b, cl.a));
        		}
        		JPanel pan = new JPanel(new ToolbarLayout());
        		pan.setOpaque(false);
        		UIUtils.setMargin(pan, new Insets(2,3,2,3));
        		pan.add(label);
        		return pan;
        	}
        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}