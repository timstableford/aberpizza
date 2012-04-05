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
		if(mainFrame.isLocked()){ return; }
		if("Add To Order".equals(actionCommand)){
			mainFrame.addItemToOrder();
			mainFrame.updatePrices();
		}else if("Pizza".equals(actionCommand)){
			mainFrame.pizzaOnly();
		}else if("Drink".equals(actionCommand)){
			mainFrame.drinkOnly();
		}else if("Side".equals(actionCommand)){
			mainFrame.sideOnly();
		}else if("Cancel".equals(actionCommand)){
			mainFrame.cancel();
		}else if("Pay".equals(actionCommand)){
			mainFrame.pay();
		}
	}
}
