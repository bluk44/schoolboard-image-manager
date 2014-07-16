package imagemanager.gui.label;

import imagemanager.model.ImageManager;
import imagemanager.model.ImageManagerImpl;
import imagemanager.model.Label;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JComponent;

public class LabelsView extends JComponent {
	
	private List<LabelComponent> labelComps;
	private ImageManager imageManager = new ImageManagerImpl();
	
	public LabelsView(){
		labelComps = new ArrayList<LabelComponent>();
		List<Label> labels = imageManager.getAllLabels();
		System.out.println(labels.size());
		for (Label label : labels) {
			System.out.println(label);
			labelComps.add(new LabelComponent(label));
		}
		initGUI();
	}

	private void initGUI() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		for (LabelComponent labelComponent : labelComps) {
			this.add(labelComponent);
		}
	}
}
