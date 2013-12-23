package imagemanager.gui;
import imagemanager.BoardImage;
import imageprocessing.ImageProcessing;
import imageprocessing.Util;
import imageprocessing.boundsdetection.BoardQuadrangle;
import imageprocessing.boundsdetection.BoundaryDetector.QuadrangleNotFoundException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class SourceEditionFrame extends javax.swing.JFrame {
	private JScrollPane boardRegionScrollPane;
	private SourceImageComponent src;
	private JPopupMenu imageOpsMenu;
	private JMenuItem autoDetect;
	private JMenuItem manualSelect;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				SourceEditionFrame inst = new SourceEditionFrame();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public SourceEditionFrame() {
		super();
		initImageComp(Util.readFromFile("images/szkola/sk01.JPG"));
		initGUI();
		addEventListeners();
	}
	
	public SourceEditionFrame(BufferedImage image){
		super();
		initImageComp(image);
		initGUI();
		addEventListeners();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.setResizable(false);
			getContentPane().setLayout(null);
			{
				boardRegionScrollPane = new JScrollPane();
				getContentPane().add(boardRegionScrollPane);
				boardRegionScrollPane.setBounds(826, 0, 300, 600);
			}
			{
				getContentPane().add(src);
			}
			{
				imageOpsMenu = new JPopupMenu();
				src.setComponentPopupMenu(imageOpsMenu);
				{
					manualSelect = new JMenuItem("Zaznacz obszar tablicy");
					imageOpsMenu.add(manualSelect);
				}
				{
					autoDetect = new JMenuItem("Automatycznie wykryj tablic\u0119");
					imageOpsMenu.add(autoDetect);
				}
			}
			pack();
			this.setSize(1128, 633);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	
	private void initImageComp(BufferedImage image){
		src = new SourceImageComponent(image);
		src.setBounds(0, 0, ImageComponent.PREF_WIDTH, ImageComponent.PREF_HEIGHT);
	}
	
	private void addEventListeners() {
		manualSelect.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				src.setSelectionMode();
			}
		
		});
		
		autoDetect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					BoardQuadrangle bq;
					bq = ImageProcessing.findBoardRegion(src.getImage());
					src.setAutodetectedBoard(bq.getPolygon());

				} catch (QuadrangleNotFoundException e1) {
					JOptionPane.showMessageDialog(src, "Board region not found");

				}
			}
		});
		
		addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER && src.isBoardSelected()){
					BoardImage extracted = ImageProcessing.
							extractBoardRegion(src.getImage(), src.getDistorted(), src.getFixed());
					src.unsetSelectionMode();
					BoardImageFrame frame = new BoardImageFrame(extracted);	
					frame.setVisible(true);
					
					src.repaint();
				} else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
					src.unsetSelectionMode();
				}			
			}
		});
	}
	
}
