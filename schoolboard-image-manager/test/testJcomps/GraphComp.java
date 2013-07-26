package testJcomps;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class GraphComp extends JComponent {

	
	static final int YMax = 250, XMax = 256;
	static final int XZeroXPos = 60;
	static final int YZeroYPos = 20;
	static final int pixPerUnit = 2;
	
	static final int XScaleLen = XMax * pixPerUnit;
	static final int YScaleLen = YMax *pixPerUnit;
	static final int XAxisLenTotal = XZeroXPos + XScaleLen + 5;
	static final int YAxisLenTotal = YZeroYPos + YScaleLen + 5;

	// x axis position
	static final int XAxisX1 = 0, XAxisY = YAxisLenTotal - YZeroYPos, XAxisX2 = XAxisLenTotal;
	
	// y axis position
	static final int YAxisX = 50, YAxisY1 = 0, YAxisY2 = YAxisLenTotal;
	
	private double YAxisStep = 1;
	private int[] pixelCount;
	private int treshold = -1;
	{
		this.setPreferredSize(new Dimension(XAxisLenTotal, YAxisLenTotal));
	}
	public GraphComp() {
	}
	public GraphComp(int[] pixelCount) throws IllegalArgumentException{
		if (pixelCount.length != 256) {
			throw new IllegalArgumentException(
					"pixelCount must be of length 256");
		}
		countStep(pixelCount);
		this.pixelCount = pixelCount;
	}
	@Override
	protected void paintComponent(Graphics g) {

		g.setFont(new Font("Dialog", Font.PLAIN, 10));

		// y axis
		g.drawLine(YAxisX, YAxisY1, YAxisX, YAxisY2);
		// kreski
		NumberFormat formatter = new DecimalFormat("0.##E0");
		for (int i = 0; i <= YMax; i++) {
			if (i % 10 == 0) {
				g.drawLine(YAxisX - 5, XAxisY - i * pixPerUnit, YAxisX, XAxisY - i
						* pixPerUnit);
				g.drawString(formatter.format(i * YAxisStep), YAxisX - 50,
						XAxisY - i * pixPerUnit + 3);
			} else {
				g.drawLine(YAxisX - 2, XAxisY - i * pixPerUnit, YAxisX, XAxisY - i
						* pixPerUnit);
			}
		}
		// x axis
		g.drawLine(XAxisX1, XAxisY, XAxisX2, XAxisY);
		// kreski
		for (int i = 0; i <= XMax; i++) {
			if (i % 10 == 0) {
				g.drawLine(XZeroXPos + i * pixPerUnit, XAxisY, XZeroXPos + i * pixPerUnit,
						XAxisY + 5);
				g.drawString("" + i, XZeroXPos + i * pixPerUnit - 4, XAxisY + 16);

			} else {
				g.drawLine(XZeroXPos + i * pixPerUnit, XAxisY, XZeroXPos + i * pixPerUnit,
						XAxisY + 2);
			}
		}
		// data
		if (pixelCount != null) {
			for (int i = 0; i < pixelCount.length; i++) {
				int len = (int)Math.floor((double)pixelCount[i]/YAxisStep);
				g.setColor(Color.BLACK);
				g.drawLine(XZeroXPos + i * pixPerUnit, XAxisY-len*pixPerUnit, XZeroXPos + i * pixPerUnit, XAxisY);
			}
		}
		// treshold
		if(treshold != -1){
			g.setColor(Color.RED);
			g.drawLine(XZeroXPos + treshold * pixPerUnit+1, 0, XZeroXPos + treshold * pixPerUnit+1, XAxisY);			
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GraphComp comp1 = new GraphComp();
		JScrollPane scrollPane = new JScrollPane(comp1);
		JFrame frame = new JFrame("graph test");
		frame.add(scrollPane);
		frame.pack();
		frame.setVisible(true);
	}

	public int[] getPixelCount() {
		return pixelCount;
	}

	public void setPixelCount(int[] pixelCount) throws IllegalArgumentException {
		if (pixelCount.length != 256) {
			throw new IllegalArgumentException(
					"pixelCount must be of length 256");
		}
		this.pixelCount = pixelCount;
	}

	private void countStep(int[] pixelCount) {
		int max = 0;
		for (int i = 0; i < pixelCount.length; i++) {
			if (pixelCount[i] > max) {
				max = pixelCount[i];
			}
		}
		YAxisStep = (double)max / YMax;

	}
	public int getTreshold() {
		return treshold;
	}
	public void setTreshold(int treshold) {
		this.treshold = treshold;
	}

}
