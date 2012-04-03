package uk.ac.aber.dcs.cs12420.gui;

import java.awt.FlowLayout;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JFrame;

import uk.ac.aber.dcs.cs12420.aberpizza.data.ItemPizza;

public class PizzaSizeFrame extends JFrame{
	private ItemPizza pizza;
	public PizzaSizeFrame(ItemPizza p){
		pizza = p;
		JButton small = new JButton("Small"), medium = new JButton("Medium"), large = new JButton("Large");
		this.setLayout(new FlowLayout());
		this.add(small);
		this.add(medium);
		this.add(large);
	}
	public static void main(String[] args){
		PizzaSizeFrame p = new PizzaSizeFrame(new ItemPizza(new BigDecimal("9.99"),"Cheese Pizza"));
	}
}
