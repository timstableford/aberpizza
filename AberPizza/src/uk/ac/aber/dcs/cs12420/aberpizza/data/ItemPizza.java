package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.math.BigDecimal;

public class ItemPizza extends ItemSuper {
	private PizzaEnum size;
	public ItemPizza(BigDecimal price, String description, PizzaEnum size){
		super(price, description);
		this.size = size;
	}
	public PizzaEnum getSize(){
		return size;
	}
	public void setSize(PizzaEnum size){
		this.size = size;
	}
}
