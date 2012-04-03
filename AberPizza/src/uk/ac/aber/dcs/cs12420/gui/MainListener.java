package uk.ac.aber.dcs.cs12420.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainListener implements ActionListener{
	private MainFrame mainFrame;
	public MainListener(MainFrame f){
		mainFrame = f;
	}
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		if("Add To Order".equals(actionCommand)){
			mainFrame.addItemToOrder();
			mainFrame.updatePrices();
		}
	}
}
