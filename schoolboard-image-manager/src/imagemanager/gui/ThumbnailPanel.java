package imagemanager.gui;

import imagemanager.ImageManager;
import imagemanager.model.ImageRecord;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JPanel;
import javax.swing.Scrollable;

public class ThumbnailPanel extends JPanel implements Scrollable {
	
	private static Integer thumbSize = Integer.parseInt(ImageManager.getParameter("thumbnailSize"));
	
	private Map<Integer, ThumbnailComponent> thumbs;
	private int scCounter = 0;
	
	public ThumbnailPanel(Collection<ImageRecord> images) {
		thumbs = new HashMap<Integer, ThumbnailComponent>();
		
		setLayout(new ThumbnailLayout());
		
		for (ImageRecord imageRecord : images) {
			thumbs.put(imageRecord.getId(), new ThumbnailComponent(imageRecord, thumbSize));
		}
	}
	
	public ThumbnailComponent getThumbnail(int thumbId){
		return thumbs.get(thumbId);
	}
	
	public void rescaleComponents(int thumbSize) {
		
		ThumbnailPanel.thumbSize = thumbSize;
		for (ThumbnailComponent thumbComp : thumbs.values()) {
			thumbComp.setThumbImageArea(thumbSize);
		}
		refresh();
	}
	
	public void refresh(){
		((ThumbnailLayout) getLayout()).layoutContainer(this);		
	}
	class ThumbnailLayout implements LayoutManager {

		@Override
		public void addLayoutComponent(String name, Component comp) {
		}

		@Override
		public void removeLayoutComponent(Component comp) {
		}

		@Override
		public Dimension preferredLayoutSize(Container parent) {
			return null;
		}

		@Override
		public Dimension minimumLayoutSize(Container parent) {
			return null;
		}

		@Override
		public void layoutContainer(Container parent) {
//			System.out.println("[thumbnailPanel] layoutContainer() called");
//			System.out.println("[thumbnailPanel] parent's prefered size "
//					+ parent.getPreferredSize());
			fitComponents(parent);
		}

		private void fitComponents(Container parent) {
			System.out.println("fitComponents called scCounter "+scCounter);
			
			parent.removeAll();
			parent.repaint();
			if (scCounter == 0) {
				return;
			}
			int compsCount = scCounter;
			double panelWidth = parent.getWidth(), panelHeight = parent
					.getHeight();

			Dimension minThumbCompSize = new Dimension(thumbSize, thumbSize);
			if (panelWidth < minThumbCompSize.getWidth())
				panelWidth = minThumbCompSize.getWidth();

			// obliczyc ile przyciskow zmiesci sie w rzedzie
			int nCompsInRow = (int) (panelWidth / minThumbCompSize.getWidth());
			nCompsInRow = (nCompsInRow > scCounter) ? scCounter : nCompsInRow;

			// ile zostanie miejsca w szerz
			int leftPixels = (int) (panelWidth - nCompsInRow
					* minThumbCompSize.getWidth());
			int addPixels = leftPixels / nCompsInRow;

			// obliczyc ile komponentow w kolumnie i wysokosc panelu
			int nCompsInCol = compsCount / nCompsInRow;
			nCompsInCol += ((compsCount % nCompsInRow) > 0) ? 1 : 0;

			panelHeight = nCompsInCol * minThumbCompSize.getHeight();

			// zapisuje nowy rozmiar panelu
			parent.setPreferredSize(new Dimension((int) panelWidth,
					(int) panelHeight));

			int minCompWidth = (int) minThumbCompSize.getWidth();
			int minCompHeight = (int) minThumbCompSize.getHeight();

			// wyznaczyc polozenie komponentow
			
			int i = 0, j = 0;
			for (ThumbnailComponent tc : thumbs.values()) {
				if(tc.refCounter == 0) continue;
				
				tc.setBounds(j * (minCompWidth + addPixels), i * minCompHeight,
						minCompWidth + addPixels, minCompHeight);
				parent.add(tc);
				++j;
				if (j == nCompsInRow) {
					j = 0;
					++i;
				}
			}			
			
		}

	}

	@Override
	public Dimension getPreferredScrollableViewportSize() {
		return getPreferredSize();
	}

	@Override
	public int getScrollableUnitIncrement(Rectangle visibleRect,
			int orientation, int direction) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getScrollableBlockIncrement(Rectangle visibleRect,
			int orientation, int direction) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean getScrollableTracksViewportHeight() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setPreferredSize(Dimension preferredSize) {
		super.setPreferredSize(preferredSize);
//		System.out.println("[thumbnailPanel] preferredSize changed: "
//				+ getPreferredSize().width + " " + getPreferredSize().height);
	}
	
	
}