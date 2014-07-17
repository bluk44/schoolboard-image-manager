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

	private List<LabelComponent> labelComps;
	private ImageManager imageManager = new ImageManagerImpl();
	private JPopupMenu labelMarkedPopup = null;
	private JPopupMenu labelUnmarkedPopup = null;
	private boolean labelMarked = false;
	
	public LabelsView() {
		labelComps = new ArrayList<LabelComponent>();
		List<Label> labels = imageManager.getAllLabels();
		for (Label label : labels) {
			LabelComponent lc = new LabelComponent(label);
			labelComps.add(lc);
		}
		initGUI();
	}
	
	private void addLabel(Label label){	
		// Insert in alphabetical order
		int i = 0;
		while(i < labelComps.size() && labelComps.get(i).getTitle().compareTo(label.getTitle()) < 0){
			i++;
		}
		LabelComponent lc = new LabelComponent(label);
		labelComps.add(i, lc);
		this.add(lc, i);
		
		this.revalidate();
	}
	
	private void initGUI() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		// labelMarkedPopup
		{
			JMenuItem renameLabelMenuItem = new JMenuItem("zmień tytuł");
			JMenuItem removeLabelMenuItem = new JMenuItem("usuń etykietę");
			labelMarkedPopup = new JPopupMenu();
			labelMarkedPopup.add(renameLabelMenuItem);
			labelMarkedPopup.add(removeLabelMenuItem);

		}
		// labelUnmarkedPopup
		{
			JMenuItem createNewLabelMunuItem = new JMenuItem(
					"utwórz nową etykietę");
			createNewLabelMunuItem.addActionListener(createLabelAction);

			labelUnmarkedPopup = new JPopupMenu();
			labelUnmarkedPopup.add(createNewLabelMunuItem);
		}
		// add mouseListener for popup menu
		this.addMouseListener(mouseListener);
		for (LabelComponent labelComponent : labelComps) {
			this.add(labelComponent);
		}
	}

	// Wywolanie menu kontekstowego

	private MouseListener mouseListener = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3) {
				System.out.println("ml called");
				if (!labelMarked) {
					labelUnmarkedPopup.show((JComponent) e.getSource(),
							e.getX(), e.getY());
				} else {
					labelMarkedPopup.show((JComponent) e.getSource(), e.getX(),
							e.getY());
				}
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

}
