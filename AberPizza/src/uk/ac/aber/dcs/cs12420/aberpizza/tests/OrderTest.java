package uk.ac.aber.dcs.cs12420.aberpizza.tests;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import uk.ac.aber.dcs.cs12420.aberpizza.data.*;

import org.junit.Before;
import org.junit.Test;
/**
 * tests creating an order and adding a discount to it
 * @author tim
 *
 */
public class OrderTest {
	private Order order;
	private BigDecimal vat = new BigDecimal("20");
	private ArrayList<Discount> discount;
	private ArrayList<Item> items;
	/**
	 * sets up a discount, order and item list, propagates item list, also tests orderitem
	 */
	@Before
	public void setup(){
		discount = new ArrayList<Discount>();
		order = new Order(discount, vat);
		items = new ArrayList<Item>();
		items.add(new ItemPizza(new BigDecimal("9.99"),"Cheese Pizza",PizzaSizeEnum.LARGE));
		items.add(new ItemSide(new BigDecimal("0.80"),"Garlic Bread"));
		items.add(new ItemDrink(new BigDecimal("0.80"),"Cola"));
	}
	/**
	 * adds item to order and checks
	 */
	@Test
	public void testAddItemToOrderAndCheck() {
		order.addItem(items.get(0), 2);
		if(!order.getItems().get(0).equals(new OrderItem(items.get(0),2))){
			fail("Item added is not what we get back");
		}
	}
	/**
	 * change orderitem quantity and check
	 */
	@Test
	public void testItemQuantityChange(){
		order.addItem(items.get(1), 1);
		order.updateItemQuantity(items.get(1),2);
		if(order.getItems().get(0).getQuantity()!=3){
			fail("Not the correct number of items");
		}
		order.updateItemQuantity(items.get(1),-3);
		try{
			order.getItems().get(0);
			fail("Item did not get removed");
		}catch(IndexOutOfBoundsException e){
			//this means it passed
		}
	}
	/**
	 * check that subtotals and vat are right
	 */
	@Test
	public void checkTotalsSubtotalsAndVat(){
		order.addItem(items.get(0),2);
		order.addItem(items.get(1),1);
		if(order.getSubtotal().compareTo(new BigDecimal("20.78"))!=0){
			fail("subtotal is wrong");
		}
		if(order.getVAT().compareTo(new BigDecimal("4.16"))!=0){
			fail("vat is wrong");
		}
		if(order.getTotal().compareTo(new BigDecimal("24.94"))!=0){
			fail("total is wrong");
		}
	}
	/**
	 * tests to make sure the correct discount is applies for the correct amount
	 */
	@Test
	public void testGetDiscount(){
		ArrayList<OrderItem> d1l = new ArrayList<OrderItem>();
		d1l.add(new OrderItem(items.get(2),2));
		Discount d1 = new DiscountSetValue(d1l, new BigDecimal("0.50"),"50p off 2 cola");
		discount.add(d1);
		order.addItem(items.get(2), 10);
		order.addItem(items.get(0), 3);
		if(order.getDiscount().compareTo(new BigDecimal("0.50"))!=0){
			fail("Discount not applied");
		}
		ArrayList<OrderItem> d2l = new ArrayList<OrderItem>();
		d2l.add(new OrderItem(items.get(0),2));
		Discount d2 = new DiscountSetPercent(d2l, new BigDecimal("90"),"90% off 2 medium cheese pizza");
		discount.add(d2);
		if(order.getDiscount().compareTo(new BigDecimal("34.17"))!=0){
			fail("Wrong or no discount applied");
		}
	}
}
