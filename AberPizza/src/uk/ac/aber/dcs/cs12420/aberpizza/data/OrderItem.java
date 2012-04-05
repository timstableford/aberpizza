package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.math.BigDecimal;

public class OrderItem {
	private int quantity;
	private Item item;
	public OrderItem(Item item, int quantity){
		this.quantity = quantity;
		this.item = item;
	}
	public OrderItem(){};
	public void setItem(Item i){
		item = i;
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
		return item.getOrderString()+" - "+quantity+" - £"+item.getPrice().multiply(new BigDecimal(quantity));
	}
	public boolean equals(OrderItem i){
		boolean returnVal = true;
		if(!i.getItem().equals(item)||i.getQuantity()!=quantity){
			returnVal = false;
		}
		return returnVal;
	}
	public boolean disApplies(OrderItem i){
		boolean returnVal = true;
		if(!i.getItem().equals(item)||i.getQuantity()<quantity){
			returnVal = false;
		}
		return returnVal;
	}
}
