package imageprocessing.boundsdetection;

import imageprocessing.Util;
import imageprocessing.geometry.Point;
import imageprocessing.geometry.Polygon;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

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
	private JButton jButton1;
	private JButton jButton2;
	private BufferedImage image = Util
			.readFromFile("images/blackboard/bb31.png");
	private TransformationTestComp ttc = new TransformationTestComp(image);

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
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(null);
			{
				ttc.addMouseListener(new MouseListener() {

					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseClicked(MouseEvent e) {
						ttc.updateDis(new Point(e.getX(), e.getY()));
					}
				});
				jScrollPane1 = new JScrollPane();
				jScrollPane1.setViewportView(ttc);
				getContentPane().add(jScrollPane1);
				jScrollPane1.setBounds(0, 0, 1009, 634);
			}
			{
				jButton1 = new JButton();
				jButton1.addMouseListener(new MouseListener() {

					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseClicked(MouseEvent e) {
						BufferedImage fixed = QuadrangleTransformation
								.transform(image, ttc.dis.getPolygon(),
										ttc.fix.getPolygon());

						Test.showImage(fixed, "fixed");
						BufferedImage region = Util.subImage(fixed, (int)ttc.fix.getPoint(0).x,
								(int)ttc.fix.getPoint(0).y, (int)ttc.fix.getPoint(2).x,
								(int)ttc.fix.getPoint(2).y);
						Test.showImage(region, "region");
					}
				});
				getContentPane().add(jButton1);
				jButton1.setText("trans");
				jButton1.setBounds(1015, 7, 71, 33);
			}
			{
				jButton2 = new JButton();
				getContentPane().add(jButton2);
				jButton2.setText("cut");
				jButton2.setBounds(1015, 45, 71, 32);
			}
			pack();
			this.setSize(1102, 675);
		} catch (Exception e) {
			// add your error handling code here
			e.printStackTrace();
		}
	}

}
