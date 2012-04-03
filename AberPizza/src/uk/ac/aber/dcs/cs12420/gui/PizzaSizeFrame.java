package uk.ac.aber.dcs.cs12420.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import uk.ac.aber.dcs.cs12420.aberpizza.data.ItemPizza;
import uk.ac.aber.dcs.cs12420.aberpizza.data.PizzaSizeEnum;

public class PizzaSizeFrame extends JFrame implements ActionListener{
	private static final long serialVersionUID = -5408487799624675311L;
	private ItemPizza pizza;
	private MainFrame mainFrame;
	public PizzaSizeFrame(ItemPizza p, MainFrame mF){
		pizza = p;
		mainFrame = mF;
		JButton small = new JButton("Small"), medium = new JButton("Medium"), large = new JButton("Large");
		this.setLayout(new FlowLayout());
		this.add(small);
		this.add(medium);
		this.add(large);
		small.addActionListener(this);
		medium.addActionListener(this);
		large.addActionListener(this);
		this.pack();
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
		mainFrame.updateOrderList();
		this.dispose();
	}
}
