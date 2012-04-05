package uk.ac.aber.dcs.cs12420.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class MainPanel extends JPanel{
	private static final long serialVersionUID = -981214312750360842L;
	private JTextField customerName;
	private JList itemList, orderList;
	private MainListener mainListener;
	private JLabel disLabel, subtotalLabel, totalLabel;
	private MainFrame mainFrame;
	public MainPanel(MainFrame mF){
		mainFrame = mF;
		mainListener = new MainListener(mainFrame);
		//the main panel
		this.setLayout(new GridBagLayout());
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
		this.add(pizza,c);
		//side button
		c.gridx = 1;
		this.add(side,c);
		//drink button
		c.gridx = 2;
		this.add(drink,c);
		//le list
		c.gridy = 1;
		c.gridx = 0;
		c.gridwidth = 3;
		c.weighty = 1;
		c.gridheight = 4;
		itemList = new JList();
		
		itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane itemScroll = new JScrollPane(itemList);
		itemList.ensureIndexIsVisible(itemList.getSelectedIndex());
		this.add(itemScroll, c);
		c.gridheight = 1;
		c.weighty = 0;
		//add to order button
		c.gridy = 5;
		c.gridwidth = 3;
		this.add(add, c);
		
		//right panel
		JLabel orderLabel = new JLabel("Order for:");
		disLabel = new JLabel("Discounts: £0.00");
		subtotalLabel = new JLabel("Subtotal: £0.00");
		totalLabel = new JLabel("Total: £0.00");
		JButton payButton = new JButton("Pay"), cancelButton = new JButton("Cancel");
		payButton.addActionListener(mainListener);
		cancelButton.addActionListener(mainListener);
		//name label
		c.gridx = 3;
		c.gridy = 0;
		c.gridwidth = 1;
		this.add(orderLabel, c);
		//entry for name
		c.gridx = 4;
		customerName = new JTextField(12);
		this.add(customerName, c);
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
		this.add(orderScroll, c);
		c.weighty = 0;
		//discount label
		c.gridwidth = 1;
		c.gridx = 3;
		c.gridy = 3;
		this.add(disLabel, c);
		//subtotal label
		c.gridy = 4;
		this.add(subtotalLabel, c);
		//total label
		c.gridx = 4;
		this.add(totalLabel, c);
		//pay button
		c.gridy = 5;
		c.gridx = 4;
		this.add(payButton, c);
		//cancel button
		c.gridx = 3;
		this.add(cancelButton, c);
		//quantity things
		JButton qplus = new JButton("Quantity+"),qminus = new JButton("Quantity-");
		qplus.addActionListener(mainListener);
		qminus.addActionListener(mainListener);
		c.gridx = 3;
		c.gridy = 2;
		this.add(qminus, c);
		c.gridx = 4;
		this.add(qplus, c);
	}
	public JList getItemList(){
		return itemList;
	}
	public JList getOrderList(){
		return orderList;
	}
	public void setSubtotal(String t){
		subtotalLabel.setText(t);
	}
	public void setTotal(String t){
		totalLabel.setText(t);
	}
	public void setDiscount(String t){
		disLabel.setText(t);
	}
	public String getSelectedItem(){
		if(itemList.isSelectionEmpty()){ return null; }
		return (String)itemList.getSelectedValue();
	}
	public String getSelectedOrderItem(){
		if(orderList.isSelectionEmpty()){ return null; }
		return (String)orderList.getSelectedValue();
	}
	public int getSelectedOrderItemIndex(){
		if(orderList.isSelectionEmpty()){ return 0; }
		return orderList.getSelectedIndex();
	}
	public void setSelectedOrderItemIndex(int i){
		orderList.setSelectedIndex(i);
	}
	public void setOrderListData(String[] data){
		orderList.setListData(data);
	}
	public void setItemListData(String[] data){
		itemList.setListData(data);
	}
	public String getCustomerName(){
		return customerName.getText().trim();
	}
	public void clearCustomerName(){
		customerName.setText("");
	}
}
