package uk.ac.aber.dcs.cs12420.aberpizza.tests;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import uk.ac.aber.dcs.cs12420.aberpizza.data.DiscountPercent;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Item;
import uk.ac.aber.dcs.cs12420.aberpizza.data.ItemSide;
import uk.ac.aber.dcs.cs12420.aberpizza.data.OrderItem;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Till;

public class Discount extends Till {
	private Till till;
	
	@Before
	public void setup(){
		till = new Till();
	}
	@Test
	public void addDiscounts(){
		ArrayList<OrderItem> i = new ArrayList<OrderItem>();
		Item i1 = new ItemSide();
		i.add(new OrderItem(i1, 3));
		Discount d1 = (Discount)(new DiscountPercent());
	}
}
