package test;

import ij.ImageJ;

import java.io.IOException;

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
