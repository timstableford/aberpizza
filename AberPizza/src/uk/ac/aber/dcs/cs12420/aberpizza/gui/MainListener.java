package uk.ac.aber.dcs.cs12420.aberpizza.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import uk.ac.aber.dcs.cs12420.aberpizza.data.Item;
import uk.ac.aber.dcs.cs12420.aberpizza.data.ItemPizza;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Till;
/**
 * Listens to button presses in the main panel
 * @author tim
 *
 */
public class MainListener implements ActionListener{
	private Till till;
	private MainPanel mainPanel;
	public MainListener(Till t, MainPanel mP){
		till = t;
		mainPanel = mP;
	}
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		if("Add To Order".equals(actionCommand)){
			Item selectedItem = mainPanel.getSelectedItem();
			if(selectedItem==null){ return; }
			if(selectedItem instanceof ItemPizza){
				PizzaSizeFrame p = new PizzaSizeFrame((ItemPizza)selectedItem, mainPanel, till.getCurrentOrder());
				p.setVisible(true);
			}else{
				till.getCurrentOrder().addItem(selectedItem, 1);
				mainPanel.updateOrderList();
			}
		}else if("Pizza".equals(actionCommand)){
			mainPanel.setItemListData(till.itemListToStringArray(till.getPizza()));
		}else if("Drink".equals(actionCommand)){
			mainPanel.setItemListData(till.itemListToStringArray(till.getDrink()));
		}else if("Side".equals(actionCommand)){
			mainPanel.setItemListData(till.itemListToStringArray(till.getSide()));
		}else if("Cancel".equals(actionCommand)){
			till.removeMostRecentOrder();
			mainPanel.updateOrderList();
		}else if("Pay".equals(actionCommand)){
			if(mainPanel.getCustomerName().equals("")){
				String message = "Name isn't set";
				JOptionPane.showMessageDialog(mainPanel, message, "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(till.getCurrentOrder().getOrderItems().size()<1){
				String message = "No items on order";
				JOptionPane.showMessageDialog(mainPanel, message, "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			till.getCurrentOrder().setCustomerName(mainPanel.getCustomerName());
			till.pay();
			mainPanel.updateOrderList();
			mainPanel.clearCustomerName();
		}else if("Quantity+".equals(actionCommand)){
			mainPanel.quantityChange(1);
		}else if("Quantity-".equals(actionCommand)){
			mainPanel.quantityChange(-1);
		}
	}
}
