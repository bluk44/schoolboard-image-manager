package imagemanager.model;

import imagemanager.exception.DBException;

import java.util.List;

public interface ImageManager {	
	
	public List<Label> getAllLabels();
	public void createLabel(Label newLabel) throws DBException;
	public void rename(String title, Label label);
	public void deleteLabel(Label...labels);
	
}
