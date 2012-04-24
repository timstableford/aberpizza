package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.math.BigDecimal;

/**
 * The same as item super except for implementing size
 * @author tim
 * Pizza price is calculated according to a percentage of the price of a large pizza
 * Percentages are specified in here
 */
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
		if(size==null){
			size = PizzaSizeEnum.UNSET;
		}
		return size;
	}
	public void setSize(PizzaSizeEnum size){
		this.size = size;
	}
	public String toString(){
		return description+" - £"+getPrice();
	}
	@Override
	public String getOrderString(){
		return description+" - "+size;
	}
	public ItemPizza(){
		this.size = PizzaSizeEnum.UNSET;
	}
	@Override
	public boolean equals(Item i){
		if(!(i instanceof ItemPizza)){ return false; }
		ItemPizza j = (ItemPizza)i;
		if(j.getDescription().equals(description)&&j.getSize()==size){
			return true;
		}else{
			return false;
		}
	}
	@Override
	public BigDecimal getPrice(){
		BigDecimal returnPrice = new BigDecimal("0.00");
		if(size==null){ return price; }
		switch(this.size){
		case SMALL:
			returnPrice = price.divide(new BigDecimal("100")).multiply(new BigDecimal("50"));
			returnPrice = returnPrice.setScale(2, BigDecimal.ROUND_HALF_EVEN);
			return returnPrice;
		case MEDIUM:
			returnPrice = price.divide(new BigDecimal("100")).multiply(new BigDecimal("80"));
			returnPrice = returnPrice.setScale(2, BigDecimal.ROUND_HALF_EVEN);
			return returnPrice;
		case LARGE: 
			return price;
		default:
			return price;
		}
	}
}
