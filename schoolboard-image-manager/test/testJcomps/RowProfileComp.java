package testJcomps;

import imageprocessing.Util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JFrame;

import test.Test;


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
public class RowProfileComp extends JComponent implements MouseListener{
	
	private BufferedImage image;
	private RowComp rowComp = new RowComp();
	private JFrame rowCompFrame = new JFrame();
	String title;
	int selectedRow;
	int band;
	
	public RowProfileComp(BufferedImage image, String title, int band){
		this.image = image;
		this.title = title;
		this.band = band;
		rowComp.setPreferredSize(new Dimension(image.getWidth(), 256));
		setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
		addMouseListener(this);
		rowCompFrame.add(rowComp);
		rowCompFrame.pack();
	}
	
	public void selectRow(int row){
		selectedRow = row;
		rowCompFrame.setTitle(title+" "+row+" band: "+band);
		calculateHistogram();
		repaint();
		rowCompFrame.repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.RED);
		g.drawImage(image, 0, 0, null);
		g.drawLine(0, selectedRow, getWidth(), selectedRow);
	}
	
	private void calculateHistogram(){
		int[] row = new int[image.getWidth()];
		for(int i = 0; i < image.getWidth(); i++){
			row[i] = image.getRaster().getSample(i, selectedRow, band);
		}
		rowComp.rowHistogram = row;
		Rectangle r = this.getTopLevelAncestor().getBounds();
		rowCompFrame.setBounds(r.x, r.y + r.height, rowCompFrame.getWidth(), rowCompFrame.getHeight());
		rowCompFrame.setVisible(true);
		rowComp.repaint();
	}
	
	private class RowComp extends JComponent{
		int[] rowHistogram;
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.ORANGE);
			for(int i = 0; i < rowHistogram.length; i++){
				g.drawLine(i, getHeight(), i, getHeight() - rowHistogram[i]);
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		selectRow(e.getPoint().y);
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args){
		String inputPath = "images/bg_remove/input.png";
		BufferedImage img = Util.readFromFile(inputPath);
		RowProfileComp rpc = new RowProfileComp(img, "test green", 1);
		Test.showComponent(rpc, "input green");
		RowProfileComp rpc2 = new RowProfileComp(img, "test red", 0);
		Test.showComponent(rpc2, "input red");
	}

}