package imagemanager.gui.label;

import imagemanager.model.Label;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JToggleButton;
import javax.swing.SpringLayout;

public class LabelComponent extends JComponent {
	private JCheckBox showImagesBox;
	private JToggleButton titleButton;
	private Label label;
	
	public LabelComponent(){
		initGUI();
	}
	
	public LabelComponent(Label label){
		this();
		this.label = label;
		setTitle(label.getTitle());
	}
	
	public Label getLabel(){
		return label;
	}
	
	public String getTitle(){
		return titleButton.getText();
	}
	
	private void setTitle(String title){
		titleButton.setText(title);
	}
	
	private void initGUI(){
		SpringLayout sl = new SpringLayout();
		this.setLayout(sl);
		{
			showImagesBox = new JCheckBox();
			//showImagesBox.setPreferredSize(new Dimension(40, 40));
			this.add(showImagesBox);
			sl.putConstraint(SpringLayout.WEST, showImagesBox, 0, SpringLayout.WEST, this);
			sl.putConstraint(SpringLayout.NORTH, showImagesBox, 0, SpringLayout.NORTH, this);
		}
		{
			titleButton = new JToggleButton();
			titleButton.addMouseListener(new EventPropagator());
			//titleButton.setPreferredSize(new Dimension(40, 100));
			this.add(titleButton);
			
			sl.putConstraint(SpringLayout.WEST, titleButton, 0, SpringLayout.EAST, showImagesBox);
			sl.putConstraint(SpringLayout.EAST, titleButton, 0, SpringLayout.EAST, this);
			sl.putConstraint(SpringLayout.NORTH, titleButton, 0, SpringLayout.NORTH, this);
			sl.putConstraint(SpringLayout.SOUTH, titleButton, 20, SpringLayout.NORTH, this);
		}
		
		this.setMaximumSize(new Dimension(getMaximumSize().width, 20));

	}

	private class EventPropagator extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			e.getComponent().getParent().getParent().dispatchEvent(e);
		}
	}
}
