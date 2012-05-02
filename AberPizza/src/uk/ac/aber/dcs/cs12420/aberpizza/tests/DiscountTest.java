package uk.ac.aber.dcs.cs12420.aberpizza.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import uk.ac.aber.dcs.cs12420.aberpizza.data.DiscountSetValue;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Item;
import uk.ac.aber.dcs.cs12420.aberpizza.data.ItemSide;
import uk.ac.aber.dcs.cs12420.aberpizza.data.OrderItem;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Till;
/**
 * tests loading and saving discounts, more tests in order test
 * @author tim
 *
 */
public class DiscountTest {
	private Till till,till2;
	private DiscountSetValue d1;
	private File configFile = new File(System.getProperty("user.dir")+
			System.getProperty("file.separator")+"testConfig.xml");
	@Before
	public void setup(){
		ArrayList<OrderItem> i = new ArrayList<OrderItem>();
		Item i1 = new ItemSide(new BigDecimal("1.99"), "Chips");
		i.add(new OrderItem(i1, 3));
		d1 = new DiscountSetValue(i, new BigDecimal("0.50"),"50p Discount off 3 or more chips");
		till = new Till(configFile);
		till.addDiscount(d1);
		till.saveDiscounts();
	}

	@Test
	public void loadAndCompareDiscounts(){
		till2 = new Till(configFile);
		till2.loadDiscounts();
		if(!till2.getDiscounts().get(0).equals(d1)){
			fail();
		}
	}
}
