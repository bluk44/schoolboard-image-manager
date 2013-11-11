package imagemanager.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.Scrollable;

public class ThumbnailPanel extends JPanel implements Scrollable{
	
	protected int visibleRowCount;
	
	public ThumbnailPanel(){
		this.setLayout(new ThumbnailLayout());
	}
	
	public void rescaleComponents(int thumbSize){
		Component[] comps = getComponents();
		for(Component c : comps){
			((ThumbnailComponent)c).setThumbImageArea(thumbSize);
		}
		((ThumbnailLayout)getLayout()).layoutContainer(this);
	}
	class ThumbnailLayout implements LayoutManager{
		
		@Override
		public void addLayoutComponent(String name, Component comp) {}

		@Override
		public void removeLayoutComponent(Component comp) {}

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
			System.out.println("[thumbnailPanel] layoutContainer() called");
			System.out.println("[thumbnailPanel] parent's prefered size "+parent.getPreferredSize());
			fitComponents(parent);
		}
		
		private void fitComponents(Container parent){
			if(parent.getComponentCount() == 0){
				return;
			}
			
			int compsCount = parent.getComponentCount();
			double panelWidth = parent.getWidth(), panelHeight = parent.getHeight();
			
			Dimension minThumbCompSize = parent.getComponent(0).getMinimumSize();
			if(panelWidth < minThumbCompSize.getWidth()) panelWidth = minThumbCompSize.getWidth();
			
			// obliczyc ile przyciskow zmiesci sie w rzedzie
			int nCompsInRow = (int) (panelWidth / minThumbCompSize.getWidth());
			nCompsInRow = (nCompsInRow > parent.getComponentCount()) ? parent.getComponentCount() : nCompsInRow;
			
			// ile zostanie miejsca w szerz
			int leftPixels = (int) (panelWidth - nCompsInRow * minThumbCompSize.getWidth());
			int addPixels = leftPixels / nCompsInRow;
			
			// obliczyc ile komponentow w kolumnie i wysokosc panelu
			int nCompsInCol = compsCount / nCompsInRow;
			nCompsInCol += ((compsCount % nCompsInRow) > 0) ? 1 : 0;			
			
			panelHeight = nCompsInCol * minThumbCompSize.getHeight();
			
			// zapisuje nowy rozmiar panelu
			parent.setPreferredSize(new Dimension((int)panelWidth, (int)panelHeight));
			
			int minCompWidth = (int) minThumbCompSize.getWidth();
			int minCompHeight = (int) minThumbCompSize.getHeight();
			
			// wyznaczyc polozenie komponentow
			Component[] comps = parent.getComponents();
			int i = 0, j = 0;
			for (Component c : comps) {
				c.setBounds(j * (minCompWidth+addPixels), i * minCompHeight, minCompWidth+addPixels, minCompHeight);
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
		System.out.println("[thumbnailPanel] preferredSize changed: "+getPreferredSize().width+" "+getPreferredSize().height);
	}
}