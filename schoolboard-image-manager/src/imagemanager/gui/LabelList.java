package imagemanager.gui;

import imagemanager.model.LabelRecord;

import java.util.ArrayList;

import javax.swing.JList;

public class LabelList extends JList<LabelRecord> {
	
	boolean[] prevSelection;
	boolean[] currSelection;
	/**
	 * 
	 * @return tablica etykiet ktore zmienily zaznaczenie 
	 * 	0 - etykiety zaznaczone
	 * 	1 - etykiety odznaczone
	 */
	public ArrayList<LabelRecord>[] getLabelsToChange(){
//		System.out.println("call");
//		return null;
		int[] selectedIdxs = getSelectedIndices();
		currSelection = new boolean[prevSelection.length];
		for(int i = 0; i<selectedIdxs.length; i++){
			currSelection[selectedIdxs[i]] = true;
		}
		
		ArrayList[] labsToChange = new ArrayList[2];
		labsToChange[0] = new ArrayList<LabelRecord>();
		labsToChange[1] = new ArrayList<LabelRecord>();
		
		for(int i = 0; i < currSelection.length; i++){
			if(prevSelection[i] == false && currSelection[i] == true)
				labsToChange[0].add(getModel().getElementAt(i));
			else if(prevSelection[i] == true && currSelection[i] == false)
				labsToChange[1].add(getModel().getElementAt(i));
			
		}
		prevSelection = currSelection;
		return labsToChange;
	}
	
	public LabelList(LabelRecord[] records){
		super(records);
		prevSelection = new boolean[records.length];
	}
	
	
}
