package test;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class Util {

	public static void showComponent(JComponent comp, String title){
		JScrollPane sp = new JScrollPane();
		sp.setViewportView(comp);
		JFrame frame = new JFrame(title);
		frame.add(sp);
		frame.pack();
		frame.setVisible(true);
	}
}
