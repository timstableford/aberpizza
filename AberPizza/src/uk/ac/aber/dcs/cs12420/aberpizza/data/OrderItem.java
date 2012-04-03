package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.math.BigDecimal;

public class OrderItem {
	private int quantity;
	private Item item;
	public OrderItem(Item item, int quantity){
		this.quantity = quantity;
		this.item = item;
	}
	public int getQuantity(){
		return quantity;
	}
	public BigDecimal getOrderItemTotal(){
		BigDecimal returnVal = item.getPrice().multiply(new BigDecimal(quantity));
		return returnVal;
		
	}
	public Item getItem(){
		return item;
	}
	public void setQuantity(int i){
		quantity = i;
	}
	public String toString(){
		return item.getOrderString()+" - £"+item.getPrice().multiply(new BigDecimal(quantity));
	}
}
