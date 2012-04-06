package uk.ac.aber.dcs.cs12420.gui;

import java.awt.BorderLayout;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import uk.ac.aber.dcs.cs12420.aberpizza.data.Item;
import uk.ac.aber.dcs.cs12420.aberpizza.data.ItemPizza;
import uk.ac.aber.dcs.cs12420.aberpizza.data.OrderItem;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Till;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 4076991635240155077L;
	private Till till = null;
	private JMenuBar menubar;
	private MainPanel mainPanel;
	private boolean lock = false;
	public MainFrame(Till t){
		till = t;
		mainPanel = new MainPanel(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("AberPizza Till");
		//menu bar
		JMenu file = new JMenu("File"),admin = new JMenu("Admin"),help = new JMenu("Help");
		menubar = new JMenuBar();
		menubar.add(file);
		menubar.add(admin);
		menubar.add(help);
		this.add(menubar, BorderLayout.NORTH);
		//main panel
		this.add(mainPanel, BorderLayout.CENTER);
		String[] listData = arrayListToStringArray(till.getItems());
		String[] listBlank = {" "};
		if(till.getItems().size()<1){
			listData = listBlank;
		}
		mainPanel.setItemListData(listData);
		this.pack();
	}
	public String[] arrayListToStringArray(ArrayList<Item> i){
		if(i.size()<1){ return null; }
		String[] returnVal = new String[i.size()];
		for(int j = 0; j<i.size(); j++){
			returnVal[j] = i.get(j).toString();
		}
		return returnVal;
	}
	public void addItemToOrder(){
		Item selectedItem = getSelectedItem();
		if(selectedItem==null){ return; }
		if(selectedItem instanceof ItemPizza){
			PizzaSizeFrame p = new PizzaSizeFrame((ItemPizza)selectedItem, this);
			p.setVisible(true);
		}
		till.getCurrentOrder().addItem(selectedItem, 1);
		updateOrderList();
	}
	public void updateOrderList(){
		ArrayList<OrderItem> orderItems = till.getCurrentOrder().getOrderItems();
		String[] data = {" "};
		if(orderItems.size()>0){
			data = new String[orderItems.size()];
			for(int i = 0; i<orderItems.size(); i++){
				data[i] = orderItems.get(i).toString();
			}
		}
		mainPanel.setOrderListData(data);
	}
	private Item getSelectedItem(){
		String data = mainPanel.getSelectedItem();
		if(data==null){ return null; }
		String[] d = data.split("-");
		d[0].trim();
		d[1].trim();
		for(Item i: till.getItems()){
			if(i.getDescription().trim().equals(d[0].trim())
					&&i.getPrice().equals(new BigDecimal(d[1].trim().substring(1,d[1].trim().length())))){
				return till.getCurrentOrder().getClone(i);
			}
		}
		return null;
	}
	private OrderItem getSelectedOrderItem(){
		String data = mainPanel.getSelectedOrderItem();
		if(data==null){ return null; }
		String[] d = data.split("-");
		d[0].trim();
		d[d.length-1].trim();
		for(OrderItem i: till.getCurrentOrder().getOrderItems()){
			Item j = i.getItem();
			if(j.getDescription().trim().equals(d[0].trim())
					&&i.getOrderItemTotal().equals(new BigDecimal(d[d.length-1].trim().substring(1,d[d.length-1].trim().length())))){
				return i;
			}
		}
		return null;
	}
	public void cancel(){
		till.removeMostRecentOrder();
		updatePrices();
		updateOrderList();
	}
	public void pay(){
		if(mainPanel.getCustomerName().equals("")){
			String message = "Name isn't set";
			JOptionPane.showMessageDialog(this, message, "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(till.getCurrentOrder().getOrderItems().size()<1){
			String message = "No items on order";
			JOptionPane.showMessageDialog(this, message, "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		till.getCurrentOrder().setCustomerName(mainPanel.getCustomerName());
		till.pay();
		updatePrices();
		updateOrderList();
		mainPanel.clearCustomerName();
	}
	public void updatePrices(){
		mainPanel.setSubtotal("Subtotal: £"+till.getCurrentOrder().getSubtotal());
		mainPanel.setDiscount("Discounts: £"+till.getCurrentOrder().getDiscount());
		mainPanel.setTotal("Total: £"+till.getCurrentOrder().getTotal());
	}
	public void pizzaOnly(){
		mainPanel.setItemListData(arrayListToStringArray(till.getPizza()));
	}
	public void sideOnly(){
		mainPanel.setItemListData(arrayListToStringArray(till.getSide()));
	}
	public void drinkOnly(){
		mainPanel.setItemListData(arrayListToStringArray(till.getDrink()));
	}
	public void lock(boolean l){
		lock = l;
	}
	public boolean isLocked(){
		return lock;
	}
	public void quantityPlus(){
		OrderItem o = getSelectedOrderItem();
		if(o==null){ return; }
		o.setQuantity(o.getQuantity()+1);
		int i = mainPanel.getSelectedOrderItemIndex();
		updateOrderList();
		updatePrices();
		mainPanel.setSelectedOrderItemIndex(i);
	}
	public void quantityMinus(){
		OrderItem o = getSelectedOrderItem();
		if(o==null){ return; }
		o.setQuantity(o.getQuantity()-1);
		if(o.getQuantity()<1){
			till.getCurrentOrder().removeOrderItem(o);
		}
		int i = mainPanel.getSelectedOrderItemIndex();
		updateOrderList();
		updatePrices();
		mainPanel.setSelectedOrderItemIndex(i);
	}
}
