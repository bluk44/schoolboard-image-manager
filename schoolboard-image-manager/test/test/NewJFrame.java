package test;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;


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
public class NewJFrame extends javax.swing.JFrame {
	private JScrollPane jScrollPane1;
	private JButton jButton1;
	private JButton diss;

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
	
	public NewJFrame() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			GroupLayout thisLayout = new GroupLayout((JComponent)getContentPane());
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(thisLayout);
			{
				jScrollPane1 = new JScrollPane();
			}
			{
				diss = new JButton();
				diss.setText("diss");
			}
			{
				jButton1 = new JButton();
				jButton1.setText("fix");
			}
				thisLayout.setVerticalGroup(thisLayout.createParallelGroup()
					.addComponent(jScrollPane1, GroupLayout.Alignment.LEADING, 0, 607, Short.MAX_VALUE)
					.addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
					    .addGap(12)
					    .addComponent(diss, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					    .addGap(37)
					    .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					    .addContainerGap(488, Short.MAX_VALUE)));
				thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
					.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 580, GroupLayout.PREFERRED_SIZE)
					.addGap(43)
					.addGroup(thisLayout.createParallelGroup()
					    .addGroup(thisLayout.createSequentialGroup()
					        .addComponent(diss, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE))
					    .addGroup(thisLayout.createSequentialGroup()
					        .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(48, Short.MAX_VALUE));
			pack();
			this.setSize(728, 637);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}

}
