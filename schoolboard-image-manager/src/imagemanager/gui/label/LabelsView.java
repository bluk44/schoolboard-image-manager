package imagemanager.gui.label;

import imagemanager.exception.DBException;
import imagemanager.model.ImageManager;
import imagemanager.model.ImageManagerImpl;
import imagemanager.model.Label;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

public class LabelsView extends JComponent {

	private ImageManager imageManager = new ImageManagerImpl();
	private JPopupMenu labelPopup = null;
	JMenuItem removeLabelMenuItem;
	JMenuItem createNewLabelMunuItem;
	JMenuItem renameLabelMenuItem;
		
	public LabelsView() {
		initGUI();
		List<Label> labels = imageManager.getAllLabels();
		for (Label label : labels) {
			this.add(new LabelComponent(label));
		}
	}

	private void addLabel(Label label) {
		// Insert in alphabetical order
		int i = 0;
		while (i < this.getComponents().length
				&& ((LabelComponent) this.getComponent(i)).getTitle()
						.compareTo(label.getTitle()) < 0) {
			i++;
		}
		LabelComponent lc = new LabelComponent(label);
		this.add(lc, i);

		this.revalidate();
	}
	
	private void deleteLabel(int compId){
		remove(compId);
		revalidate();
		repaint();
	}
	
	private void initGUI() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// labelPopup
		{
			removeLabelMenuItem = new JMenuItem("usuń etykiety");
			createNewLabelMunuItem = new JMenuItem("utwórz etykietę");
			renameLabelMenuItem = new JMenuItem("zmień tytuł");
			
			createNewLabelMunuItem.addActionListener(createLabelAction);
			removeLabelMenuItem.addActionListener(deleteLabelsAction);
			
			labelPopup = new JPopupMenu();
			labelPopup.add(removeLabelMenuItem);		
			labelPopup.add(createNewLabelMunuItem);
			labelPopup.add(renameLabelMenuItem);

		}
		// add mouseListener for popup menu
		this.addMouseListener(mouseListener);

	}
	
	private int countSelectedLabels(){
		Component[] comps = getComponents();
		int count = 0;
		for (Component component : comps) {
			if(((LabelComponent)component).isMarked()){
				++count;
			}
		}
		return count;
	}
	
	// Wywolanie menu kontekstowego

	private MouseListener mouseListener = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3) {
				System.out.println(countSelectedLabels());
					if(countSelectedLabels() != 1){
						renameLabelMenuItem.setEnabled(false);
					}else{
						renameLabelMenuItem.setEnabled(true);						
					}
					labelPopup.show((JComponent) e.getSource(), e.getX(),
							e.getY());
			}
		}
	};

	// TWORZENIE NOWEJ ETYKIETY

	private ActionListener createLabelAction = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			String title = JOptionPane.showInputDialog("podaj tytuł etykiety");
			if (title != null && title.length() > 0) {
				Label label = new Label(title);
				try {
					imageManager.createLabel(label);
					addLabel(label);
				} catch (DBException ex) {
					JOptionPane.showMessageDialog((Component) e.getSource(),
							ex.getMessage(), "Database Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	};
	
	// Usuwanie zaznaczonych etykiet
	
	private ActionListener deleteLabelsAction = new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			List<Label> labsDeletion = new ArrayList<Label>();
			List<Integer> idsDeletion = new ArrayList<Integer>();
			Component[] comps = getComponents();
			
			for(int i = 0; i < comps.length; i++){
				if(((LabelComponent)comps[i]).isMarked()){
					labsDeletion.add(((LabelComponent)comps[i]).getLabel());
					idsDeletion.add(i);
				}
			}
			try{
				imageManager.deleteLabel(labsDeletion.toArray(new Label[]{}));	
				for (Integer i : idsDeletion) {
					System.out.println("deleting "+i);
					deleteLabel(i);
				}
			} catch(DBException ex){
				JOptionPane.showMessageDialog((Component) e.getSource(),
						ex.getMessage(), "Database Error",
						JOptionPane.ERROR_MESSAGE);				
			}
		}
		
	};
}
