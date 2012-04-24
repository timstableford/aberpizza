package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.math.BigDecimal;
import java.util.ArrayList;
/**
 * Interface to define discounts
 * @author tim
 *
 */
public interface Discount {
		public void addItem(OrderItem i);
		public void setItems(ArrayList<OrderItem> i);
		public ArrayList<OrderItem> getItems();
		public void setDiscount(BigDecimal p);
		public BigDecimal getDiscount();
		public boolean discountApplies(Order i);
		public BigDecimal getDiscount(Order o);
		public boolean equals(Discount d);
		public String getDescription();
		public void setDescription(String s);
		public OrderItem findItem(String desc);
}
