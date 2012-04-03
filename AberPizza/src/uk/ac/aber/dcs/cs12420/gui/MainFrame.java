package uk.ac.aber.dcs.cs12420.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import uk.ac.aber.dcs.cs12420.aberpizza.data.Item;
import uk.ac.aber.dcs.cs12420.aberpizza.data.ItemPizza;
import uk.ac.aber.dcs.cs12420.aberpizza.data.OrderItem;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Till;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 4076991635240155077L;
	private Till till = null;
	private JMenuBar menubar;
	private JPanel mainPanel;
	private JTextField customerName;
	private JList itemList, orderList;
	private MainListener mainListener;
	private JLabel disLabel, totLabel;
	public MainFrame(Till t){
		till = t;
		mainListener = new MainListener(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//menubar
		JMenu file = new JMenu("File"),admin = new JMenu("Admin"),help = new JMenu("Help");
		menubar = new JMenuBar();
		menubar.add(file);
		menubar.add(admin);
		menubar.add(help);
		this.add(menubar, BorderLayout.NORTH);
		
		//the main panel
		mainPanel = new JPanel(new GridBagLayout());
		//gridbag
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(2,2,2,2);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		
		//left panel
		JButton pizza = new JButton("Pizza"), side = new JButton("Side"), drink = new JButton("Drink"), add = new JButton("Add To Order");
		pizza.addActionListener(mainListener);
		side.addActionListener(mainListener);
		drink.addActionListener(mainListener);
		add.addActionListener(mainListener);
		//pizza button
		c.gridx = 0;
		c.gridy = 0;
		mainPanel.add(pizza,c);
		//side button
		c.gridx = 1;
		mainPanel.add(side,c);
		//drink button
		c.gridx = 2;
		mainPanel.add(drink,c);
		//le list
		c.gridy = 1;
		c.gridx = 0;
		c.gridwidth = 3;
		c.weighty = 1;
		String[] listData = arrayListToStringArray(t.getItems());
		itemList = new JList(listData);
		itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane itemScroll = new JScrollPane(itemList);
		itemList.ensureIndexIsVisible(itemList.getSelectedIndex());
		mainPanel.add(itemScroll, c);
		c.weighty = 0;
		//add to order button
		c.gridy = 2;
		c.gridwidth = 3;
		mainPanel.add(add, c);
		
		//right panel
		JLabel orderLabel = new JLabel("Order for:");
		disLabel = new JLabel("Discounts: £0.00");
		totLabel = new JLabel("Subtotal: £0.00");
		JButton payButton = new JButton("Pay"), cancelButton = new JButton("Cancel");
		payButton.addActionListener(mainListener);
		cancelButton.addActionListener(mainListener);
		//name label
		c.gridx = 3;
		c.gridy = 0;
		c.gridwidth = 1;
		mainPanel.add(orderLabel, c);
		//entry for name
		c.gridx = 4;
		customerName = new JTextField(12);
		mainPanel.add(customerName, c);
		//order list
		c.gridx = 3;
		c.gridy = 1;
		c.gridwidth = 2;
		c.weighty = 1;
		String[] data = {" "};
		orderList = new JList(data);
		orderList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane orderScroll = new JScrollPane(orderList);
		orderList.ensureIndexIsVisible(orderList.getSelectedIndex());
		mainPanel.add(orderScroll, c);
		c.weighty = 0;
		//discount label
		c.gridwidth = 1;
		c.gridx = 3;
		c.gridy = 2;
		mainPanel.add(disLabel, c);
		//total label
		c.gridy = 3;
		mainPanel.add(totLabel, c);
		//pay button
		c.gridy = 4;
		c.gridx = 3;
		mainPanel.add(payButton, c);
		//cancel button
		c.gridx = 4;
		mainPanel.add(cancelButton, c);
		
		this.add(mainPanel, BorderLayout.CENTER);
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
		if(selectedItem instanceof ItemPizza){
			PizzaSizeFrame p = new PizzaSizeFrame((ItemPizza)selectedItem, this);
			p.setVisible(true);
		}
		till.getCurrentOrder().addItem(selectedItem, 1);
		updateOrderList();
	}
	public void updateOrderList(){
		ArrayList<OrderItem> orderItems = till.getCurrentOrder().getOrderItems();
		orderList.removeAll();
		String[] data = new String[orderItems.size()];
		for(int i = 0; i<orderItems.size(); i++){
			data[i] = orderItems.get(i).toString();
		}
		orderList.setListData(data);
	}
	private Item getSelectedItem(){
		String data = (String)itemList.getSelectedValue();
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
	public void updatePrices(){
		totLabel.setText("Subtotal: £"+till.getCurrentOrder().getSubtotal());
		totLabel.setText("Discounts: £"+till.getCurrentOrder().getDiscount());
	}
}
