package uk.ac.aber.dcs.cs12420.aberpizza.tests;


import static org.junit.Assert.*;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

import uk.ac.aber.dcs.cs12420.aberpizza.data.Item;
import uk.ac.aber.dcs.cs12420.aberpizza.data.ItemDrink;
import uk.ac.aber.dcs.cs12420.aberpizza.data.ItemPizza;
import uk.ac.aber.dcs.cs12420.aberpizza.data.ItemSide;
import uk.ac.aber.dcs.cs12420.aberpizza.data.PizzaSizeEnum;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Till;

public class ItemsTest {
	private Till t;
	private Till l;
	private Item i1 = new ItemPizza(new BigDecimal("9.99"),"Tasty Pizza",PizzaSizeEnum.UNSET),
			i2 = new ItemSide(new BigDecimal("1.99"),"Chips"),
			i3 = new ItemDrink(new BigDecimal("0.60"),"Cola");
	private File configFile = new File(System.getProperty("user.dir")+
			System.getProperty("file.separator")+"testConfig.xml");
	private Item findItemByName(ArrayList<Item> a, Item b){
		Item returnVal = null;
		for(Item i: a){
			if(i.getDescription().trim().equals(b.getDescription().trim())){
				returnVal = i;
			}
		}
		return returnVal;
	}
	@Before
	public void setup(){
		t = new Till(configFile);
		l = new Till(configFile);
		t.addItem(i1);
		t.addItem(i2);
		t.addItem(i3);
		t.saveItems();
		l.loadItems();
	}
	@Test
	public void testComparePizza(){
		Item b = findItemByName(l.getItems(),i1);
		if(b==null){
			fail("Pizza not found");
		}
		assertEquals(i1.getPrice(),b.getPrice());
		assertEquals(((ItemPizza) i1).getSize(),((ItemPizza) b).getSize());
	}
	@Test
	public void testCompareSide(){
		Item b = findItemByName(l.getItems(),i2);
		if(b==null){
			fail("Side not found");
		}
		assertEquals(i2.getPrice(),b.getPrice());
	}
	@Test
	public void testCompareDrink(){
		Item b = findItemByName(l.getItems(),i3);
		if(b==null){
			fail("Drink not found");
		}
		assertEquals(i3.getPrice(),b.getPrice());
	}
	@Test
	public void testSetGetPrice(){
		i2.setPrice(new BigDecimal("2.43"));
		assertEquals(i2.getPrice(),new BigDecimal("2.43"));
		i2.setPrice(new BigDecimal("2.567"));
		assertEquals(i2.getPrice(),new BigDecimal("2.57"));
	}
	@Test
	public void testEquals(){
		Item i4 = new ItemSide(new BigDecimal("1.99"),"Chips");
		if(i2.equals(i4)==false){
			fail("items not equal");
		}
		if(i2.equals(i3)){
			fail("items not meant to be equal");
		}
	}
	@Test
	public void testPizzaSize(){
		ItemPizza u = (ItemPizza)i1;
		u.setSize(PizzaSizeEnum.SMALL);
		assertEquals(u.getSize(),PizzaSizeEnum.SMALL);
	}

}
