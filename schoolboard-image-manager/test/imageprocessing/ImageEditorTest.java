package imageprocessing;

import imagemanager.editor.ImageEditor;

public class ImageEditorTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ImageEditor imgEd = new ImageEditor(Util.readFromFile("images/blackboard/bb01.png"));
		imgEd.autodetectBoardRegion();
	}

}
