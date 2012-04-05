package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.math.BigDecimal;
import java.util.ArrayList;

public class DiscountValue extends DiscountSuper {
	public DiscountValue(ArrayList<OrderItem> i, BigDecimal d, boolean e){
		super(i,d,e);
	}
	public DiscountValue(){}
	public BigDecimal getDiscount(Order o) {
		ArrayList<OrderItem> l = o.getOrderItems();
		boolean hasItems = true;
		for(OrderItem i: items){
			for(OrderItem j: l){
				if(!i.getItem().getDescription().equals(j.getItem().getDescription())|
						!(j.getItem().getPrice().doubleValue()>=i.getItem().getPrice().doubleValue())){
					hasItems = false;
				}
			}
		}
		if(hasItems){
			return discount;
		}else{
			return new BigDecimal("0");
		}
	}

}
