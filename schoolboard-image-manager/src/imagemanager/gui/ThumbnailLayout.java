package imagemanager.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

public class ThumbnailLayout implements LayoutManager {

	private Dimension minimumLayoutSize;
	
	ThumbnailLayout(int width, int height) {
		minimumLayoutSize = new Dimension(width, height);
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	@Override
	public void removeLayoutComponent(Component comp) {
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return parent.getPreferredSize();
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return minimumLayoutSize;
	}

	private void fitComponents(Container parent) {
		
		if(parent.getComponentCount() == 0){
			return;
		}
		
		int width = parent.getWidth(), height = parent.getHeight();
		int compsInRow = 10, compsInCol = 10;
		int compWidth = width / compsInRow;
		int compHeight = height / compsInCol;
		
		
		int totalWidth = compWidth * compsInRow;
		int totalHeight = (parent.getHeight() < compHeight * compsInCol) ? compHeight
				* compsInCol
				: parent.getHeight();

		parent.setSize(totalWidth, totalHeight);

		Component[] comps = parent.getComponents();
		for (int i = 0; i < compsInCol; i++) {
			for (int j = 0; j < compsInRow; j++) {
				comps[i * compsInRow + j].setBounds(j * compWidth, i
						* compHeight, compWidth, compHeight);
			}
		}
	}

	@Override
	public void layoutContainer(Container parent) {
		fitComponents(parent);
	}

}
