package imagemanager.gui;

import imagemanager.Label;

import javax.swing.JList;


public class ImageLabelList extends JList<Label> {
	
	Label[] allLabels;
	
	public ImageLabelList(Label[] allLabels){
		super(allLabels);
		this.allLabels = allLabels;
	}
	
	@Override
	protected void fireSelectionValueChanged(int firstIndex, int lastIndex,
			boolean isAdjusting) {
		// TODO Auto-generated method stub
		super.fireSelectionValueChanged(firstIndex, lastIndex, isAdjusting);
	}
}
