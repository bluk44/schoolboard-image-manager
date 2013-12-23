package imagemanager.gui;

import imageprocessing.Util;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class ButtonsComponent extends Container {
	
	JToggleButton magButton = new JToggleButton(new ImageIcon(Util.readFromFile("resources/magnify.png")));
	
	ImageIcon autoDetectIcon = new ImageIcon(Util.readFromFile("resources/rectangle_auto.png"));
	ImageIcon manualSelectIcon = new ImageIcon(Util.readFromFile("resources/rectangle_manual.png"));
	JToggleButton findBoardButton = new JToggleButton(autoDetectIcon);

	JToggleButton handButton = new JToggleButton(new ImageIcon(Util.readFromFile("resources/hand.png")));
	
	
	public ButtonsComponent(){
		setLayout(new GridLayout(1, 4));
		setPreferredSize(new Dimension(200, 60));
			
		JPopupMenu jpm = new JPopupMenu();
		JMenuItem autoDetectOption = new JMenuItem("automatcznie wykryj talice");
		JMenuItem manualSelectOption = new JMenuItem("zaznacz obszar tablicy");
		
		autoDetectOption.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(e.getActionCommand());
				findBoardButton.setIcon(autoDetectIcon);
			}
		});
		
		manualSelectOption.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(e.getActionCommand());
				findBoardButton.setIcon(manualSelectIcon);
			}
		});

		jpm.add(autoDetectOption);
		jpm.add(manualSelectOption);
		findBoardButton.setComponentPopupMenu(jpm);
		
		magButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!magButton.isSelected()) magButton.setSelected(true);
				handButton.setSelected(false);
				findBoardButton.setSelected(false);
			}
		});

		handButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!handButton.isSelected()) handButton.setSelected(true);
				magButton.setSelected(false);
				findBoardButton.setSelected(false);
			}
		});
		
		findBoardButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!findBoardButton.isSelected()) findBoardButton.setSelected(true);
				magButton.setSelected(false);
				handButton.setSelected(false);
			}
		});
		
		add(magButton);
		add(handButton);
		add(findBoardButton);
		
		
	}
	
	public static void main(String[] args){
		JFrame jf = new JFrame();
		jf.add(new ButtonsComponent());
		jf.pack();
		jf.setVisible(true);
	}

	public JToggleButton getMagButton() {
		return magButton;
	}

	public JToggleButton getFindBoardButton() {
		return findBoardButton;
	}

	public JToggleButton getHandButton() {
		return handButton;
	}
		
}
