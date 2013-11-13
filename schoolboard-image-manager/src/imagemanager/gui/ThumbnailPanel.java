package imagemanager.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JPanel;
import javax.swing.Scrollable;

public class ThumbnailPanel extends JPanel implements Scrollable {
	
	private ThumbnailComponent[] thumbs;
	private Map<Integer, Integer> labelRefCount;
	//private boolean[] selectedThumbs;
	private int scCounter = 0;
	
	public ThumbnailPanel(ThumbnailComponent[] thumbs) {
		this.setLayout(new ThumbnailLayout());
		this.thumbs = thumbs;
		labelRefCount = new HashMap<Integer, Integer>();
		for (ThumbnailComponent tc : thumbs) {
			labelRefCount.put(tc.getImageId(), 0);
		}
	//	selectedThumbs = new boolean[thumbs.length];

	}

	public void markThumbnail(Integer thumbId) {
//		if (selectedThumbs[i] == false) {
//			selectedThumbs[i] = true;
//			++scCounter;
//		}
		if(labelRefCount.get(thumbId) == 0) ++scCounter;
		labelRefCount.put(thumbId, labelRefCount.get(thumbId)+1);
	}

	public void markThumbnail(Integer[] thumbIds) {
		System.out.println("marking thumbs "+thumbIds);
//		for (int i = 0; i < idxs.length; i++) {
//			if (selectedThumbs[i] == false) {
//				selectedThumbs[i] = true;
//				++scCounter;
//			}
//		}
		for (int i = 0; i < thumbIds.length; i++) {
			if(labelRefCount.get(thumbIds[i]) == 0) ++scCounter;
			labelRefCount.put(thumbIds[i], labelRefCount.get(thumbIds[i])+1);
		}
		for (Iterator it = labelRefCount.entrySet().iterator(); it.hasNext();) {
			Entry<Integer, Integer> entry = (Entry<Integer, Integer>) it.next();
			System.out.println(entry.getKey()+"labeled "+entry.getValue()+" times");
		}
	}

	public void unmarkThumbnail(Integer thumbId) {
//		if (selectedThumbs[i] == true) {
//			selectedThumbs[i] = false;
//			--scCounter;
//		}
		if(labelRefCount.get(thumbId) == 1) --scCounter;
		labelRefCount.put(thumbId, labelRefCount.get(thumbId)-1);
	}

	public void unmarkThumbnail(Integer[] thumbIds) {
//		for (int i = 0; i < idxs.length; i++) {
//			if (selectedThumbs[i] == true) {
//				selectedThumbs[i] = false;
//				--scCounter;
//			}
//		}
		for (int i = 0; i < thumbIds.length; i++) {
			if(labelRefCount.get(thumbIds[i]) == 1) --scCounter;
			labelRefCount.put(thumbIds[i], labelRefCount.get(thumbIds[i])-1);
		}
		for (Iterator it = labelRefCount.entrySet().iterator(); it.hasNext();) {
			Entry<Integer, Integer> entry = (Entry<Integer, Integer>) it.next();
			System.out.println(entry.getKey()+"labeled "+entry.getValue()+" times");
		}
	}

	public void rescaleComponents(int thumbSize) {

		for (int i = 0; i < thumbs.length; i++) {
			thumbs[i].setThumbImageArea(thumbSize);
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

			Dimension minThumbCompSize = thumbs[0].getMinimumSize();
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
			int ii = 0;
			for (ThumbnailComponent th : thumbs) {
				if(labelRefCount.get(th.getImageId()) == 0) continue;
				th.setBounds(j * (minCompWidth + addPixels), i * minCompHeight,
						minCompWidth + addPixels, minCompHeight);
				parent.add(th);
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