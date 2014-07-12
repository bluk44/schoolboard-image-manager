package imagemanager.gui;
import imagemanager.model.ImageLabel;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class NewJFrame2 extends javax.swing.JFrame implements ListSelectionListener {
	private JScrollPane jScrollPane1;
	private JList jList1;

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				NewJFrame inst = new NewJFrame();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public NewJFrame2() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				jScrollPane1 = new JScrollPane();
				getContentPane().add(jScrollPane1, BorderLayout.CENTER);
				jScrollPane1.setBackground(new java.awt.Color(0,0,255));
//				{
//					ListModel jList1Model = 
//							new DefaultComboBoxModel(
//									new String[] { "Item One", "Item Two", "Lulz", "Shieeeet" });
//					jList1 = new JList();
//					jScrollPane1.setViewportView(jList1);
//					jList1.setModel(jList1Model);
//					jList1.addListSelectionListener(this);
//				}
				{
					ImageLabel[] labels = new ImageLabel[2];
					labels[0] = new ImageLabel(7, "unprocessed");
					labels[1] = new ImageLabel(11, "chemia");
					
					ImageLabelList list = new ImageLabelList(labels);
					list.addListSelectionListener(this);
					jScrollPane1.setViewportView(list);
				}
			}
			pack();
			setSize(400, 300);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		JList<ImageLabel> list = (JList<ImageLabel>) e.getSource();	
		List<ImageLabel> l = list.getSelectedValuesList();
		System.out.println(l.size());
		for (Iterator<ImageLabel> it = l.iterator(); it.hasNext();) {
			System.out.println(it.next().getId());
		}
	}

}
