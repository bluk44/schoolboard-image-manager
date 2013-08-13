package test;

import java.io.IOException;

import ij.ImageJ;

public class TransformationPluginTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ImageJ ij = new ImageJ();
		ij.setVisible(true);
		try {
			System.in.read();
			TransformationPlugin tp = new TransformationPlugin();
			tp.run("");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
