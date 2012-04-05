package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.math.BigDecimal;
import java.util.ArrayList;

public class DiscountSetPercent extends DiscountSuper{
	public DiscountSetPercent(ArrayList<OrderItem> i, BigDecimal d, boolean e){
		super(i, d, e);
	}
	public DiscountSetPercent(){}
	public BigDecimal getDiscount(Order o) {
		ArrayList<OrderItem> l = o.getOrderItems();
		BigDecimal sub = o.getSubtotal();
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
			return sub.divide(new BigDecimal("100")).multiply(discount);
		}else{
			return new BigDecimal("0");
		}
	}

}
