package uk.ac.aber.dcs.cs12420.aberpizza.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class Order {
	private Date date;
	private String customerName;
	private ArrayList<OrderItem> items = new ArrayList<OrderItem>();
	public Order(){
		date = new Date();
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public void addItem(Item item, int quantity){
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
			returnVal.add(i.getOrderItemTotal());
		}
		return returnVal;
	}
	public BigDecimal getDiscount(){
		return new BigDecimal("0");
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
