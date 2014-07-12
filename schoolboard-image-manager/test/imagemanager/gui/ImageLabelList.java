package imagemanager.gui;

import imagemanager.model.ImageLabel;

import javax.swing.JList;


public class ImageLabelList extends JList<ImageLabel> {
	
	ImageLabel[] allLabels;
	
	public ImageLabelList(ImageLabel[] allLabels){
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
