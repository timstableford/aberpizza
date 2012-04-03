package uk.ac.aber.dcs.cs12420.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;

import uk.ac.aber.dcs.cs12420.aberpizza.data.Item;
import uk.ac.aber.dcs.cs12420.aberpizza.data.OrderItem;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Till;

public class MainFrame extends JFrame {
	private Till till = null;
	private JMenuBar menubar;
	private JPanel leftPanel, rightPanel;
	private JTextField customerName;
	private JList itemList, orderList;
	private MainListener mainListener;
	public MainFrame(Till t){
		till = t;
		mainListener = new MainListener(this);
		JMenu file = new JMenu("File"),admin = new JMenu("Admin"),help = new JMenu("Help");
		menubar = new JMenuBar();
		menubar.add(file);
		menubar.add(admin);
		menubar.add(help);
		this.add(menubar, BorderLayout.NORTH);
		leftPanel = new JPanel(new GridBagLayout());
		leftPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		JPanel northPanel = new JPanel();
		this.add(northPanel, BorderLayout.WEST);
		northPanel.add(leftPanel, BorderLayout.NORTH);
		this.setSize(new Dimension(800,600));
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(2,2,2,2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//left panel
		JButton pizza = new JButton("Pizza"), side = new JButton("Side"), drink = new JButton("Drink"), add = new JButton("Add To Order");
		add.addActionListener(mainListener);
		String[] listData = arrayListToStringArray(t.getItems());
		itemList = new JList(listData);
		c.gridx = 0;
		c.gridy = 0;
		leftPanel.add(pizza,c);
		c.gridx = 1;
		leftPanel.add(side,c);
		c.gridx = 2;
		leftPanel.add(drink,c);
		c.gridy = 1;
		c.gridx = 0;
		c.gridwidth = 3;
		c.fill = GridBagConstraints.BOTH;
		leftPanel.add(itemList, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 2;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.CENTER;
		leftPanel.add(add, c);
		//right panel
		rightPanel = new JPanel(new GridBagLayout());
		rightPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		JLabel orderLabel = new JLabel("Order for:"), disLabel = new JLabel("Discounts: £0.00"), totLabel = new JLabel("SubTotal: £0.00");
		JButton payButton = new JButton("Pay"), cancelButton = new JButton("Cancel");
		customerName = new JTextField(12);
		GridBagConstraints d = new GridBagConstraints();
		d.insets = new Insets(2,2,2,2);
		d.gridx = 0;
		d.gridy = 0;
		String[] data = {" "};
		orderList = new JList(data);
		rightPanel.add(orderLabel, d);
		d.gridx = 1;
		rightPanel.add(customerName, d);
		d.gridx = 0;
		d.gridy = 1;
		d.gridwidth = 2;
		d.fill = GridBagConstraints.BOTH;
		rightPanel.add(orderList, d);
		d.gridy = 2;
		rightPanel.add(disLabel, d);
		d.gridy = 3;
		rightPanel.add(totLabel, d);
		d.gridy = 4;
		d.gridx = 0;
		d.gridwidth = 1;
		rightPanel.add(payButton, d);
		d.gridx = 1;
		rightPanel.add(cancelButton, d);
		northPanel.add(rightPanel,BorderLayout.WEST);
		
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
		till.getCurrentOrder().addItem(getSelectedItem(), 1);
		updateOrderList();
	}
	private void updateOrderList(){
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
				return i;
			}
		}
		return null;
	}
}
