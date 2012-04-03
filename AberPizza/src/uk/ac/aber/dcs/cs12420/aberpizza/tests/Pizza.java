package uk.ac.aber.dcs.cs12420.aberpizza.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import uk.ac.aber.dcs.cs12420.aberpizza.data.ItemPizza;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Order;
import uk.ac.aber.dcs.cs12420.aberpizza.data.Till;

public class Pizza {
	private Order order;
	private ItemPizza p;
	private Till till;
	@Before
	public void setup(){
		till = new Till();
		order = new Order(till);
		p = new ItemPizza();
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
