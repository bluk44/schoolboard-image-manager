package imagemanager.gui;

import imagemanager.Label;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JList;

public class LabelList extends JList<Label> {
	
	boolean[] prevSelection;
	boolean[] currSelection;
	/**
	 * 
	 * @return tablica etykiet ktore zmienily zaznaczenie 
	 * 	0 - etykiety zaznaczone
	 * 	1 - etykiety odznaczone
	 */
	public ArrayList<Label>[] getLabelsToChange(){

		int[] selectedIdxs = getSelectedIndices();
		currSelection = new boolean[prevSelection.length];
		for(int i = 0; i<selectedIdxs.length; i++){
			currSelection[selectedIdxs[i]] = true;
		}
		
		ArrayList[] labsToChange = new ArrayList[2];
		labsToChange[0] = new ArrayList<Label>();
		labsToChange[1] = new ArrayList<Label>();
		
		for(int i = 0; i < currSelection.length; i++){
			if(prevSelection[i] == false && currSelection[i] == true)
				labsToChange[0].add(getModel().getElementAt(i));
			else if(prevSelection[i] == true && currSelection[i] == false)
				labsToChange[1].add(getModel().getElementAt(i));
			
		}
		prevSelection = currSelection;
		return labsToChange;
	}
	
	public LabelList(Collection<Label> records){
		super(records.toArray(new Label[] {}));
		prevSelection = new boolean[records.size()];
	}
	
	
}
