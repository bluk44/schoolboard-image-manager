package imagemanager.gui;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import dataaccess.IOFileSystem;

public class FileChooserTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("frame");
		JFileChooser fc = new JFileChooser("images");
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int value = fc.showOpenDialog(frame);
		if(value == JFileChooser.APPROVE_OPTION){
			File chosen = fc.getSelectedFile();
			IOFileSystem.getInstance().readImage(chosen);
			
//			System.out.println(chosen);
//			File[] list = chosen.listFiles();
//			System.out.println();
//			for (File file : list) {
//				System.out.println(file);
//			}
			
			
			
//			fileChooser.setCurrentDirectory(fileChooser.getCurrentDirectory());
//
//			File file = fileChooser.getSelectedFile();
//			System.out.println(file.getName());
//			ioFS.importImages(file);
			
		} else {
			
		}
		
	}

}
