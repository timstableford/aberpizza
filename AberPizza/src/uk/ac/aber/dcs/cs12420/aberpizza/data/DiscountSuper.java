package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.math.BigDecimal;
import java.util.ArrayList;

public abstract class DiscountSuper implements Discount{
	protected ArrayList<OrderItem> items = new ArrayList<OrderItem>();
	protected BigDecimal discount;
	//public abstract BigDecimal getDiscount(Order o);
	public DiscountSuper(){ }
	public DiscountSuper(ArrayList<OrderItem> i, BigDecimal d){
		items = i;
		discount = d;
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
	public boolean discountApplies(ArrayList<OrderItem> i){
		boolean returnVal = true;
		for(OrderItem j: i){
			boolean setVal = false;
			for(OrderItem k: items){
				if(k.equals(j)){
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
