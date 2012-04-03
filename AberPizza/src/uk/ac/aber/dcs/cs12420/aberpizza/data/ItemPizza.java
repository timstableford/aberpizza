package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.math.BigDecimal;


public class ItemPizza extends ItemSuper implements Cloneable{
	private PizzaSizeEnum size;
	public ItemPizza(BigDecimal price, String description){
		super(price, description);
		this.size = PizzaSizeEnum.LARGE;
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
		return description+" - £"+getPrice()+" - "+size;
	}
	@Override
	public String getOrderString(){
		return description+" - "+size;
	}
	public ItemPizza(){
		
	}
	@Override
	public boolean equals(Item i){
		if(!(i instanceof ItemPizza)){ return false; }
		ItemPizza j = (ItemPizza)i;
		if(j.getDescription().equals(description)&&j.getPrice().equals(price)&&j.getSize()==size){
			return true;
		}else{
			return false;
		}
	}
	@Override
	public BigDecimal getPrice(){
		switch(size){
		case SMALL:
			return price.divide(new BigDecimal("100")).multiply(new BigDecimal("50"));
		case MEDIUM:
			return price.divide(new BigDecimal("100")).multiply(new BigDecimal("80"));
		case LARGE: 
			return price;
		default:
			return price;
		}
	}
}
