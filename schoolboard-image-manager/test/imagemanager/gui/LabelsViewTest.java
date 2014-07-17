package imagemanager.gui;

import imagemanager.gui.label.LabelsView;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class LabelsViewTest extends JFrame {
	
	private LabelsView labelsView;
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				LabelsViewTest inst = new LabelsViewTest();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});		
	}
	
	public LabelsViewTest() {
		super();
		initGUI();
	}
	
	private void initGUI(){
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			// init labelsView
			{
				labelsView = new LabelsView();
				labelsView.setPreferredSize(new Dimension(200, 400));
				labelsView.setBackground(Color.BLUE);
				this.add(labelsView);
			}
			pack();
		}catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
}
