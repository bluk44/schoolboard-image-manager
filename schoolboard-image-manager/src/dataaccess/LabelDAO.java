package dataaccess;

import imagemanager.model.Label;
import imagemanager.model.SourceImage;

public interface LabelDAO extends GenericDAO<Label, Long> {
	
	public void assignLabel(Label label, SourceImage image);
	public void unassignLabel(Label label, SourceImage image);
	
}
