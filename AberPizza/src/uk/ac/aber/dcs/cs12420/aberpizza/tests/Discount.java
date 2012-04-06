package uk.ac.aber.dcs.cs12420.aberpizza.tests;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import uk.ac.aber.dcs.cs12420.aberpizza.data.DiscountSetValue;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Item;
import uk.ac.aber.dcs.cs12420.aberpizza.data.ItemSide;
import uk.ac.aber.dcs.cs12420.aberpizza.data.OrderItem;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Till;

public class Discount extends Till {
	private Till till,till2;
	private DiscountSetValue d1;
	@Before
	public void setup(){
		till = new Till();
		till2 = new Till();
	}
	@Test
	public void addDiscounts(){
		ArrayList<OrderItem> i = new ArrayList<OrderItem>();
		Item i1 = new ItemSide(new BigDecimal("1.99"), "Chips");
		i.add(new OrderItem(i1, 3));
		d1 = new DiscountSetValue(i, new BigDecimal("0.50"),true);
		till.addDiscount(d1);
		till.saveDiscounts("/home/tim/etc/discounts.xml");
		System.out.println(till.getDiscounts());
	}
	@Test
	public void loadAndCompareDiscounts(){
		till2.loadDiscounts("/home/tim/etc/discounts.xml");
		System.out.println(till2.getDiscounts());
	}
}
