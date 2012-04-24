package uk.ac.aber.dcs.cs12420.aberpizza.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import uk.ac.aber.dcs.cs12420.aberpizza.data.Item;
import uk.ac.aber.dcs.cs12420.aberpizza.data.ItemPizza;
import uk.ac.aber.dcs.cs12420.aberpizza.data.OrderItem;
import uk.ac.aber.dcs.cs12420.aberpizza.data.PizzaSizeEnum;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Till;
/**
 * The main gui is created here
 * @author tim
 *
 */
public class MainPanel extends JPanel{
	private static final long serialVersionUID = -981214312750360842L;
	private JTextField customerName;
	private JList itemList, orderList;
	private MainListener mainListener;
	private JLabel disLabel, subtotalLabel, totalLabel;
	private Till till;
	public MainPanel(Till t){
		till = t;
		mainListener = new MainListener(till, this);
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
	/**
	 * finds the item selected and updates its quantity
	 * @param j quantity to change by
	 */
	public void quantityChange(int j){
		if(orderList.isSelectionEmpty()){ return; }
		String data = (String)orderList.getSelectedValue();
		String[] d = data.split("-");
		int i = orderList.getSelectedIndex();
		Item item = till.findItem(d[0].trim());
		if(item instanceof ItemPizza){
			((ItemPizza) item).setSize(PizzaSizeEnum.valueOf(d[1].trim()));
		}
		till.getCurrentOrder().updateItemQuantity(item, j);
		updateOrderList();
		orderList.setSelectedIndex(i);
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
	public void updateItemList(){
		String[] listData = till.itemListToStringArray(till.getItems());
		String[] listBlank = {" "};
		if(till.getItems().size()<1){
			listData = listBlank;
		}
		itemList.setListData(listData);
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
		orderList.setListData(data);
		subtotalLabel.setText("Subtotal: £"+till.getCurrentOrder().getSubtotal());
		disLabel.setText("Discounts: £"+till.getCurrentOrder().getDiscount());
		totalLabel.setText("Total: £"+till.getCurrentOrder().getTotal());
	}
	public Item getSelectedItem(){
		String data = null;
		if(itemList.isSelectionEmpty()){ return null; }
		data = (String)itemList.getSelectedValue();
		String[] d = data.split("-");
		Item i = till.findItem(d[0].trim());
		return i;
	}
}
