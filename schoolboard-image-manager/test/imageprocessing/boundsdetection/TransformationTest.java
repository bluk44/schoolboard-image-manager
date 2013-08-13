package imageprocessing.boundsdetection;

import imageprocessing.Util;
import imageprocessing.geometry.Point;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import javax.swing.ButtonGroup;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import test.Test;
import testJcomps.TransformationTestComp;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class TransformationTest extends javax.swing.JFrame {
	private JScrollPane jScrollPane1;
	private JButton transform;
	private JRadioButton jRadioButton2;
	private JRadioButton jRadioButton1;

	private BufferedImage image = Util.readFromFile("images/rect_detection/whiteboard01.png");
	private TransformationTestComp ttc = new TransformationTestComp(image);
	boolean diss = false, fix = false;

	/**
	 * Auto-generated main method to display this JFrame
	 */
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				TransformationTest inst = new TransformationTest();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});

	}

	public TransformationTest() {
		super();
		initGUI();
	}

	private void initGUI() {

		try {
			GroupLayout thisLayout = new GroupLayout((JComponent) getContentPane());
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(thisLayout);
			{
				jScrollPane1 = new JScrollPane();
				jScrollPane1.addMouseMotionListener(new MouseMotionAdapter() {
					public void mouseDragged(MouseEvent evt) {
						Point p = new Point(evt.getX(), evt.getY());
						if (diss)
							ttc.updateDis(p);
						else if (fix)
							ttc.updateFix(p);

					}
				});
				jScrollPane1.setViewportView(ttc);
			}
			{
				transform = new JButton();
				transform.setText("jButton1");
				transform.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						BufferedImage image2 = QuadrangleTransformation.transform(image, ttc.dis.getPolygon(), ttc.fix.getPolygon());
						Test.showImage(image2, "after transformation");
						repaint();
					}
				});
			}
			{
				jRadioButton1 = new JRadioButton();
				jRadioButton1.setText("diss");
				jRadioButton1.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent evt) {
						if (jRadioButton1.isSelected()) {
							jRadioButton2.setSelected(false);
							diss = true;
							fix = false;
						}
					}
				});
			}
			{
				jRadioButton2 = new JRadioButton();
				jRadioButton2.setText("fix");
				jRadioButton2.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent evt) {
						if (jRadioButton2.isSelected()) {
							jRadioButton1.setSelected(false);
							fix = true;
							diss = false;
						}
					}
				});
			}
			thisLayout.setVerticalGroup(thisLayout.createParallelGroup()
				.addComponent(jScrollPane1, GroupLayout.Alignment.LEADING, 0, 607, Short.MAX_VALUE)
				.addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				    .addGap(50)
				    .addComponent(jRadioButton1, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addGap(15)
				    .addComponent(jRadioButton2, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
				    .addGap(91)
				    .addComponent(transform, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
				    .addContainerGap(369, Short.MAX_VALUE)));
			thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
				.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 580, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(thisLayout.createParallelGroup()
				    .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				        .addComponent(jRadioButton1, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				        .addGap(0, 71, Short.MAX_VALUE))
				    .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				        .addComponent(jRadioButton2, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
				        .addGap(0, 72, Short.MAX_VALUE))
				    .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				        .addPreferredGap(jRadioButton1, transform, LayoutStyle.ComponentPlacement.INDENT)
				        .addComponent(transform, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
				        .addGap(0, 0, Short.MAX_VALUE)))
				.addContainerGap(18, 18));
			pack();
			this.setSize(728, 637);
		} catch (Exception e) {
			// add your error handling code here
			e.printStackTrace();
		}

	}

	@Override
	public void paintComponents(Graphics g) {
		super.paintComponents(g);
	}

}
