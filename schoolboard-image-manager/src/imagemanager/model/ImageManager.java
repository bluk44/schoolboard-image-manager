package imagemanager.model;

import java.util.List;

public interface ImageManager {	
	
	public List<Label> getAllLabels();
	public Label createLabel(Label newLabel);
	public void rename(String title, Label label);
	public void deleteLabel(Label...labels);
	
}
