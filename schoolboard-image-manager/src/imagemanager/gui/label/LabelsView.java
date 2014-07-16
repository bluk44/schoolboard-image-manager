package imagemanager.gui.label;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComponent;

public class LabelsView extends JComponent {
	
	private List<LabelComponent> labelComps;
	
	public LabelsView(){
		labelComps = new ArrayList<LabelComponent>();
		for(int i = 0; i < 10; i++){
			labelComps.add(new LabelComponent());
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
