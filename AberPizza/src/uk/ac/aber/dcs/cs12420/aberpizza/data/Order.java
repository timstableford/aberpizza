package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Order {
	private Date date;
	private String customerName;
	private Till till;
	private ArrayList<OrderItem> items = new ArrayList<OrderItem>();
	public Order(Till t){
		date = new Date();
		till = t;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public void addItem(Item item, int quantity){
		for(OrderItem i: items){
			if(i.getItem().equals(item)){
				i.setQuantity(i.getQuantity()+1);
				return;
			}
		}
		OrderItem o = new OrderItem(item, quantity);
		items.add(o);
	}
	public Item getClone(Item i){
		try {
			Item d = (Item)(i.clone());
			return d;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
	public void updateItemQuantity(OrderItem item, int quantity){
		item.setQuantity(quantity);
	}
	public ArrayList<OrderItem> getOrderItems(){
		return items;
	}
	public BigDecimal getSubtotal(){
		BigDecimal returnVal = new BigDecimal("0.00");
		for(OrderItem i: items){
			returnVal = returnVal.add(i.getOrderItemTotal());
		}
		return returnVal;
	}
	public BigDecimal getDiscount(){
		ArrayList<BigDecimal> discountsThatApply = new ArrayList<BigDecimal>();
		for(DiscountSuper d: till.getDiscounts()){
			if(d.discountApplies(items)){
				discountsThatApply.add(d.getDiscount());
			}
		}
		Collections.sort(discountsThatApply);
		System.out.println(discountsThatApply);
		return new BigDecimal(0);
	}
	public String getReceipt(){
		return "";
	}
	public Date getDate(){
		return date;
	}
	public void setDate(Date date){
		this.date = date;
	}
	public void printItems(){
		for(OrderItem i: items){
			System.out.println(i);
		}
	}
}
