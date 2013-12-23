package imagemanager.gui;

import java.awt.Dimension;

import ij.ImagePlus;
import ij.gui.ImageWindow;

public class MyImageWindow extends ImageWindow {

	public MyImageWindow(ImagePlus imp) {
		super(imp);
		
		//ic.setPreferredSize(new Dimension(100, 100));
		//System.out.println(ic.getBounds());

	}

}
