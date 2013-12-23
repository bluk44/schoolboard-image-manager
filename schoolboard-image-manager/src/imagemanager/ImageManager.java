package imagemanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

import dataaccess.IODatabase;

public class ImageManager {
	
	private static Map<String, String> params = new HashMap<String, String>();
//	private static SourceEditionFrame sourceEditionFrame;
//	private static MainFrame mainFrame;
	
	private Map<Integer, AbstractImage> images;
	private Map<Integer, Label> labels;
	
	private final static Logger LOG = Logger.getLogger(ImageManager.class.getName());
	static { 
		LOG.addAppender(new ConsoleAppender(new SimpleLayout())); 
		readConfig();
	}
	
	public static void main(String[] args){
		
		
		//initMainFrame();
	}
	
	private static void readConfig(){
		
		Scanner scanner = null;
		
		File[] cfgFiles;
		try {
			File cfgDir = new File("settings");
			cfgFiles = cfgDir.listFiles();
			for (File file : cfgFiles) {
				scanner = new Scanner(new FileInputStream(file));
				String[] line;
				while(scanner.hasNextLine()){
					line = scanner.nextLine().split(" ");
					
					params.put(line[0], line[1]);
				}
				scanner.close();
			}

		} catch (FileNotFoundException e) {
			LOG.error("config file not found "+e.getMessage());
			System.exit(1);
		} finally{
			if(scanner != null) scanner.close();

		}
	}
	
	private void readImages(){
		images = new HashMap<Integer, AbstractImage>();
		
		IODatabase.getInstance();
	}
	
//	public static MainFrame getMainFrame(){
//		return mainFrame;
//	}
	
	public static String getParameter(String key){
		return params.get(key);
	}
	
//	public void initSourceEditFrame(ImageRecord sourceImage){
//		
//	//	sourceEditionFrame = 
//	}
	
//	private static void initMainFrame(){
//		mainFrame = new MainFrame();
//		mainFrame.setVisible(true);
//	}
	
//	private static void displayFrame(JFrame frame){
//
//
//		SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
//				frame.setLocationRelativeTo(null);
//				frame.setVisible(true);
//			}
//		});		
//	}
	
	
}	
