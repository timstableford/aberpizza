package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.math.BigDecimal;
import java.util.ArrayList;
/**
 * Discount based on subtracting a set value
 * @author tim
 *
 */
public class DiscountSetValue extends DiscountSuper {
	/**
	 * @param i list of items
	 * @param d amount to subtract
	 * @param n description
	 */
	public DiscountSetValue(ArrayList<OrderItem> i, BigDecimal d, String n){
		super(i,d,n);
	}
	public DiscountSetValue(){}
	public BigDecimal getDiscount(Order o) {
		if(super.discountApplies(o)){
			return discount;
		}else{
			return new BigDecimal("0.00");
		}
	}
	public String toString(){
		return super.getDescription()+" - (Set Value)";
	}

}
