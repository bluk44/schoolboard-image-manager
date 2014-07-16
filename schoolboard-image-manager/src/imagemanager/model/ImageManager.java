package imagemanager.model;

import java.util.Set;

public interface ImageManager {	
	
	public void readAllLabels();
	public Set<Label> getLabels();
	public Label createLabel(Label newLabel);
	public void rename(String title, Label label);
	public void deleteLabel(Label...labels);
	
}
