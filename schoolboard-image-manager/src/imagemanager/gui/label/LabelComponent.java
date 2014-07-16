package imagemanager.gui.label;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JToggleButton;
import javax.swing.SpringLayout;

public class LabelComponent extends JComponent {
	private JCheckBox showImagesBox;
	private JToggleButton titleButton;
	
	public LabelComponent(){
		initGUI();
		//setTitle("untitled");

	}
	
	public String getTitle(){
		return titleButton.getName();
	}
	
	public void setTitle(String title){
		titleButton.setName(title);
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
			titleButton = new JToggleButton("untitled");
			//titleButton.setPreferredSize(new Dimension(40, 100));
			this.add(titleButton);
			sl.putConstraint(SpringLayout.WEST, titleButton, 0, SpringLayout.EAST, showImagesBox);
			sl.putConstraint(SpringLayout.EAST, titleButton, 0, SpringLayout.EAST, this);
			sl.putConstraint(SpringLayout.NORTH, titleButton, 0, SpringLayout.NORTH, this);
			sl.putConstraint(SpringLayout.SOUTH, titleButton, 20, SpringLayout.NORTH, this);
		}
		this.setMaximumSize(new Dimension(getMaximumSize().width, 20));

	}

}
