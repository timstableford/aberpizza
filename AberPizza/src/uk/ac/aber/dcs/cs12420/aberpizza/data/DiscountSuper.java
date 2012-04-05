package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.math.BigDecimal;
import java.util.ArrayList;

public abstract class DiscountSuper implements Discount{
	protected ArrayList<OrderItem> items = new ArrayList<OrderItem>();
	protected BigDecimal discount;
	protected boolean enabled = true;
	public DiscountSuper(){ }
	public DiscountSuper(ArrayList<OrderItem> i, BigDecimal d, boolean enabled){
		items = i;
		discount = d;
		this.enabled = enabled;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public void addItem(OrderItem i){
		items.add(i);
	}
	public void setItems(ArrayList<OrderItem> i){
		items = i;
	}
	public ArrayList<OrderItem> getItems(){
		return items;
	}
	public void setDiscount(BigDecimal p){
		discount = p;
	}
	public BigDecimal getDiscount(){
		return discount;
	}
	public boolean discountApplies(Order o){
		if(!enabled){ return false; }
		boolean returnVal = true;
		ArrayList<OrderItem> i = o.getOrderItems();
		for(OrderItem j: items){
			boolean setVal = false;
			for(OrderItem k: i){
				if(j.disApplies(k)){
					setVal = true;
				}
			}
			if(setVal==false){
				returnVal = false;
			}
		}
		return returnVal;
	}
}
