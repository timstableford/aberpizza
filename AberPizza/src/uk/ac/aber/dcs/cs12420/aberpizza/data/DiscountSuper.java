package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.math.BigDecimal;
import java.util.ArrayList;
/**
 * Superclass for other discounts
 * @author tim
 * Contains items, discount amount and description
 */
public abstract class DiscountSuper implements Discount{
	protected ArrayList<OrderItem> items = new ArrayList<OrderItem>();
	protected BigDecimal discount;
	private String description = "";
	public DiscountSuper(){ }
	public DiscountSuper(ArrayList<OrderItem> i, BigDecimal d, String n){
		items = i;
		discount = d;
		description = n;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	/**
	 * Checks to see for each item in the discount that the order has at least the same quantity
	 * @return true if the discount applies
	 */
	public boolean discountApplies(Order o){
		boolean returnVal = true;
		ArrayList<OrderItem> i = o.getOrderItems();
		for(OrderItem j: items){
			if(j.getQuantity()>0){
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
		}
		return returnVal;
	}
	public boolean equals(Discount d){
		if(d.getDescription().equals(this.getDescription())){
			return true;
		}else{
			return false;
		}
	}
	public OrderItem findItem(String desc){
		for(OrderItem j: items){
			if(j.getItem().getDescription().equals(desc)){
				return j;
			}
		}
		return null;
	}
}
