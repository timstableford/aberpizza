package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.math.BigDecimal;


public class ItemPizza extends ItemSuper implements Cloneable{
	private PizzaSizeEnum size;
	public ItemPizza(BigDecimal price, String description){
		super(price, description);
		this.size = PizzaSizeEnum.UNSET;
	}
	public ItemPizza(BigDecimal price, String description, PizzaSizeEnum size){
		super(price, description);
		this.size = size;
	}
	public PizzaSizeEnum getSize(){
		return size;
	}
	public void setSize(PizzaSizeEnum size){
		this.size = size;
	}
	public String toString(){
		return super.toString()+" - "+size.name();
	}
	public ItemPizza(){
		
	}
}
