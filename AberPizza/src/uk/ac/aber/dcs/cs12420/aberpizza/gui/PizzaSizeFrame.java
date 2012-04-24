package uk.ac.aber.dcs.cs12420.aberpizza.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import uk.ac.aber.dcs.cs12420.aberpizza.data.ItemPizza;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Order;
import uk.ac.aber.dcs.cs12420.aberpizza.data.PizzaSizeEnum;
/**
 * Shows small medium large button
 * @author tim
 * I realise now that I could have used a joptionchooser instead of this
 */
public class PizzaSizeFrame extends JFrame implements ActionListener{
	private static final long serialVersionUID = -5408487799624675311L;
	private ItemPizza pizza;
	private MainPanel mainPanel;
	private Order order;
	public PizzaSizeFrame(ItemPizza p, MainPanel mP, Order o){
		order = o;
		pizza = p;
		mainPanel = mP;
		JButton small = new JButton("Small"), medium = new JButton("Medium"), large = new JButton("Large");
		this.setLayout(new FlowLayout());
		this.add(small);
		this.add(medium);
		this.add(large);
		this.setAlwaysOnTop(true);
		this.setTitle("Set Pizza Size");
		small.addActionListener(this);
		medium.addActionListener(this);
		large.addActionListener(this);
		this.setLocationRelativeTo(mainPanel);
		this.pack();
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		if("Small".equals(actionCommand)){
			setSize(PizzaSizeEnum.SMALL);
		}else if("Medium".equals(actionCommand)){
			setSize(PizzaSizeEnum.MEDIUM);
		}else if("Large".equals(actionCommand)){
			setSize(PizzaSizeEnum.LARGE);
		}
	}
	public void setSize(PizzaSizeEnum size){
		pizza.setSize(size);
		mainPanel.updateOrderList();
		order.addItem(pizza, 1);
		mainPanel.updateOrderList();
		this.dispose();
	}
}
