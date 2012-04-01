package uk.ac.aber.dcs.cs12420.aberpizza.tests;


import static org.junit.Assert.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

import uk.ac.aber.dcs.cs12420.aberpizza.data.Item;
import uk.ac.aber.dcs.cs12420.aberpizza.data.ItemDrink;
import uk.ac.aber.dcs.cs12420.aberpizza.data.ItemPizza;
import uk.ac.aber.dcs.cs12420.aberpizza.data.ItemSide;
import uk.ac.aber.dcs.cs12420.aberpizza.data.PizzaEnum;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Till;

public class Items {
	private Till t;
	private Till l;
	private Item i1 = new ItemPizza(new BigDecimal("9.99"),"Tasty Pizza",PizzaEnum.LARGE),
			i2 = new ItemSide(new BigDecimal("1.99"),"Chips"),
			i3 = new ItemDrink(new BigDecimal("0.60"),"Cola");
	private Item findItemByName(ArrayList<Item> a, Item b){
		Item returnVal = null;
		for(Item i: a){
			if(i.getDescription().equals(b.getDescription())){
				returnVal = i;
			}
		}
		return returnVal;
	}
	@Before
	public void setup(){
		t = new Till();
		l = new Till();
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
	public void testDrink(){
		Item b = findItemByName(l.getItems(),i3);
		if(b==null){
			fail("Drink not found");
		}
		assertEquals(i3.getPrice(),b.getPrice());
	}

}
