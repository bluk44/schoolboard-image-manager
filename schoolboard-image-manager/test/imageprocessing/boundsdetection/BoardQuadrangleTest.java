package imageprocessing.boundsdetection;

import imagemanager.editor.ImageEditor;
import imageprocessing.Util;

import java.awt.image.BufferedImage;

public class BoardQuadrangleTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BufferedImage img = Util.readFromFile("images/blackboard/bb01.png");
		ImageEditor imEd = new ImageEditor(img);
		imEd.autodetectBoardRegion();
	}

}
