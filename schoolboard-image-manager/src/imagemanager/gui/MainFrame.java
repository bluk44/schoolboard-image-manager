package imagemanager.gui;

import imagemanager.ImageManager;
import imagemanager.Label;
import imagemanager.model.ImageRecord;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.jruby.RubyProcess.Sys;

import dataaccess.IODatabase;
import dataaccess.IOFileSystem;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class MainFrame extends javax.swing.JFrame {
	
	private IODatabase ioDB = IODatabase.getInstance();
	private IOFileSystem ioFS = IOFileSystem.getInstance();

	private JScrollPane thumbnailScrollPane;
	private JScrollPane imageLabelsScrollPane;
	private JScrollPane jScrollPane4;
	private JScrollPane secondaryScrollPane;
	private JSplitPane mainSplitPane;
	private JSplitPane secondarySplitPane;
	private JTabbedPane collectionsTabbedPane;

	private JPanel propertiesPanel;
	private JPanel bottomPanel;
	private JPanel topPanel;
	
	private JMenuBar mainMenuBar;
	private JMenu helpMenu;
	private JMenu settingsMenu;	
	private JMenu fileMenu;
	private JMenuItem deleteMenuItem;
	private JMenuItem importMenuItem;

	private ThumbnailPanel thumbnailPanel1;
	
	private JFileChooser fileChooser;
			
	private LabelList allLabelsList;
	int[] refCount;
	
	private final static Logger LOG = Logger.getLogger(MainFrame.class.getName());
	static { LOG.addAppender(new ConsoleAppender(new SimpleLayout())); }
	
	/**
	 * Auto-generated main method to display this JFrame
	 */
	public static void main(String[] args) {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainFrame inst = new MainFrame();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}

	public MainFrame() {
		super();
		createThumbPanel();
		createLabelsList();
		initGUI();
	}

	public boolean isPanelDB() {
		return thumbnailPanel1.isDoubleBuffered();
	}

	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			GroupLayout thisLayout = new GroupLayout(
					(JComponent) getContentPane());
			getContentPane().setLayout(thisLayout);
			this.setTitle("Image manager");
			getContentPane().setBackground(new java.awt.Color(127, 127, 127));
			this.setPreferredSize(new java.awt.Dimension(874, 697));
			this.setMinimumSize(new java.awt.Dimension(600, 300));
			{
				mainSplitPane = new JSplitPane();
				mainSplitPane.setResizeWeight(1.0);
				mainSplitPane.setDividerSize(5);
				{
					secondaryScrollPane = new JScrollPane();
					mainSplitPane.add(secondaryScrollPane, JSplitPane.RIGHT);
					secondaryScrollPane.setMinimumSize(new java.awt.Dimension(
							150, 1));
					{
						secondarySplitPane = new JSplitPane();
						secondaryScrollPane.setViewportView(secondarySplitPane);
						secondarySplitPane
								.setOrientation(JSplitPane.VERTICAL_SPLIT);
						secondarySplitPane.setResizeWeight(1.0);
						secondarySplitPane.setDividerSize(5);
						{
							propertiesPanel = new JPanel();
							secondarySplitPane.add(propertiesPanel,
									JSplitPane.RIGHT);
							propertiesPanel
									.setMinimumSize(new java.awt.Dimension(150,
											150));
						}
						
						{
							collectionsTabbedPane = new JTabbedPane();
							secondarySplitPane.add(collectionsTabbedPane,
									JSplitPane.LEFT);
							collectionsTabbedPane
									.setMinimumSize(new java.awt.Dimension(150,
											400));
							{
								imageLabelsScrollPane = new JScrollPane();
								{
									imageLabelsScrollPane
											.setViewportView(allLabelsList);
								}
								collectionsTabbedPane.addTab(
										"imageLabelsScrollPane", null,
										imageLabelsScrollPane, null);
							}
							{
								jScrollPane4 = new JScrollPane();
								collectionsTabbedPane.addTab("jScrollPane4",
										null, jScrollPane4, null);
							}
						}
					}
				}
				{
					thumbnailScrollPane = new JScrollPane();
					mainSplitPane.add(thumbnailScrollPane, JSplitPane.LEFT);
					thumbnailScrollPane.setMinimumSize(new java.awt.Dimension(
							650, 200));
					thumbnailScrollPane.setSize(650, 631);
					{
						thumbnailScrollPane.setViewportView(thumbnailPanel1);
						thumbnailPanel1
								.setPreferredSize(new java.awt.Dimension(704,
										573));
					}
				}
				mainSplitPane.setDividerLocation(thumbnailScrollPane
						.getMinimumSize().width);
				secondarySplitPane.setDividerLocation(collectionsTabbedPane
						.getMinimumSize().height);
			}
			{
				bottomPanel = new JPanel();
				bottomPanel.setLayout(null);
			}
			{
				topPanel = new JPanel();
				topPanel.setSize(770, 10);
			}
			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
				.addComponent(topPanel, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
				.addComponent(mainSplitPane, GroupLayout.PREFERRED_SIZE, 569, GroupLayout.PREFERRED_SIZE)
				.addComponent(bottomPanel, 0, 41, Short.MAX_VALUE));
			thisLayout.setHorizontalGroup(thisLayout.createParallelGroup()
				.addComponent(topPanel, GroupLayout.Alignment.LEADING, 0, 864, Short.MAX_VALUE)
				.addComponent(mainSplitPane, GroupLayout.Alignment.LEADING, 0, 864, Short.MAX_VALUE)
				.addComponent(bottomPanel, GroupLayout.Alignment.LEADING, 0, 864, Short.MAX_VALUE));
			
			// MENU GLOWNE 
			{
				mainMenuBar = new JMenuBar();
				setJMenuBar(mainMenuBar);
				{
					fileMenu = new JMenu();
					mainMenuBar.add(fileMenu);
					fileMenu.setText("Plik");
					{
						importMenuItem = new JMenuItem();
						fileMenu.add(importMenuItem);
						importMenuItem.setText("Zaimportuj zdj\u0119cia");
						
						importMenuItem.addActionListener(mainMenuActionListener);
					}
					{
						deleteMenuItem = new JMenuItem();
						fileMenu.add(deleteMenuItem);
						deleteMenuItem.setText("Usu\u0144 zdj\u0119cia");
					}
				}
				{
					settingsMenu = new JMenu();
					mainMenuBar.add(settingsMenu);
					settingsMenu.setText("Ustawienia");
				}
				{
					helpMenu = new JMenu();
					mainMenuBar.add(helpMenu);
					helpMenu.setText("Pomoc");
				}
			}
			
			// FILE CHOOOSER
			{
				String startPath = ImageManager.getParameter("startPath");
				if(startPath != null)
					fileChooser = new JFileChooser(startPath);
				else 
					fileChooser = new JFileChooser();
				
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			}
			pack();
			this.setSize(874, 697);
		} catch (Exception e) { e.printStackTrace(); }
	}

	private void rescaleThumbs(int thumbSize) {
//		thumbnailPanel1.rescaleComponents(thumbSize);
		Component[] comps = thumbnailScrollPane.getViewport().getComponents();
		((Container)comps[0]).removeAll();
//		for (int i = 0; i < comps.length; i++) {
//			System.out.println(comps[i].getClass());
//		}
		
		thumbnailScrollPane.revalidate();
	}
	
	private void createThumbPanel() {
		try{
			Collection<ImageRecord> imageRecords = ioDB.importThumbnails();
			thumbnailPanel1 = new ThumbnailPanel(imageRecords);

		} catch(SQLException e){
			LOG.error("unable to create thumbnail panel "+e.getMessage());
			System.exit(1);
		}

	}

	private void createLabelsList() {
		try {
			 LinkedHashMap<Integer, Label> labels;
			labels = ioDB.importAllLabels();
		
			allLabelsList = new LabelList(labels.values());
			allLabelsList.addListSelectionListener(new ListSelectionListener() {

				@Override
				public void valueChanged(ListSelectionEvent e) {
					LabelList source = (LabelList) e.getSource();

					ArrayList<Label>[] labs = source.getLabelsToChange();
					markThumbs(labs[0]);
					unmarkThumbs(labs[1]);
					
					thumbnailPanel1.refresh();
					thumbnailScrollPane.revalidate();

				}
			});
		} catch (SQLException e) {
			LOG.error("unable to fetch labels form database "+e.getMessage());
		}
	}

	private void markThumbs(ArrayList<Label> marked) {
		for (Label labelRecord : marked) {
			try {
				Collection<Integer> imageIds = ioDB.getLabeledImagesIds(labelRecord.getId());
				for (Integer id : imageIds) ++thumbnailPanel1.getThumbnail(id).refCounter;		

			} catch (SQLException e) {
				LOG.error("label not found in databse: "+labelRecord.getTitle());
			}
			
		}

	}

	private void unmarkThumbs(ArrayList<Label> unmarked) {
		for (Label labelRecord : unmarked) {
			try {
				Collection<Integer> imageIds = ioDB.getLabeledImagesIds(labelRecord.getId());
				for (Integer id : imageIds) ++thumbnailPanel1.getThumbnail(id).refCounter;			

			} catch (SQLException e) {
				LOG.error("label not found in databse: "+labelRecord.getTitle());
			}
			
		}
	}
		
	private ActionListener mainMenuActionListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int value = fileChooser.showOpenDialog(ImageManager.getMainFrame());
			
			if(value == JFileChooser.APPROVE_OPTION){
				
				fileChooser.setCurrentDirectory(fileChooser.getCurrentDirectory());

				File file = fileChooser.getSelectedFile();
				System.out.println(file.getName());
				ioFS.readImage(file);
				
			} else {
				
			}
		}
	};
}
