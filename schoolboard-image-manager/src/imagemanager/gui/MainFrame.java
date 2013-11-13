package imagemanager.gui;

import imagemanager.ImageManager;
import imagemanager.model.ImageRecord;
import imagemanager.model.LabelRecord;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MainFrame extends javax.swing.JFrame {
	private JMenuBar mainMenuBar;
	private JSplitPane secondarySplitPane;
	private JScrollPane thumbnailScrollPane;
	private JScrollPane imageLabelsScrollPane;
	private JRadioButton thirdScaleButton;
	private JRadioButton secScaleButton;
	private JRadioButton firstScaleButton;
	private ButtonGroup scaleButtonGroup;
	private JScrollPane jScrollPane4;
	private JTabbedPane collectionsTabbedPane;
	private JPanel propertiesPanel;
	private JPanel bottomPanel;
	private JPanel topPanel;
	private JScrollPane secondaryScrollPane;
	private JSplitPane mainSplitPane;
	private JMenu jMenu1;

	private ThumbnailPanel thumbnailPanel1;
	private static int DEF_THUMB_SIZE = 150;
	private MouseListener ml = new MouseListener() {

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}
	};
	private LabelList allLabelsList;
	boolean[] markedLabels;
	private ImageManager imageManager = ImageManager.getInstance();

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

				scaleButtonGroup = new ButtonGroup();
				thirdScaleButton = new JRadioButton();
				secScaleButton = new JRadioButton();
				firstScaleButton = new JRadioButton();

				scaleButtonGroup.add(firstScaleButton);
				firstScaleButton.setText("150");
				firstScaleButton.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						rescaleThumbs(150);
					}
				});
				scaleButtonGroup.add(secScaleButton);
				secScaleButton.setText("200");
				secScaleButton.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						rescaleThumbs(200);
					}
				});
				scaleButtonGroup.add(thirdScaleButton);
				thirdScaleButton.setText("250");
				thirdScaleButton.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						rescaleThumbs(250);
					}
				});

				GroupLayout bottomPanelLayout = new GroupLayout(
						(JComponent) bottomPanel);
				bottomPanelLayout.setHorizontalGroup(bottomPanelLayout
						.createParallelGroup(Alignment.LEADING).addGroup(
								Alignment.TRAILING,
								bottomPanelLayout
										.createSequentialGroup()
										.addContainerGap(649, Short.MAX_VALUE)
										.addComponent(firstScaleButton,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addComponent(secScaleButton,
												GroupLayout.PREFERRED_SIZE, 69,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addComponent(thirdScaleButton,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap()));
				bottomPanelLayout
						.setVerticalGroup(bottomPanelLayout
								.createParallelGroup(Alignment.LEADING)
								.addGroup(
										bottomPanelLayout
												.createSequentialGroup()
												.addGroup(
														bottomPanelLayout
																.createParallelGroup(
																		Alignment.LEADING)
																.addComponent(
																		thirdScaleButton,
																		GroupLayout.PREFERRED_SIZE,
																		GroupLayout.PREFERRED_SIZE,
																		GroupLayout.PREFERRED_SIZE)
																.addGroup(
																		bottomPanelLayout
																				.createParallelGroup(
																						Alignment.BASELINE)
																				.addComponent(
																						secScaleButton,
																						GroupLayout.PREFERRED_SIZE,
																						17,
																						GroupLayout.PREFERRED_SIZE)
																				.addComponent(
																						firstScaleButton,
																						0,
																						23,
																						Short.MAX_VALUE)))
												.addContainerGap()));
				bottomPanel.setLayout(bottomPanelLayout);
			}
			{
				topPanel = new JPanel();
				topPanel.setSize(770, 10);
			}
			thisLayout.setVerticalGroup(thisLayout
					.createSequentialGroup()
					.addComponent(topPanel, GroupLayout.PREFERRED_SIZE, 32,
							GroupLayout.PREFERRED_SIZE)
					.addComponent(mainSplitPane, 0, 540, Short.MAX_VALUE)
					.addComponent(bottomPanel, GroupLayout.PREFERRED_SIZE, 32,
							GroupLayout.PREFERRED_SIZE));
			thisLayout.setHorizontalGroup(thisLayout
					.createParallelGroup()
					.addComponent(topPanel, GroupLayout.Alignment.LEADING, 0,
							770, Short.MAX_VALUE)
					.addComponent(mainSplitPane, GroupLayout.Alignment.LEADING,
							0, 770, Short.MAX_VALUE)
					.addComponent(bottomPanel, GroupLayout.Alignment.LEADING,
							0, 770, Short.MAX_VALUE));
			{
				mainMenuBar = new JMenuBar();
				setJMenuBar(mainMenuBar);
				{
					jMenu1 = new JMenu();
					mainMenuBar.add(jMenu1);
					jMenu1.setText("jMenu1");
				}
			}
			pack();
			this.setSize(874, 697);
		} catch (Exception e) {
			// add your error handling code here
			e.printStackTrace();
		}
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
		Collection<ImageRecord> imageRecords = imageManager.getAllImages()
				.values();
		ThumbnailComponent[] thumbs = new ThumbnailComponent[imageRecords
				.size()];
		int i = 0;
		for (Iterator<ImageRecord> it = imageRecords.iterator(); it.hasNext();) {
			ImageRecord imageRecord = (ImageRecord) it.next();
			thumbs[i] = new ThumbnailComponent(imageRecord, DEF_THUMB_SIZE);
			thumbs[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			thumbs[i].addMouseListener(ml);
			++i;
		}

		thumbnailPanel1 = new ThumbnailPanel(thumbs);
		// thumbnailPanel1.selectThumbnail(1);
		// thumbnailPanel1.selectThumbnail(2);
		// thumbnailPanel1.selectThumbnail(3);

	}

	private void createLabelsList() {
		LabelRecord[] records = imageManager.getAllLabels().values()
				.toArray(new LabelRecord[] {});

		allLabelsList = new LabelList(records);
		allLabelsList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				LabelList source = (LabelList) e.getSource();
				// boolean[] newMarked = new boolean[markedLabels.length];
				// int[] selectedIndices = source.getSelectedIndices();
				// for (int i = 0; i < selectedIndices.length; i++) {
				// newMarked[i] = true;
				// }
				//
				// for (int i = 0; i < markedLabels.length; i++) {
				// if (markedLabels[i] == false && newMarked[i] == true) {
				// Collection<Integer> imageIds = imageManager.getImageIds();
				// } else if (markedLabels[i] == false && newMarked[i] == true)
				// {
				// // TODO usunac odznaczone miniaturki
				// }
				// }

				ArrayList<LabelRecord>[] labs = source.getLabelsToChange();
				markThumbs(labs[0]);
				unmarkThumbs(labs[1]);
				//thumbnailPanel1.doLayout();
				//thumbnailPanel1.getLayout().layoutContainer(thumbnailPanel1);
				thumbnailPanel1.refresh();
				thumbnailScrollPane.revalidate();
				// System.out.println(" marked "+labs[0]);
				// System.out.println(" unmarked "+labs[1]);

			}
		});
	}

	private void markThumbs(ArrayList<LabelRecord> marked) {
		// Integer[] m = new Integer[marked.size()];
		// int i = 0;
		// for (Iterator iterator = marked.iterator(); iterator.hasNext();) {
		// LabelRecord labelRecord = (LabelRecord) iterator.next();
		// m[i] = labelRecord.getId();
		// }
		// thumbnailPanel1.markThumbnail(3);
		//System.out.println("markThumbs call");
		for (Iterator it = marked.iterator(); it.hasNext();) {
			Integer idlabel = ((LabelRecord) it.next()).getId();
		//	System.out.println("marking label " + idlabel);
			Collection<Integer> labelImages = imageManager.getImagesLabels().get(idlabel);
			if(labelImages == null) continue;
			thumbnailPanel1.markThumbnail((Integer[]) labelImages.toArray(new Integer[]{}));
		}
	}

	private void unmarkThumbs(ArrayList<LabelRecord> unmarked) {
		//System.out.println("unmarkThumbs call");
		for (Iterator it = unmarked.iterator(); it.hasNext();) {
			Integer idlabel = ((LabelRecord) it.next()).getId();
		//	System.out.println("unmarking label " + idlabel);
			Collection<Integer> labelImages = imageManager.getImagesLabels().get(idlabel);
			if(labelImages == null) continue;
			thumbnailPanel1.unmarkThumbnail((Integer[]) labelImages.toArray(new Integer[]{}));
		}
	}

}
