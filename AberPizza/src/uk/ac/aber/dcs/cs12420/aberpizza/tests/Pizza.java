package uk.ac.aber.dcs.cs12420.aberpizza.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import uk.ac.aber.dcs.cs12420.aberpizza.data.ItemPizza;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Order;

public class Pizza {
	private Order order;
	private ItemPizza p;
	@Before
	public void setup(){
		order = new Order();
		p = new ItemPizza();
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
